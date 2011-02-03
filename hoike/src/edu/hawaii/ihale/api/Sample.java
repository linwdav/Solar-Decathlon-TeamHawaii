package edu.hawaii.ihale.api;

import java.util.Date;
import java.util.Map;


/**
 * Provides a simple record of information about a person.
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class Sample {

  /** all data will be stored in this map. */
  private Map<String, String> data;
  
  /** The contact's unique ID. */
  private long uniqueTime;
  /** The unique ID element name. */
  
  private String systemID;
  
  /**
   * Creates a Contact instance given its field values as strings. 
   * @param data The map holding the data.
   * @param systemID String holds name of system.
 
   */
  public Sample (Map<String, String> data, String systemID) {
    this.data = data;
    this.uniqueTime = (new Date()).getTime();
    this.systemID = systemID;
  }
  
  /**
   * Returns the unique time associated with this contact.
   * @return The unique time.
   */
  public long getUniqueTime() {
    return this.uniqueTime;
  }

  /**
   * Returns the unique time associated with this contact.
   * @return The unique time.
   */
  public String getSystemID() {
    return this.systemID;
  }
  
  /**
   * Returns the data associated to the key.
   * @param key The type of data to retrieve from the map.
   * @return The data associated to the key.
   */
  public String getData(String key) {
    return data.get(key);
  }
  
  /**
   * Return this sample as a formatted string.
   * @return The sample as a string. 
   */
  @Override
  public String toString() {
    return "in development.";
  }
  
}
