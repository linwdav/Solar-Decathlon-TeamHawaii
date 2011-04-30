package edu.hawaii.systemh.housemodel.lighting;

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
}