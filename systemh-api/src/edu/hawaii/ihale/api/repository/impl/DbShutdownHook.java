package edu.hawaii.ihale.api.repository.impl;

import java.util.Collection;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;

/**
 * Provides a shutdown hook that closes all BerkeleyDB entity stores and environments. 
 * For more details, see: http://onjava.com/pub/a/onjava/2003/03/26/shutdownhook.html
 * @author Philip Johnson
 */
public class DbShutdownHook extends Thread {
  /** The environment instance to be closed upon shutdown. */
  private Environment env;
  /** The collection of all entity stores to be closed upon shutdown. */
  private Collection<EntityStore> stores;

  /**
   * Called from within the Repository static block to pass the Environment and stores to be closed.
   * @param env The Environment.
   * @param stores The collection of all stores.
   */
  public DbShutdownHook(Environment env, Collection<EntityStore> stores) {
    this.env = env;
    this.stores = stores;
  }

  /**
   * Runs at system shutdown time and closes the environment and entity store. 
   */
  public void run() {
    try {
      if (env != null) {
        for (EntityStore store : stores) {
          store.close(); 
        }
        env.cleanLog();
        env.close();
        // Comment this next line out during production, but good to see during development.
        System.out.println("Database closed.");
      } 
    } 
    catch (DatabaseException dbe) {
      System.out.println("Database not closed successfully.");
    } 
  }
}
