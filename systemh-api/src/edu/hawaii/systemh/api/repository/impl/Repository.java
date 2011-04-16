package edu.hawaii.systemh.api.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;
import edu.hawaii.systemh.api.ApiDictionary.SystemHRoom;
import edu.hawaii.systemh.api.ApiDictionary.SystemHState;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.SystemHRepository;
import edu.hawaii.systemh.api.repository.SystemStateListener;
import edu.hawaii.systemh.api.repository.SystemStatusMessage;
import edu.hawaii.systemh.api.repository.SystemStatusMessageListener;
import edu.hawaii.systemh.api.repository.TimestampBooleanPair;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.api.repository.TimestampIntegerPair;
import edu.hawaii.systemh.api.repository.TimestampStringPair;

/**
 * An implementation of the SystemH repository using the BerkeleyDB system. Repository instances are
 * thread safe. You can create as many of these as you want.
 * 
 * @author Philip Johnson
 */
public class Repository implements SystemHRepository {

  /** The System property that the user can set to override the default repo directory name. */
  public static final String repositoryDirectoryPropertyKey = "SystemH.RepositoryDirectoryName";

  /** Maps System+State to its corresponding EntityStore. */
  private static Map<String, EntityStore> stores = new ConcurrentHashMap<String, EntityStore>();
  /** Maps System+State to its corresponding PrimaryIndex. */
  private static Map<String, PrimaryIndex<Long, StateEntry>> indices =
      new ConcurrentHashMap<String, PrimaryIndex<Long, StateEntry>>();

  /** The entity store name for system status messages. */
  private static String messageStoreName = "SystemStatusMessageStore";
  /** The PrimaryIndex for storing messages. */
  private static PrimaryIndex<Long, SystemStatusMessageEntry> messageStoreIndex;

  /** The thread safe data structures that store the listeners for this repository. */
  private static Map<SystemHSystem, List<SystemStateListener>> stateListeners =
      new ConcurrentHashMap<SystemHSystem, List<SystemStateListener>>();
  private static List<SystemStatusMessageListener> statusListeners =
      new CopyOnWriteArrayList<SystemStatusMessageListener>();

  /**
   * Defines a new EntityStore, PrimaryIndex, and Cursor for a specified (System, State) pair. Used
   * only in the static block during initialization.
   * 
   * @param env The Environment for this repository.
   * @param storeConfig The configuration for all stores.
   * @param system The system.
   * @param state The state.
   */
  private static void defineStore(Environment env, StoreConfig storeConfig, SystemHSystem system,
      SystemHState state) {
    // Call the more generic defineStore passing null for room.
    defineStore(env, storeConfig, system, null, state);
  }

  /**
   * Defines a new EntityStore and PrimaryIndex for a specified (Room, State) pair. Used only in the
   * static block during initialization.
   * 
   * @param env The Environment for this repository.
   * @param storeConfig The configuration for all stores.
   * @param room The room. (Implies that the system is Lighting).
   * @param state The state.
   */
  private static void defineStore(Environment env, StoreConfig storeConfig, SystemHRoom room,
      SystemHState state) {
    // Call the more generic defineStore passing null for system.
    defineStore(env, storeConfig, null, room, state);
  }

  /**
   * Defines a new EntityStore and PrimaryIndex for a specified (System, Room, State) pair. Used
   * only in the static block during initialization.
   * 
   * @param env The Environment for this repository.
   * @param storeConfig The configuration for all stores.
   * @param system The system (optional, can be null when room is supplied).
   * @param room The room (optional, can be null, when used implies that system is Lighting).
   * @param state The state.
   */
  private static void defineStore(Environment env, StoreConfig storeConfig, SystemHSystem system,
      SystemHRoom room, SystemHState state) {
    String storeName = makeStoreName(system, room, state);
    EntityStore store = new EntityStore(env, storeName, storeConfig);
    PrimaryIndex<Long, StateEntry> index = store.getPrimaryIndex(Long.class, StateEntry.class);
    stores.put(storeName, store);
    indices.put(storeName, index);
  }

