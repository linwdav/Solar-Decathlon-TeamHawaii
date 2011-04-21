package edu.hawaii.systemh.energymodel.chartgenerator;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import edu.hawaii.systemh.api.repository.impl.Repository;
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
    // Setup chart data instance with repository and chart interface
    Repository repository = new Repository();
    EnergyManagementChartInterface chartInterface = new SampleChartInterface(repository);
    chartData = new EnergyChartData(chartInterface);
  }

  /**
   * Tests to ensure chart data is populated correctly.
   */
  @Test
  public void testPopulateFromDataMap() {
    
    // Populate data map and arrays
    chartData.populateChartDataMap(ChartDisplayType.CURRENT_LOAD);
    chartData.populateFromDataMap();
    
    assertEquals("Check labels", Arrays.toString(chartData.getLabelString()), 
        "[SOLAR THERMAL CONTROLLER, REFRIDGERATOR, OTHER, VENTILATION FAN, " +
        "TELEVISION, WASHER DRYER, DISHWASHER, STEREO SYSTEM, FREEZER]");
    
    assertEquals("Check labels", Arrays.deepToString(chartData.getDataArray()), 
        "[[8.0, 1.0, 9.0, 7.0, 6.0, 4.0, 3.0, 5.0, 2.0]]");
  }

  /*
  @Test
  public void testPopulateChartDataMap() {
  }
  */
} // End test class
