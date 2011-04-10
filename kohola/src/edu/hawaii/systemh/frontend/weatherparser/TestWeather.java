package edu.hawaii.systemh.frontend.weatherparser;


import org.junit.BeforeClass;

import edu.hawaii.ihale.api.command.IHaleCommand;
import edu.hawaii.systemh.frontend.BlankBackend;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;

public class TestWeather {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IHaleCommand testingBackend = new BlankBackend();
		SolarDecathlonApplication.setBackend(testingBackend);
	}

}
