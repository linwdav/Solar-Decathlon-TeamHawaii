package edu.hawaii.systemh.housemodel;

import java.util.List;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * Models the current energy consumption being used by the house systems. Systems concerned are
 * HVAC, Aquaponics, Lighting, and various plug loads of appliances used in the SystemH home.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class EnergyConsumptionModel implements EnergyManagementChartInterface {

  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getSystemCurrentLoad(SystemHSystem system) {
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
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system,
      Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double powerConsumed(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double powerGenerated(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }
}
