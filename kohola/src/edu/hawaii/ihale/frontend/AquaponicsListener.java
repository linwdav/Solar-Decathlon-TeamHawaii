package edu.hawaii.ihale.frontend;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener for aquaponics that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsListener extends SystemStateListener {

  private long temp = -1;
  private double pH = -1.0;
  private double oxygen = -1.0;

  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("aquaponics");
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * 
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);

    // update instances
    if (entry.getLongValue("temp") != -1) {
      temp = entry.getLongValue("temp");
    }
    if (entry.getDoubleValue("ph") != -1.0) {
      pH = entry.getDoubleValue("ph");
    }
    if (entry.getDoubleValue("oxygen") != -1.0) {
      oxygen = entry.getDoubleValue("oxygen");
    }
  }

  /**
   * Return the temperature.
   * 
   * @return The temperature Fahrenheit.
   */
  public long getTemp() {
    return temp;
  }

  /**
   * Return the ph value.
   * 
   * @return The ph value.
   */
  public double getPH() {
    return pH;
  }

  /**
   * Return the oxygen.
   * 
   * @return The oxygen
   */
  public double getOxygen() {
    return oxygen;
  }

}
