package edu.hawaii.ihale.photovoltaics;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.hawaii.ihale.housesimulator.EGauge;
/**
 * Simulates the Photovoltaics System, holds values for power and energy consumed.
 * @author Team Maka
 *
 */
public class PhotovoltaicResource extends EGauge {
  //Maps need to be non-final...
  @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
  static Map<String, String> gridData = new ConcurrentHashMap<String, String>();
  static double goalEnergy = 1443.5, goalPower = 2226.2, goalWs = 2130813014;
  static final String energy = "energy", ws = "energyWs", power = "power";
  /** Local keys used by the resource.*/
  public String[] localKeys = {energy, power, ws};
  /**
   * Constructor.
   */
  public PhotovoltaicResource() {
    super();
    meterName = "Solar";
    if (gridData.get(localKeys[0]) == null) {
      gridData.put(energy, String.valueOf(goalEnergy));
      gridData.put(power, String.valueOf(goalPower));
      gridData.put(ws, String.valueOf(goalWs));
      data.put(meterName, gridData);
    }
    keys = localKeys; 
    list = Arrays.asList(keys);
  }

  /**
   * Refreshes data.
   */
  @Override
  public void poll() {
    gridData.put(energy, String.valueOf(getEnergy()));
    gridData.put(power, String.valueOf(getPower()));
    gridData.put(ws, String.valueOf(getWs()));
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
  private double getEnergy() {
    double currentEnergy = sToD(gridData.get(energy));
    return currentEnergy + (goalEnergy - currentEnergy) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the pH changing slowly over time towards the goal state.
   * @return An updated pH value.
   */
  private double getPower() {
    double currentPH = sToD(gridData.get(power));
    return currentPH + (goalPower - currentPH) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the wattage change.
   * @return An updated wattage value.
   */
  private double getWs() {
    double currentWs = sToD(gridData.get(ws));
    return currentWs + (goalWs - currentWs) / 100 + mt.nextDouble (-1000,1000);
  }
}
