package edu.hawaii.ihale.api;

/**
 * Provides information about the Hvac system.
 * 
 * @author Team Kohola
 */
public class Hvac {

  private long roomTemperature;
  private long waterVolume;

  /**
   * Creates a Hvac instance given its field values.
   * 
   * @param roomTemperature The room temperature in Celsius.
   * @param waterVolume The water volume in the tank.
   */
  public Hvac(long roomTemperature, long waterVolume) {
    this.roomTemperature = roomTemperature;
    this.waterVolume = waterVolume;
  }

  /**
   * Set the desired temperature.
   * 
   * @param desiredTemperature The temperature in Celsius.
   */
  public void setTemperature(long desiredTemperature) {
    this.roomTemperature = desiredTemperature;
  }

  /**
   * Returns the desired temperature.
   * 
   * @return The temperautre in Celsius.
   */
  public long getTemperature() {
    return this.roomTemperature;
  }

  /**
   * Set the water volume in the water tank.
   * 
   * @param waterVolume The water volume in liter.
   */
  public void setWaterVolume(long waterVolume) {
    this.waterVolume = waterVolume;
  }

  /**
   * Returns the water volume in the water tank.
   * 
   * @return The water volume in liter.
   */
  public long getWaterVolume() {
    return this.waterVolume;
  }
}
