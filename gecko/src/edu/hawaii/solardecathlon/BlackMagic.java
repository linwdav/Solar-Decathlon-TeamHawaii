package edu.hawaii.solardecathlon;

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
   */
  public BlackMagic(SystemStateEntryDB db) {
    // Create a SystemStateEntry.
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = null;
    
    //Aquaponics
    entry = new SystemStateEntry("aquaponics", "arduino-1", timestamp);
    entry.putDoubleValue("pH", 7.5);
    entry.putDoubleValue("Oxygen", 6.2);    
    db.putEntry(entry);
  }
  
  
}
