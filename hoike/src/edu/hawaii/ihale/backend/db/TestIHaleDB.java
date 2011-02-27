package edu.hawaii.ihale.backend.db;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.backend.rest.IHaleDAO;

/**
 * Unit test of the IHale database functionality. 
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TestIHaleDB {

  /**
   * Test the transformation of entries from IHaleDAO to IHaleDB and in reverse.
   */
  @Test
  public void testIHaleDAO() {
    
    String aquaponics = "aquaponics";
    String hvac = "hvac";
    String lighting = "lighting";
    String level = "level";
    // To ensure timestamp uniqueness.
    int counter = 0;
    
    IHaleDAO dao = new IHaleDAO();
    
    String system = aquaponics;
    String device = "arduino-1";
    long timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 7.5);
    entry.putDoubleValue("oxygen", 6.2);
    entry.putLongValue("temp", 25);
    dao.putEntry(entry);
    IHaleSystemStateEntry iHaleEntry = IHaleDB.getEntry(system, device, timestamp);
    assertEquals("Testing storing via DAO and retrieving an entry straight from the repository: ", 
        entry.toString(), iHaleEntry.toString());
    
    system = aquaponics;
    device = "arduino-1";
    timestamp = (new Date()).getTime() + counter++;    
    SystemStateEntry entry2 = new SystemStateEntry(system, device, timestamp);
    entry2.putDoubleValue("pH", 11.5);
    entry2.putDoubleValue("oxygen", 100.2);
    entry2.putLongValue("temp", 98);
    dao.putEntry(entry2);
    SystemStateEntry entry3 = dao.getEntry(system, device, timestamp);
    assertEquals("Testing storing via DAO and retrieving an entry via DAO: ", 
        entry2.toString(), entry3.toString());
    
    system = hvac;
    device = "arduino-3";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry4 = new SystemStateEntry(system, device, timestamp);
    entry4.putLongValue("temp", 25);
    dao.putEntry(entry4);
    
    system = lighting;
    device = "arduino-8";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry5 = new SystemStateEntry(system, device, timestamp);
    entry5.putLongValue("level", 10);
    dao.putEntry(entry5);
    
    system = "photovoltaics";
    device = "egauge-1";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry6 = new SystemStateEntry(system, device, timestamp);
    entry6.putLongValue("power", 10);
    entry6.putLongValue("energy", 10234);
    dao.putEntry(entry6);
    
    system = "electrical";
    device = "egauge-2";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry7 = new SystemStateEntry(system, device, timestamp);
    entry7.putLongValue("power", 50);
    entry7.putLongValue("energy", 15000);
    dao.putEntry(entry7);
        
    // An assertion.
    assertEquals("Checking for number of systems, should be 5: ", 5, dao.getSystemNames().size());
    
    system = lighting;
    device = "arduino-7";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry8 = new SystemStateEntry(system, device, timestamp);
    entry8.putLongValue(level, 20);
    dao.putEntry(entry8);
    
    system = lighting;
    device = "arduino-6";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry9 = new SystemStateEntry(system, device, timestamp);
    entry9.putLongValue(level, 30);
    dao.putEntry(entry9);
    
    system = lighting;
    device = "arduino-5";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry10 = new SystemStateEntry(system, device, timestamp);
    entry10.putLongValue(level, 30);
    dao.putEntry(entry10);
    
    // An assertion.
    try {
      assertEquals("Checking for number of devices for Lighting System, should be 4: ",
          4, dao.getDeviceNames(lighting).size());
    }
    catch (SystemStateEntryDBException e) {
      e.printStackTrace();
    }
    
    List<String> args = new ArrayList<String>();
    args.add("27");
    //dao.doCommand("aquaponics", "arduino-1", "setTemp", args);
    
  }
}
