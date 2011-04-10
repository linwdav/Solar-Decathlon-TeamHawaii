package edu.hawaii.systemh.frontend.weatherparser;



import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.hawaii.ihale.api.command.IHaleCommand;
import edu.hawaii.systemh.frontend.BlankBackend;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;

public class TestWeather {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IHaleCommand testingBackend = new BlankBackend();
		SolarDecathlonApplication.setBackend(testingBackend);
	}
	
	@Test
	public void testWeatherModule() {
		WeatherParser parser = new WeatherParser("Honolulu");
	    String currentWeather = parser.getCurrentWeather().toString();
	    assertTrue("Check Weather", currentWeather != "");
	}

}
