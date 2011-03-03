package edu.hawaii.ihale.frontend;

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

  /**
   * Provide a default constructor that indicates that this listener is for Photovoltaics.
   */
  public PhotovoltaicListener() {
    super("photovoltaics");
  }

  /**
   * Invoked whenever a new state entry for PV is received by the system.
   * 
   * @param entry A SystemStateEntry for the PV system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in PV: " + entry);
    if (entry.getLongValue("power") != -1) {
      Energy.setRemainingPower(entry.getLongValue("power"));
    }
  }
}
