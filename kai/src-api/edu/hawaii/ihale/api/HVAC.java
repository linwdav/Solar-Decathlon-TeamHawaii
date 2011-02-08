package edu.hawaii.ihale.api;


/**
 * Provides a record of meter readings for the HVAC system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class HVAC {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage of the system. */
  private double currentPower;
  /** The exterior thermometer reading. */
  private double exteriorThermometer;
  /** The interior thermometer reading. */
  private double interiorThermometer;
  /** The state of the system. */
  private String hvacSystem;
  /** The mode that the system is in. */
  private String hvacMode;
  /** The current thermostat setting. */
  private double hvacThermostat;
  
  /**
   * Creates a new HVAC object to store readings.
   * @param timestamp the timstamp when readings were taken
   * @param currentPower the current power usage of the system
   * @param exteriorThermometer the exterior temp
   * @param interiorThermometer the interior temp
   * @param hvacSystem the state of the hvac system
   * @param hvacMode the mode of the hvac system
   */
  public HVAC (float timestamp, double currentPower, double exteriorThermometer, 
          double interiorThermometer, String hvacSystem, String hvacMode) {
    this.timestamp = timestamp;
    this.currentPower = currentPower;
    this.exteriorThermometer = exteriorThermometer;
    this.interiorThermometer = interiorThermometer;
    this.hvacSystem = hvacSystem;
    this.hvacMode = hvacMode;
  }

  /**
   * Return this object as a formatted string.
   * @return The object as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Exterior Temp: %s Interior Temp: %s " +
                   "HVAC State: %s HVAC Mode: %s HVAC Thermostat: %s]", this.timestamp, 
                   this.currentPower, this.exteriorThermometer, this.interiorThermometer, 
                   this.hvacSystem, this.hvacMode, this.hvacThermostat);
  }

/**
 * Sets the timestamp for the HVAC object.
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
   * Sets the exteriorThermometer reading.
   * @param exteriorThermometer the exterior temperature.
   */
  public void setExteriorThermometer(double exteriorThermometer) {
    this.exteriorThermometer = exteriorThermometer;
  }

  /**
   * Gets the exteriorThermometer reading.
   * @return exteriorThermometer
   */
  public double getExteriorThermometer() {
    return exteriorThermometer;
  }

  /**
   * Sets the interiorThermometer reading.
   * @param interiorThermometer the current interior temperature
   */
  public void setInteriorThermometer(double interiorThermometer) {
    this.interiorThermometer = interiorThermometer;
  }

  /**
   * Gets the interiorThermometer reading.
   * @return interiorThermometer
   */
  public double getInteriorThermometer() {
    return interiorThermometer;
  }

  /**
   * Sets the current hvacSystem state.
   * @param hvacSystem stores the current state
   */
  public void setHvacSystem(String hvacSystem) {
    this.hvacSystem = hvacSystem;
  }

  /**
   * Gets the current hvacSystem state. 
   * @return hvacSystem
   */
  public String getHvacSystem() {
    return hvacSystem;
  }

  /**
   * Sets the current hvacMode.
   * @param hvacMode stores the mode of the hvac
   */
  public void setHvacMode(String hvacMode) {
    this.hvacMode = hvacMode;
  }

  /**
   * Gets the current hvacMode. 
   * @return hvacMode
   */ 
  public String getHvacMode() {
    return hvacMode;
  }

  /**
   * Sets the hvacThermostat.
   * @param hvacThermostat the current thermostat setting
   */
  public void setHvacThermostat(double hvacThermostat) {
    this.hvacThermostat = hvacThermostat;
  }

  /**
   * Gets the hvacThermostat reading.
   * @return hvacThermostat
   */
  public double getHvacThermostat() {
    return hvacThermostat;
  }
  
}
