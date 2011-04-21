package edu.hawaii.systemh.energymodel.chartgenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.ChartDisplayType;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;
import edu.hawaii.systemh.energymodel.chartinterface.EnergyManagementChartInterface;

/**
 * Collects and organizes System-H energy data for chart generation.
 * This is specific to Pie Charts.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 *
 */
public class EnergyChartData {

  // The chart interface associated with this class.
  EnergyManagementChartInterface chartInterface;
  
  // The list of labels
  String[] chartLabelArray;
  
  // The data for the chart
  double[][] chartDataArray;
  
  // Key-value pairs for chart data
  Map<String, Double> chartDataMap = new HashMap<String,Double>();
  
  /**
   * Assigns a chart interface to the instance.
   * @param chartInterface The interface to use.
   */
  public EnergyChartData (EnergyManagementChartInterface chartInterface) {
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
      
      // Assign data values.  data must be in a 2D array consisting
      // of a single row.
      tempDataArray[0][i++] = e.getValue();
    }
    
    // Assign temp arrays to instance variables
    chartLabelArray = tempLabelArray;
    chartDataArray = tempDataArray;
    
  } // End populate from data map method
  
  /**
   * Populate the chart data hash map.
   */
  void populateChartDataMap (ChartDisplayType type) {
    
    Map<String, Double> tempDataMap = new HashMap<String, Double>();
    
    // Populate the map depending on display type specified
    switch (type) {
    
    case CURRENT_LOAD:
      // Iterate through enum class of devices/appliances
      for (EnergyConsumptionDevice device : EnergyConsumptionDevice.values()) {
        Double value = chartInterface.getDeviceCurrentLoad(device);
        tempDataMap.put(device.toString(), value);
      }
    } // End switch
    
    chartDataMap = tempDataMap;
    
  } // End populate map method
  
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
