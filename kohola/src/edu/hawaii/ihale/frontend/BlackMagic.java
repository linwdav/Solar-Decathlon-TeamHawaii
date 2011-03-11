package edu.hawaii.ihale.frontend;

import java.util.Date;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * A class that shouldn't exist in the real iHale UI system. Exists to simulate data coming in from
 * sensors.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 * 
 */
public class BlackMagic {

  private static final String ELECTRICAL_SYSTEM_NAME = "electrical";
  private static final String PHOTOVOLTAICS_SYSTEM_NAME = "photovoltaics";
  private static final String AQUAPONICS_SYSTEM_NAME = "aquaponics";
  private static final String HVAC_SYSTEM_NAME = "hvac";
  private static final String ARDUINO_3 = "arduino-3";
  private static final String ARDUINO_23 = "arduino-23";
  private static final String EGAUGE_1 = "egauge-1";
  private static final String EGAUGE_2 = "egauge-2";
  private static final String POWER_KEY = "power";
  private static final String ENERGY_KEY = "energy";
  private static final String TEMP_KEY = "temp";
  private static final String OXYGEN_KEY = "oxygen";
  private static final String PH_KEY = "ph";

  /**
   * Public constructor adds 10 state entries to the passed database at 1 second intervals.
   * 
   * @param db The database.
   * @throws Exception If problems occur.
   */
  public BlackMagic(SystemStateEntryDB db) throws Exception {
    for (int i = 1; i < 12; i++) {
      Long longValue = (long) (Math.random() * 100 + 1);
      Long pValue = (long) (Math.random() * 50 + 1);
      Long cValue = (long) (Math.random() * 50 + 1);
      Double doubleValue = (Math.random() * 10 + 1);
      // Create a SystemStateEntry.
      long timestamp = (new Date()).getTime();
      long mHour = 2 * 60 * 60 * 1000L;
      long mDay = 24 * 60 * 60 * 1000L;
      long mFive = 5 * 24 * 60 * 60 * 1000L;

      // Alternate between Aquaponics and PV.
      SystemStateEntry entry, cEntry, pEntry;
      if (i == 1) {
        timestamp = (new Date()).getTime() - (mHour * 3);
      }
      else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
        timestamp = (new Date()).getTime() - (mHour * 2);
      }
      else if (i == 7 || i == 8 || i == 9) {
        timestamp = (new Date()).getTime() - mHour;
      }
      else {
        timestamp = (new Date()).getTime() - (mHour * 4);
      }
      cEntry = new SystemStateEntry(ELECTRICAL_SYSTEM_NAME, EGAUGE_2, timestamp);
      Thread.sleep(10);
      timestamp = (new Date()).getTime();
      if (i == 1) {
        timestamp = (new Date()).getTime() - mHour;
      }
      else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
        timestamp = (new Date()).getTime() - mDay;
      }
      else if (i == 7 || i == 8 || i == 9) {
        timestamp = (new Date()).getTime() - mFive;
      }
      pEntry = new SystemStateEntry(PHOTOVOLTAICS_SYSTEM_NAME, EGAUGE_1, timestamp);
      Thread.sleep(10);
      timestamp = (new Date()).getTime();
      entry = new SystemStateEntry(AQUAPONICS_SYSTEM_NAME, ARDUINO_23, timestamp);

      cEntry.putLongValue(POWER_KEY, cValue);
      cEntry.putLongValue(ENERGY_KEY, cValue);
      pEntry.putLongValue(POWER_KEY, pValue);
      pEntry.putLongValue(ENERGY_KEY, pValue);
      if (i == 1 || i == 7 || i == 6) {
        entry.putLongValue(TEMP_KEY, longValue);
        entry.putDoubleValue(OXYGEN_KEY, doubleValue);
        entry.putDoubleValue(PH_KEY, doubleValue);
      }
      else if (i == 2 || i == 3 || i == 5) {
        longValue = (long) (Math.random() * 200);
        entry.putLongValue(TEMP_KEY, longValue);
        entry.putDoubleValue(OXYGEN_KEY, doubleValue);
        entry.putDoubleValue(PH_KEY, doubleValue);
      }
      else {
        entry = new SystemStateEntry(HVAC_SYSTEM_NAME, ARDUINO_3, timestamp);
        entry.putLongValue(TEMP_KEY, longValue);
      }

      // Now add the entry. This will trigger a listener.
      db.putEntry(entry);
      db.putEntry(pEntry);
      db.putEntry(cEntry);
      // Pause for a while.
      Thread.sleep(10);
    }
  }
}
