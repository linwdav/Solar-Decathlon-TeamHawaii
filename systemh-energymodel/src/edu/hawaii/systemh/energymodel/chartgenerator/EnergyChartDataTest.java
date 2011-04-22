package edu.hawaii.systemh.energymodel.chartgenerator;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.ChartDisplayType;
import edu.hawaii.systemh.energymodel.chartinterface.EnergyManagementChartInterface;
import edu.hawaii.systemh.energymodel.chartinterface.SampleChartInterface;

/**
 * Test class for the Energy Chart Data Helper class.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 *
 */
public class EnergyChartDataTest {

  // Chart data instance to use
  EnergyChartData chartData;

  /**
   * Setup.
   * @throws Exception exception.
   */
  @Before
  public void setUp() throws Exception {
    // Setup chart data instance with chart interface
    EnergyManagementChartInterface chartInterface = new SampleChartInterface();
    chartData = new EnergyChartData(chartInterface);
  }

  /**
   * Tests to ensure chart data is populated correctly.
   */
  @Test
  public void testPopulateFromDataMap() {
    
    // Populate data map and arrays
    chartData.populateChartDataMap(ChartDisplayType.DEVICES_CURRENT_LOAD);
    chartData.populateFromDataMap();
    
    assertEquals("Check labels", Arrays.toString(chartData.getLabelString()), 
        "[SOLAR THERMAL CONTROLLER, REFRIDGERATOR, OTHER, VENTILATION FAN, " +
        "TELEVISION, WASHER DRYER, DISHWASHER, STEREO SYSTEM, FREEZER]");
    System.out.println("LABELS: " + Arrays.toString(chartData.getLabelString()));
    System.out.println("DATA: " + Arrays.deepToString(chartData.getDataArray()));
    
    assertEquals("Check labels", Arrays.deepToString(chartData.getDataArray()), 
        "[[17.8, 2.2, 20.0, 15.6, 13.3, 8.9, 6.7, 11.1, 4.4]]");
  }

  /*
  @Test
  public void testPopulateChartDataMap() {
  }
  */
} // End test class
