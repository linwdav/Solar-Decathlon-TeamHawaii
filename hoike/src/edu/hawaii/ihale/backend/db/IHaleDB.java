package edu.hawaii.ihale.backend.db;

import java.util.List;
import com.sleepycat.persist.EntityStore;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Needs to be commented.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class IHaleDB implements SystemStateEntryDB {

  /** The EntityStore for our Contact database. */
  //Commented atm due to complaints from Findbugs.
  //private static EntityStore store;
  
  @Override
  public void addSystemStateListener(SystemStateListener arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void deleteEntry(String arg0, String arg1, long arg2) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doCommand(String arg0, String arg1, String arg2, List<String> arg3) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<String> getDeviceNames(String arg0) throws SystemStateEntryDBException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SystemStateEntry> getEntries(String arg0, String arg1, long arg2, long arg3)
      throws SystemStateEntryDBException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SystemStateEntry getEntry(String arg0, String arg1, long arg2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getSystemNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void putEntry(SystemStateEntry entry) {
    System.out.println("Test code: putEntry method in IHaleDB");
  }
}
