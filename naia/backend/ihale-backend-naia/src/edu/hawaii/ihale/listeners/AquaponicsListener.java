package edu.hawaii.ihale.listeners;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Listener Class for aquaponics.
 * 
 * @author Team Nai'a
 *
 */
public class AquaponicsListener extends SystemStateListener {
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("Aquaponics");
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Current state of aquaponics pH is: " + entry.getDoubleValue("pH"));
  }
}
