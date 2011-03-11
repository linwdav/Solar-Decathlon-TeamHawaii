package edu.hawaii.ihale.frontend.page.energy;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class PhotovoltaicListener extends SystemStateListener {
  
  private static final String SYSTEM_NAME = "photovoltaics";
  private static final String POWER_KEY = "power";
  private static final String ENERGY_KEY = "energy";

  private long power = -1;
  private long energy = -1;

  /**
   * Provide a default constructor that indicates that this listener is for Photovoltaics.
   */
  public PhotovoltaicListener() {
    super(SYSTEM_NAME);
  }

  /**
   * Invoked whenever a new state entry for PV is received by the system.
   * 
   * @param entry A SystemStateEntry for the PV system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in PV: " + entry);
    if (entry.getLongValue(POWER_KEY) != -1) {
      power = entry.getLongValue(POWER_KEY);
    }
     if (entry.getLongValue(ENERGY_KEY) != -1) {
     energy = entry.getLongValue(ENERGY_KEY);
     }
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
