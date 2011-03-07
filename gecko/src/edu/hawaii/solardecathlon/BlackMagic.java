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
    entry = new SystemStateEntry("aquaponics", "arduino-23", timestamp);
    entry.putDoubleValue("ph", Math.random() * 10);
    entry.putDoubleValue("oxygen", 6.2);    
    entry.putLongValue("temp", 6L);    
    db.putEntry(entry);
    
    entry = new SystemStateEntry("hvac", "arduino-2", timestamp);
    entry.putLongValue("temp", 6L);    
    db.putEntry(entry);
  }
  
  
}
