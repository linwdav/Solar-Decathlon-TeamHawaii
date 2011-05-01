package edu.hawaii.systemh.frontend.page.hvac;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;
import edu.hawaii.systemh.frontend.components.panel.SystemPanel.SystemHStatus;

/**
 * A listener for Hvac that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Chuan Lun Hung
 * @author Kylan Hughes
 */
public class HvacListener extends SystemStateListener {

  // private static final String SYSTEM_NAME = "hvac";
  // private static final String TEMP_KEY = "temp";

  private int temp = -1;
  private int tempCommand = -1;
  
  private static final int LOW_ROOM_TEMP_BOTTOM = 16;
  private static final int LOW_ROOM_TEMP_TOP = 18;
  private static final int HIGH_ROOM_TEMP_BOTTOM = 30;
  private static final int HIGH_ROOM_TEMP_TOP = 32;

  private SystemHStatus hvacStatus;

  /**
   * Provide a default constructor that indicates that this listener is for Hvac.
   */
  public HvacListener() {
    super(IHaleSystem.HVAC);
    this.hvacStatus = SystemHStatus.OK;
  }

  /**
   * Runs when the Hvac state changes.
   * 
   * @param state One of the Hvac state values.
   * @param room Always null for the Hvac system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change.
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    switch (state) {
    case TEMPERATURE:
      temp = (Integer) value;
      // When temperature changes, check its value to populate warnings if applicable.
      if ((temp <= LOW_ROOM_TEMP_TOP && temp >= LOW_ROOM_TEMP_BOTTOM)
          || (temp <= HIGH_ROOM_TEMP_TOP && temp >= HIGH_ROOM_TEMP_BOTTOM)) {
        hvacStatus = SystemHStatus.CAUTION;
      }
      else if (temp < LOW_ROOM_TEMP_BOTTOM || temp > HIGH_ROOM_TEMP_TOP) {
        hvacStatus = SystemHStatus.WARNING;
      }
      else {
        hvacStatus = SystemHStatus.OK;
      }
      break;
    case SET_TEMPERATURE_COMMAND:
      tempCommand = (Integer) value;
      break;
    default:
      System.out.println("Unhandled aquaponics state: " + state);
    }
  }

  /**
   * The house temperature.
   * 
   * @return The temperature in Celsius.
   */
  public int getTemp() {
    return temp;
  }
  
  /**
   * The desired house temperature set by user.
   * 
   * @return The temperature in Celsius.
   */
  public int getTempCommand() {
    return tempCommand;
  }
  
  /**
   * Gets this HVAC status.
   * 
   * @return SystemHStatus
   */
  public SystemHStatus getHvacStatus() {
    return this.hvacStatus;
  }
}