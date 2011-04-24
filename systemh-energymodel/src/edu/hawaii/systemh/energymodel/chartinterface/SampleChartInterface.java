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

    switch (device) {
    case REFRIDGERATOR:
      return populateList(0, 1.0, startTime, endTime);
    case FREEZER:
      return populateList(0, 2.0, startTime, endTime);
    case DISHWASHER:
      return populateList(0, 3.0, startTime, endTime);
    case WASHER_DRYER:
      return populateList(0, 4.0, startTime, endTime);
    case STEREO_SYSTEM:
      return populateList(0, 5.0, startTime, endTime);
    case TELEVISION:
      return populateList(0, 6.0, startTime, endTime);
    case VENTILATION_FAN:
      return populateList(0, 7.0, startTime, endTime);
    case SOLAR_THERMAL_CONTROLLER:
      return populateList(0, 8.0, startTime, endTime);
    case OTHER:
      return populateList(0, 9.0, startTime, endTime);
    default:
      List<TimestampDoublePair> list = new ArrayList<TimestampDoublePair>();
      list.add(new TimestampDoublePair(endTime, 0.0));
      return list;
    }
  }

  @Override
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system,
      Long startTime, Long endTime) {
    switch (system) {
    case AQUAPONICS:
      return populateList(1, 100.0, startTime, endTime);
    case HVAC:
      return populateList(1, 90.0, startTime, endTime);
    case PHOTOVOLTAIC:
      return populateList(1, 80.0, startTime, endTime);
    case ELECTRIC:
      return populateList(1, 70.0, startTime, endTime);
    case LIGHTING:
      return populateList(1, 60.0, startTime, endTime);
    default:
      List<TimestampDoublePair> list = new ArrayList<TimestampDoublePair>();
      list.add(new TimestampDoublePair(endTime, 0.0));
      return list;
    }
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

  private List<TimestampDoublePair> populateList(int type, double value, Long startTime, Long endTime) {
    // Get the time now
    Long now = System.currentTimeMillis();
    Long dayBeforeNow = now - oneDayInMillis;
    Long weekBeforeNow = now - oneWeekInMillis;
    Long monthBeforeNow = now - oneMonthInMillis;

    List<TimestampDoublePair> list = new ArrayList<TimestampDoublePair>();
    if (type == 0) {
      if (startTime <= monthBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value + 2));
        list.add(new TimestampDoublePair(endTime, value + 1));
        list.add(new TimestampDoublePair(endTime, value + 3));
      }
      else if (startTime <= weekBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value + 1));
        list.add(new TimestampDoublePair(endTime, value));
        list.add(new TimestampDoublePair(endTime, value + 2));
      }
      else if (startTime <= dayBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value));
        list.add(new TimestampDoublePair(endTime, value - 1));
        list.add(new TimestampDoublePair(endTime, value + 1));
      }
    }
    else {
      if (startTime <= monthBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value + 200));
        list.add(new TimestampDoublePair(endTime, value + 100));
        list.add(new TimestampDoublePair(endTime, value + 300));
      }
      else if (startTime <= weekBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value + 100));
        list.add(new TimestampDoublePair(endTime, value));
        list.add(new TimestampDoublePair(endTime, value + 200));
      }
      else if (startTime <= dayBeforeNow) {
        list.add(new TimestampDoublePair(endTime, value));
        list.add(new TimestampDoublePair(endTime, value - 100));
        list.add(new TimestampDoublePair(endTime, value + 100));
      }
    }
    return list;
  }
} // End Sample Chart Interface Class
