package edu.hawaii.systemh.housemodel.misc;

import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.System;

/**
 * . . . 
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class MiscSystem extends System {
  
  public MiscSystem(String systemName) {
    super(systemName);
    initDeviceValues();
  }
  
  public void initDeviceValues() {

    Device refrigerator = new Device("REFRIGERATOR");
    double[] refrigEntries = {17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17, 17, 17, 17, 17, 17, 17,
                              17, 17, 17, 17};
    for (int i = 0; i < 23; i++) {
      refrigerator.addHourEntry(i, refrigEntries[i]);
    }
    //deviceList.add(refrigerator);
    deviceMap.put(refrigerator.getDeviceName(), refrigerator);
  }
}
