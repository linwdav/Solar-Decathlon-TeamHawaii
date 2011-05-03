package edu.hawaii.systemh.housemodel.lighting;

import edu.hawaii.systemh.api.ApiDictionary.SystemHRoom;
import edu.hawaii.systemh.api.repository.impl.Repository;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.HouseSystem;

/**
 * Creates the Lighting System model for energy consumption.
 * 
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 */
public class LightingSystem extends HouseSystem {

  /** How much Wattage Hour of the light bulbs being used in each room. **/
  private static final double energyUsageOfLightBulb = 100;
  
  /**
   * Default Constructor.
   */
  public LightingSystem() {
    super(EnergyConsumptionSystem.LIGHTING.toString());
  }

  @Override
  protected void initDeviceValues() {

    /** The lights in the home. **/
    Device lighting = new Device(EnergyConsumptionDevice.LIGHTING.toString());
    double[] values = { 0, 0, 0, 0, 0, 200, 200, 0, 0, 0, 
                        0, 0, 0, 0, 0, 0, 0, 0, 50, 175, 
                        200, 150, 75, 0 };
    for (int i = 0; i < 24; i++) {
      lighting.addHourEntry(i, values[i]);
    }

    // Associated the following devices with the Lighting System.
    deviceMap.put(lighting.getDeviceName(), lighting);    
  }
  
  /**
   * Returns the current energy usage load of the lights being used throughout the home.
   *
   * @return The current energy usage load of the lights being used in the home.
   */
  public double energyUsageByLightIntensity() {
    
    Repository repo = new Repository();
    double energyUsagePerHour = 0.0;
    
    energyUsagePerHour += energyUsageOfLightBulb * 
      ((double) repo.getLightingLevel(SystemHRoom.BATHROOM).getValue() / 100.0);
    
    energyUsagePerHour += energyUsageOfLightBulb * 
      ((double) repo.getLightingLevel(SystemHRoom.DINING).getValue() / 100.0);
    
    energyUsagePerHour += energyUsageOfLightBulb *
    ((double) repo.getLightingLevel(SystemHRoom.KITCHEN).getValue() / 100.0);
    
    energyUsagePerHour += energyUsageOfLightBulb *
      ((double) repo.getLightingLevel(SystemHRoom.LIVING).getValue() / 100.0);
    
    return energyUsagePerHour;
  }
}