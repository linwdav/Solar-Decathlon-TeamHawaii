package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the Aquaponics System class to hold all
 * information regarding the Aquaponics at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class Aquaponics extends GenericSystem {

  private double waterLevel;
  private double capacity;
  private double phLevel;
  private double algaeContent;
  private double energyUse;
  private double temperature;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   * @param waterLevel double
   * @param capacity double
   * @param phLevel double
   * @param algaeContent double
   * @param energyUse double
   * @param temperature double
   */
  public Aquaponics(String title, String desc, long timestamp, int status, double waterLevel,
      double capacity, double phLevel, double algaeContent, double energyUse, double temperature) {
    super(title, desc, timestamp, status);
    this.waterLevel = waterLevel;
    this.capacity = capacity;
    this.phLevel = phLevel;
    this.algaeContent = algaeContent;
    this.energyUse = energyUse;
    this.temperature = temperature;
  }

  /**
   * Gets this System's water level.
   * 
   * @return double
   */
  public double getWaterLevel() {
    return waterLevel;
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
   * Get this System's PH Level.
   * 
   * @return double
   */
  public double getPhLevel() {
    return phLevel;
  }

  /**
   * Get this System's alage content.
   * 
   * @return double
   */
  public double getAlgaeContent() {
    return algaeContent;
  }

  /**
   * Get this System's energy use.
   * 
   * @return double
   */
  public double getEnergyUse() {
    return energyUse;
  }

  /**
   * Gets this System's temperature.
   * 
   * @return double
   */
  public double getTemperature() {
    return temperature;
  }
}
