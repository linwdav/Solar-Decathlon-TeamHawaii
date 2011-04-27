package edu.hawaii.systemh.housemodel;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.hvac.HvacSystem;
import edu.hawaii.systemh.housemodel.misc.MiscSystem;

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
  //private AquaponicsSystem aquaponics;  
  //private LightingSystem lighting;
  private HvacSystem hvac;
  private MiscSystem misc;

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
    this.misc = new MiscSystem();
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
    // Devices associated with the HVAC system.
    case HEATING_COOLING:
    case HUMIDIFIER:
      value = this.hvac.getDeviceCurrentLoad(deviceName);
      break;
    // Devices associated with the the MiscSystem.
    case REFRIGERATOR:
    case FREEZER:
    case HOME_ELECTRONIC:
    case CLOTHES_WASHER:
    case CLOTHES_DRYER:
    case DISHWASHER:
    case COOKING:
    case HOT_WATER:
      value = this.misc.getDeviceCurrentLoad(deviceName);
      break;
    default:
      throw new RuntimeException(deviceName.toString() + errorMsg);
    }
    
    return value;
  }

  /**
   * Gets the system current load.
   * 
   * @param system EnergyConsumptionSystem
   * @return double
   */
  @Override
  public double getSystemCurrentLoad(EnergyConsumptionSystem system) {

    double value = 0;

    switch (system) {
    //case AQUAPONICS:
    //  value = this.aquaponics.getSystemCurrentLoad();
    //  break;
    case HVAC:
      value = this.hvac.getSystemCurrentLoad();
      break;
    //case LIGHTING:
    //  value = this.lighting.getSystemCurrentLoad();
    //  break;
    case MISC:
      value = this.misc.getSystemCurrentLoad();
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
    
    List<TimestampDoublePair> deviceLoadList = null;

    switch (deviceName) {
    // Devices associated with the HVAC system.
    case HEATING_COOLING:
    case HUMIDIFIER:
      deviceLoadList = this.hvac.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
      break;
    // Devices associated with the the MiscSystem.
    case REFRIGERATOR:
    case FREEZER:
    case HOME_ELECTRONIC:
    case CLOTHES_WASHER:
    case CLOTHES_DRYER:
    case DISHWASHER:
    case COOKING:
    case HOT_WATER:
      deviceLoadList = this.misc.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
      break;  
    default:
      throw new RuntimeException(deviceName.toString() + errorMsg);
    }

    return deviceLoadList;
  }

  /**
   * Gets the congregate load for a system during a certain interval.
   * 
   * @param system EnergyConsumptionSystem
   * @param startTime Long
   * @param endTime Long
   * @return List<TimestampDoublePair>
   */
  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(EnergyConsumptionSystem system,
      Long startTime, Long endTime) {
    
    List<TimestampDoublePair> systemLoadList = null;
    
    switch (system) {
    //case AQUAPONICS:
    //  systemLoadList = this.aquaponics.getSystemLoadDuringInterval(startTime, endTime);
    //  break;
    //case LIGHTING:
    //  systemLoadList = this.lighting.getSystemLoadDuringInterval(startTime, endTime);
    //  break;
    case HVAC:
      systemLoadList = this.hvac.getSystemLoadDuringInterval(startTime, endTime);
      break;
    case MISC:
      systemLoadList = this.misc.getSystemLoadDuringInterval(startTime, endTime);
      break;
    default:
      throw new RuntimeException(system.toString() + errorMsg);
    }

    return systemLoadList;
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
