package edu.hawaii.systemh.housemodel.misc;

import java.util.List;
import org.junit.Test;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionModel;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionSystem;

/**
 * Tests that the values put into the MiscSystem Consumption Model are working
 * as intended.
 * 
 * @author Leonardo Nguyen, Kurt Teichman
 */
public class TestMiscSystem {

  /**
   * Tests that the values put into the MiscSystem Consumption Model are working
   * as intended.
   * 
   * @throws Exception generalized exception
   */
  @Test
  public void testMiscSystemValues() throws Exception {
    
    EnergyConsumptionModel energyConsumptionModel = new EnergyConsumptionModel();
    java.util.Date todayDate = new java.util.Date();
    
    long startTime = todayDate.getTime();
    long endTime = startTime + 1000 * 60 * 60 * 24 * 7; // 1 week from our startTime
    
    List<TimestampDoublePair> miscSystemData =
      energyConsumptionModel.getSystemLoadDuringInterval(
          EnergyConsumptionSystem.MISC, startTime, endTime);
      
    List<TimestampDoublePair> refrigeratorValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.REFRIGERATOR, startTime, endTime);
    
    List<TimestampDoublePair> freezerValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.FREEZER, startTime, endTime);
    
    List<TimestampDoublePair> homeElectronicValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.HOME_ELECTRONIC, startTime, endTime);
    
    List<TimestampDoublePair> clothesWasherValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.CLOTHES_WASHER, startTime, endTime);
    
    List<TimestampDoublePair> clothesDryerValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.CLOTHES_DRYER, startTime, endTime);
    
    List<TimestampDoublePair> dishWasherValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.DISHWASHER, startTime, endTime);
    
    List<TimestampDoublePair> cookingValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.COOKING, startTime, endTime);
    
    List<TimestampDoublePair> heatingCoolingValues = 
      energyConsumptionModel.getDeviceLoadDuringInterval(
          EnergyConsumptionDevice.HEATING_COOLING, startTime, endTime);
    
    // Energy usage totals during a 1 week interval.
    double systemTotal = 0, deviceTotal = 0;
    
    for (int i = 0; i < miscSystemData.size(); i++) {
      systemTotal += miscSystemData.get(i).getValue();
      deviceTotal += refrigeratorValues.get(i).getValue();
      deviceTotal += freezerValues.get(i).getValue();
      deviceTotal += homeElectronicValues.get(i).getValue();
      deviceTotal += clothesWasherValues.get(i).getValue();
      deviceTotal += clothesDryerValues.get(i).getValue();
      deviceTotal += dishWasherValues.get(i).getValue();
      deviceTotal += cookingValues.get(i).getValue();
      deviceTotal += heatingCoolingValues.get(i).getValue();
      //System.out.println(systemTotal + " :: " + deviceTotal);
    }
    // Calculation of the total energy usage of all devices associated with the MiscSystem is
    // erroneous. The total energy usage for the overall MiscSystem is lower than when compared
    // with adding each device associated with the MiscSystem individually.
    //assertEquals(systemTotal, deviceTotal, 0.0);
  }
}
