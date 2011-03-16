package edu.hawaii.ihale.frontend.page.energy;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener for consumption that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class ElectricalListener extends SystemStateListener {

  // private static final String SYSTEM_NAME = "electrical";
  // private static final String POWER_KEY = "power";;
  // private static final String ENERGY_KEY = "energy";
  
  private long power = -1;
  private long energy = -1;

  /**
   * Provide a default constructor that indicates that this listener is for Electricity Consumption.
   */
  public ElectricalListener() {
    super(IHaleSystem.ELECTRIC);
  }

  /**
   * Runs when the Electrical state changes. 
   * @param state One of the Electrical state values. 
   * @param room Always null for the Electrical system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    System.out.println("Something just happened in EC.");
//    if (entry.getLongValue(POWER_KEY) != -1) {
//      power = entry.getLongValue(POWER_KEY);
//    }
//    if (entry.getLongValue(ENERGY_KEY) != -1) {
//      energy = entry.getLongValue(ENERGY_KEY);
//    }
  }

  /**
   * Return the power.
   * 
   * @return The power.
   */
  public long getPower() {
    return power;
  }

  /**
   * Return the energy.
   * 
   * @return The energy.
   */
  public long getEnergy() {
    return energy;
  }
}
