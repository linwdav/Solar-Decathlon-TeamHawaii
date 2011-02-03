package edu.hawaii.ihale.api;

/** 
 * A Class representing an Actuator Object, returns data in XML Format.
 * @author Team Maka
 */
public class Actuator extends Device {
  /** The goal state of the Actuator. The Actuator will attempt to 
   *  maintain this state.*/
  private Object goal;
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (eg "Aquaponics").
   * @param deviceDescription String describing the location and function of the device.
   */
  Actuator(String subSystemName, String deviceDescription) {
    super(subSystemName, deviceDescription);
  }
  
  /**
   * Set the desired goal for the Actuator.
   * @param obj The new goal.
   */
  public void setGoal(Object obj) {
    goal = obj;
  }
  
  /**
   * Returns the Actuator's goal.
   * @return The Actuator's goal.
   */
  public Object getGoal() {
    return goal;
  }
  
  /**
   * Activates the Actuator.
   */
  public void doOperation() {
    //Tell actuator to do it's job
  }
}
