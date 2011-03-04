package edu.hawaii.ihale.frontend;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener for lighting that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class LightsListener extends SystemStateListener {
  
  private static final String SYSTEM_NAME = "lighting";
  
  /**
   * Provide a default constructor that indicates that this listener is for lighting.
   */
  public LightsListener() {
    super(SYSTEM_NAME);
  }

  /**
   * Invoked whenever a new state entry for lighting is received by the system.
   * @param entry A SystemStateEntry for the lighting system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Lights: " + entry);
  }
}
