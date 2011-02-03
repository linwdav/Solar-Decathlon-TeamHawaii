package edu.hawaii.ihale.api;

/**
 * An Object used by HSIM.  Actuators will change this object, and Sensors will
 * read data from it.
 * @author Team Maka
 *
 */
public class Environment {

	/** Unique sensor for GET requests on the Environment. */
	private Sensor;
	/** Unique actuator for PUT requests on the Environment. */
	private Actuator;

	/**
	 * Polls environment data.
	 * @return data The current data reading of the Environment.
	 */
	public Document getData() {
		// To be implemented later.
		return null;
	}
	
	/**
	 * Issues command to envinronment.
	 */
	public void putCommand(Document command) {
		// To be implemented later.
	}
}
