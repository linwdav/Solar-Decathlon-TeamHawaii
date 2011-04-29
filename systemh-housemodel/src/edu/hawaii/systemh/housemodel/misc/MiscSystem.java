package edu.hawaii.systemh.housemodel.misc;

import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;
import edu.hawaii.systemh.housemodel.HouseSystem;

/**
 * This is not a real functioning system of the SystemH home. All devices that aren't defined or
 * belong to an actual system within the SystemH home (Aquaponics, Lighting, HVAC) are instead
 * associated with this MiscSystem.
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class MiscSystem extends HouseSystem {
    
  static final long oneHourInMillis = 1000L * 60L * 60L;
  
  /**
   * The main constructor. Initializes all the associated devices with this system.
   */
  public MiscSystem() {
    super(EnergyConsumptionSystem.MISC.toString());
  }
  
  @Override
  /**
   * Initialize the energy consumption for each hour throughout the day for each respective
   * devices of the HouseSystem.
   */
  protected void initDeviceValues() {

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
}