package edu.hawaii.systemh.frontend.weatherparser;

import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;
import edu.hawaii.ihale.api.command.IHaleCommand;
import edu.hawaii.systemh.frontend.BlankBackend;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;

/**
 * Tests the Weather Module.
 * 
 * @author FrontEnd
 * 
 */
public class TestWeather {

  /**
   * Setup method for Weather Tester class.
   * 
   * @throws Exception Any problems during setup.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    IHaleCommand testingBackend = new BlankBackend();
    SolarDecathlonApplication.setBackend(testingBackend);
  }

  /**
   * Test method for the Weather Module.
   */
  @Test
  public void testWeatherModule() {
    WeatherParser parser = new WeatherParser("Honolulu");
    String currentWeather = parser.getCurrentWeather().toString();
    assertFalse("Check Weather", currentWeather.equalsIgnoreCase(""));
  }

}
