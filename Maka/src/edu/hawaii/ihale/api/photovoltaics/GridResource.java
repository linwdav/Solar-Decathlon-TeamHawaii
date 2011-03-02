package edu.hawaii.ihale.api.photovoltaics;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.hawaii.ihale.api.hsim.EGauge;
/**
 * Simulates the Photovoltaics System, holds values for power and energy consumed.
 * @author Team Maka
 *
 */
public class GridResource extends EGauge {
  //Maps need to be non-final...
  @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
  static Map<String, String> gridData = new ConcurrentHashMap<String, String>();
  static double goalEnergy = 1443.5, goalPower = 2226.2;
  static final String energy = "energy", power = "power";
  /** Local keys used by the resource.*/
  public String[] localKeys = {energy, power};
  /**
   * Constructor.
   */
  public GridResource() {
    super();
    meterName = "Solar";
    if (gridData.get(localKeys[0]) == null) {
      gridData.put(energy, String.valueOf(goalEnergy));
      gridData.put(power, String.valueOf(goalPower));
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

}
