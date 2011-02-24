package edu.hawaii.ihale.backend.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

/**
 * The database entity representation of the SystemStateEntry object specific to the iHale
 * house systems.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
@Entity
public class IHaleSystemStateEntry {

  /** The timestamp (UTC format) indicating when this state info was collected. This is
   *  also the Primary Key.
   */
  @PrimaryKey
  private long timestamp;
  
  /**
   * The name of the System. Example: "Aquaponics". This is also the Secondary Key.
   * There can be many entries with the same system name but must have a unique timestamp 
   * (the primary key) value.
   */
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String systemName;
  
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String deviceName;
  
  private Map<String, Long> longData = new HashMap<String, Long>();
  private Map<String, String> stringData = new HashMap<String, String>();
  private Map<String, Double> doubleData = new HashMap<String, Double>();
  
  /**
   * Provide the default constructor required by BerkeleyDB.
   */
  public IHaleSystemStateEntry() {
    // BerkeleyDB requires a default constructor. 
  }
  
  /**
   * A simple constructor that has no initial Map data.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public IHaleSystemStateEntry(String systemName, String deviceName, long timestamp) {
    this.systemName = systemName;
    this.deviceName = deviceName;
    this.timestamp = timestamp;
  }
  
  /**
   * A constructor with initial Map data.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @param longData  State information associated with this entry that are of Long value type.
   * @param stringData State information associated with this entry that are of String value type.
   * @param doubleData State information associated with this entry that are of Double value type.
   */
  public IHaleSystemStateEntry(String systemName, String deviceName, long timestamp,
      Map<String, Long> longData, Map<String, String> stringData, Map<String, Double> doubleData) {
    this.timestamp = timestamp;
    this.systemName = systemName;
    this.deviceName = deviceName;
    this.longData = longData;
    this.stringData = stringData;
    this.doubleData = doubleData;
  }
  
  /**
   * Purpose of this method is to suppress errors and pass verify.
   * Delete this method later when the variables are actually used locally.
   */
  public void methodToSuppressUnusedVariables() {
    System.out.println(timestamp);
    System.out.println(systemName);
    System.out.println(deviceName);
  }
  
  /**
   * Returns the system name associated with this contact.
   * @return The system name.
   */
  public String getSystemName() {
    return this.systemName;
  }
  
  /**
   * Returns the device name associated with this contact.
   * @return The device name.
   */
  public String getDeviceName() {
    return this.deviceName;
  }
  
  /**
   * Returns the timestamp associated with this entry.
   * @return The timestamp.
   */
  public long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Returns a double state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The double state value.
   */
  public double getDoubleValue(String key) {
    return this.doubleData.get(key);
  }
  
  /**
   * Returns a long state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The long state value.
   */
  public long getLongValue(String key) {
    return this.longData.get(key);
  }
  
  /**
   * Returns a string state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The double state value.
   */
  public String getStringValue(String key) {
    return this.stringData.get(key);
  }
  
  /**
   * Sets the double value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putDoubleValue(String key, Double value) {
    this.doubleData.put(key, value);
  }
  
  /**
   * Sets the long value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putLongValue(String key, long value) {
    this.longData.put(key, value);
  }
  
  /**
   * Sets the double value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putStringValue(String key, String value) {
    this.stringData.put(key, value);
  }  
  
  /**
   * Returns a Map containing Long value types.
   *
   * @return Map of Long values. 
   */
  public Map<String, Long> getLongData() {
    return this.longData;
  }
  
  /**
   * Returns a Map containing String value types.
   *
   * @return Map of Long values. 
   */
  public Map<String, String> getStringData() {
    return this.stringData;
  }
  
  /**
   * Returns a Map containing Double value types.
   *
   * @return Map of Long values. 
   */
  public Map<String, Double> getDoubleData() {
    return this.doubleData;
  }
  
  /**
   * Return this state entry as a formatted string.
   * @return The state entry as a string. 
   */
  @Override
  public String toString() {
    return String.format(
        "[StateEntry System: %s Device: %s Time: %s (%s) State: %s %s %s]", 
        this.systemName, this.deviceName, this.timestamp, new Date(this.timestamp), 
        this.stringData, this.longData, this.doubleData);
  }
}
