package edu.hawaii.ihale.db;

import java.util.Date;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * Very simple test suite for the iHale DB illustrating the use of the listener.
 * @author Philip Johnson  
 */
public class TestIHaleDB {
  
  /**
   * Test a wee bit of the iHaleDB implementation. In other words, the part that works.  
   */
  @Test
  public void testIHaleDB() {
    // Create an instance of the iHaleDB for testing. 
    SystemStateEntryDB db = new IHaleDB();
    // Add a listener for Aquaponics to this database. 
    db.addSystemStateListener(new ExampleAquaponicsListener());
    // Create a SystemStateEntry for Aquaponics. 
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = new SystemStateEntry("Aquaponics", "Arduino-23", timestamp);
    entry.putDoubleValue("pH", 6.2);
    // Now add the entry. This will trigger the Aquaponics listener.
    db.putEntry(entry);
  }
}
