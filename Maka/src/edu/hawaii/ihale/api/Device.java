package edu.hawaii.ihale.api;

/**
 * An Object that represents a Device used in the Solar Decathlon Home.
 * Extended by Sensor and Actuator.
 * @author Team Maka
 *
 */
public class Device {
  /** ensures unique IDs.*/
  private static int idCounter = 100;
  /** A unique Identifier.**/
  protected int ID;
  /** A String describing the device and it's location.**/
  protected String deviceDescription;
  /** A String holding the name of the subSystem to which this Device belongs.*/
  protected String subSystemName;
  
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (eg "Aquaponics").
   * @param deviceDescription String describing the location and function of the device.
   */
  Device(String subSystemName, String deviceDescription) {
    ID = idCounter;
    idCounter ++;
    this.subSystemName = subSystemName;
    this.deviceDescription = deviceDescription;
  }
  
  /**
   * Returns a String containing the sensor's description and location.
   * @return A String containing the sensor's description and location.
   */
  public String getDeviceDescription() {
    return deviceDescription;
  }
  
  /**
   * Returns a String representing the SubSystem to which this Sensor belongs.
   * @return A String with the SubSystem's name.
   */
  public String getSubSystemName() {
    return subSystemName;
  }
  
  /**
   * Returns the Device's ID number.
   * @return An int representing the ID.
   */
  public int getID() {
    return ID;
  }
  
  /**
   * Returns a String representation of the Device.
   * @return The String representation of the device.
   */
  public String toString() {
    return "" + subSystemName + ID + "  " + deviceDescription;
  }
}
