package edu.hawaii.ihale.backend.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A thread safe data access object to store and retrieve SystemStateEntry instances.
 * @author Philip Johnson
 */
public class SystemStateDAO {
  /** The EntityStore for our Contact database. */
  private static EntityStore store;
  
  /** The PrimaryIndex accessor for contacts. */
  private static PrimaryIndex<Long, SystemStateEntry> systemStateIndex; 
  
  /** Initialize the static variables at class load time to ensure there's only one of them. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    File dir = new File(currDir, "SystemStateDB");
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the SystemStateDB directory.");
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir,  envConfig);
    SystemStateDAO.store = new EntityStore(env, "EntityStore", storeConfig);
    systemStateIndex = store.getPrimaryIndex(Long.class, SystemStateEntry.class); 
    // Guarantee that the environment is closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, store);
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
  
  /**
   * Returns the SystemStateEntry instance associated with the system, device, and timestamp, 
   * or null if not found.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @return The associated SystemStateEntry, or null if not found.
   */
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {
	  return systemStateIndex.get(timestamp);
  }
  
  /**
   * Store the passed SystemStateEntry in the database.
   * @param entry The entry instance to store. 
   */
  public void putEntry(SystemStateEntry entry) {
	  systemStateIndex.put(entry);
  }
  
  /**
   * Removes the entry with the specified system name, device name, and timestamp.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
	  systemStateIndex.delete(timestamp);
  }
  
  /**
   * Returns a list of SystemStateEntry instances consisting of all entries between the 
   * two timestamps. 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param startTime The start time.
   * @param endTime The end time. 
   * @return A (possibly empty) list of SystemStateEntries.
   * @throws SystemStateEntryDBException If startTime is greater than endTime. 
   */
  public List<SystemStateEntry> getEntries(String systemName, String deviceName, long startTime, 
      long endTime) throws SystemStateEntryDBException {
	  return new ArrayList<SystemStateEntry>();

  }
  
  /**
   * Returns a list of all currently defined system names. 
   * @return The list of system names.
   */
  public List<String> getSystemNames() {
	  return new ArrayList<String>();
  }
  
  /**
   * Returns a list of all the device names associated with the passed SystemName.
   * @param systemName A string indicating a system name. 
   * @return A list of device names for this system name.
   * @throws SystemStateEntryDBException if the system name is not known.
   */
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
	  return new ArrayList<String>();
  }
  
  /**
   * Adds a listener to this repository whose entryAdded method will be invoked whenever an
   * entry is added to the database for the system name associated with this listener.
   * This method provides a way for the user interface (for example, Wicket) to update itself
   * whenever new data comes in to the repository. 
   * 
   * @param listener The listener whose entryAdded method will be called. 
   */
  public void addSystemStateListener(SystemStateListener listener) {
	  
  }
  
  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * @param systemName The system name.
   * @param deviceName The device name. 
   * @param command The command.
   * @param args Any additional arguments required by the command. 
   */
  public void doCommand(String systemName, String deviceName, String command, List<String> args) {
	  
  }
}
