package edu.hawaii.systemh.housemodel.aquaponics;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.System;

/**
 * Creates the HVAC System model for energy consumption.
 * 
 * @author Kurt Teichman, Leonardo Ngyuen, Bret K. Ikehara
 */
public class AquaponicsSystem extends System {

  /**
   * Default Constructor.
   */
  public AquaponicsSystem() {
    super(EnergyConsumptionSystem.AQUAPONICS.toString());

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

  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getSystemCurrentLoad() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice device,
      Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return null;
  }
}