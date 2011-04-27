package edu.hawaii.systemh.housemodel.misc;

import java.util.List;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
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
