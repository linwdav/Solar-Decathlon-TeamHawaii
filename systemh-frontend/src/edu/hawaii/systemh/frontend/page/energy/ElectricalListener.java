package edu.hawaii.systemh.frontend.page.energy;

import java.util.Calendar;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.page.SystemStatusPanel.SystemStatus;

/**
 * A listener for consumption that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class ElectricalListener extends SystemStateListener {

  // private static final String SYSTEM_NAME = "electrical";
  // private static final String POWER_KEY = "power";;
  // private static final String ENERGY_KEY = "energy";

  private Integer power = -1;
  private Integer energy = -1;
  private static long[] hourlyAverage = { // in watts per hour
      1640, 1620, 1550, 1500, 1580, 1640, 1890, 2120, 1990, 1910, 1970, 1980, 1910, 1900, 1890,
          1880, 1850, 1860, 1910, 2230, 2080, 2070, 1980, 1880 };

  /**
   * Provide a default constructor that indicates that this listener is for Electricity Consumption.
   */
  public ElectricalListener() {
    super(IHaleSystem.ELECTRIC);
  }

  /**
   * Runs when the Electrical state changes.
   * 
   * @param state One of the Electrical state values.
   * @param room Always null for the Electrical system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change.
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    long cautionCap = (Long) value + 40;
    long warningCap = (Long) value + 50;
    switch (state) {
    case ENERGY:
      energy = (Integer) value;
      if (hourlyAverage[Calendar.HOUR_OF_DAY] >= cautionCap
          && hourlyAverage[Calendar.HOUR_OF_DAY] < warningCap) {
        SolarDecathlonApplication.getStatusMap().put("Energy", SystemStatus.CAUTION);
      }
      else if (hourlyAverage[Calendar.HOUR_OF_DAY] >= warningCap) {
        SolarDecathlonApplication.getStatusMap().put("Energy", SystemStatus.WARNING);
      }
      else {
        SolarDecathlonApplication.getStatusMap().put("Energy", SystemStatus.OK);
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
}
