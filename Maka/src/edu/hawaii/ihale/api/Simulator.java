package edu.hawaii.ihale.api;

/**
 * An Object that simulates data for the iHale systemm.  
 * @author Team Maka
 */
  public abstract class Simulator {
  /** Simulated data*/
  protected Document data;
  /** The Sensor associated with this simulator*/
  protected List<Sensor> sensorList;
  /** The Actuator associated with this simulator.*/
  protected List<Actuator> actuatorList;
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (e.g. "Aquaponics").
   * @param port The port number the REST interface will run on.
   */
  public SubSystemObject() {
	this.sensorList = new List<Sensor>();
	this.actuatorList = new List<Actuator>();
  }
  
  /**
   * Returns the Simulator's data object.
   * @return data This Simulator's data Document object. 
   */
  public abstract Document get();
 
  /**
   * Issues simulator with command to change current variables.
   */
  public abstract void put(Document data);

}
