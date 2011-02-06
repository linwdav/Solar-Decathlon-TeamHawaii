package edu.hawaii.ihale.api;


/**
 * Provides information about the water system of the house.
 * 
 * @author Team Kohola
 */
public class Water {
  
  private String timestamp;
  private long waterVolume;
  
  
  /**
   * Creates a water instance given its field values.
   * 
   * @param timestamp The time when this instance was created.
   * @param waterVolume The water volume in the tank in liter.
   */
  public Water (String timestamp, long waterVolume) {
    this.timestamp = timestamp;
    this.waterVolume = waterVolume;
  }
  
  /**
   * Returns the timestamp associated with this instance.
   * 
   * @return The time when this instance is created.
   */
  public String getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Returns the water volume associated with this instance.
   * 
   * @return The water volume in liter.
   */
  public long getWaterVolume() {
    return this.waterVolume;
  }
}
