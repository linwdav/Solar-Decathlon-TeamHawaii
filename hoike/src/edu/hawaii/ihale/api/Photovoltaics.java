package edu.hawaii.ihale.api;

/**
 * ADT to store information about the Photovoltaics system.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class Photovoltaics {
  
  /** Fields for the system. */
  private long timestamp;
  private String meter;
  private float energy;
  private float energyWS;
  private float power;
  private float cpower;
  private float frequency;
  
  /**
   * Default constructor.
   * 
   * @param meter The device these measurements are associated with.
   * @param energy A cumulative energy meter value expressed in units of kilo-Watt/hour.
   * @param energyWS A cumulative energy meter value expressed in units of Watt/second.
   * @param power The average power measured.
   * @param cpower The current value of the power component.
   * @param frequency The frequency in Hertz.
   * @param timestamp The time to when these measurements were obtained, measured in the seconds
   *                  elapsed since January 1st, 1970 (UTC).
   */
  public Photovoltaics(String meter, float energy, float energyWS, float power, float cpower,
      float frequency, long timestamp) {
    this.meter = meter;
    this.energy = energy;
    this.energyWS = energyWS;
    this.power = power;
    this.cpower = cpower;
    this.frequency = frequency;
    this.timestamp = timestamp;
  }

  /**
   * Returns the meter device association.
   * 
   * @return The meter device association (i.e., Solar or Grid).
   */
  public String getMeterDevice() {
    return this.meter;
  }
  
  /**
   * Returns the energy meter value expressed in units of kW/h.
   * 
   * @return The energy meter value.
   */
  public float getEnergy() {
    return this.energy;
  }
  
  /**
   * Returns the energy meter value expressed in units of W/s.
   * 
   * @return The energy meter value.
   */
  public float getEnergyWS() {
    return this.energyWS;
  }
  
  /**
   * Returns the average power.
   * 
   * @return The average power.
   */
  public float getPower() {
    return this.power;
  }
  
  /**
   * Returns the current value of the power component.
   * 
   * @return The current value of the power component.
   */
  public float getCPower() {
    return this.cpower;
  }
  
  /**
   * Returns the frequency measured in Hertz.
   * 
   * @return The frequency.
   */
  public float getFrequency() {
    return this.frequency;
  }
  
  /**
   * Returns the timestamp of when these measurements were obtained.
   *
   * @return The timestamp.
   */
  public long getTimestamp() {
    return this.timestamp;
  }
}
