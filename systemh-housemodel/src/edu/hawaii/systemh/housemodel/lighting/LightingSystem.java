package edu.hawaii.systemh.housemodel.lighting;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.System;

/**
 * Creates the Lighting System model for energy consumption.
 * 
 * @author Bret K. Ikehara
 */
public class LightingSystem extends System {

  /**
   * Default Constructor.
   * 
   * @param systemName String
   */
  public LightingSystem(String systemName) {
    super(systemName);

    Device lighting = new Device(EnergyConsumptionDevice.LIGHTING.toString());
    double[] values =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      lighting.addHourEntry(i, values[i]);
    }

    deviceMap.put(lighting.getDeviceName(), lighting);
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