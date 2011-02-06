package edu.hawaii.ihale.api;

/**
 * Provides information about the aquaponics system.
 * 
 * @author Team Kohola
 */
public class Aquaponics {

  private String timestamp;
  private long waterOxygen;
  private long waterTemperature;
  private long waterPh;
  private long waterVolume;

  /**
   * Creates an aquaponics instance given its field values.
   * 
   * @param timestamp The time when this instance is created.
   * @param waterOxygen The oxygen concentration. (unit: ppm)
   * @param waterTemperature The temperature in Celsius.
   * @param waterPh The water PH value.
   * @param waterVolume The water volume in liter.
   */
  public Aquaponics(String timestamp, long waterOxygen, long waterTemperature, long waterPh,
      long waterVolume) {
    this.timestamp = timestamp;
    this.waterOxygen = waterOxygen;
    this.waterTemperature = waterTemperature;
    this.waterPh = waterPh;
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
   * Returns the oxygen concentration associated with this instance.
   * 
   * @return The oxygen concentration in ppm.
   */
  public long getWaterOxygen() {
    return this.waterOxygen;
  }

  /**
   * Returns the water temperature associated with this instance.
   * 
   * @return The water temperature in Celsius.
   */
  public long getWaterTemperature() {
    return this.waterTemperature;
  }

  /**
   * Returns the water PH value associated with this instance.
   * 
   * @return The water PH value.
   */
  public long getWaterPh() {
    return this.waterPh;
  }

  /**
   * Returns the water volume associated with this instance.
   * 
   * @return The water volume in liter.
   */
  public long getWaterVolume() {
    return this.waterVolume;
  }

  /**
   * Return this contact as a formatted string.
   * 
   * @return The contact as a string.
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s\n Temperature: %s\n PH: %s\n Volume: %s\n]",
        this.timestamp, this.waterTemperature, this.waterPh, this.waterVolume);
  }
}
