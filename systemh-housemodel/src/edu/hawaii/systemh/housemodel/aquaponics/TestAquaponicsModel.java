package edu.hawaii.systemh.housemodel.aquaponics;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionModel;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;

/**
 * Tests that the values put into the Lighting Consumption Model are working
 * as intended.
 * 
 * @author Kurt Teichman
 */
public class TestAquaponicsModel {
  
  /**
   * Tests that the values put into the Aquaponics Consumption Model are working
   * as intended.
   * 
   * @throws Exception generalized exception
   */
  @Test
  public void testAquaponicsValues() throws Exception {
    
    EnergyConsumptionModel energyConsumptionModel = new EnergyConsumptionModel();
    java.util.Date todayDate = new java.util.Date();
    
    long startTime = todayDate.getTime();
    long endTime = startTime + 1000 * 60 * 60 * 24 * 7; // 1 week from our startTime

    List<TimestampDoublePair> aquaponicsSystemData =
      energyConsumptionModel.getSystemLoadDuringInterval(
          EnergyConsumptionSystem.AQUAPONICS, startTime, endTime);
    
    List<TimestampDoublePair> waterPumpValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.WATER_PUMP, startTime, endTime);
    
    List<TimestampDoublePair> heaterValues =
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.AQUAPONICS_HEATER, startTime, endTime);
    
    List<TimestampDoublePair> filterValues =
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.WATER_FILTER, startTime, endTime);
    
    double systemTotal = 0, waterPumpTotal = 0, heaterTotal = 0, filterTotal = 0;

    for (int i = 0; i < aquaponicsSystemData.size(); i++) {
      systemTotal += aquaponicsSystemData.get(i).getValue();
      waterPumpTotal += waterPumpValues.get(i).getValue();
      heaterTotal += heaterValues.get(i).getValue();
      filterTotal += filterValues.get(i).getValue();
    }
    
    assertEquals(systemTotal, (waterPumpTotal + heaterTotal + filterTotal), 0.0);
  }
}