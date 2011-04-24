package edu.hawaii.systemh.energymodel.chartgenerator;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.ChartDisplayType;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.energymodel.chartinterface.EnergyManagementChartInterface;

/**
 * Collects and organizes System-H energy data for chart generation. This is specific to Pie Charts.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 * 
 */
public class EnergyChartData {

  // PMD strings
  private static final String covered = "Consumption Covered";

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;

  // The chart interface associated with this class.
  EnergyManagementChartInterface chartInterface;

  // The list of labels
  String[] chartLabelArray;

  // The data for the chart
  double[][] chartDataArray;

  // Key-value pairs for chart data
  Map<String, Double> chartDataMap = new HashMap<String, Double>();

  /**
   * Assigns a chart interface to the instance.
   * 
   * @param chartInterface The interface to use.
   */
  public EnergyChartData(EnergyManagementChartInterface chartInterface) {
    this.chartInterface = chartInterface;
  }

  /**
   * Use chart data map to populate both labels and data arrays.
   */
  public void populateFromDataMap() {
    // Determine size of arrays needed.
    int numEntries = chartDataMap.size();

    // Allocate temp arrays to hold the labels and data
    String[] tempLabelArray = new String[numEntries];
    double[][] tempDataArray = new double[1][numEntries];

    // Keep track of where we are in the arrays
    int i = 0;

    // Populate arrays using the data hashmap
    for (Entry<String, Double> e : chartDataMap.entrySet()) {
      // Replace underscores with spaces in key name
      String tempString = e.getKey();
      tempString = tempString.replaceAll("_", " ");
      tempLabelArray[i] = tempString;

      // Assign data values. data must be in a 2D array consisting
      // of a single row.
      tempDataArray[0][i++] = e.getValue();
    }

    // Assign temp arrays to instance variables
    chartLabelArray = tempLabelArray;
    chartDataArray = tempDataArray;

  } // End populate from data map method

  /**
   * Populate the chart data hash map.
   * 
   * @param type The display type.
   */
  public void populateChartDataMap(ChartDisplayType type) {
    // Get the time now
    Long now = System.currentTimeMillis();
    Long dayBeforeNow = now - oneDayInMillis;
    Long weekBeforeNow = now - oneWeekInMillis;
    Long monthBeforeNow = now - oneMonthInMillis;

    // Create temporary hash map
    Map<String, Double> tempDataMap = new HashMap<String, Double>();

    // Temporary variable to hold percentage value.
    Double percentage;

    // Total sum of energy
    Double total = 0.0;


    // Populate the map depending on display type specified
    switch (type) {

    case CONSUMPTION_DAY:
      tempDataMap = consumption(tempDataMap, dayBeforeNow, now);
      break;

    case CONSUMPTION_WEEK:
      tempDataMap = consumption(tempDataMap, weekBeforeNow, now);
      break;

    case CONSUMPTION_MONTH:
      tempDataMap = consumption(tempDataMap, monthBeforeNow, now);
      break;

    // Populate with the current load of all devices/appliances.
    case DEVICES_CURRENT_LOAD:
      // Iterate through enum class of devices/appliances and calculate total
      for (EnergyConsumptionDevice device : EnergyConsumptionDevice.values()) {
        total += chartInterface.getDeviceCurrentLoad(device);
      }

      // Iterate through devices to get percentage for each.
      for (EnergyConsumptionDevice device : EnergyConsumptionDevice.values()) {
        // Calculate percentage of load for this device
        percentage = chartInterface.getDeviceCurrentLoad(device) / total;

        // Format raw percentage for display on chart
        percentage = formatPercentage(percentage);

        // Add to hashmap.
        tempDataMap.put(device.toString(), percentage);
      }
      break;

    case DEVICES_LOAD_DAY:
      tempDataMap = deviceLoad(tempDataMap, dayBeforeNow, now);
      break;

    case DEVICES_LOAD_WEEK:
      tempDataMap = deviceLoad(tempDataMap, weekBeforeNow, now);
      break;

    case DEVICES_LOAD_MONTH:
      tempDataMap = deviceLoad(tempDataMap, monthBeforeNow, now);
      break;

    case SYSTEM_CURRENT_LOAD:
      for (SystemHSystem system : SystemHSystem.values()) {
        total += chartInterface.getSystemCurrentLoad(system);
      }

      for (SystemHSystem system : SystemHSystem.values()) {
        // Calculate percentage of load for this device
        percentage = chartInterface.getSystemCurrentLoad(system) / total;

        // Format raw percentage for display on chart
        percentage = formatPercentage(percentage);

        // Add to hashmap.
        tempDataMap.put(system.toString(), percentage);
      }
      break;

    case SYSTEM_LOAD_DAY:
      tempDataMap = systemLoad(tempDataMap, dayBeforeNow, now);
      break;

    case SYSTEM_LOAD_WEEK:
      tempDataMap = systemLoad(tempDataMap, weekBeforeNow, now);
      break;

    case SYSTEM_LOAD_MONTH:
      tempDataMap = systemLoad(tempDataMap, monthBeforeNow, now);
      break;

    // Rest of display types
    default:
      break;
    } // End switch

    chartDataMap = tempDataMap;

  } // End populate map method

