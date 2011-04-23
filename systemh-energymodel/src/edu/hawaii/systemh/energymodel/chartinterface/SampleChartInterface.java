package edu.hawaii.systemh.energymodel.chartinterface;

import java.util.ArrayList;
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

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;

  /**
   * Temporary default constructor.
   */
  public SampleChartInterface() {
    super();
  }

  @Override
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    // Testing values only
    switch (deviceName) {
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
    switch (system) {
    case AQUAPONICS:
      return 100.0;
    case HVAC:
      return 90.0;
    case PHOTOVOLTAIC:
      return 80.0;
    case ELECTRIC:
      return 70.0;
    case LIGHTING:
      return 60.0;
    default:
      return 0;
    }
  }

  @Override
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice device,
      Long startTime, Long endTime) {
    List<TimestampDoublePair> list = new ArrayList<TimestampDoublePair>();
    Long differenceInterval = (endTime - startTime) / 4;
    switch (device) {
    case REFRIDGERATOR:
      list.add(new TimestampDoublePair(endTime, 1.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 2.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 0.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 1.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 1.0));
      break;
    case FREEZER:
      list.add(new TimestampDoublePair(endTime, 2.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 3.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 1.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 2.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 1.0));
      break;
    case DISHWASHER:
      list.add(new TimestampDoublePair(endTime, 3.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 4.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 2.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 3.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 2.0));
      break;
    case WASHER_DRYER:
      list.add(new TimestampDoublePair(endTime, 4.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 5.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 3.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 4.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 3.0));
      break;
    case STEREO_SYSTEM:
      list.add(new TimestampDoublePair(endTime, 5.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 6.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 4.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 5.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 4.0));
      break;
    case TELEVISION:
      list.add(new TimestampDoublePair(endTime, 6.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 7.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 5.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 6.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 5.0));
      break;
    case VENTILATION_FAN:
      list.add(new TimestampDoublePair(endTime, 7.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 8.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 6.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 7.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 6.0));
      break;
    case SOLAR_THERMAL_CONTROLLER:
      list.add(new TimestampDoublePair(endTime, 8.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 9.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 7.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 8.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 7.0));
      break;
    case OTHER:
      list.add(new TimestampDoublePair(endTime, 9.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 10.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 8.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 9.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 8.0));
      break;
    default:
      list.add(new TimestampDoublePair(endTime, 0.0));
    }
    return list;
  }

  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system,
      Long startTime, Long endTime) {
    List<TimestampDoublePair> list = new ArrayList<TimestampDoublePair>();
    Long differenceInterval = (endTime - startTime) / 4;
    switch (system) {
    case AQUAPONICS:
      list.add(new TimestampDoublePair(endTime, 100.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 110.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 90.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 109.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 90.0));
      break;
    case HVAC:
      list.add(new TimestampDoublePair(endTime, 90.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 100.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 80.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 99.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 80.0));
      break;
    case PHOTOVOLTAIC:
      list.add(new TimestampDoublePair(endTime, 80.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 90.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 70.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 89.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 70.0));
      break;
    case ELECTRIC:
      list.add(new TimestampDoublePair(endTime, 70.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 80.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 60.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 79.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 60.0));
      break;
    case LIGHTING:
      list.add(new TimestampDoublePair(endTime, 60.0));
      list.add(new TimestampDoublePair(endTime - differenceInterval, 70.0));
      list.add(new TimestampDoublePair(endTime - 2 * differenceInterval, 50.4));
      list.add(new TimestampDoublePair(endTime - 3 * differenceInterval, 69.6));
      list.add(new TimestampDoublePair(endTime - 4 * differenceInterval, 50.0));
      break;
    default:
      list.add(new TimestampDoublePair(endTime, 0.0));
    }
    return list;
  }

  @Override
  public double powerConsumed(Long startTime, Long endTime) {
    Long now = System.currentTimeMillis();
    Long dayBeforeNow = now - oneDayInMillis;
    Long weekBeforeNow = now - oneWeekInMillis;
    Long monthBeforeNow = now - oneMonthInMillis;
    
    if (startTime <= monthBeforeNow) {
      return 10000.0;
    }
    else if (startTime <= weekBeforeNow) {
      return 1000.0;
    }
    else if (startTime <= dayBeforeNow) {
      return 100.0;
    }
    return 0;
  }

  @Override
  public double powerGenerated(Long startTime, Long endTime) {
    Long now = System.currentTimeMillis();
    Long dayBeforeNow = now - oneDayInMillis;
    Long weekBeforeNow = now - oneWeekInMillis;
    Long monthBeforeNow = now - oneMonthInMillis;
    
    if (startTime <= monthBeforeNow) {
      return 9000.0;
    }
    else if (startTime <= weekBeforeNow) {
      return 800.0;
    }
    else if (startTime <= dayBeforeNow) {
      return 70.0;
    }
    return 0;
  }

} // End Sample Chart Interface Class
