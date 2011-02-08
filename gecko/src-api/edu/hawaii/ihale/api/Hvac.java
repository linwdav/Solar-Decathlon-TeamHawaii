package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the Aquaponics System class to hold all
 * information regarding the Aquaponics at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class Hvac extends GenericSystem {

  private double temperature;
  private double storedEnergy;
  private double airQuality;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   * @param temperature double
   * @param storedEnergy double
   * @param airQuality double
   */
  public Hvac(String title, String desc, long timestamp, int status, double temperature,
      double storedEnergy, double airQuality) {
    super(title, desc, timestamp, status);
    this.temperature = temperature;
    this.storedEnergy = storedEnergy;
    this.airQuality = airQuality;
  }

  /**
   * Get this System's temperature.
   * 
   * @return double
   */
  public double getTemperature() {
    return temperature;
  }

  /**
   * Get this System's stored energy.
   * 
   * @return double
   */
  public double getStoredEnergy() {
    return storedEnergy;
  }

  /**
   * Get this System's air quality.
   * 
   * @return double
   */
  public double getAirQuality() {
    return airQuality;
  }
}
