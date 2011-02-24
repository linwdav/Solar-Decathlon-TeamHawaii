package edu.hawaii.ihale.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * A thread safe data access object to store and retrieve Contact instances.
 * 
 * @author Philip Johnson
 */
public class SystemStateEntryRecordDAO {
  /** The EntityStore for our Contact database. */
  private static EntityStore store;

  /** The PrimaryIndex accessor for System States. */
  private static PrimaryIndex<SystemStateAttributes, SystemStateEntryRecord> primaryIndexAttributes;

  // Secondary Index accessors for system and device names
  private static SecondaryIndex<String, SystemStateAttributes, 
    SystemStateEntryRecord> secondarySystemName;

  /** Initialize the static variables at class load time to ensure there's only one of them. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    File dir = new File(currDir, "SystemState");
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the System State DB directory.");
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir, envConfig);
    SystemStateEntryRecordDAO.store = new EntityStore(env, "EntityStore", storeConfig);
    primaryIndexAttributes =
        store.getPrimaryIndex(SystemStateAttributes.class, SystemStateEntryRecord.class);
    secondarySystemName =
        store.getSecondaryIndex(primaryIndexAttributes, String.class, "systemName");
    // Guarantee that the environment is closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, store);
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }

  /**
   * Store the passed record in the database.
   * 
   * @param record The record to store.
   */
  public static void putSystemStateEntryRecord(SystemStateEntryRecord record) {
    primaryIndexAttributes.put(record);
  }

  /**
   * Retrieve a System State record from the database given its key.
   * 
   * @param attributes The composite primary key for the Contact.
   * @return The System State instance.
   */
  public static SystemStateEntryRecord getSystemStateEntryRecord(SystemStateAttributes attributes) {
    return primaryIndexAttributes.get(attributes);
  }

  /**
   * Removes the System State instance with the specified attributes.
   * 
   * @param attributes The unique ID for the instance to be deleted.
   */
  public static void deleteSystemStateEntryRecord(SystemStateAttributes attributes) {
    primaryIndexAttributes.delete(attributes);
  }

  /**
   * Gets a list of all system names in the database.
   * 
   * @return The list of system names
   */
  public static List<String> getSystemNames() {

    // List of names to return
    List<String> list = new ArrayList<String>();

    // Holds current entity while we iterate through the collection
    SystemStateEntryRecord record;

    // Cursor to allow us to iterate through the database
    EntityCursor<SystemStateEntryRecord> cursor = secondarySystemName.entities();

    // Look through database and only add unique names to the list
    while ((record = cursor.nextNoDup()) != null) {
      list.add(record.getAttributes().getSystemName());
    }
    cursor.close();
    return list;
  } // End getSystemNames

  /**
   * Gets a list of all device names in the database for a given system.
   * 
   * @param systemName The system to retrieve devices for
   * @return The list of device names
   */
  public static List<String> getDeviceNames(String systemName) {

    // List of names to return
    Set<String> set = new HashSet<String>();
    List<String> list = new ArrayList<String>();

    // Holds current entity while we iterate through the collection
    SystemStateEntryRecord record;

    // Cursor to allow us to iterate through the database
    EntityCursor<SystemStateEntryRecord> cursor =
        secondarySystemName.subIndex(systemName).entities();

    // Look through database and only add unique names to the set
    while ((record = cursor.nextNoDup()) != null) {
      set.add(record.getAttributes().getDeviceName());
    }

    // Transfer all entries to the list (so no duplicates)
    for (String s : set) {
      list.add(s);
    }
    cursor.close();
    return list;

  } // End getSystemNames

} // End DAO class
