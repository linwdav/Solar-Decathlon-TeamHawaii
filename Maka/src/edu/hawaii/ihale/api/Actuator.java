package edu.hawaii.ihale.api;

/** 
 * A Class representing an Actuator Object, returns data in XML Format.
 * @author Team Maka
 */
public class Actuator extends Device {
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (eg "Aquaponics").
   * @param deviceDescription String describing the location and function of the device.
   */
  public Actuator(String subSystemName, String deviceDescription) {
    super(subSystemName, deviceDescription);
  }

  /**
   * Activates the Actuator.
   */
  public void putCommand(Document command) {
    // Invoke Environment invokeChange().
  }
}
