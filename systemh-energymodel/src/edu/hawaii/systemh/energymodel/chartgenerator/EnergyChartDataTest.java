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

  // PMD strings
  private static final String deviceLabel =
      "[SOLAR THERMAL CONTROLLER, REFRIDGERATOR, OTHER, VENTILATION FAN, "
          + "TELEVISION, WASHER DRYER, DISHWASHER, STEREO SYSTEM, FREEZER]";
  private static final String deviceData = "[[17.8, 2.2, 20.0, 15.6, 13.3, 8.9, 6.7, 11.1, 4.4]]";
  private static final String systemLabel = "[HVAC, AQUAPONICS, ELECTRIC, LIGHTING, PHOTOVOLTAIC]";
  private static final String systemData = "[[22.5, 25.0, 17.5, 15.0, 20.0]]";

  // Chart data instance to use
  EnergyChartData chartData;
  EnergyManagementChartInterface chartInterface;

  /**
   * Setup.
   * 
   * @throws Exception exception.
   */
  @Before
  public void setUp() throws Exception {
    // Setup chart data instance with chart interface
    chartInterface = new SampleChartInterface();
    chartData = new EnergyChartData(chartInterface);
  }

  /**
   * Tests to ensure chart data is populated correctly.
   */
  @Test
  public void testPopulateFromDataMap() {

    helpTestChartDataMap(ChartDisplayType.DEVICES_CURRENT_LOAD, deviceLabel, deviceData);

    helpTestChartDataMap(ChartDisplayType.DEVICES_LOAD_DAY, deviceLabel, deviceData);

    helpTestChartDataMap(ChartDisplayType.DEVICES_LOAD_WEEK, deviceLabel, deviceData);

    helpTestChartDataMap(ChartDisplayType.DEVICES_LOAD_MONTH, deviceLabel, deviceData);

    helpTestChartDataMap(ChartDisplayType.SYSTEM_CURRENT_LOAD, systemLabel, systemData);

    helpTestChartDataMap(ChartDisplayType.SYSTEM_LOAD_DAY, systemLabel, systemData);

    helpTestChartDataMap(ChartDisplayType.SYSTEM_LOAD_WEEK, systemLabel, systemData);

    helpTestChartDataMap(ChartDisplayType.SYSTEM_LOAD_MONTH, systemLabel, systemData);

    helpTestChartDataMap(ChartDisplayType.CONSUMPTION_DAY, "[Consumption Covered, Consumption]",
        "[[70.0, 30.0]]");

    helpTestChartDataMap(ChartDisplayType.CONSUMPTION_WEEK, "[Consumption Covered, Consumption]",
        "[[80.0, 20.0]]");

    helpTestChartDataMap(ChartDisplayType.CONSUMPTION_MONTH, "[Consumption Covered, Consumption]",
        "[[90.0, 10.0]]");

  }

  /**
   * Helper method to make code shorter.
   * 
   * @param display - What type of display to test.
   * @param labels - expected labels of pie chart.
   * @param data - expected data of pie chart.
   */
  private void helpTestChartDataMap(ChartDisplayType display, String labels, String data) {
    // System.out.println("TESTING DISPLAY: " + display);
    chartData.populateChartDataMap(display);
    chartData.populateFromDataMap();

    assertEquals("Check system labels", Arrays.toString(chartData.getLabelString()), labels);
    assertEquals("Check system data", Arrays.deepToString(chartData.getDataArray()), data);
  }
  /*
   * @Test public void testPopulateChartDataMap() { }
   */
} // End test class
