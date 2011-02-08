package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the Aquaponics System class to hold all
 * information regarding the Aquaponics at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class WaterHeater extends GenericSystem {

  private double capacity;
  private double level;
  private int quality;
  private double temperature;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   * @param capacity double
   * @param level double
   * @param quality int
   * @param temperature double
   */
  public WaterHeater(String title, String desc, long timestamp, int status, double capacity,
      double level, int quality, double temperature) {
    super(title, desc, timestamp, status);
    this.capacity = capacity;
    this.level = level;
    this.quality = quality;
    this.temperature = temperature;
  }

  /**
   * Gets this System's water capacity.
   * 
   * @return double
   */
  public double getCapacity() {
    return capacity;
  }

  /**
   * Gets this System's water level.
   * 
   * @return double
   */
  public double getLevel() {
    return level;
  }

  /**
   * Gets this System's water quality. (i.e. hard or soft).
   * 
   * @return int
   */
  public int getQuality() {
    return quality;
  }

  /**
   * Gets this System's water temperature.
   * 
   * @return double
   */
  public double getTemperature() {
    return temperature;
  }

}
