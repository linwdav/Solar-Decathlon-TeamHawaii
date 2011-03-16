package edu.hawaii.ihale.frontend.page.aquaponics;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener for aquaponics that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsListener extends SystemStateListener {

  // private static final String SYSTEM_NAME = "aquaponics";
  // private static final String TEMPERATURE_KEY = "temp";;
  // private static final String PH_KEY = "ph";
  // private static final String OXYGEN_KEY = "oxygen";

  private long temp = -1;
  private double pH = -1.0;
  private double oxygen = -1.0;

  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super(IHaleSystem.AQUAPONICS);
  }

  /**
   * Runs when the Aquaponics state changes. 
   * @param state One of the aquaponics state values. 
   * @param room Always null for the Aquaponics system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    System.out.println("Something just happened in Aquaponics.");

    // // update instances
    // if (entry.getLongValue(TEMPERATURE_KEY) != -1) {
    // temp = entry.getLongValue(TEMPERATURE_KEY);
    // }
    // if (entry.getDoubleValue(PH_KEY) != -1.0) {
    // pH = entry.getDoubleValue(PH_KEY);
    // }
    // if (entry.getDoubleValue(OXYGEN_KEY) != -1.0) {
    // oxygen = entry.getDoubleValue(OXYGEN_KEY);
    // }    
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
