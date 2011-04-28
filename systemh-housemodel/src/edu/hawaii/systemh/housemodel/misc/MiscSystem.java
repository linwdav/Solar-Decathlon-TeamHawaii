package edu.hawaii.systemh.housemodel.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.System;

/**
 * This is not a real functioning system of the SystemH home. All devices that aren't defined or
 * belong to an actual system within the SystemH home (Aquaponics, Lighting, HVAC) are instead
 * associated with this MiscSystem.
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class MiscSystem extends System {
    
  /**
   * The main constructor. Initializes all the associated devices with this system.
   */
  public MiscSystem() {
    super(EnergyConsumptionSystem.MISC.toString());
    initDeviceValues();
  }
  
  /**
   * Initialize the energy consumption for each hour throughout the day for each respective
   * devices of the MiscSystem.
   */
  private void initDeviceValues() {

    /** Refrigerator. **/
    Device refrigerator = new Device(EnergyConsumptionDevice.REFRIGERATOR.toString());
    double[] refrigEntries = {17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17};
    for (int i = 0; i < 24; i++) {
      refrigerator.addHourEntry(i, refrigEntries[i]);
    }
    
    /** Freezer. **/
    Device freezer = new Device(EnergyConsumptionDevice.FREEZER.toString());
    double[] freezerEntries = {13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5,
                               13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5,
                               13.5, 13.5, 13.5, 13.5};
    for (int i = 0; i < 24; i++) {
      freezer.addHourEntry(i, freezerEntries[i]);
    }
    
    /** Home Electronic System. **/
    Device homeElectronic = new Device(EnergyConsumptionDevice.HOME_ELECTRONIC.toString());
    double[] homeElecEntries = {0, 0, 0, 0, 0, 0, 0, 0, 1577.5, 1577.5,
                                1577.5, 1577.5, 1577.5, 1577.5, 1577.5, 1577.5, 0, 0, 0, 0,
                                0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      homeElectronic.addHourEntry(i, homeElecEntries[i]);
    }

    /** Clothes Washer. **/
    Device clothesWasher = new Device(EnergyConsumptionDevice.CLOTHES_WASHER.toString());
    double[] clothesWasherEntries = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                     0, 0, 0, 0, 0, 0, 50, 50, 50, 50,
                                     0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      clothesWasher.addHourEntry(i, clothesWasherEntries[i]);
    }
    
    /** Clothes Dryer. **/
    Device clothesDryer = new Device(EnergyConsumptionDevice.CLOTHES_DRYER.toString());
    double[] clothesDryerEntries = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 736, 736, 736, 736,
                                    0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      clothesDryer.addHourEntry(i, clothesDryerEntries[i]);
    }
    
    /** Dish Washer. **/
    Device dishWasher = new Device(EnergyConsumptionDevice.DISHWASHER.toString());
    double[] dishWashereEntries = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                   0, 0, 0, 0, 0, 0, 900, 900, 900, 900,
                                   0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      dishWasher.addHourEntry(i, dishWashereEntries[i]);
    }
    
    /** Cooking energy usage. **/
    Device cooking = new Device(EnergyConsumptionDevice.COOKING.toString());
    double[] cookingEntries = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                               0, 0, 0, 0, 0, 0, 0, 447.5, 447.5, 447.5,
                               0, 0, 0, 0};
    for (int i = 0; i < 24; i++) {
      cooking.addHourEntry(i, cookingEntries[i]);
    }
    
    /** Hot water energy usage. **/
    Device hotWater = new Device(EnergyConsumptionDevice.HOT_WATER.toString());
    double[] hotWaterEntries = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0, 0, 25,
                                50, 25, 0, 0};
    for (int i = 0; i < 24; i++) {
      hotWater.addHourEntry(i, hotWaterEntries[i]);
    }
    
    // Associated the following devices with the MiscSystem.
    deviceMap.put(refrigerator.getDeviceName(), refrigerator);
    deviceMap.put(freezer.getDeviceName(), freezer);
    deviceMap.put(homeElectronic.getDeviceName(), homeElectronic);
    deviceMap.put(clothesWasher.getDeviceName(), clothesWasher);
    deviceMap.put(clothesDryer.getDeviceName(), clothesDryer);
    deviceMap.put(dishWasher.getDeviceName(), dishWasher);
    deviceMap.put(cooking.getDeviceName(), cooking);
    deviceMap.put(hotWater.getDeviceName(), hotWater);
  }

  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    
    double loadValue = 0.0;
    // Device name.
    String dn = deviceName.toString();
    if (deviceMap.containsKey(dn)) {
      Device device = deviceMap.get(dn);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
      loadValue = device.getEnergyConsumptionByHourOfDay(currentHour);
    }
    else {
      throw new RuntimeException("The device name requested does not belong to the MiscSystem.");
    }
    return loadValue;
  }

  @Override
  public double getSystemCurrentLoad() {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    double totalLoadValue = 0.0;
    
    // Map.Entry to retrieve the map's keys, which represents the device names.
    Set<Map.Entry<String, Device>> set = getDeviceMap().entrySet();
    
    // Iterate through the map and retrieve corresponding device's current energy load and total it
    // for the full system load.
    for (Map.Entry<String, Device> mapEntry : set) {
      String keyName = (mapEntry.getKey());
      totalLoadValue += deviceMap.get(keyName).getEnergyConsumptionByHourOfDay(currentHour);
    }
    
    return totalLoadValue;
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
