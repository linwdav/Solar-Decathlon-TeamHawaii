package edu.hawaii.ihale.api;

import java.util.List;

/**
 * This class extends the GenericSystem to create the Aquaponics System class to hold all
 * information regarding the Aquaponics at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class Lighting extends GenericSystem {

  private double energyConsumption;
  private String profile;
  private List<LightingRoom> rooms;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   * @param energyConsumption double
   * @param profile String
   * @param rooms List<LightingRoom>;
   */
  public Lighting(String title, String desc, long timestamp, int status,
      double energyConsumption, String profile, List<LightingRoom> rooms ) {
    super(title, desc, timestamp, status);
    this.energyConsumption = energyConsumption;
    this.profile = profile;
    this.rooms = rooms;
  }

  /**
   * Gets this System's energy consumption.
   * 
   * @return double
   */
  public double getEnergyConsumption() {
    return energyConsumption;
  }

  /**
   * Gets this System's profile.
   * 
   * @return String
   */
  public String getProfile() {
    return profile;
  }

  /**
   * Gets this System's user-defined rooms.
   * 
   * @return List<LightingRoom>
   */
  public List<LightingRoom> getRooms() {
    return rooms;
  }
}
