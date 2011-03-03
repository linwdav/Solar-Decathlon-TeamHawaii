package edu.hawaii.ihale.hvac;
 
import java.util.Arrays; 
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import edu.hawaii.ihale.housesimulator.Arduino;

/**
 * Simulates the HVAC System, holds values for the inside temperature.
 * @author Team Maka.
 *
 */
@edu.umd.cs.findbugs.annotations.SuppressWarnings(value =
  "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
  justification = "Restlet makes multiple instances of this class, so " +
  "nonstatic variables are lost.")
public class HVACResource extends Arduino { 
  //Maps need to be non-final...
  @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
  static Map<String, String> hvacData = new ConcurrentHashMap<String, String>();
  //These hold the goal state defined by the user.
  static double goalTemp = 79.4;
  String temp = "temp";
  //Array of known keys 
  String[] localKeys = {temp}; 
  
  /**
   * Constructor.
   */
  public HVACResource() {
    super("hvac","arduino-3");
    if (hvacData.get(localKeys[0]) == null) {
      hvacData.put(temp, String.valueOf(goalTemp));
      data2.put("hvac", hvacData);

    }
    keys = localKeys; 
    list = Arrays.asList(keys);
    /*
    if (data.get(temp) == null) {
      data.put(temp, "" + goalTemp);
    }
    */

  }
  
  /**
   * Refreshes the data.
   */
  @Override
  public void poll() {
    hvacData.put(temp, String.valueOf(getTemp()));

  }
  
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    double v = sToD(val);
      goalTemp = v;
  }
  
  /**
   * Converts a String to a double.
   * @param val String to convert.
   * @return The double represented by the String.
   */
  private Double sToD(String val) {
    double v = 0;
    try {
      v = Double.valueOf(val).doubleValue();
   } 
    catch (NumberFormatException e) {
      System.out.println(e);
   }
   return v;
  }
  
  /**
   * Simulates the temperature changing slowly over time.
   * @return An updated temp value.
   */
  private double getTemp() {
    double currentTemp = sToD(hvacData.get(temp));
    return currentTemp + (goalTemp - currentTemp) / 100 + mt.nextDouble(-.05,.05); 
  }
  
  /**
   * Simulates the outdoor temperature changing slowly over time based on
   * the time of day.
   * @return An updated temp value.
   */
  /*
  private double getOutdoorTemp() {
    double hour = Calendar.HOUR_OF_DAY;
    double min = Calendar.MINUTE / 60;
    hour += min;
    double baseTemp = 78.5;
    double rate = 2.5 / 12.0 + mt.nextDouble(0,.05);
    //night
    if (hour <= 6 || hour >= 18) {
      return (baseTemp - (hour % 18) * rate);
    }
    //day
    else {
      return (baseTemp + (hour - 6) * rate);
    }
  }
  */
}