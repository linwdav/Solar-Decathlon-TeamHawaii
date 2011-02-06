package edu.hawaii.ihale.api;

/**
 * This LightingRoom class describes the rooms which the Lighting System controls.
 * 
 * @author Team Hawaii
 */
public class LightingRoom {

  private String id;
  private double color;
  private double energyConsumption;
  private boolean status;
  private double level;

  /**
   * Default Room Constructor.
   * 
   * @param id String
   * @param status boolean Whether lights are on or off.
   * @param level double Lights brightness level
   * @param color double
   * @param energyConsumption double
   */
  public LightingRoom(String id, boolean status, double level, double color,
      double energyConsumption) {
    this.id = id;
    this.status = status;
    this.level = level;
    this.color = color;
    this.energyConsumption = energyConsumption;
  }

  /**
   * Gets this Room's id.
   * 
   * @return String
   */
  public String getId() {
    return id;
  }

  /**
   * Gets this Room's light color.
   * 
   * @return double
   */
  public double getColor() {
    return color;
  }

  /**
   * Gets this Room's energy consumption.
   * 
   * @return double
   */
  public double getEnergyConsumption() {
    return energyConsumption;
  }

  /**
   * Gets this Room's light status whether on or off.
   * 
   * @return boolean
   */
  public boolean isStatus() {
    return status;
  }

  /**
   * Gets this Room's lighting level whether dim or bright.
   * 
   * @return double
   */
  public double getLevel() {
    return level;
  }

}
