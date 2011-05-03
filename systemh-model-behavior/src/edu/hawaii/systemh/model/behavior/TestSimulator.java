package edu.hawaii.systemh.model.behavior;
  
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue; 
import org.junit.Test; 

/**
 * Verify that the simulator produces data within reasonable bounds.
 * @author Gregory Burgess
 * */
public class TestSimulator {
   
  /**
   * Checks to see that data functions work within normal bounds.
   * This ensures that any errors thrown will be related to internet
   * access.
   */
 @Test
 public void checkData() {
   Simulator sim = Simulator.getInstance();
   double data = sim.getSolarIntensity();
   for (double i = 12; i >= -12; i--) {
     data = sim.getSolarIntensity(i);
     assertTrue("Solar values are not negative", data >= 0 && data <= 100);
   }
   data = sim.getOutsideTemp();
   double check = sim.cToF(sim.fToC(data));
   assertTrue("Make sure the conversion functions work, within a margin of error"
       ,data > check - .1 || data < check + .1);
   
   assertNotNull("Ensure WeatherReport was created succesfully",
                 sim.getWeatherReport());
   assertTrue("Time is within correct bounds",sim.getTime() > -12 && sim.getTime() < 12);
   long time = System.currentTimeMillis();
   double newTime = sim.getSystemTime(time);
   assertTrue("Time conversion works properly", newTime > -12 && newTime < 12 );
 }
}
