package edu.hawaii.ihale.api;

/**
 * Provides information about Hvac system of the house.
 * 
 * @author Team Kohola
 */
public class Hvac {

  private String timestamp;
  private long roomTemperature;

  /**
   * Creates a Hvac instance given its field values.
   * 
   * @param roomTemperature The room temperature in Celsius.
   * @param waterVolume The water volume in the tank.
   */
  public Hvac(String timestamp, long roomTemperature) {
    this.timestamp = timestamp;
    this.roomTemperature = roomTemperature;    
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
   * Returns the timestamp associated with this instance.
   * 
   * @return The timestamp.
   */
  public String getTimestamp() {
    return this.timestamp;
  }
}
