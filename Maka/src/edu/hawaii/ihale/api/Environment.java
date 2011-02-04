package edu.hawaii.ihale.api;

import java.util.Map;

/**
 * A room-based sub-grouping that allows interaction between 
 * Sensors and Actuators of various SubSystems.  This class
 * should be extended, and all Devices should be instantiated
 * in the new Class.  This means that any Device defined should 
 * have a controller/accessor method.
 * 
 * @author Team Maka
 *
 */
public abstract class Environment {

	/** Unique sensor for GET requests on the Environment. */
	@SuppressWarnings("unused")
  private Map<String,Sensor> sensors;
	/** Unique actuator for PUT requests on the Environment. */
	@SuppressWarnings("unused")
  private Map<String,Actuator> actuators;
	/** String holding the Environment name (e.g. "livingroom").*/
  private String environmentName;

  /**
   * Returns the environment's name as a String.
   * @return A String containing the environment's name.
   */
  public String getEnvironmentName() {
    return environmentName;
  }
}
