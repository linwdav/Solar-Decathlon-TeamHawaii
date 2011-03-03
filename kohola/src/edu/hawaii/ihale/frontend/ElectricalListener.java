package edu.hawaii.ihale.frontend;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener for consumption that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class ElectricalListener extends SystemStateListener {

  /**
   * Provide a default constructor that indicates that this listener is for Electricity Consumption.
   */
  public ElectricalListener() {
    super("electrical");
  }

  /**
   * Invoked whenever a new state entry for consumption is received by the system.
   * 
   * @param entry A SystemStateEntry.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in EC: " + entry);

  }

}
