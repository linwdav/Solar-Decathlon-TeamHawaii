package edu.hawaii.systemh.housemodel.misc;

import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.System;

/**
 * . . . 
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class MiscSystem extends System {
  
  /**
   * . . .
   * @param systemName . . .
   */
  public MiscSystem(String systemName) {
    super(systemName);
    initDeviceValues();
  }
  
  /**
   * . . .
   */
  private void initDeviceValues() {

    Device refrigerator = new Device(EnergyConsumptionDevice.REFRIGERATOR.toString());
    double[] refrigEntries = {17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17};
    for (int i = 0; i < 24; i++) {
      refrigerator.addHourEntry(i, refrigEntries[i]);
    }
    
    Device freezer = new Device(EnergyConsumptionDevice.FREEZER.toString());
    double[] freezerEntries = {13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5,
                               13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5, 13.5,
                               13.5, 13.5, 13.5, 13.5};
    for (int i = 0; i < 24; i++) {
      freezer.addHourEntry(i, freezerEntries[i]);
    }
    
    //deviceList.add(refrigerator);
    deviceMap.put(refrigerator.getDeviceName(), refrigerator);
    deviceMap.put(freezer.getDeviceName(), freezer);

  }
}
