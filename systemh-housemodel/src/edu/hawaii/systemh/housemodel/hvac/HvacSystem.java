package edu.hawaii.systemh.housemodel.hvac;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.HouseSystem;

/**
 * Creates the HVAC System model for energy consumption.
 * 
 * @author Bret K. Ikehara
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
    
    Device temperature = new Device(EnergyConsumptionDevice.HEATING_COOLING.toString());
    double[] tempValues =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      temperature.addHourEntry(i, tempValues[i]);
    }

    deviceMap.put(temperature.getDeviceName(), temperature);

    Device humidifier = new Device(EnergyConsumptionDevice.HUMIDIFIER.toString());
    double[] humidifierValues =
        { 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
            17, 17 };
    for (int i = 0; i < 24; i++) {
      humidifier.addHourEntry(i, humidifierValues[i]);
    }

    deviceMap.put(humidifier.getDeviceName(), humidifier);
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