package edu.hawaii.systemh.housemodel;

import java.util.List;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.hvac.HvacSystem;

/**
 * Models the current energy consumption being used by the house systems. Systems concerned are
 * HVAC, Aquaponics, Lighting, and various plug loads of appliances used in the SystemH home.
 * 
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class EnergyConsumptionModel implements EnergyManagementChartInterface {

  /**
   * Error message.
   */
  private static final String errorMsg;
  private HvacSystem hvac;

  /**
   * Initialize static values.
   */
  static {
    errorMsg = " is unknown.";
  }

  /**
   * Default Constructor.
   */
  public EnergyConsumptionModel() {
    this.hvac = new HvacSystem();
  }

  /**
   * Gets the device current load.
   * 
   * @param deviceName EnergyConsumptionDevice
   * @return double
   */
  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    double value = 0;

    switch (deviceName) {
    case HEATING_COOLING:
    case HUMIDIFIER:
      value = this.hvac.getDeviceCurrentLoad(deviceName);
      break;
    default:
      throw new RuntimeException(deviceName.toString() + errorMsg);
    }

    return value;
  }

  /**
   * Gets the system current load.
   * 
   * @param system SystemHSystem
   * @return double
   */
  @Override
  public double getSystemCurrentLoad(SystemHSystem system) {

    double value = 0;

    switch (system) {
    case HVAC:
      value = this.hvac.getSystemCurrentLoad();
      break;
    default:
      throw new RuntimeException(system.toString() + errorMsg);
    }

    return value;
  }

  /**
   * Gets the load for a device during a certain interval.
   * 
   * @param deviceName EnergyConsumptionDevice
   * @param startTime Long
   * @param endTime Long
   * @return List<TimestampDoublePair>
   */
  @Override
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice deviceName,
      Long startTime, Long endTime) {
    List<TimestampDoublePair> interval = null;

    switch (deviceName) {
    case HEATING_COOLING:
    case HUMIDIFIER:
      interval = this.hvac.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
      break;
    default:
      throw new RuntimeException(deviceName.toString() + errorMsg);
    }

    return interval;
  }

  /**
   * Gets the congregate load for a system during a certain interval.
   * 
   * @param system SystemHSystem
   * @param startTime Long
   * @param endTime Long
   * @return List<TimestampDoublePair>
   */
  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system,
      Long startTime, Long endTime) {
    List<TimestampDoublePair> interval = null;

    switch (system) {
    case HVAC:
      interval = this.hvac.getSystemLoadDuringInterval(startTime, endTime);
      break;
    default:
      throw new RuntimeException(system.toString() + errorMsg);
    }

    return interval;
  }

  /**
   * What is this?
   * 
   * @param startTime Long
   * @param endTime Long
   * @return double
   */
  @Override
  public double powerConsumed(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * What is this?
   * 
   * @param startTime Long
   * @param endTime Long
   * @return double
   */
  @Override
  public double powerGenerated(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }
}
