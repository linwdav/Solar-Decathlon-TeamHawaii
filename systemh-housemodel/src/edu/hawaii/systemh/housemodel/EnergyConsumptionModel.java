package edu.hawaii.systemh.housemodel;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.api.repository.TimestampIntegerPair;
import edu.hawaii.systemh.api.repository.impl.Repository;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.aquaponics.AquaponicsSystem;
import edu.hawaii.systemh.housemodel.hvac.HvacSystem;
import edu.hawaii.systemh.housemodel.lighting.LightingSystem;
import edu.hawaii.systemh.housemodel.misc.MiscSystem;

/**
 * Models the current energy consumption being used by the house systems. Systems concerned are
 * HVAC, Aquaponics, Lighting, and various plug loads of appliances used in the SystemH home.
 * 
 * @author Leonardo Nguyen
 * @author Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class EnergyConsumptionModel implements EnergyManagementChartInterface {

  /** Error message. **/
  private static final String errorMsg;

  private AquaponicsSystem aquaponics;
  private LightingSystem lighting;
  private HvacSystem hvac;
  private MiscSystem misc;

  private static final Repository repo;

  /**
   * Initialize static values.
   */
  static {
    errorMsg = " is unknown.";

    repo = new Repository();
  }

  /**
   * Default Constructor.
   */
  public EnergyConsumptionModel() {
    this.aquaponics = new AquaponicsSystem();
    this.hvac = new HvacSystem();
    this.lighting = new LightingSystem();
    this.misc = new MiscSystem();
  }

  /**
   * Retrieves the device current energy load.
   * 
   * @param deviceName The device.
   * @return The device's current energy usage load.
   */
  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {

    double value = 0;

    switch (deviceName) {
    // Devices associated with the Aquaponics system.
    case AQUAPONICS_HEATER:
    case WATER_PUMP:
    case WATER_FILTER:
      value = this.aquaponics.getDeviceCurrentLoad(deviceName);
      break;
    // Devices associated with the HVAC system.
    case HEATING_COOLING:
    case HUMIDIFIER:
      value = this.hvac.getDeviceCurrentLoad(deviceName);
      break;
    // Devices associated with the Lighting system.
    case LIGHTING:
      value = this.lighting.getDeviceCurrentLoad(deviceName);
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
   * Retrieves the system's current energy usage load.
   * 
   * @param system The system.
   * @return The system's current energy usage load.
   */
  @Override
  public double getSystemCurrentLoad(EnergyConsumptionSystem system) {

    double value = 0;

    switch (system) {
    case AQUAPONICS:
      value = this.aquaponics.getSystemCurrentLoad();
      break;
    case HVAC:
      value = this.hvac.getSystemCurrentLoad();
      break;
    case LIGHTING:
      value = this.lighting.getSystemCurrentLoad();
      break;
    case MISC:
      value = this.misc.getSystemCurrentLoad();
      break;
    default:
      throw new RuntimeException(system.toString() + errorMsg);
    }

    return value;
  }

  /**
   * Retrieves the energy usage load for a device during a certain interval.
   * 
   * @param deviceName The device.
   * @param startTime The start time in ms since "the epoch".
   * @param endTime The end time in ms since "the epoch".
   * @return List of entries of one hour intervals since the startTime of the device's energy usage
   * load.
   */
  @Override
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice deviceName,
      Long startTime, Long endTime) {

    List<TimestampDoublePair> deviceLoadList = null;

    switch (deviceName) {
    // Devices associated with the Aquaponics system.
    case AQUAPONICS_HEATER:
    case WATER_PUMP:
    case WATER_FILTER:
      deviceLoadList = this.aquaponics.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
      break;
    // Devices associated with the HVAC system.
    case HEATING_COOLING:
    case HUMIDIFIER:
      deviceLoadList = this.hvac.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
      break;
    case LIGHTING:
      deviceLoadList = this.lighting.getDeviceLoadDuringInterval(deviceName, startTime, endTime);
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
   * Retrieves the congregate of energy usage load for a system during a certain interval.
   * 
   * @param system The system.
   * @param startTime The start time in ms since "the epoch".
   * @param endTime The end time in ms since "the epoch".
   * 
   * @return List of entries one hour intervals since the startTime of the the system's energy usage
   * load.
   */
  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(EnergyConsumptionSystem system,
      Long startTime, Long endTime) {

    List<TimestampDoublePair> systemLoadList = null;

    switch (system) {
    case AQUAPONICS:
      systemLoadList = this.aquaponics.getSystemLoadDuringInterval(startTime, endTime);
      break;
    case HVAC:
      systemLoadList = this.hvac.getSystemLoadDuringInterval(startTime, endTime);
      break;
    case LIGHTING:
      systemLoadList = this.lighting.getSystemLoadDuringInterval(startTime, endTime);
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
   * Gets the amount of power that SystemH consumed from a given interval.
   * 
   * @param startTime Long
   * @param endTime Long
   * @return double
   */
  @Override
  public double powerConsumed(Long startTime, Long endTime) {
    List<TimestampIntegerPair> list = repo.getElectricalPowerDuringInterval(startTime, endTime);
    int power = 0;

    for (TimestampIntegerPair entry : list) {
      power += entry.getValue();
    }

    return power;
  }

  /**
   * Gets the amount of power that SystemH generated from a given interval.
   * 
   * @param startTime Long
   * @param endTime Long
   * @return double
   */
  @Override
  public double powerGenerated(Long startTime, Long endTime) {

    List<TimestampIntegerPair> list = repo.getPhotovoltaicPowerDuringInterval(startTime, endTime);
    int power = 0;

    for (TimestampIntegerPair entry : list) {
      power += entry.getValue();
    }

    return power;
  }
}
