package edu.hawaii.ihale.backend.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
import org.junit.Test;

/**
 * Unit test of the IHale database functionality. 
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TestIHaleDB {

  /**
   * Test the IHaleStateSystemEntry objects.
   */
  @Test
  public void testEntries() {    
    
    String system = "Aquaponics";
    String device = "Arduino-23";
    long timestamp = (new Date()).getTime();
    IHaleSystemStateEntry entry = new IHaleSystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 4.5);
    entry.putStringValue("FishType", "Tilapia");
    entry.putLongValue("NumFish", 2);
    System.out.println(entry);
    assertEquals("Checking pH", 4.5, entry.getDoubleValue("pH"), 0.01);
    
    IHaleDB.putEntry(entry);
    assertNotNull("Checking for entry existence", IHaleDB.getEntry(system, device, timestamp));
    assertEquals("Checking timestamp", timestamp, 
        IHaleDB.getEntry(system, device, timestamp).getTimestamp());
    
    system = "HVAC";
    device = "Arduino-48";
    timestamp = (new Date()).getTime();
    entry = new IHaleSystemStateEntry(system, device, timestamp);
    entry.putLongValue("Temp", 76);
    System.out.println(entry);
    assertEquals("Checking temperature", 76, entry.getLongValue("Temp"), 0.01);
    
    IHaleDB.putEntry(entry);
    assertNotNull("Checking for entry existence", IHaleDB.getEntry(system, device, timestamp));
    assertEquals("Checking system name", system, 
        IHaleDB.getEntry(system, device, timestamp).getSystemName());
  }
}
