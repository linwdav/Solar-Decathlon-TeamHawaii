package edu.hawaii.ihale.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Provides an implementation of the SystemStateEntryDB.  
 * Note this implementation doesn't really work correctly. This should be obvious.
 * @author Philip Johnson
 */
public class IHaleDB implements SystemStateEntryDB {
  
  /** Holds an in-memory list of entries by their timestamp. Obviously bogus. */
  private Map<Long, SystemStateEntry> entries = new HashMap<Long, SystemStateEntry>();

  /** Holds an in-memory list of system state listeners. This is OK. */
  private List<SystemStateListener> listeners = new ArrayList<SystemStateListener>();
  
  /**
   * Returns the SystemStateEntry instance associated with the system, device, and timestamp, 
   * or null if not found.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @return The associated SystemStateEntry, or null if not found.
   */
  @Override
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {
    // Ignoring the names: that's BOGUS. Can't do this in a real implementation. 
    return entries.get(timestamp);
  }
  
  /**
   * Store the passed SystemStateEntry in the database.
   * @param entry The entry instance to store. 
   */
  @Override
  public void putEntry(SystemStateEntry entry) {
    this.entries.put(entry.getTimestamp(), entry);
    // Here is where the listeners get invoked if they are interested in this systemName. 
    for (SystemStateListener listener : listeners) {
      if (entry.getSystemName().equals(listener.getSystemName())) {
        listener.entryAdded(entry);
      }
    }
  }
  
  /**
   * Removes the entry with the specified system name, device name, and timestamp.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  @Override
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    // Ignoring the names: that's BOGUS. Can't do this in a real implementation. 
    this.entries.remove(timestamp);
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
  @Override
  public List<SystemStateEntry> getEntries(String systemName, String deviceName, long startTime, 
      long endTime) throws SystemStateEntryDBException {
    // Ignoring all arguments and returning the entire list.  That's just WRONG!
    return new ArrayList<SystemStateEntry>(this.entries.values());
  }
  
  /**
   * Returns a list of all currently defined system names. 
   * @return The list of system names.
   */
  @Override
  public List<String> getSystemNames() {
    // Returning an empty list.  That's just WRONG!
    return new ArrayList<String>();
  }
  
  /**
   * Returns a list of all the device names associated with the passed SystemName.
   * @param systemName A string indicating a system name. 
   * @return A list of device names for this system name.
   * @throws SystemStateEntryDBException if the system name is not known.
   */
  @Override
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    // Returning an empty list.  That's just WRONG!
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
  @Override
  public void addSystemStateListener(SystemStateListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Sends a message to the specified system and device instructing them to perform
   * this command along with any other required arguments. 
   * @param systemName The system.
   * @param deviceName The device.
   * @param command The command.
   * @param args Any arguments required for this command. 
   */
  public void doCommand(String systemName, String deviceName, String command, List<String> args) {
    System.out.format("Sending the %s system the command %s with args %s%n",
        systemName, command, args);
  }
}
