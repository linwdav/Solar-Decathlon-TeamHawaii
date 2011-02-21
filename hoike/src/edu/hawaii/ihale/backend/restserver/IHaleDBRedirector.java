package edu.hawaii.ihale.backend.restserver;

import java.util.List;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.backend.db.IHaleDB;
import edu.hawaii.ihale.backend.db.IHaleSystemStateEntry;

/**
 * A class that resolves the persistency issues of the Java API by mirroring
 * IHaleDBRedirector and IHaleDB, converting SystemStateEntry objects to 
 * IHaleSystemEntry objects (has persistency traits). Then passing the 
 * IHaleSystemEntry objects to IHaleDB methods to store into the
 * database repository.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class IHaleDBRedirector implements SystemStateEntryDB {

  /**
   * Default constructor.
   */
  public IHaleDBRedirector() {
    // Default constructor.
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
    
    return null;
  }
  
  /**
   * Store the passed SystemStateEntry in the database.
   * @param entry The entry instance to store. 
   */
  public void putEntry(SystemStateEntry entry) {    
    IHaleSystemStateEntry entryToStore = 
      new IHaleSystemStateEntry(entry.getSystemName(), entry.getDeviceName(), entry.getTimestamp());
    
    /** TO-DO: Retrieve the 3 Maps and transfer their value from SystemStateEntry object to 
     *         IHaleStateEntry object. Requires how the data dictionary defines the various
     *         system names, device names, and their data type. Suggestion, MultiMaps.
     *         Currently only stores systemName, deviceName, and timestamp information into DB.
     */
    IHaleDB.putEntry(entryToStore);
  }
  
  /**
   * Removes the entry with the specified system name, device name, and timestamp.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    // TO-DO: . . .
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
    
    return null;
  }
  
  /**
   * Returns a list of all currently defined system names. 
   * @return The list of system names.
   */
  public List<String> getSystemNames() {
    
    return null;
  }
  
  /**
   * Returns a list of all the device names associated with the passed SystemName.
   * @param systemName A string indicating a system name. 
   * @return A list of device names for this system name.
   * @throws SystemStateEntryDBException if the system name is not known.
   */
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    
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
  public void addSystemStateListener(SystemStateListener listener) {
    // TO-DO: . . .
  }
                                                                 
  
  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * @param systemName The system name.
   * @param deviceName The device name. 
   * @param command The command.
   * @param args Any additional arguments required by the command. 
   */
  public void doCommand(String systemName, String deviceName, String command, List<String> args) {
    // TO-DO: . . .
  }
}
