package edu.hawaii.ihale.photovoltaics;

import java.util.Arrays;
import edu.hawaii.ihale.housesimulator.EGauge;
/**
 * Simulates the Photovoltaics System, holds values for power and energy consumed.
 * @author Team Maka
 *
 */
public class PhotovoltaicResource extends EGauge {

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
    PhotovoltaicRepository.getInstance();
    keys = localKeys; 
    list = Arrays.asList(keys);
  }

  /**
   * Refreshes data.
   */
  @Override
  public void poll() {
    PhotovoltaicRepository.setEnergy(String.valueOf(getEnergy()));
    PhotovoltaicRepository.setPower(String.valueOf(getPower()));
    PhotovoltaicRepository.setJoules(String.valueOf(getWs()));
  }
  
  /**
   * Returns the Contact instance requested by the URL. 
   * @return The XML representation of the contact, or CLIENT_ERROR_NOT_ACCEPTABLE if the 
   * unique ID is not present.
   * @throws Exception If problems occur making the representation. Shouldn't occur in 
   * practice but if it does, Restlet will set the Status code. 
   */
  
  /**
   * Converts a String to a double.
   * @param val String to convert.
   * @return The double represented by the String.
   */
  private Double sToD(String val) {
    double v = 0;
    try {
      v = Double.valueOf(val);
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
    double currentEnergy = sToD(PhotovoltaicRepository.getEnergy());
    return currentEnergy + (goalEnergy - currentEnergy) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the pH changing slowly over time towards the goal state.
   * @return An updated pH value.
   */
  private double getPower() {
    double currentPH = sToD(PhotovoltaicRepository.getEnergy());
    return currentPH + (goalPower - currentPH) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the wattage change.
   * @return An updated wattage value.
   */
  private double getWs() {
    double currentWs = sToD(PhotovoltaicRepository.getJoules());
    return currentWs + (goalWs - currentWs) / 100 + mt.nextDouble (-1000,1000);
  }
}