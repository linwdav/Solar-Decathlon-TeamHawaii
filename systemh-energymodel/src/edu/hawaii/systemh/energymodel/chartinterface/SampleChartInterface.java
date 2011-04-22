package edu.hawaii.systemh.energymodel.chartinterface;

import java.util.List;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * Sample Chart Interface class to populate data for charts.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 *
 */
public class SampleChartInterface implements EnergyManagementChartInterface {
  
  /**
   * Temporary default constructor.
   */
  public SampleChartInterface() {
    super();
  }

  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    // Testing values only
    switch(deviceName) {
    case REFRIDGERATOR:
      return 1.0;
    case FREEZER:
      return 2.0;
    case DISHWASHER:
      return 3.0;
    case WASHER_DRYER:
      return 4.0;
    case STEREO_SYSTEM:
      return 5.0;
    case TELEVISION:
      return 6.0;
    case VENTILATION_FAN:
      return 7.0;
    case SOLAR_THERMAL_CONTROLLER:
      return 8.0;
    case OTHER:
      return 9.0;
    default:
      return 0;
    } // End switch
  } // End getDeviceCurrentLoad

  @Override
  public double getSystemCurrentLoad(SystemHSystem system) {
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
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system,
      Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double powerConsumed(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double powerGenerated(Long startTime, Long endTime) {
    // TODO Auto-generated method stub
    return 0;
  }
  
  

} // End Sample Chart Interface Class
