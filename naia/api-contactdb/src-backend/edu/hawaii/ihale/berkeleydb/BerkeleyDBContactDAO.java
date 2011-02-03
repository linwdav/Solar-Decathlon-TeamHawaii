package edu.hawaii.ihale.berkeleydb;

import java.io.File;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * A thread safe data access object to store and retrieve Contact instances.
 * @author Philip Johnson
 */
public class BerkeleyDBContactDAO {
  /** The EntityStore for our Contact database. */
  private static EntityStore store;
  
  /** The PrimaryIndex accessor for contacts. */
  private static PrimaryIndex<String, BerkeleyDBContact> contactIndex; 
  
  /** Initialize the static variables at class load time to ensure there's only one of them. */
  static {
    // Create the directory in which this store will live.
    String currDir = System.getProperty("user.dir");
    File dir = new File(currDir, "contactDB");
    boolean success = dir.mkdirs();
    if (success) {
      System.out.println("Created the contactDB directory.");
    }
    EnvironmentConfig envConfig = new EnvironmentConfig();
    StoreConfig storeConfig = new StoreConfig();
    envConfig.setAllowCreate(true);
    storeConfig.setAllowCreate(true);
    Environment env = new Environment(dir,  envConfig);
    BerkeleyDBContactDAO.store = new EntityStore(env, "EntityStore", storeConfig);
    contactIndex = store.getPrimaryIndex(String.class, BerkeleyDBContact.class); 
    // Guarantee that the environment is closed upon system exit.
    DbShutdownHook shutdownHook = new DbShutdownHook(env, store);
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
  
  /**
   * Store the passed Contact in the database.
   * @param contact The contact to store. 
   */
  public static void putContact(BerkeleyDBContact contact) {
    contactIndex.put(contact);
  }
  
  /**
   * Retrieve a Contact from the database given its key.
   * @param uniqueID The primary key for the Contact.
   * @return The Contact instance. 
   */
  public static BerkeleyDBContact getContact(String uniqueID) {
    return contactIndex.get(uniqueID);
  }
  
  /**
   * Removes the Contact instance with the specified ID.
   * @param uniqueID The unique ID for the instance to be deleted.
   */
  public static void deleteContact(String uniqueID) {
    contactIndex.delete(uniqueID);
  }
}
