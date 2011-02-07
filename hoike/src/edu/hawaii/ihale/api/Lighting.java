package edu.hawaii.ihale.api;

/**
 * ADT to store information about the Lighting system.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class Lighting {

  /** Fields for the system. */
  private float waterVolume;
  private float waterTemperature;
  private float waterPH;
  private float waterOxygen;
  private long lastUpdated;

  /**
   * Default constructor.
   * 
   * @param waterVolume The water volume in liters.
   * @param waterTemperature The water temperature in Celsius.
   * @param waterPH The water PH.
   * @param waterOxygen The oyxgenation level of the water.
   * @param lastUpdated The timestamp for the last update.
   */
  public Lighting(float waterVolume, float waterTemperature, float waterPH, float waterOxygen,
      long lastUpdated) {
    this.waterVolume = waterVolume;
    this.waterTemperature = waterTemperature;
    this.waterPH = waterPH;
    this.waterOxygen = waterOxygen;
    this.lastUpdated = lastUpdated;
  }

  /**
   * Return the water volume in liters.
   * 
   * @return the waterVolume
   */
  public float getWaterVolume() {
    return waterVolume;
  }

  /**
   * Return the water temperature in Celsius.
   * 
   * @return the waterTemperature
   */
  public float getWaterTemperature() {
    return waterTemperature;
  }

  /**
   * Return the PH of the water.
   * 
   * @return the waterPH
   */
  public float getWaterPH() {
    return waterPH;
  }

  /**
   * Return the oxygenation level of the water.
   * 
   * @return the waterOxygen
   */
  public float getWaterOxygen() {
    return waterOxygen;
  }

  /**
   * Return the timestamp for the last update.
   * 
   * @return the lastUpdated
   */
  public long getLastUpdated() {
    return lastUpdated;
  }
}
