package edu.hawaii.systemh.housemodel.hvac;

import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.HouseSystem;

/**
 * Creates the HVAC System model for energy consumption.
 * 
 * @author Bret K. Ikehara, Leonardo Nguyen, Kurt Teichman.
 */
public class HvacSystem extends HouseSystem {

  /**
   * Default Constructor.
   */
  public HvacSystem() {
    super(EnergyConsumptionSystem.HVAC.toString());
  }

  @Override
  /**
   * Initialize the energy consumption for each hour throughout the day for each respective
   * devices of the HVAC system.
   */
  protected void initDeviceValues() {
    
    /** Heating and Cooling. **/
    Device temperature = new Device(EnergyConsumptionDevice.HEATING_COOLING.toString());
    double[] tempValues = {228, 245, 274, 290, 290, 290, 0, 0, 0, 0,
                           17, 17, 17, 17, 376, 308, 211, 0, 0, 0,
                           0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      temperature.addHourEntry(i, tempValues[i]);
    }

    /** Humidifier. **/
    Device humidifier = new Device(EnergyConsumptionDevice.HUMIDIFIER.toString());
    double[] humidifierValues =  {165, 165, 165, 165, 165, 165, 165, 165, 165, 165,
                                  165, 165, 165, 165, 0, 0, 0, 0, 0, 0,
                                  0, 0, 165, 165};
    for (int i = 0; i < 24; i++) {
      humidifier.addHourEntry(i, humidifierValues[i]);
    }
    
    // Associated the following devices with the HVAC System.
    deviceMap.put(temperature.getDeviceName(), temperature);
    deviceMap.put(humidifier.getDeviceName(), humidifier);
  }
}