package edu.hawaii.ihale.api.aquaponics;

import java.util.Calendar;
import java.util.Arrays;
import edu.hawaii.ihale.api.hsim.MT;
import edu.hawaii.ihale.api.hsim.Arduino;
/**
 * Simulates the Aquaponics System, holds values for temperature (temp), 
 * pH (pH), and dissolved oxygen (oxygen).
 * @author Team Maka
 *
 */
public class AquaponicsResource extends Arduino{
  MT mt;
  //These hold the goal state defined by the user.
  double goalPH = 7, goalTemp = 78., goalOxygen = .5;
  String temp = "aqtemp", pH = "aqpH", oxygen = "aqoxygen";
  String[] localKeys = {temp, pH, oxygen};
  
  /**
   * Constructor.
   */
  public AquaponicsResource() {
    super("aquaPonics","arduino-1");
    keys = localKeys;
    mt = new MT(Calendar.MILLISECOND);
    //initialize all lights to "off"
    list = Arrays.asList(keys);
    data.put(temp, "" + goalTemp);
    data.put(pH, "" + goalPH);
    data.put(oxygen, "" + goalOxygen);
    
  }
  
  /**
   * Refreshes data.
   */
  @Override
  public void poll() {
    data.put(temp, "" + getTemp());
    data.put(pH, "" + getPH());
    data.put(oxygen, "" + getOxygen());
    System.out.println("Polled");
  }
  
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    double v = sToD(val);
    if (key.toLowerCase().equals(temp.substring(2))) {
      goalTemp = v;
      System.out.println("Temp set to" + goalTemp);
    }
    else if (key.toLowerCase().equals(pH.substring(2))) {
      goalPH = v;
      System.out.println("pH set to" + goalPH);
    }
    else if (key.toLowerCase().equals(oxygen.substring(2))) {
      goalOxygen = v;
      System.out.println("Oxygen set to" + goalOxygen);
    }
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
   * Simulates the pH changing slowly over time towards the goal state.
   * @return An updated pH value.
   */
  private double getPH() {
    double currentPH = sToD(data.get(pH));
    return currentPH + (currentPH - goalPH) / 10 + mt.nextDouble(-.05,.05);
  }

  /**
   * Simulates the dissolved Oxygen changing slowly over time towards
   * the goal state.
   * @return An updated oxygen value.
   */
  private double getOxygen() {
    double currentDO = sToD(data.get(oxygen));
    return currentDO + (currentDO - goalOxygen) / 10 + mt.nextDouble(-.01,.01);
  }
  
  /**
   * Simulates the temperature changing slowly over time.
   * @return An updated temp value.
   */
  private double getTemp() {
    double currentTemp = sToD(data.get(temp));
    return (currentTemp + goalTemp) / 10 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the outdoor temperature changing slowly over time based on
   * the time of day.
   * @return An updated temp value.
   */
 /* private double getOutdoorTemp() {
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
  }*/
}
