package edu.hawaii.ihale.api;

import java.util.Date;
import java.util.Map;


/**
 * Provides a simple record of information on a component within a house system.
 * @author Team Hoike
 */
public class Component {

  /** All data will be stored in this map. */
  private Map<String, String> data;
  
  /** The component's ID, represented as a long value. */
  private long componentID;

  /** The identifier for the different systems within the house, i.e HVAC and energy. */
  private String systemID;
  
  /**
   * Creates a Component instance given its field values as strings. 
   * @param data The map holding the data.
   * @param systemID String holds name of system.
 
   */
  public Component (Map<String, String> data, String systemID) {
    this.data = data;
    this.componentID = (new Date()).getTime();
    this.systemID = systemID;
  }
  
  /**
   * Returns the ID associated with this component.
   * @return The component's ID.
   */
  public long getComponentID() {
    return this.componentID;
  }

  /**
   * Returns the unique time associated with this system.
   * @return The systemID.
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
   * Return this component as a formatted string.
   * @return The component as a string. 
   */
  @Override
  public String toString() {
    return "in development.";
  }
  
}
