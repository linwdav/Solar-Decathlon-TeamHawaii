package edu.hawaii.systemh.frontend.page.energy;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class PhotovoltaicListener extends SystemStateListener {
  

  // private static final String SYSTEM_NAME = "photovoltaics";
  // private static final String POWER_KEY = "power";
  // private static final String ENERGY_KEY = "energy";

  private Integer power = -1;
  private Integer energy = -1;

  /**
   * Provide a default constructor that indicates that this listener is for Photovoltaics.
   */
  public PhotovoltaicListener() {
    super(IHaleSystem.PHOTOVOLTAIC);
  }

  /**
   * Runs when the Photovoltaics state changes. 
   * @param state One of the Photovoltaics state values. 
   * @param room Always null for the Photovoltaics system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    switch (state) {
    case ENERGY: 
      energy = (Integer) value;
      System.out.println("Photovoltaics energy level is: " + energy);
      break;
    
    case POWER: 
      power = (Integer) value;
      System.out.println("Photovoltaics power level is: " + power);
      break;
    
    default: 
      System.out.println("Unhandled Photovoltaics state: " + state);
    
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