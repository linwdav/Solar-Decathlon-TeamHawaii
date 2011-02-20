package edu.hawaii.ihale.backend.db;

import java.io.File;
import java.util.List;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * The iHale database repository which stores the various system state entries generated
 * by the home systems such as Aquaponics, Lighting, HVAC, etc.  
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class IHaleDB implements SystemStateEntryDB {
  
  /** The EntityStore for our state entries database. */
  private static EntityStore store;
  
  /** The PrimaryIndex accessor for the entries. */
  private static PrimaryIndex<Long, IHaleSystemStateEntry> entryIndexPKey;

  /** The SecondaryIndex accessor for entries related to its system name. */
  private static SecondaryIndex<String, Long, IHaleSystemStateEntry> entryIndexSKey;

  /** Initialize the static variables at class load time to ensure there's only one of them. */
  static {
    String databaseName = "iHale";
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    File dir = new File(currDir, databaseName);
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the " + databaseName + " directory.");
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir,  envConfig);
    IHaleDB.store = new EntityStore(env, "EntityStore", storeConfig);
    entryIndexPKey = store.getPrimaryIndex(Long.class, IHaleSystemStateEntry.class);
    entryIndexSKey = store.getSecondaryIndex(entryIndexPKey, String.class, "timestamp");
    // Guarantee that the environment is closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, store);
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
  
  /**
   * Returns the IHaleSystemStateEntry instance associated with the system, device, and timestamp, 
   * or null if not found.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @return The associated IHaleSystemStateEntry, or null if not found.
   */
  @Override
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {
    /** TO-DO: systemName and deviceName, either both or one of them need to be SecondaryKeys
     *         since there could be records/entities that share the same timestamp (PrimaryKey).
     *         Need to thus define SecondaryKey and do cusory search for proper entry to be
     *         returned.
     */
    
    
    // Below code is just for testing - Remove later.
    SystemStateEntry testEntry = new SystemStateEntry("Aquaponics", "Arduino-23", 111111111);
    return testEntry;
  }
  
  /**
   * Store the passed entry in the database.
   * 
   * @param entry The entry instance to store. 
   */
  @Override
  public void putEntry(SystemStateEntry entry) {
    entryIndexPKey.put((IHaleSystemStateEntry) entry);

  }
  
  /**
   * Removes the entry with the specified system name, device name, and timestamp.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  @Override
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    /** TO-DO: systemName and deviceName, either both or one of them need to be SecondaryKeys
     *         since there could be records/entities that share the same timestamp (PrimaryKey).
     *         Need to thus define SecondaryKey and do cusory search for proper entry to be
     *         removed.
     */
    
    // Below is simply place holder code - Remove/modify later.
    entryIndexPKey.delete(timestamp);
  }

  /**
   * Returns a list of IHaleSystemStateEntry instances consisting of all entries between the 
   * two timestamps. 
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param startTime The start time.
   * @param endTime The end time. 
   * @return A (possibly empty) list of IHaleSystemStateEntries.
   * @throws SystemStateEntryDBException If startTime is greater than endTime. 
   */  
  @Override
  public List<SystemStateEntry> getEntries(String arg0, String arg1, long arg2, long arg3)
      throws SystemStateEntryDBException {
    // TODO Auto-generated method stub
    return null;
  }
  
  /**
   * Returns a list of all currently defined system names. 
   * 
   * @return The list of system names.
   */
  @Override
  public List<String> getSystemNames() {
    // TODO Auto-generated method stub
    return null;
  }
  
  /**
   * Returns a list of all the device names associated with the passed SystemName.
   * 
   * @param systemName A string indicating a system name. 
   * @return A list of device names for this system name.
   * @throws SystemStateEntryDBException if the system name is not known.
   */
  @Override
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    // TODO Auto-generated method stub
    return null;
  }
  
  /**
   * Adds a listener to this repository whose entryAdded method will be invoked whenever an
   * entry is added to the database for the system name associated with this listener.
   * This method provides a way for the user interface (for example, Wicket) to update itself
   * whenever new data comes in to the repository. 
   * 
   * @param listener The listener whose entryAdded method will be called. 
   */
  @Override
  public void addSystemStateListener(SystemStateListener arg0) {
    // TODO Auto-generated method stub
    
  }

  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * 
   * @param systemName The system name.
   * @param deviceName The device name. 
   * @param command The command.
   * @param args Any additional arguments required by the command. 
   */
  @Override
  public void doCommand(String systemName, String deviceName, String command, List<String> args) {
    // TODO Auto-generated method stub
    
  }
}
