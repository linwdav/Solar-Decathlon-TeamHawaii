package edu.hawaii.ihale.api;

/**
 * Provides a record of meter readings for the aquaponics system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class Aquaponics {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage of the system. */
  private double currentPower;
  /** The water level in the aquaponics pond. */
  private double waterLevel;
  /** PH level in the pond. */
  private double phLevel;
  /** Pond's water temperature. */
  private double waterTemperature;
  /** Pond's air temperature. */
  private double airTemperature;
  
  /**
   * Creates an Aquaponics object given the required parameters.
   * @param timestamp timestamp of when data was read
   * @param currentPower current power usage of this system
   * @param waterLevel water level in the pond
   * @param phLevel pH level in the pond
   * @param waterTemperature water temp of the pond
   * @param airTemperature air temp in the system
   */
  public Aquaponics (float timestamp, double currentPower, double waterLevel, 
          double phLevel, double waterTemperature, double airTemperature) {
    this.timestamp = timestamp;
    this.currentPower = currentPower;
    this.waterLevel = waterLevel;
    this.phLevel = phLevel;
    this.waterTemperature = waterTemperature;
    this.airTemperature = airTemperature;
  }

  /**
   * Return this object as a formatted string.
   * @return The object as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Water Level: %s pH Level: %s " +
                   "Water Temp: %s Air Temp: %s]", this.timestamp, this.currentPower, 
                    this.waterLevel, this.phLevel, this.waterTemperature, this.airTemperature);
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

  /**
   * Sets the phLevel in the pond.
   * @param phLevel the current phLevel in the pond
   */
  public void setPhLevel(double phLevel) {
    this.phLevel = phLevel;
  }

  /**
   * Gets the current pHLevel of the pond.
   * @return phLevel
   */
  public double getPhLevel() {
    return phLevel;
  }

  /**
   * Sets the current waterTemperature in the pond.
   * @param waterTemperature stores the current water temperature
   */
  public void setWaterTemperature(double waterTemperature) {
    this.waterTemperature = waterTemperature;
  }

  /**
   * Gets the current waterTemperature. 
   * @return waterTemperature
   */
  public double getWaterTemperature() {
    return waterTemperature;
  }

  /**
   * Sets the current airTemperature in the pond.
   * @param airTemperature stores the current air temperature
   */
  public void setAirTemperature(double airTemperature) {
    this.airTemperature = airTemperature;
  }

  /**
   * Gets the current airTemperature. 
   * @return airTemperature
   */ 
  public double getAirTemperature() {
    return airTemperature;
  }
  
}
