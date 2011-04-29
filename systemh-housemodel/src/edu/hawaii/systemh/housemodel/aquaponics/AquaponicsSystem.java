package edu.hawaii.systemh.housemodel.aquaponics;

import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.HouseSystem;

/**
 * Creates the HVAC System model for energy consumption.
 * 
 * @author Kurt Teichman, Leonardo Ngyuen, Bret K. Ikehara
 */
public class AquaponicsSystem extends HouseSystem {

  /**
   * Default Constructor.
   */
  public AquaponicsSystem() {
    super(EnergyConsumptionSystem.AQUAPONICS.toString());
  }

  @Override
  protected void initDeviceValues() {

    Device heater = new Device(EnergyConsumptionDevice.AQUAPONICS_HEATER.toString());
    double[] heaterValues =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      heater.addHourEntry(i, heaterValues[i]);
    }

    deviceMap.put(heater.getDeviceName(), heater);

    Device waterpump = new Device(EnergyConsumptionDevice.PUMP.toString());
    double[] waterPumpValues =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      waterpump.addHourEntry(i, waterPumpValues[i]);
    }

    deviceMap.put(waterpump.getDeviceName(), waterpump);
    
    Device waterFilter = new Device(EnergyConsumptionDevice.FILTER.toString());
    double[] waterFilterValues =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      waterFilter.addHourEntry(i, waterFilterValues[i]);
    }

    deviceMap.put(waterpump.getDeviceName(), waterFilter);
  }
}