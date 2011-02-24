package edu.hawaii.ihale.backend;

import java.io.File;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * A thread safe data access object to store and retrieve Contact instances.
 * 
 * @author Philip Johnson
 */
public class SystemStateEntryRecordDAO {
  /** The EntityStore for our Contact database. */
  private static EntityStore store;

  /** The PrimaryIndex accessor for contacts. */
  private static PrimaryIndex<SystemStateAttributes, SystemStateEntryRecord> contactIndex;

  /** Initialize the static variables at class load time to ensure there's only one of them. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    File dir = new File(currDir, "SystemState");
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the contactDB directory.");
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir, envConfig);
    SystemStateEntryRecordDAO.store = new EntityStore(env, "EntityStore", storeConfig);
    contactIndex = store.getPrimaryIndex(SystemStateAttributes.class, SystemStateEntryRecord.class);
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
    contactIndex.put(record);
  }

  /**
   * Retrieve a System State record from the database given its key.
   * 
   * @param attributes The composite primary key for the Contact.
   * @return The System State instance.
   */
  public static SystemStateEntryRecord getSystemStateEntryRecord(SystemStateAttributes attributes) {
    return contactIndex.get(attributes);
  }

  /**
   * Removes the System State instance with the specified attributes.
   * 
   * @param attributes The unique ID for the instance to be deleted.
   */
  public static void deleteSystemStateEntryRecord(SystemStateAttributes attributes) {
    contactIndex.delete(attributes);
  }
}