  /**
   * Formats percentage from raw value (0.17777777) to 17.7 for display on chart. One digit after
   * the decimal place.
   * 
   * @param percentage The raw percentage. E.g., 0.177777
   * @return The formatted percentage. E.g., 17.7
   */
  double formatPercentage(double percentage) {
    // Allow one digit after the decimal
    DecimalFormat df = new DecimalFormat("#.#");

    // Multiply by 100 and leave one digit after decimal place
    return Double.valueOf(df.format(percentage * 100));
  } // End format percentage

  /**
   * Accessor method for the chart data array.
   * 
   * @return Returns the data array
   */
  public double[][] getDataArray() {
    return chartDataArray;
  }

  /**
   * Accessor method for the label string.
   * 
   * @return Returns the label string as an array.
   */
  public String[] getLabelString() {
    return chartLabelArray;
  }

  /**
   * Calculates device load for a given time interval.
   * 
   * @param dataMap - the map to put the loads.
   * @param startTime - start time
   * @param endTime - end time
   * @return - The device loads for the time interval.
   */
  private Map<String, Double> deviceLoad(Map<String, Double> dataMap, 
      long startTime, long endTime) {
    double total = 0.0;
    double percentage = 0.0;
    for (EnergyConsumptionDevice device : EnergyConsumptionDevice.values()) {
      List<TimestampDoublePair> list =
          chartInterface.getDeviceLoadDuringInterval(device, startTime, endTime);
      for (TimestampDoublePair value : list) {
        total += value.getValue();
      }
    }

    for (EnergyConsumptionDevice device : EnergyConsumptionDevice.values()) {
      List<TimestampDoublePair> list =
          chartInterface.getDeviceLoadDuringInterval(device, startTime, endTime);
      Double deviceTotal = 0.0;
      for (TimestampDoublePair value : list) {
        deviceTotal += value.getValue();
      }

      percentage = deviceTotal / total;

      percentage = formatPercentage(percentage);

      dataMap.put(device.toString(), percentage);
    }
    return dataMap;
  }

  /**
   * Calculates system loads for a given time interval.
   * 
   * @param dataMap - The system loads.
   * @param startTime - start time
   * @param endTime - end time
   * @return - The system loads for the time interval.
   */
  private Map<String, Double> systemLoad(Map<String, Double> dataMap, 
      long startTime, long endTime) {
    double total = 0.0;
    double percentage = 0.0;
    for (SystemHSystem system : SystemHSystem.values()) {
      List<TimestampDoublePair> list =
          chartInterface.getSystemLoadDuringInterval(system, startTime, endTime);
      for (TimestampDoublePair value : list) {
        total += value.getValue();
      }
    }

    for (SystemHSystem system : SystemHSystem.values()) {
      Double systemTotal = 0.0;
      List<TimestampDoublePair> list =
          chartInterface.getSystemLoadDuringInterval(system, startTime, endTime);
      for (TimestampDoublePair value : list) {
        systemTotal += value.getValue();
      }

      percentage = systemTotal / total;

      percentage = formatPercentage(percentage);

      dataMap.put(system.toString(), percentage);
    }
    return dataMap;
  }
  
  /**
   * Calculates how much was generated vs consumed over a time interval.
   * @param dataMap - Holds the percentages of generation vs consumption.
   * @param startTime - start time
   * @param endTime - end time
   * @return - The percentages of generation vs consumption.
   */
  private Map<String, Double> consumption(Map<String, Double> dataMap, 
      long startTime, long endTime) {
    double consumption = chartInterface.powerConsumed(startTime, endTime);
    double generation = chartInterface.powerGenerated(startTime, endTime);
    double percentage = 0.0;
    percentage = generation / consumption;
    // The amount generated covers what was consumed.
    if (percentage >= 1.0) {
      dataMap.put(covered, 100.0);
      // Add this so unit testing will always have 2 labels for consumption.
      dataMap.put("Consumption", 0.0);
    }
    else {
      percentage = formatPercentage(percentage);
      // amount covered by generation of solar panels.
      dataMap.put(covered, percentage);
      // amount that consumption goes over generation.
      dataMap.put("Consumption", 100.0 - percentage);
    }
    return dataMap;
  }
} // End Class
