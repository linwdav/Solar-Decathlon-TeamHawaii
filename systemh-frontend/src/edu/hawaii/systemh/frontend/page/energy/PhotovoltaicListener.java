package edu.hawaii.systemh.frontend.page.energy;

import java.util.Calendar;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;
import edu.hawaii.systemh.frontend.components.panel.SystemPanel.SystemHStatus;

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
  private static long[] hourlyAverage = { 1, 1, 1, 1, 1, 1, 3000 / 64, 3000 / 32, 3000 / 16,
      3000 / 8, 3000 / 4, 3000 / 2, 3000 / 2, 3000 / 4, 3000 / 8, 3000 / 16, 3000 / 32, 3000 / 64,
      1, 1, 1, 1, 1, 1, 6000 / 24 };

  private SystemHStatus photovoltaicStatus;

  /**
   * Provide a default constructor that indicates that this listener is for Photovoltaics.
   */
  public PhotovoltaicListener() {
    super(IHaleSystem.PHOTOVOLTAIC);
    this.photovoltaicStatus = SystemHStatus.OK;
  }

  /**
   * Runs when the Photovoltaics state changes.
   * 
   * @param state One of the Photovoltaics state values.
   * @param room Always null for the Photovoltaics system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change.
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    long cautionCap = (Long) value - 10;
    long warningCap = (Long) value - 20;
    switch (state) {
    case ENERGY:
      energy = (Integer) value;
      if (hourlyAverage[Calendar.HOUR_OF_DAY] <= cautionCap
          && hourlyAverage[Calendar.HOUR_OF_DAY] > warningCap) {
        photovoltaicStatus = SystemHStatus.CAUTION;
      }
      else if (hourlyAverage[Calendar.HOUR_OF_DAY] <= warningCap) {
        photovoltaicStatus = SystemHStatus.WARNING;
      }
      else {
        photovoltaicStatus = SystemHStatus.OK;
      }
      break;

    case POWER:
      power = (Integer) value;
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

  /**
   * Gets this photovoltaic status.
   * 
   * @return SystemHStatus
   */
  public SystemHStatus getPhotovoltaicStatus() {
    return this.photovoltaicStatus;
  }
}
