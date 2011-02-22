package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 */
public class WaterListener extends SystemStateListener {

  /**
   * Provide a default constructor that indicates that this listener is for Water.
   */
  public WaterListener() {
    super("Water");
  }

  /**
   * Invoked whenever a new state entry for Water is received by the system.
   * 
   * @param entry A SystemStateEntry for the Water system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Water: " + entry);
  }
}
