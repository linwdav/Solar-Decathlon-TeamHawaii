package edu.hawaii.ihale.api.repository.impl;

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
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.repository.IHaleRepository;
import edu.hawaii.ihale.api.repository.SystemStateListener;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.SystemStatusMessageListener;
import edu.hawaii.ihale.api.repository.TimestampBooleanPair;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.ihale.api.repository.TimestampStringPair;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;

/**
 * An implementation of the iHale repository using the BerkeleyDB system. Repository instances are
 * thread safe. You can create as many of these as you want.
 * 
 * @author Philip Johnson
 */
public class Repository implements IHaleRepository {

  /** The System property that the user can set to override the default repo directory name. */
  public static final String repositoryDirectoryPropertyKey = "iHale.RepositoryDirectoryName";

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
  private static Map<IHaleSystem, List<SystemStateListener>> stateListeners =
      new ConcurrentHashMap<IHaleSystem, List<SystemStateListener>>();
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
  private static void defineStore(Environment env, StoreConfig storeConfig, IHaleSystem system,
      IHaleState state) {
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
  private static void defineStore(Environment env, StoreConfig storeConfig, IHaleRoom room,
      IHaleState state) {
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
  private static void defineStore(Environment env, StoreConfig storeConfig, IHaleSystem system,
      IHaleRoom room, IHaleState state) {
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
  private static String makeStoreName(IHaleSystem system, IHaleRoom room, IHaleState state) {
    // return either <system>_<state> or Lighting_<room>_<state>
    return (room == null) ? system.toString() + "_" + state.toString() : IHaleSystem.LIGHTING
        .toString() + "_" + room.toString() + "_" + state.toString();
  }

  /** Initialize the static variables at class load time to ensure we do it only once. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    // Allow user to override repository name by setting this variable.
    String repoDirName = System.getProperty(repositoryDirectoryPropertyKey, "IHaleRepository");
    File dir = new File(currDir, repoDirName);
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the iHale repository directory: " + dir);
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir, envConfig);
    // Create a separate repository store for each (System, State) combination.

    // Aquaponics state variables.
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.CIRCULATION);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.ELECTRICAL_CONDUCTIVITY);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.NUTRIENTS);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.OXYGEN);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.PH);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.TEMPERATURE);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.TURBIDITY);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.WATER_LEVEL);

    // Aquaponics commands.
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.FEED_FISH_COMMAND);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.HARVEST_FISH_COMMAND);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.SET_NUTRIENTS_COMMAND);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.SET_PH_COMMAND);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.SET_TEMPERATURE_COMMAND);
    defineStore(env, storeConfig, IHaleSystem.AQUAPONICS, IHaleState.SET_WATER_LEVEL_COMMAND);

    // HVAC state and commands.
    defineStore(env, storeConfig, IHaleSystem.HVAC, IHaleState.TEMPERATURE);
    defineStore(env, storeConfig, IHaleSystem.HVAC, IHaleState.SET_TEMPERATURE_COMMAND);

    // PV and ELECTRIC state (no commands)
    defineStore(env, storeConfig, IHaleSystem.PHOTOVOLTAIC, IHaleState.POWER);
    defineStore(env, storeConfig, IHaleSystem.PHOTOVOLTAIC, IHaleState.ENERGY);
    defineStore(env, storeConfig, IHaleSystem.ELECTRIC, IHaleState.POWER);
    defineStore(env, storeConfig, IHaleSystem.ELECTRIC, IHaleState.ENERGY);

    // Lighting state and commands (by room)
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.LIGHTING_COLOR);
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.BATHROOM, IHaleState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.LIGHTING_COLOR);
    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.LIVING, IHaleState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.LIGHTING_COLOR);
    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.DINING, IHaleState.SET_LIGHTING_ENABLED_COMMAND);

    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.LIGHTING_LEVEL);
    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.LIGHTING_COLOR);
    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.LIGHTING_ENABLED);
    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_LEVEL_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_COLOR_COMMAND);
    defineStore(env, storeConfig, IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_ENABLED_COMMAND);

    // Create the EntityStore and Index for the SystemStatusMessageStore.
    defineMessageStore(env, storeConfig);

    // Guarantee that all EntityStores will be closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, stores.values());
    Runtime.getRuntime().addShutdownHook(shutdownHook);

    // Initialize System State Listeners with all possible keys for the listener map.
    for (IHaleSystem system : IHaleSystem.values()) {
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
  private PrimaryIndex<Long, StateEntry> getIndex(IHaleSystem system, IHaleRoom room,
      IHaleState state) {
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
  private EntityCursor<StateEntry> makeCursor(IHaleSystem system, IHaleRoom room
      , IHaleState state) {
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
  private EntityCursor<StateEntry> makeCursorSince(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime) {
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
  private EntityCursor<StateEntry> makeCursorDuringInterval(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime, Long endTime) {
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
  private TimestampDoublePair getDoubleStateValue(IHaleSystem system, IHaleRoom room,
      IHaleState state) {
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
  private List<TimestampDoublePair> getDoubleStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime) {
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
  private List<TimestampDoublePair> getDoubleStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime, Long endTime) {
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
  private TimestampIntegerPair getIntegerStateValue(IHaleSystem system, IHaleRoom room,
      IHaleState state) {
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
  private List<TimestampIntegerPair> getIntegerStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime) {
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
  private List<TimestampIntegerPair> getIntegerStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime, Long endTime) {
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
  private TimestampStringPair getStringStateValue(IHaleSystem system, IHaleRoom room,
      IHaleState state) {
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
  private List<TimestampStringPair> getStringStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime) {
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
  private List<TimestampStringPair> getStringStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime, Long endTime) {
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
  private TimestampBooleanPair getBooleanStateValue(IHaleSystem system, IHaleRoom room,
      IHaleState state) {
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
  private List<TimestampBooleanPair> getBooleanStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime) {
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
  private List<TimestampBooleanPair> getBooleanStateValues(IHaleSystem system, IHaleRoom room,
      IHaleState state, Long startTime, Long endTime) {
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
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.CIRCULATION);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsCirculationSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.CIRCULATION, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsCirculationDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.CIRCULATION, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsDeadFish() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.DEAD_FISH);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsDeadFishSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.DEAD_FISH, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsDeadFishDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.DEAD_FISH, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsElectricalConductivity() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.ELECTRICAL_CONDUCTIVITY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsElectricalConductivitySince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.ELECTRICAL_CONDUCTIVITY,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsElectricalConductivityDuringInterval(
      Long startTime, Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.ELECTRICAL_CONDUCTIVITY,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsNutrients() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.NUTRIENTS);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientsSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.NUTRIENTS, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientsDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.NUTRIENTS, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsOxygen() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.OXYGEN);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsOxygenSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.OXYGEN, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsOxygenDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.OXYGEN
        , startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsPh() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.PH);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.PH, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhDuringInterval(Long startTime, Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.PH, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsTemperature() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.TEMPERATURE);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.TEMPERATURE, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.TEMPERATURE, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsTemperatureCommand() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.SET_TEMPERATURE_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureCommandSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_TEMPERATURE_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsTemperatureCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_TEMPERATURE_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsTurbidity() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.TURBIDITY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsTurbiditySince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.TURBIDITY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsTurbidityDuringInterval(Long startTime
      , Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.TURBIDITY, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsWaterLevel() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.WATER_LEVEL);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.WATER_LEVEL, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.WATER_LEVEL, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getHvacTemperature() {
    return getIntegerStateValue(IHaleSystem.HVAC, null, IHaleState.TEMPERATURE);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.HVAC, null, IHaleState.TEMPERATURE, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(IHaleSystem.HVAC, null, IHaleState.TEMPERATURE, startTime
        , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getHvacTemperatureCommand() {
    return getIntegerStateValue(IHaleSystem.HVAC, null, IHaleState.SET_TEMPERATURE_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureCommandSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.HVAC, null, IHaleState.SET_TEMPERATURE_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getHvacTemperatureCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.HVAC, null, IHaleState.SET_TEMPERATURE_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getElectricalEnergy() {
    return getIntegerStateValue(IHaleSystem.ELECTRIC, null, IHaleState.ENERGY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalEnergySince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.ELECTRIC, null, IHaleState.ENERGY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalEnergyDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(IHaleSystem.ELECTRIC, null, IHaleState.ENERGY, startTime
        , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getElectricalPower() {
    return getIntegerStateValue(IHaleSystem.ELECTRIC, null, IHaleState.POWER);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalPowerSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.ELECTRIC, null, IHaleState.POWER, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getElectricalPowerDuringInterval(Long startTime
      , Long endTime) {
    return getIntegerStateValues(IHaleSystem.ELECTRIC, null, IHaleState.POWER, startTime
        , endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampStringPair getLightingColor(IHaleRoom room) {
    return getStringStateValue(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_COLOR);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorSince(IHaleRoom room, Long startTime) {
    return getStringStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_COLOR, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorDuringInterval(IHaleRoom room, Long startTime,
      Long endTime) {
    return getStringStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_COLOR, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getLightingLevel(IHaleRoom room) {
    return getIntegerStateValue(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_LEVEL);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelSince(IHaleRoom room, Long startTime) {
    return getIntegerStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_LEVEL, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelDuringInterval(IHaleRoom room, Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_LEVEL, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampBooleanPair getLightingEnabled(IHaleRoom room) {
    return getBooleanStateValue(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_ENABLED);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledSince(IHaleRoom room, Long startTime) {
    return getBooleanStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_ENABLED
        , startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledDuringInterval(IHaleRoom room,
      Long startTime, Long endTime) {
    return getBooleanStateValues(IHaleSystem.LIGHTING, room, IHaleState.LIGHTING_ENABLED,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getPhotovoltaicEnergy() {
    return getIntegerStateValue(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.ENERGY);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicEnergySince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.ENERGY, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicEnergyDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.ENERGY, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getPhotovoltaicPower() {
    return getIntegerStateValue(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.POWER);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicPowerSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.POWER, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getPhotovoltaicPowerDuringInterval(Long startTime, 
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.PHOTOVOLTAIC, null, IHaleState.POWER, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsFeedFishCommand() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsFeedFishCommandSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsFeedFishCommandDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsHarvestFishCommand() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsHarvestFishCommandSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsHarvestFishCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.FEED_FISH_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsNutrientsCommand() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.SET_NUTRIENTS_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientCommandSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_NUTRIENTS_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsNutrientCommandDuringInterval(Long startTime,
      Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_NUTRIENTS_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampDoublePair getAquaponicsPhCommand() {
    return getDoubleStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.SET_PH_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhCommandSince(Long startTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_PH_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampDoublePair> getAquaponicsPhCommandDuringInterval(Long startTime, 
      Long endTime) {
    return getDoubleStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_PH_COMMAND, startTime,
        endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getAquaponicsWaterLevelCommand() {
    return getIntegerStateValue(IHaleSystem.AQUAPONICS, null, IHaleState.SET_WATER_LEVEL_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelCommandSince(Long startTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_WATER_LEVEL_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getAquaponicsWaterLevelCommandDuringInterval(Long startTime,
      Long endTime) {
    return getIntegerStateValues(IHaleSystem.AQUAPONICS, null, IHaleState.SET_WATER_LEVEL_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampIntegerPair getLightingLevelCommand(IHaleRoom room) {
    return getIntegerStateValue(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_LEVEL_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelCommandSince(IHaleRoom room, Long startTime) {
    return getIntegerStateValues(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_LEVEL_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampIntegerPair> getLightingLevelCommandDuringInterval(IHaleRoom room,
      Long startTime, Long endTime) {
    return getIntegerStateValues(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_LEVEL_COMMAND,
        startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampBooleanPair getLightingEnabledCommand(IHaleRoom room) {
    return getBooleanStateValue(IHaleSystem.LIGHTING, room,
        IHaleState.SET_LIGHTING_ENABLED_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledCommandSince(IHaleRoom room, Long startTime) {
    return getBooleanStateValues(IHaleSystem.LIGHTING, room,
        IHaleState.SET_LIGHTING_ENABLED_COMMAND, startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampBooleanPair> getLightingEnabledCommandDuringInterval(IHaleRoom room,
      Long startTime, Long endTime) {
    return getBooleanStateValues(IHaleSystem.LIGHTING, room,
        IHaleState.SET_LIGHTING_ENABLED_COMMAND, startTime, endTime);
  }

  /** {@inheritDoc} */
  @Override
  public TimestampStringPair getLightingColorCommand(IHaleRoom room) {
    return getStringStateValue(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_COLOR_COMMAND);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorCommandSince(IHaleRoom room, Long startTime) {
    return getStringStateValues(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_COLOR_COMMAND,
        startTime);
  }

  /** {@inheritDoc} */
  @Override
  public List<TimestampStringPair> getLightingColorCommandDuringInterval(IHaleRoom room,
      Long startTime, Long endTime) {
    return getStringStateValues(IHaleSystem.LIGHTING, room, IHaleState.SET_LIGHTING_COLOR_COMMAND,
        startTime, endTime);
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
  public void store(IHaleSystem system, IHaleState state, Long timestamp, Object stateValue) {
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
  public void store(IHaleRoom room, IHaleState state, Long timestamp, Object stateValue) {
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
    getIndex(IHaleSystem.LIGHTING, room, state).put(entry);

    // Run any listeners interested in this system's state change.
    for (SystemStateListener listener : stateListeners.get(IHaleSystem.LIGHTING)) {
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
  public void store(IHaleSystem system, IHaleRoom room, IHaleState state, Long timestamp,
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
    IHaleSystem system = listener.getSystem();
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
