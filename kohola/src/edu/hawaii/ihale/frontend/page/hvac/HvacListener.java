package edu.hawaii.ihale.frontend.page.hvac;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener for Hvac that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @author Chuan Lun Hung
 * @author Kylan Hughes
 */
public class HvacListener extends SystemStateListener {
  
  //private static final String SYSTEM_NAME = "hvac";
  //private static final String TEMP_KEY = "temp";
  
  private int temp = -1;
  
  /**
   * Provide a default constructor that indicates that this listener is for Hvac.
   */
  public HvacListener() {
    super(IHaleSystem.HVAC);
  }

  /**
   * Runs when the Hvac state changes. 
   * @param state One of the Hvac state values. 
   * @param room Always null for the Hvac system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    switch (state) {
    case TEMPERATURE :
      temp = (Integer) value;
      System.out.println("New Hvac temperature is: " + temp);
      break;
    case SET_TEMPERATURE_COMMAND :
      temp = (Integer) value;
      System.out.println("Hvac temperature set to: " + temp);
      break;
    default:
      System.out.println("Unhandled aquaponics state: " + state);
    }
  }
  
  /**
   * The house temperature.
   * @return The temperature in Fahrenheit.
   */
  public int getTemp() {
    return temp;
  } 
}
