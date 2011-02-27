package edu.hawaii.ihale.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.KeyField;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Provides a record of a snapshot of the state of a particular system.
 * 
 * @author Team Nai'a
 */
@Entity
public class SystemStateEntryRecord {

  @PrimaryKey
  SystemStateAttributes attributes;

  private Map<String, Long> longData;
  private Map<String, String> stringData;
  private Map<String, Double> doubleData;

  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  String systemName;

  // The name of the device. This is strictly for retrieving data
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  String deviceName;

  /**
   * Provide the default constructor required by BerkeleyDB.
   */
  public SystemStateEntryRecord() {
    // BerkeleyDB requires a default constructor.
  }

  /**
   * Creates an instance of a SystemStateEntry Object.
   * 
   * @param entry The API entry to convert.
   * @param longList A list of long data keys.
   * @param doubleList A list of double data keys.
   * @param stringList A list of string data keys.
   */
  public SystemStateEntryRecord(SystemStateEntry entry, List<String> longList,
      List<String> doubleList, List<String> stringList) {

    this.systemName = entry.getSystemName();
    this.deviceName = entry.getDeviceName();

    // Transfer all system state attributes.
    this.attributes =
        new SystemStateAttributes(entry.getSystemName(), entry.getDeviceName(),
            entry.getTimestamp());

    // Transfer all long key-value pairs
    if (longList != null) {
      longData = new HashMap<String, Long>();
      for (String data : longList) {
        System.out.println("NAME: " + data);
        System.out.println("VALUE: " + entry.getLongValue(data));
        longData.put(data, entry.getLongValue(data));
      }
    } // End if

    // Transfer all double key-value pairs
    if (doubleList != null) {
      doubleData = new HashMap<String, Double>();
      for (String data : doubleList) {
        doubleData.put(data, entry.getDoubleValue(data));
      }
    } // End if

    // Transfer all string key-value pairs
    if (stringList != null) {
      stringData = new HashMap<String, String>();
      for (String data : stringList) {
        stringData.put(data, entry.getStringValue(data));
      }
    } // End if
  } // End constructor

  /**
   * Returns the attributes associated with this System State.
   * 
   * @return The attributes.
   */
  public SystemStateAttributes getAttributes() {
    return this.attributes;
  }

  /**
   * Converts Entity object to an API System State Entry object.
   * 
   * @return The converted API entry object
   */
  public SystemStateEntry convertToEntry() {

    // Create a new entry object using the attributes from the current entity
    SystemStateEntry entry =
        new SystemStateEntry(attributes.getSystemName(), attributes.getDeviceName(),
            attributes.getTimestamp());

    // Import key-value pairs into the entry (Long values)
    if (longData != null) {
      for (Map.Entry<String, Long> pair : longData.entrySet()) {
        entry.putLongValue(pair.getKey(), pair.getValue());
      }
    } // End IF

    // Import key-value pairs into the entry (Double values)
    if (doubleData != null) {
      for (Map.Entry<String, Double> pair : doubleData.entrySet()) {
        entry.putDoubleValue(pair.getKey(), pair.getValue());
      }
    } // End IF

    // Import key-value pairs into the entry (String values)
    if (stringData != null) {
      for (Map.Entry<String, String> pair : stringData.entrySet()) {
        entry.putStringValue(pair.getKey(), pair.getValue());
      }
    } // End IF

    return entry;
  }

  /**
   * Accessor Method for Key-value pairs of type Long.
   * 
   * @return Map of data of type Long
   */
  public Map<String, Long> getLongData() {
    return longData;
  }

  /**
   * Accessor Method for Key-value pairs of type Double.
   * 
   * @return Map of data of type Double
   */
  public Map<String, Double> getDoubleData() {
    return doubleData;
  }

  /**
   * Accessor Method for Key-value pairs of type String.
   * 
   * @return Map of data of type String
   */
  public Map<String, String> getStringData() {
    return stringData;
  }

  /**
   * Return this System State as a formatted string.
   * 
   * @return The system state as a string.
   */
  @Override
  public String toString() {
    return attributes.toString();
  }

} // End SystemStateRecord class

/**
 * An attribute class to hold the composite key for the database. Consists of the System Name,
 * Device Name, and Timestamp.
 * 
 * @author Team Nai'a
 * 
 */
@Persistent
class SystemStateAttributes {

  /** The name of the System. Example: "Aquaponics". */
  @KeyField(1)
  private String systemName;

  /** The name of the Device. Example: "Arduino-23". */
  @KeyField(2)
  private String deviceName;

  /** The timestamp (UTC format) indicating when this state info was collected. */
  @KeyField(3)
  private long timestamp;

  /**
   * Provide the default constructor required by BerkeleyDB.
   */
  public SystemStateAttributes() {
    // Default Constructor for Berkeley DB
  }

  /**
   * Constructor.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public SystemStateAttributes(String systemName, String deviceName, long timestamp) {
    this.systemName = systemName;
    this.deviceName = deviceName;
    this.timestamp = timestamp;
  }

  /**
   * Accessor method for System Name.
   * 
   * @return the system name.
   */
  public String getSystemName() {
    return systemName;
  }

  /**
   * Accessor method for Device Name.
   * 
   * @return the device name.
   */
  public String getDeviceName() {
    return deviceName;
  }

  /**
   * Accessor method for timestamp.
   * 
   * @return the timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Return this attribute object as a formatted string.
   * 
   * @return The attributes as a string.
   */
  @Override
  public String toString() {
    return String.format(
        "[System State Attributes: System Name: %s Device Name: %s Timestamp: %d]",
        this.systemName, this.deviceName, this.timestamp);
  }

} // End Composite Key Class

