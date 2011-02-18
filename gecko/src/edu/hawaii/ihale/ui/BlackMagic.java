package edu.hawaii.ihale.ui;

import java.util.Date;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * A class that shouldn't exist in the real iHale UI system.
 * Exists to simulate data coming in from sensors.  
 * @author Philip Johnson
 *
 */
public class BlackMagic {
  
  /**
   * Public constructor adds 10 state entries to the passed database at 1 second intervals. 
   * @param db The database. 
   * @throws Exception If problems occur.
   */
  public BlackMagic(SystemStateEntryDB db) throws Exception {
    for (int i = 1; i < 10; i++) {
      // Create a SystemStateEntry. 
      long timestamp = (new Date()).getTime();
      
      // Alternate between Aquaponics and PV.
      SystemStateEntry entry;
      if (i % 2 == 0) {
        entry = new SystemStateEntry("Aquaponics", "Arduino-23", timestamp);
        entry.putDoubleValue("pH", Math.random() * 10);
      } 
      else {
        entry = new SystemStateEntry("Photovoltaics", "Arduino-47", timestamp);
        entry.putDoubleValue("Watts", Math.random() * 1000);
      }
      
      // Now add the entry. This will trigger a listener.
      db.putEntry(entry);
      // Pause for a second. 
      Thread.sleep(1000);
    }
  }
}
