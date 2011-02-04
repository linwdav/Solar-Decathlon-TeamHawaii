package edu.hawaii.ihale.api;

import org.w3c.dom.Document;

/** 
 * A Class representing an Actuator Object, returns data in XML Format.
 * @author Team Maka
 */
public class Actuator extends Device {
  
  /**
   * Constructor.
   * @param environmentName String representing the name of the Environment (eg "livingroom").
   * @param deviceDescription String describing the location and function of the device.
   */
  public Actuator(String environmentName, String deviceDescription) {
    super(environmentName, deviceDescription);
  }

  /**
   * Activates the Actuator.
   */
  public void putCommand(Document command) {
    // Invoke Environment invokeChange().
  }
}