  /**
   * Defines a new EntityStore and PrimaryIndex for SystemStatusMessageEntry. Used only in the
   * static block during initialization.
   * 
   * @param env The Environment for this repository.
   * @param storeConfig The configuration for all stores.
   */
  private static void defineMessageStore(Environment env, StoreConfig storeConfig) {
    EntityStore store = new EntityStore(env, messageStoreName, storeConfig);
    messageStoreIndex = store.getPrimaryIndex(Long.class, SystemStatusMessageEntry.class);
    // Store this here so that the DbShutdownHook will close it.
    stores.put(messageStoreName, store);
  }

  /**
   * Creates a string representing a unique EntityStore name from the system and state variables.
   * 
   * @param system The system.
   * @param room The room (optional, can be null).
   * @param state The state.
   * @return A string representing the EntityStore name.
   */
  private static String makeStoreName(SystemHSystem system, SystemHRoom room, SystemHState state) {
    // return either <system>_<state> or Lighting_<room>_<state>
    return (room == null) ? system.toString() + "_" + state.toString() : SystemHSystem.LIGHTING
        .toString() + "_" + room.toString() + "_" + state.toString();
  }

  /** Initialize the static variables at class load time to ensure we do it only once. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    // Allow user to override repository name by setting this variable.
    String repoDirName = System.getProperty(repositoryDirectoryPropertyKey, "SystemHRepository");
    File dir = new File(currDir, repoDirName);
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the SystemH repository directory: " + dir);
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir, envConfig);
    // Create a separate repository store for each (System, State) combination.

    // Aquaponics state variables.
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.CIRCULATION);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.DEAD_FISH);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.ELECTRICAL_CONDUCTIVITY);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.NUTRIENTS);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.OXYGEN);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.PH);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.TEMPERATURE);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.TURBIDITY);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.WATER_LEVEL);

    // Aquaponics commands.
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.FEED_FISH_COMMAND);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.HARVEST_FISH_COMMAND);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.SET_NUTRIENTS_COMMAND);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.SET_PH_COMMAND);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.SET_TEMPERATURE_COMMAND);
    defineStore(env, storeConfig, SystemHSystem.AQUAPONICS, SystemHState.SET_WATER_LEVEL_COMMAND);

    // HVAC state and commands.
    defineStore(env, storeConfig, SystemHSystem.HVAC, SystemHState.TEMPERATURE);
    defineStore(env, storeConfig, SystemHSystem.HVAC, SystemHState.SET_TEMPERATURE_COMMAND);

    // PV and ELECTRIC state (no commands)
    defineStore(env, storeConfig, SystemHSystem.PHOTOVOLTAIC, SystemHState.POWER);
    defineStore(env, storeConfig, SystemHSystem.PHOTOVOLTAIC, SystemHState.ENERGY);
    defineStore(env, storeConfig, SystemHSystem.ELECTRIC, SystemHState.POWER);
    defineStore(env, storeConfig, SystemHSystem.ELECTRIC, SystemHState.ENERGY);

    // Lighting state and commands (by room)
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.LIGHTING_COLOR);
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.BATHROOM, SystemHState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.LIGHTING_COLOR);
    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.LIVING, SystemHState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.LIGHTING_COLOR);
    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.DINING, SystemHState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.LIGHTING_COLOR);
    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_ENABLED_COMMAND);

    // Create the EntityStore and Index for the SystemStatusMessageStore.
    defineMessageStore(env, storeConfig);

    // Guarantee that all EntityStores will be closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, stores.values());
    Runtime.getRuntime().addShutdownHook(shutdownHook);

    // Initialize System State Listeners with all possible keys for the listener map.
    for (SystemHSystem system : SystemHSystem.values()) {
      stateListeners.put(system, new ArrayList<SystemStateListener>());
    }
  }

  /**
   * Returns the primary index associated with the store associated with (system, state).
   * 
   * @param system The system. Could be null if room is supplied (then it's Lighting).
   * @param room The room. Will be null except when system is Lighting.
   * @param state The state.
   * @return The primary index for this (system, state) pair.
   */
  private PrimaryIndex<Long, StateEntry> getIndex(SystemHSystem system, SystemHRoom room,
      SystemHState state) {
    return indices.get(makeStoreName(system, room, state));
  }

  /**
   * Creates a new EntityCursor for the EntityStore associated with the given system and state.
   * 
   * @param system The system.
   * @param room The room. Will be null except when System is Lighting.
   * @param state The state.
   * @return An EntityCursor for this (system, state) pair.
   */
  private EntityCursor<StateEntry> makeCursor(SystemHSystem system, SystemHRoom room
      , SystemHState state) {
    return getIndex(system, room, state).entities();
  }

