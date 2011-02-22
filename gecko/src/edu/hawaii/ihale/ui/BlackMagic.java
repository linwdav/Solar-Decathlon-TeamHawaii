package edu.hawaii.ihale.ui;

import java.util.Date;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * A class that shouldn't exist in the real iHale UI system. Exists to simulate data coming in from
 * sensors.
 * 
 * @author Philip Johnson
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
    // Create a SystemStateEntry.
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = null;
    
    //Aquaponics
    timestamp++;
    entry = new SystemStateEntry("Aquaponics", "Arduino-23", timestamp);
    entry.putDoubleValue("pH", Math.random() * 10);
    entry.putDoubleValue("Oxygen", Math.random() * 10);
    entry.putLongValue("Temp", (long) (Math.random() * 10));
    db.putEntry(entry);
  }
}
