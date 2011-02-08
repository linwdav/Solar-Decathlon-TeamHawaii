package edu.hawaii.contactdb.api.db;


/**
 * Provides a simple record of information about a person.
 * @author Team Maka
 */
public class DBResource {
  /** The subSystem the resource belongs to. */
  private String subSystem;
  /** The device's unique ID. */
  private int sensorID;
  /** The data associated with this resource */
  private Object data;
  /** The timestamp corresponding to when the resource was added. */
  private Long timestamp;
  
  /**
   * Creates a resource instance given its field values as strings. 
   * @param subSystem The subSystem name.
   * @param sensorID The unique ID associated with the device.
   * @param data The data.
   */
  public Contact (String subSystem, int sensorID, Object data) {
    this.subSystem = subSystem;
    this.data = data;
    this.sensorID = sensorID;
  }
  
  /**
   * Returns the name of the subSystem associated with this resource.
   * @return subSystem The subSystem name.
   */
  public String getSubSystem() {
    return this.subSystem;
  }
  
  /**
   * Returns the last name associated with this contact.
   * @return data The data.
   */
  public Object getData() {
    return this.data;
  }

  /**
   * Returns the unique ID associated with this contact.
   * @return The unique ID.
   */
  public String getUniqueID() {
    return this.uniqueID;
  }
  
  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Sub System: %s ID: %s Data: %s]", this.subSystem, this.sensorID, 
        this.data);
  }
  
}