  /**
   * Creates a new EntityCursor for the EntityStore associated with the given system and state and
   * range from the since value to the present, inclusive.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state.
   * @param startTime A long indicating the start for this cursor.
   * @return An EntityCursor for this (system, state, startTime) tuple.
   */
  private EntityCursor<StateEntry> makeCursorSince(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime) {
    Long currTime = (new Date()).getTime();
    return getIndex(system, room, state).entities(startTime, true, currTime, true);
  }

  /**
   * Creates a new EntityCursor for the EntityStore associated with the given system and state and
   * range from the given time interval, inclusive.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state.
   * @param startTime A long indicating the start for this cursor.
   * @param endTime A long indicating the end for this cursor.
   * @return An EntityCursor for this system, state, startTime, endTime) tuple.
   */
  private EntityCursor<StateEntry> makeCursorDuringInterval(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime, Long endTime) {
    return getIndex(system, room, state).entities(startTime, true, endTime, true);
  }

  /**
   * Returns the (timestamp, double) pair representing the most recently stored value associated
   * with the system, room (if the system is lighting, otherwise ignored), and state variable.
   * 
   * @param system The system.
   * @param room The room (ignored unless system is Lighting)
   * @param state The state variable.
   * @return The (timestamp, double) pair or null if there is no stored data associated with these
   * parameters or if an error occurs retrieving the data.
   */
  private TimestampDoublePair getDoubleStateValue(SystemHSystem system, SystemHRoom room,
      SystemHState state) {
    EntityCursor<StateEntry> cursor = null;
    StateEntry entry;
    try {
      cursor = makeCursor(system, room, state);
      entry = cursor.last();
      return new TimestampDoublePair(entry.getTimestamp(), entry.getDoubleValue());
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, double) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to the present.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @return The list of data representing change in state over time or null if no data or if an
   * error occurs retrieving the data.
   */
  private List<TimestampDoublePair> getDoubleStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime) {
    List<TimestampDoublePair> pairs = new ArrayList<TimestampDoublePair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorSince(system, room, state, startTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampDoublePair(entry.getTimestamp(), entry.getDoubleValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, double) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to endTime.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @param endTime The end time.
   * @return The list of data representing change in state over time or null if no data or if an
   * error occurs retrieving the data.
   */
  private List<TimestampDoublePair> getDoubleStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime, Long endTime) {
    List<TimestampDoublePair> pairs = new ArrayList<TimestampDoublePair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorDuringInterval(system, room, state, startTime, endTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampDoublePair(entry.getTimestamp(), entry.getDoubleValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns the (timestamp, integer) pair representing the most recently stored value associated
   * with the system, room (if the system is lighting, otherwise ignored), and state variable.
   * 
   * @param system The system.
   * @param room The room (ignored unless system is Lighting)
   * @param state The state variable.
   * @return The (timestamp, integer) pair or null if there is no stored data associated with these
   * parameters or if an error occurs retrieving the data.
   */
  private TimestampIntegerPair getIntegerStateValue(SystemHSystem system, SystemHRoom room,
      SystemHState state) {
    EntityCursor<StateEntry> cursor = null;
    StateEntry entry;
    try {
      cursor = makeCursor(system, room, state);
      entry = cursor.last();
      return new TimestampIntegerPair(entry.getTimestamp(), entry.getLongValue().intValue());
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, double) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to the present.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @return The list of data representing change in state over time or null if no data or if an
   * error occurs retrieving the data.
   */
  private List<TimestampIntegerPair> getIntegerStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime) {
    List<TimestampIntegerPair> pairs = new ArrayList<TimestampIntegerPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorSince(system, room, state, startTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampIntegerPair(entry.getTimestamp(), entry.getLongValue().intValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, double) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to end time.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @param endTime The end time.
   * @return The list of data representing change in state over time or null if no data or if an
   * error occurs retrieving the data.
   */
  private List<TimestampIntegerPair> getIntegerStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime, Long endTime) {
    List<TimestampIntegerPair> pairs = new ArrayList<TimestampIntegerPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorDuringInterval(system, room, state, startTime, endTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampIntegerPair(entry.getTimestamp(), entry.getLongValue().intValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns the (timestamp, string) pair representing the most recently stored value associated
   * with the system, room (if the system is lighting, otherwise ignored), and state variable.
   * 
   * @param system The system.
   * @param room The room (ignored unless system is Lighting)
   * @param state The state variable.
   * @return The (timestamp, string) pair or null if there is no stored data associated with these
   * parameters or if an error occurs retrieving the data.
   */
  private TimestampStringPair getStringStateValue(SystemHSystem system, SystemHRoom room,
      SystemHState state) {
    EntityCursor<StateEntry> cursor = null;
    StateEntry entry;
    try {
      cursor = makeCursor(system, room, state);
      entry = cursor.last();
      return new TimestampStringPair(entry.getTimestamp(), entry.getStringValue());
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, string) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to the present.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @return The list of data representing change in state over time or null if there is not data or
   * if an error occurs retrieving the data.
   */
  private List<TimestampStringPair> getStringStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime) {
    List<TimestampStringPair> pairs = new ArrayList<TimestampStringPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorSince(system, room, state, startTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampStringPair(entry.getTimestamp(), entry.getStringValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, string) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to end time.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @param endTime The end time.
   * @return The list of data representing change in state over time or null if there is not data or
   * if an error occurs retrieving the data.
   */
  private List<TimestampStringPair> getStringStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime, Long endTime) {
    List<TimestampStringPair> pairs = new ArrayList<TimestampStringPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorDuringInterval(system, room, state, startTime, endTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampStringPair(entry.getTimestamp(), entry.getStringValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns the (timestamp, boolean) pair representing the most recently stored value associated
   * with the system, room (if the system is lighting, otherwise ignored), and state variable.
   * 
   * @param system The system.
   * @param room The room (ignored unless system is Lighting)
   * @param state The state variable.
   * @return The (timestamp, boolean) pair or null if there is no stored data associated with these
   * parameters or if an error occurs retrieving the data.
   */
  private TimestampBooleanPair getBooleanStateValue(SystemHSystem system, SystemHRoom room,
      SystemHState state) {
    EntityCursor<StateEntry> cursor = null;
    StateEntry entry;
    try {
      cursor = makeCursor(system, room, state);
      entry = cursor.last();
      return new TimestampBooleanPair(entry.getTimestamp(), entry.getBooleanValue());
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, boolean) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to the present.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @return The list of data representing change in state over time or null if there is not data or
   * if an error occurs retrieving the data.
   */
  private List<TimestampBooleanPair> getBooleanStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime) {
    List<TimestampBooleanPair> pairs = new ArrayList<TimestampBooleanPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorSince(system, room, state, startTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampBooleanPair(entry.getTimestamp(), entry.getBooleanValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of (timestamp, boolean) pairs representing all data collected for this system,
   * room (if system is lighting), and state variable from startTime up to end time.
   * 
   * @param system The system.
   * @param room The room.
   * @param state The state variable.
   * @param startTime The start time.
   * @param endTime The end time.
   * @return The list of data representing change in state over time or null if there is not data or
   * if an error occurs retrieving the data.
   */
  private List<TimestampBooleanPair> getBooleanStateValues(SystemHSystem system, SystemHRoom room,
      SystemHState state, Long startTime, Long endTime) {
    List<TimestampBooleanPair> pairs = new ArrayList<TimestampBooleanPair>();
    EntityCursor<StateEntry> cursor = null;
    try {
      cursor = makeCursorDuringInterval(system, room, state, startTime, endTime);
      for (StateEntry entry : cursor) {
        pairs.add(new TimestampBooleanPair(entry.getTimestamp(), entry.getBooleanValue()));
      }
      return pairs;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /**
   * Returns a list of SystemStatusMessage instances representing all status message from startTime
   * up to the present.
   * 
   * @param startTime The start time.
   * @return The list of data representing status messages over time or null if there is not data or
   * if an error occurs retrieving the data.
   */
  private List<SystemStatusMessage> getSystemStatusMessages(Long startTime) {
    List<SystemStatusMessage> messages = new ArrayList<SystemStatusMessage>();
    EntityCursor<SystemStatusMessageEntry> cursor = null;
    try {
      Long currTime = (new Date()).getTime();
      cursor = messageStoreIndex.entities(startTime, true, currTime, true);
      for (SystemStatusMessageEntry entry : cursor) {
        messages.add(new SystemStatusMessage(entry.getTimestamp(), entry.getSystem(), entry
            .getType(), entry.getMessage()));
      }
      return messages;
    }
    catch (Exception e) {
      return null;
    }
    finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsCirculation() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.CIRCULATION);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsCirculationSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.CIRCULATION, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsCirculationDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.CIRCULATION, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsDeadFish() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.DEAD_FISH);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsDeadFishSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.DEAD_FISH, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsDeadFishDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.DEAD_FISH, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsElectricalConductivity() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, 
        SystemHState.ELECTRICAL_CONDUCTIVITY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsElectricalConductivitySince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.ELECTRICAL_CONDUCTIVITY,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsElectricalConductivityDuringInterval(
      Long startTime, Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.ELECTRICAL_CONDUCTIVITY,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsNutrients() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.NUTRIENTS);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientsSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.NUTRIENTS, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientsDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.NUTRIENTS, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsOxygen() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.OXYGEN);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsOxygenSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.OXYGEN, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsOxygenDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.OXYGEN
        , startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsPh() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.PH);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.PH, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhDuringInterval(Long startTime, Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.PH, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsTemperature() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.TEMPERATURE);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.TEMPERATURE, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.TEMPERATURE, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsTemperatureCommand() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_TEMPERATURE_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureCommandSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_TEMPERATURE_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_TEMPERATURE_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsTurbidity() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.TURBIDITY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsTurbiditySince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.TURBIDITY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsTurbidityDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.TURBIDITY, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsWaterLevel() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.WATER_LEVEL);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.WATER_LEVEL, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.WATER_LEVEL, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getHvacTemperature() {
    return getIntegerStateValue(SystemHSystem.HVAC, null, SystemHState.TEMPERATURE);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.HVAC, null, 
        SystemHState.TEMPERATURE, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(SystemHSystem.HVAC, null, 
        SystemHState.TEMPERATURE, startTime , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getHvacTemperatureCommand() {
    return getIntegerStateValue(SystemHSystem.HVAC, null, SystemHState.SET_TEMPERATURE_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureCommandSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.HVAC, null, SystemHState.SET_TEMPERATURE_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.HVAC, null, SystemHState.SET_TEMPERATURE_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getElectricalEnergy() {
    return getIntegerStateValue(SystemHSystem.ELECTRIC, null, SystemHState.ENERGY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalEnergySince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.ELECTRIC, null, SystemHState.ENERGY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalEnergyDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(SystemHSystem.ELECTRIC, null, SystemHState.ENERGY, startTime
        , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getElectricalPower() {
    return getIntegerStateValue(SystemHSystem.ELECTRIC, null, SystemHState.POWER);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalPowerSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.ELECTRIC, null, SystemHState.POWER, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalPowerDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(SystemHSystem.ELECTRIC, null, SystemHState.POWER, startTime
        , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampStringPair getLightingColor(SystemHRoom room) {
    return getStringStateValue(SystemHSystem.LIGHTING, room, SystemHState.LIGHTING_COLOR);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorSince(SystemHRoom room, Long startTime) {
    return getStringStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.LIGHTING_COLOR, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorDuringInterval(SystemHRoom room, Long startTime,
      Long endTime) {
    return getStringStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.LIGHTING_COLOR, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getLightingLevel(SystemHRoom room) {
    return getIntegerStateValue(SystemHSystem.LIGHTING, room, SystemHState.LIGHTING_LEVEL);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelSince(SystemHRoom room, Long startTime) {
    return getIntegerStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.LIGHTING_LEVEL, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelDuringInterval(SystemHRoom room, Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.LIGHTING_LEVEL, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampBooleanPair getLightingEnabled(SystemHRoom room) {
    return getBooleanStateValue(SystemHSystem.LIGHTING, room, SystemHState.LIGHTING_ENABLED);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledSince(SystemHRoom room, Long startTime) {
    return getBooleanStateValues(SystemHSystem.LIGHTING, room, SystemHState.LIGHTING_ENABLED
        , startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledDuringInterval(SystemHRoom room,
      Long startTime, Long endTime) {
    return getBooleanStateValues(SystemHSystem.LIGHTING, room, SystemHState.LIGHTING_ENABLED,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getPhotovoltaicEnergy() {
    return getIntegerStateValue(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.ENERGY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicEnergySince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.ENERGY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicEnergyDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.ENERGY, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getPhotovoltaicPower() {
    return getIntegerStateValue(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.POWER);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicPowerSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.POWER, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicPowerDuringInterval(Long startTime, 
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.PHOTOVOLTAIC, null, SystemHState.POWER, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsFeedFishCommand() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsFeedFishCommandSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsFeedFishCommandDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsHarvestFishCommand() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsHarvestFishCommandSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsHarvestFishCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.FEED_FISH_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsNutrientsCommand() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.SET_NUTRIENTS_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientCommandSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.SET_NUTRIENTS_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientCommandDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, SystemHState.SET_NUTRIENTS_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsPhCommand() {
    return getDoubleStateValue(SystemHSystem.AQUAPONICS, null, SystemHState.SET_PH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhCommandSince(Long startTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_PH_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhCommandDuringInterval(Long startTime, 
      Long endTime) {
    return getDoubleStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_PH_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsWaterLevelCommand() {
    return getIntegerStateValue(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_WATER_LEVEL_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelCommandSince(Long startTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_WATER_LEVEL_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(SystemHSystem.AQUAPONICS, null, 
        SystemHState.SET_WATER_LEVEL_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getLightingLevelCommand(SystemHRoom room) {
    return getIntegerStateValue(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_LEVEL_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelCommandSince(SystemHRoom room, Long startTime) {
    return getIntegerStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_LEVEL_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelCommandDuringInterval(SystemHRoom room,
      Long startTime, Long endTime) {
    return getIntegerStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_LEVEL_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampBooleanPair getLightingEnabledCommand(SystemHRoom room) {
    return getBooleanStateValue(SystemHSystem.LIGHTING, room,
        SystemHState.SET_LIGHTING_ENABLED_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledCommandSince
  (SystemHRoom room, Long startTime) {
    return getBooleanStateValues(SystemHSystem.LIGHTING, room,
        SystemHState.SET_LIGHTING_ENABLED_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledCommandDuringInterval(SystemHRoom room,
      Long startTime, Long endTime) {
    return getBooleanStateValues(SystemHSystem.LIGHTING, room,
        SystemHState.SET_LIGHTING_ENABLED_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampStringPair getLightingColorCommand(SystemHRoom room) {
    return getStringStateValue(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_COLOR_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorCommandSince(SystemHRoom room, Long startTime) {
    return getStringStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_COLOR_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorCommandDuringInterval(SystemHRoom room,
      Long startTime, Long endTime) {
    return getStringStateValues(SystemHSystem.LIGHTING, room, 
        SystemHState.SET_LIGHTING_COLOR_COMMAND, startTime, endTime);
  }

  /**
   * Stores a state entry for the given system and state type as long as the system type is not
   * lighting. To simplify interface, we accept the state value as an Object and then store it as a
   * Long, Double, Integer, Boolean, or String depending upon what it actually is.
   * 
   * @param system Any system but Lighting; use the other form of stateEntry for rooms.
   * @param state The state type.
   * @param timestamp The timestamp.
   * @param stateValue The value of the state associated with this system, state, and timestamp.
   * @throws RuntimeException If stateValue is not a Double, Long, Integer, Boolean, or String.
   */
  public void store(SystemHSystem system, SystemHState state, Long timestamp, Object stateValue) {
    StateEntry entry;
    if (stateValue instanceof Double) {
      entry = new StateEntry(timestamp, (Double) stateValue);
    }
    else if (stateValue instanceof Long) {
      entry = new StateEntry(timestamp, (Long) stateValue);
    }
    else if (stateValue instanceof Integer) {
      entry = new StateEntry(timestamp, Long.valueOf((Integer) stateValue));
    }
    else if (stateValue instanceof String) {
      entry = new StateEntry(timestamp, (String) stateValue);
    }
    else if (stateValue instanceof Boolean) {
      entry = new StateEntry(timestamp, (Boolean) stateValue);
    }
    else {
      throw new RuntimeException("addState passed an unsupported state value type.");
    }
    getIndex(system, null, state).put(entry);

    // Run any listeners interested in this system's state change.
    for (SystemStateListener listener : stateListeners.get(system)) {
      listener.entryAdded(state, null, timestamp, stateValue);
    }
  }

  /**
   * Store method for use when the system in question is lighting. To simplify interface, we accept
   * the state value as an Object and then save it as a Long, Double, Boolean, or String depending
   * upon what it actually is.
   * 
   * @param room The room, when System is Lighting.
   * @param state The state.
   * @param timestamp The timestamp.
   * @param stateValue The value of the state associated with this system, state, and timestamp.
   * @throws RuntimeException If stateValue is not a Double, Long, Integer, Boolean, or String.
   */
  public void store(SystemHRoom room, SystemHState state, Long timestamp, Object stateValue) {
    StateEntry entry;
    if (stateValue instanceof Double) {
      entry = new StateEntry(timestamp, (Double) stateValue);
    }
    else if (stateValue instanceof Long) {
      entry = new StateEntry(timestamp, (Long) stateValue);
    }
    else if (stateValue instanceof Integer) {
      entry = new StateEntry(timestamp, Long.valueOf((Integer) stateValue));
    }
    else if (stateValue instanceof String) {
      entry = new StateEntry(timestamp, (String) stateValue);
    }
    else if (stateValue instanceof Boolean) {
      entry = new StateEntry(timestamp, (Boolean) stateValue);
    }
    else {
      throw new RuntimeException("addState passed an unsupported state value type.");
    }
    getIndex(SystemHSystem.LIGHTING, room, state).put(entry);

    // Run any listeners interested in this system's state change.
    for (SystemStateListener listener : stateListeners.get(SystemHSystem.LIGHTING)) {
      listener.entryAdded(state, room, timestamp, stateValue);
    }
  }

  /**
   * The general store method which accepts both system and room. If system is not LIGHTING, then
   * room should be null. To simplify interface, we accept the state value as an Object and then
   * save it as a Long, Double, Boolean, or String depending upon what it actually is.
   * 
   * @param system The system.
   * @param room The room, when System is Lighting.
   * @param state The state.
   * @param timestamp The timestamp.
   * @param stateValue The value of the state associated with this system, state, and timestamp.
   * @throws RuntimeException If stateValue is not a Double, Long, Integer, Boolean, or String.
   */
  public void store(SystemHSystem system, SystemHRoom room, SystemHState state, Long timestamp,
      Object stateValue) {
    StateEntry entry;
    if (stateValue instanceof Double) {
      entry = new StateEntry(timestamp, (Double) stateValue);
    }
    else if (stateValue instanceof Long) {
      entry = new StateEntry(timestamp, (Long) stateValue);
    }
    else if (stateValue instanceof Integer) {
      entry = new StateEntry(timestamp, Long.valueOf((Integer) stateValue));
    }
    else if (stateValue instanceof String) {
      entry = new StateEntry(timestamp, (String) stateValue);
    }
    else if (stateValue instanceof Boolean) {
      entry = new StateEntry(timestamp, (Boolean) stateValue);
    }
    else {
      throw new RuntimeException("addState passed an unsupported state value type.");
    }
    getIndex(system, room, state).put(entry);

    // Run any listeners interested in this system's state change.
    for (SystemStateListener listener : stateListeners.get(system)) {
      listener.entryAdded(state, room, timestamp, stateValue);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void store(SystemStatusMessage message) {
    SystemStatusMessageEntry entry = new SystemStatusMessageEntry(message);
    messageStoreIndex.put(entry);

    // Run any listeners interested in this system's state change.
    for (SystemStatusMessageListener listener : statusListeners) {
      listener.messageAdded(message.getTimestamp(), message.getSystem(), message.getType(),
          message.getMessage());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void addSystemStateListener(SystemStateListener listener) {
    SystemHSystem system = listener.getSystem();
    List<SystemStateListener> systemListeners = stateListeners.get(system);
    systemListeners.add(listener);
  }

  /** {@inheritDoc} */
  @Override
  public void addSystemStatusMessageListener(SystemStatusMessageListener listener) {
    statusListeners.add(listener);

  }

  /** {@inheritDoc} */
  @Override
  public List<SystemStatusMessage> getSystemStatusMessageSince(Long timestamp) {
    return getSystemStatusMessages(timestamp);
  }
}
