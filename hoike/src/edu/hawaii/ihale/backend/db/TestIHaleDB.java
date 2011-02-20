package edu.hawaii.ihale.backend.db;

import static org.junit.Assert.assertEquals;
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
    
    IHaleDB db = new IHaleDB();
    //Currently failing because of SystemStateEntry not supporting persistency attribute.
    //db.putEntry(entry);
    
    system = "Aquaponics";
    device = "Arduino-48";
    timestamp = (new Date()).getTime();
    entry = new IHaleSystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 7.0);
    entry.putStringValue("FishType", "Shark");
    entry.putLongValue("NumFish", 4);
    System.out.println(entry);
    assertEquals("Checking pH", 7.0, entry.getDoubleValue("pH"), 0.01);
    
    //Currently failing because of SystemStateEntry not supporting persistency attribute.
    //db.putEntry(entry);
  }
}
