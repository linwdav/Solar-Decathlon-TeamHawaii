package edu.hawaii.ihale.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * This class acts as an interface between the Berkeley database and the Java API for the iHale
 * Back-End System.
 * 
 * @author Team Nai'a
 * 
 */
public class SystemStateEntryBerkeleyDB implements SystemStateEntryDB {

  // Variable to store the device identifier
  int device = 0;

  // Hard Coding of the Java API Data Dictionary

  // Aquaponics
  static List<String> aquaponicsDouble = Arrays.asList("pH", "Oxygen");
  static List<String> aquaponicsLong = Arrays.asList("Temp");

  // HVAC
  static List<String> HVACLong = Arrays.asList("Temp");

  // Lighting
  static List<String> LightingLong = Arrays.asList("Level");

  // Holds an in-memory list of system state listeners.
  private List<SystemStateListener> listeners = new ArrayList<SystemStateListener>();

  /**
   * Sets the device.
   * 
   * @param i The device number.
   */
  public void setDevice(int i) {
    this.device = i;
  }

  /**
   * Adds a listener to this repository whose entryAdded method will be invoked whenever an entry is
   * added to the database for the system name associated with this listener. This method provides a
   * way for the user interface (for example, Wicket) to update itself whenever new data comes in to
   * the repository.
   * 
   * @param listener The listener whose entryAdded method will be called.
   */
  @Override
  public void addSystemStateListener(SystemStateListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    // Create lookup object for database
    SystemStateAttributes attributes = new SystemStateAttributes(systemName, deviceName, timestamp);

    // Delete entry
    SystemStateEntryRecordDAO.deleteSystemStateEntryRecord(attributes);
  }

  @Override
  public void doCommand(String systemName, String deviceName, String command, List<String> arg3) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    return SystemStateEntryRecordDAO.getDeviceNames(systemName);
  }

  @Override
  public List<SystemStateEntry> getEntries(String systemName, String deviceName, long startTime,
      long endTime) throws SystemStateEntryDBException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {

    // Create an attributes object (this is the composite key)
    SystemStateAttributes attributes = new SystemStateAttributes(systemName, deviceName, timestamp);

    // Get the database entity associated with the given parameters
    SystemStateEntryRecordDAO.getSystemStateEntryRecord(attributes);

    return null;
  }

  @Override
  public List<String> getSystemNames() {
    return SystemStateEntryRecordDAO.getSystemNames();
  }

  @Override
  public void putEntry(SystemStateEntry entry) {

    // Create BerkeleyDB-Friendly Entity object
    SystemStateEntryRecord record = convertEntry(entry);

    // Insert into database
    SystemStateEntryRecordDAO.putSystemStateEntryRecord(record);

    // Here is where the listeners get invoked if they are interested in this systemName.
    for (SystemStateListener listener : listeners) {
      if (entry.getSystemName().equals(listener.getSystemName())) {
        listener.entryAdded(entry);
      }
    } // End for each

  } // End putEntry

  /**
   * Converts the SystemStateEntry into a Berkeley DB friendly object.
   * 
   * @param entry The SystemStateEntry to convert
   * @return Berkeley DB-Friendly SystemStateEntryRecord Entity
   */
  public SystemStateEntryRecord convertEntry(SystemStateEntry entry) {

    boolean validDevice = true;

    // Converts the entry to an Entity object
    switch (device) {

    // Aquaponics Device (Arduino-1)
    case 1:
      return new SystemStateEntryRecord(entry, aquaponicsLong, aquaponicsDouble, null);

      // HVAC Device (Arduino-3)
    case 3:
      return new SystemStateEntryRecord(entry, HVACLong, null, null);

      // Lighting Devices (Arudino 5-8)
    case 5:
    case 6:
    case 7:
    case 8:
      return new SystemStateEntryRecord(entry, LightingLong, null, null);

      // If no applicable devices are given, then set boolean value to false
    default:
      validDevice = false;
      break;
    } // End Switch

    // If it's not a valid device, throw a database exception
    if (!validDevice) {
      try {
        throw new SystemStateEntryDBException("Not a valid device");
      }
      catch (SystemStateEntryDBException e) {
        // TODO Auto-generated catch block
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }
    return null;
  } // End convertEntry

} // End DB Class
