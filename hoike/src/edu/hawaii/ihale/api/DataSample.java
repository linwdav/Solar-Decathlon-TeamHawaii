package edu.hawaii.ihale.api;

import java.util.Date;
import java.util.Map;

/**
 * Provides a simple record of information about a sample.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class DataSample {

  /** All data will be stored in this map. */
  private Map<String, String> data;

  /** The sample's unique ID. */
  private long uniqueTime;
  /** The unique ID element name. */

  /** The sample's secondary key, name of the system. */
  private String systemID;

  /**
   * Creates a DataSample instance given its field values as strings.
   * 
   * @param data The map holding the data.
   * @param systemID String holds name of system.
   */
  public DataSample(Map<String, String> data, String systemID) {
    this.data = data;
    this.uniqueTime = (new Date()).getTime();
    this.systemID = systemID;
  }

  /**
   * Returns the entire sample instance.
   * 
   * @return The sample.
   */
  public DataSample getSample() {
    return this;
  }

  /**
   * Returns the unique time associated with this sample.
   * 
   * @return The unique time.
   */
  public long getUniqueTime() {
    return this.uniqueTime;
  }

  /**
   * Returns the system name associated with this sample.
   * 
   * @return The system name.
   */
  public String getSystemID() {
    return this.systemID;
  }

  /**
   * Returns all data in this sample.
   * 
   * @return The data map.
   */
  public Map<String, String> getData() {
    return this.data;
  }

  /**
   * Returns the data associated to the key.
   * 
   * @param key The type of data to retrieve from the map.
   * @return The data associated to the key.
   */
  public String getKey(String key) {
    return this.data.get(key);
  }

  /**
   * Return this sample as a formatted string.
   * 
   * @return The sample as a string.
   */
  @Override
  public String toString() {
    return "in development.";
  }

}
