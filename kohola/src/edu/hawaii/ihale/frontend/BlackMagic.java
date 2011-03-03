package edu.hawaii.ihale.frontend;

//import java.util.Date;
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
      cEntry = new SystemStateEntry("electrical", "eGauge-2", timestamp);
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
      pEntry = new SystemStateEntry("photovoltaics", "eGauge-1", timestamp);
      Thread.sleep(10);
      timestamp = (new Date()).getTime();
      entry = new SystemStateEntry("aquaponics", "arduino-23", timestamp);
      
      cEntry.putLongValue("Power", cValue);
      pEntry.putLongValue("Power", pValue);
      if (i == 1 || i == 7 || i == 6) {
        entry.putLongValue("Temp", longValue);
        entry.putDoubleValue("Oxygen", doubleValue);
        entry.putDoubleValue("pH", doubleValue);
      }
      else if (i == 2 || i == 3 || i == 5) {
        longValue = (long) (Math.random() * 200);
        entry.putLongValue("Temp", longValue);
        entry.putDoubleValue("Oxygen", doubleValue);
        entry.putDoubleValue("pH", doubleValue);
      }
      else {        
        entry = new SystemStateEntry("hvac", "arduino-3", timestamp);
        entry.putLongValue("Temp", longValue);        
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
