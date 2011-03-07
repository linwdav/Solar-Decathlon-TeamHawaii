package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
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
public class SystemStateEntryDAO implements SystemStateEntryDB {

  // Variable to store the device identifier
  String device = "";

  // Hard Coding of the Java API Data Dictionary

  // Aquaponics
  static List<String> aquaponicsDouble = Arrays.asList("ph", "oxygen");
  static List<String> aquaponicsLong = Arrays.asList("temp");

  // HVAC
  static List<String> HVACLong = Arrays.asList("temp");

  // Lighting
  static List<String> LightingLong = Arrays.asList("level");

  // Photovoltaics
  static List<String> PVLong = Arrays.asList("power", "energy");

  // Electricity Consumption
  static List<String> ECLong = Arrays.asList("power", "energy");

  // Holds an in-memory list of system state listeners.
  static List<SystemStateListener> listeners = new ArrayList<SystemStateListener>();

  /**
   * Sets the device.
   * 
   * Device numbers for arduinos are 1-1 correspondence.
   * 
   * egauge-1 is device 9 egauge-2 is device 10
   * 
   * @param deviceName The device number.
   */
  public void setDevice(String deviceName) {
    this.device = deviceName;
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
    listeners.add(listener);
  }

  @Override
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    // Create lookup object for database
    SystemStateAttributes attributes = new SystemStateAttributes(systemName, deviceName, timestamp);

    // Delete entry
    SystemStateEntryBerkeleyDB.deleteSystemStateEntryRecord(attributes);
  }

  @Override
  public void doCommand(String systemName, String deviceName, String command, List<String> value) {

    // Create XML file for PUT command
    Document doc = XmlMethods.createXml(deviceName, command, value);

    // Final portion of the actual URL
    String valueTitle = "";

    if (("setLevel").equalsIgnoreCase(command)) {
      valueTitle = "level";
    }
    else if ("setTemp".equalsIgnoreCase(command)) {
      valueTitle = "temp";
    }
    else if ("setPH".equalsIgnoreCase(command)) {
      valueTitle = "ph";
    }
    else if ("setOxygen".equalsIgnoreCase(command)) {
      valueTitle = "oxygen";
    }

    // Construct the correct uri to send the command to
    String dash = "-";
    String key = "";

    if ("arduino-5".equalsIgnoreCase(deviceName)) {
      key = systemName + dash + "living-control";
    }
    else if ("arduino-6".equalsIgnoreCase(deviceName)) {
      key = systemName + dash + "dining-control";
    }
    else if ("arduino-7".equalsIgnoreCase(deviceName)) {
      key = systemName + dash + "kitchen-control";
    }
    else if ("arduino-8".equalsIgnoreCase(deviceName)) {
      key = systemName + dash + "bathroom-control";
    }
    else {
      key = systemName + "-control";

    }

    System.out.println("System: " + key);

    // Get the URI for that particular system
    String uri = SimulatorInterface.getHosts().get(key);
    // Generate the URL to send the created XML for controls
    String url = uri + systemName + "/" + valueTitle;

    System.out.println("URL: " + url);

    try {
      // Send the XML representation of the command to the appropriate device
      ClientResource client = new ClientResource(url);
      DomRepresentation representation;
      representation = new DomRepresentation();
      representation.setDocument(doc);
      client.put(representation);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ResourceException e) {
      System.out.println("ERROR: " + e.getStatus());
    }

  } // End Do Command

  @Override
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    return SystemStateEntryBerkeleyDB.getDeviceNames(systemName);

  }

  @Override
  public List<SystemStateEntry> getEntries(String systemName, String deviceName, long startTime,
      long endTime) throws SystemStateEntryDBException {
    return SystemStateEntryBerkeleyDB.getEntries(systemName, deviceName, startTime, endTime);
  }

  @Override
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {

    // Create an attributes object (this is the composite key)
    SystemStateAttributes attributes = new SystemStateAttributes(systemName, deviceName, timestamp);

    // Get the database entity associated with the given parameters
    SystemStateEntryRecord record =
        SystemStateEntryBerkeleyDB.getSystemStateEntryRecord(attributes);

    if (record == null) {
      return null;
    }
    else {
      return convertRecord(record);
    }
  }

  @Override
  public List<String> getSystemNames() {
    return SystemStateEntryBerkeleyDB.getSystemNames();
  }

  @Override
  public void putEntry(SystemStateEntry entry) {

    // Create BerkeleyDB-Friendly Entity object
    SystemStateEntryRecord record = convertEntry(entry);

    // Insert into database
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(record);

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

    // Aquaponics Device (Arduino-1)
    if ("aquaponics".equalsIgnoreCase(device)) {
      return new SystemStateEntryRecord(entry, aquaponicsLong, aquaponicsDouble, null);
    }

    // HVAC Device (Arduino-3)
    else if ("hvac".equalsIgnoreCase(device)) {
      return new SystemStateEntryRecord(entry, HVACLong, null, null);
    }

    // Lighting Device (Arduino 5-8)
    else if ("lighting".equalsIgnoreCase(device)) {
      return new SystemStateEntryRecord(entry, LightingLong, null, null);
    }

    // Photovoltaic Device (egauge)
    else if ("photovoltaics".equalsIgnoreCase(device)) {
      return new SystemStateEntryRecord(entry, PVLong, null, null);
    }

    // Electrical Device (egauge)
    else if ("electrical".equalsIgnoreCase(device)) {
      return new SystemStateEntryRecord(entry, ECLong, null, null);
    }
    else {
      validDevice = false;
    }

    // If it's not a valid device, throw a database exception
    if (!validDevice) {
      try {
        throw new SystemStateEntryDBException("Not a valid device");
      }
      catch (SystemStateEntryDBException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }
    return null;
  } // End convertEntry

  /**
   * Converts SystemStateEntryRecord object into SystemStateEntry object.
   * 
   * @param record The SystemStateEntryRecord object
   * @return entry The converted record
   */
  public SystemStateEntry convertRecord(SystemStateEntryRecord record) {

    String systemName = record.getAttributes().getSystemName();
    String deviceName = record.getAttributes().getDeviceName();
    Long timestamp = record.getAttributes().getTimestamp();

    // New SystemStateEntry object
    SystemStateEntry entry = new SystemStateEntry(systemName, deviceName, timestamp);

    // Hash maps
    Map<String, Double> doubleData = record.getDoubleData();
    Map<String, Long> longData = record.getLongData();
    Map<String, String> stringData = record.getStringData();

    // Check for Map with double value
    if (doubleData != null) {
      for (Map.Entry<String, Double> en : doubleData.entrySet()) {
        String key = en.getKey();
        Double value = en.getValue();
        // Add to entry
        entry.putDoubleValue(key, value);
      }
    }

    // Check for map with long value
    if (longData != null) {
      for (Map.Entry<String, Long> en : longData.entrySet()) {
        String key = en.getKey();
        long value = en.getValue();
        // Add to entry
        entry.putLongValue(key, value);
      }
    }
    // Check for map with string value
    if (stringData != null) {
      for (Map.Entry<String, String> en : stringData.entrySet()) {
        String key = en.getKey();
        String value = en.getValue();
        // Add to entry
        entry.putStringValue(key, value);
      }
    }

    return entry;
  } // End convertRecord

} // End DB Class
