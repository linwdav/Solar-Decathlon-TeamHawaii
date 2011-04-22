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
  void populateFromDataMap() {
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
  void populateChartDataMap(ChartDisplayType type) {
    // Get the time now
    Long now = System.currentTimeMillis();

    // Create temporary hash map
    Map<String, Double> tempDataMap = new HashMap<String, Double>();

    // Temporary variable to hold percentage value.
    Double percentage;

    // Total sum of energy
    Double total = 0.0;

    // Populate the map depending on display type specified
    switch (type) {

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

    case SYSTEM_LOAD_WEEK:
      // Get the time one week before now
      Long weekBeforeNow = now - oneWeekInMillis;

      for (SystemHSystem system : SystemHSystem.values()) {
        List<TimestampDoublePair> list =
            chartInterface.getSystemLoadDuringInterval(system, weekBeforeNow, now);
        for (TimestampDoublePair value : list) {
          total += value.getValue();
        }
      }

      for (SystemHSystem system : SystemHSystem.values()) {
        Double systemTotal = 0.0;
        List<TimestampDoublePair> list =
            chartInterface.getSystemLoadDuringInterval(system, weekBeforeNow, now);
        for (TimestampDoublePair value : list) {
          systemTotal += value.getValue();
        }
        
        percentage = systemTotal / total;
        
        percentage = formatPercentage(percentage);
        
        tempDataMap.put(system.toString(), percentage);
      }
      break;
    // TO DO
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
  double[][] getDataArray() {
    return chartDataArray;
  }

  /**
   * Accessor method for the label string.
   * 
   * @return Returns the label string as an array.
   */
  String[] getLabelString() {
    return chartLabelArray;
  }

} // End Class
