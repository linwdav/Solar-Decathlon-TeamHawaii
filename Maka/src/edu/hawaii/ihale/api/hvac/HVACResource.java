package edu.hawaii.ihale.api.hvac;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import edu.hawaii.ihale.api.hsim.MT;

/**
 * Simulates the HVAC System, holds values for the inside temperature.
 * @author Team Maka.
 *
 */
public class HVACResource {
  MT mt = new MT();
  Map <String,String> data;
  //These hold the goal state defined by the user.
  double Temp = 79.4;
  //Array of known keys
  String[] keys;
  String[] localKeys = {"temp"};
  List<String> list;
  
  /**
   * Constructor.
   */
  public HVACResource() {
    keys = localKeys;
    mt = new MT(Calendar.MILLISECOND);
    //initialize all lights to "off"
    data = new HashMap<String,String>();
    //list = Arrays.asList(keys);
  }
  
  /**
   * Refreshes the data.
   */
  public void poll() {
    data.put("temp", "" + getTemp());
  }
  
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  public void set(String key, String val) {
    double v = sToD(val);
      Temp = v;
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
    double currentTemp = sToD(data.get("temp"));
    return (currentTemp + Temp) / 2 + mt.nextDouble(-.05,.05); 
  }
  
  /**
   * Simulates the outdoor temperature changing slowly over time based on
   * the time of day.
   * @return An updated temp value.
   */
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
}
