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
    entry = new SystemStateEntry("Aquaponics", "Arduino-1", timestamp);
    entry.putDoubleValue("pH", Math.random() * 10);
    entry.putDoubleValue("Oxygen", Math.random() * 10);
    entry.putLongValue("Temp", (long) (Math.random() * 10));
    db.putEntry(entry);
    
    //HVAC
    entry = new SystemStateEntry("HVAC", "Arduino-3", timestamp);
    entry.putLongValue("Temp", (long) (Math.random() * 10));
    db.putEntry(entry);

    //Lighting
    entry = new SystemStateEntry("Lighting", "Arduino-5", timestamp);
    entry.putLongValue("Level", (long) (Math.random() * 10));
    db.putEntry(entry);
    
    entry = new SystemStateEntry("Lighting", "Arduino-6", timestamp);
    entry.putLongValue("Level", (long) (Math.random() * 10));
    db.putEntry(entry);

    entry = new SystemStateEntry("Lighting", "Arduino-7", timestamp);
    entry.putLongValue("Level", (long) (Math.random() * 10));
    db.putEntry(entry);

    entry = new SystemStateEntry("Lighting", "Arduino-8", timestamp);
    entry.putLongValue("Level", (long) (Math.random() * 10));
    db.putEntry(entry);
    
    //Photovoltaics
    entry = new SystemStateEntry("Photovoltaics", "egauge-1", timestamp);
    entry.putLongValue("power", (long) (Math.random() * 10));
    entry.putLongValue("energy", (long) (Math.random() * 10));
    db.putEntry(entry);
    
    //Electricity consumption
    entry = new SystemStateEntry("Electrical", "egauge-2", timestamp);
    entry.putLongValue("power", (long) (Math.random() * 10));
    entry.putLongValue("energy", (long) (Math.random() * 10));
    db.putEntry(entry); 
  }
}
