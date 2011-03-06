package edu.hawaii.ihale.electrical;

import java.util.Arrays;

import edu.hawaii.ihale.housesimulator.EGauge;
/**
 * Simulates the Electrical System, holds values for power and energy consumed.
 * @author Team Maka
 *
 */
public class ElectricalResource extends EGauge {
  //Maps need to be non-final...
  static double goalEnergy = 5918.9, goalPower = -3.5, goalWs = 2130813014;
  static final String energy = "energy", ws = "energyWs", power = "power";
  /** Local keys used by the resource.*/
  public String[] localKeys = {energy, power,ws};
  private EnergyRepository repository;
  /**
   * Constructor.
   */
  public ElectricalResource() {
    super();
    meterName = "Grid";
    repository = EnergyRepository.getInstance();
    keys = localKeys; 
    list = Arrays.asList(keys);
  }

  /**
   * Refreshes data.
   */
  @Override
  public void poll() {
    repository.setEnergy(String.valueOf(getEnergy()));
    repository.setPower(String.valueOf(getPower()));
    repository.setJoules(String.valueOf(getWs()));
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
    double currentEnergy = sToD(repository.getJoules());
    return currentEnergy + (goalEnergy - currentEnergy) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the pH changing slowly over time towards the goal state.
   * @return An updated pH value.
   */
  private double getPower() {
    double currentPower = sToD(repository.getPower());
    return currentPower + (goalPower - currentPower) / 100 + mt.nextDouble(-.05,.05);
  }
  
  /**
   * Simulates the wattage change.
   * @return An updated wattage value.
   */
  private double getWs() {
    double currentWs = sToD(repository.getJoules());
    return currentWs + (goalWs - currentWs) / 100 + mt.nextDouble (-1000,1000);
  }
}
