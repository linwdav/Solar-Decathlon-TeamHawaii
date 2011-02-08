package edu.hawaii.ihale.api;


/**
 * Provides a record of meter readings for the water system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class Water {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage of the system. */
  private double currentPower;
  /** The state of the system. */
  private String waterSystem;
  /** The state of the heating system. */
  private String waterHeating;
  /** The water level in the water tank. */
  private double waterLevel;

  /**
   * Creates a new Water object for storing readings.
   * @param timestamp the timestamp when the readings were taken
   * @param currentPower the current power usage of the system
   * @param waterSystem the state of the water system 
   * @param waterHeating the state of the water heating system
   * @param waterLevel the water level in the water tank
   */
  public Water(float timestamp, double currentPower, String waterSystem, String waterHeating,
           double waterLevel) {
      this.timestamp = timestamp;
      this.currentPower = currentPower;
      this.waterSystem = waterSystem;
      this.waterHeating = waterHeating;
      this.waterLevel = waterLevel;
  }
  
  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Water System: %s Water Heating: %s " +
                   "Water Level: %s]", this.timestamp, this.currentPower, this.waterSystem,
                   this.waterHeating, this.waterLevel);
  }

 /**
  * Sets the timestamp for the aquaponics object.
  * @param timestamp the time when reading was taken
  */
  public void setTimestamp(float timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Returns the timestamp of when readings were taken.
   * @return timestamp
   */
  public float getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the currentPower usage.
   * @param currentPower the current power usage reading
   */
  public void setCurrentPower(double currentPower) {
    this.currentPower = currentPower;
  }

  /**
   * Gets the currentPower usage.
   * @return currentPower
   */
  public double getCurrentPower() {
    return currentPower;
  }

  /**
   * Sets the state of the water system.
   * @param waterSystem the state of the water system
   */
  public void setWaterSystem(String waterSystem) {
    this.waterSystem = waterSystem;
  }

  /**
   * Gets the state of the water system.
   * @return waterSystem
   */
  public String getWaterSystem() {
    return waterSystem;
  }
  
  /**
   * Sets the state of the waterHeating system.
   * @param waterHeating the state of the water heating system.
   */
  public void setWaterHeating(String waterHeating) {
    this.waterHeating = waterHeating;
  }
  
  /**
   * Gets the state of the waterHeating system.
   * @return waterHeating
   */
  public String getWaterHeating() {
    return waterHeating;
  }

  /**
   * Sets the waterLevel of the pond.
   * @param waterLevel the current water level in the pond.
   */
  public void setWaterLevel(double waterLevel) {
    this.waterLevel = waterLevel;
  }

  /**
   * Gets the waterLevel of the pond.
   * @return waterLevel
   */
  public double getWaterLevel() {
    return waterLevel;
  }

}
