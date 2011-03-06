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
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
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
    String arduino1 = "arduino-1";
    String temp = "temp";
    // To ensure timestamp uniqueness.
    int counter = 0;

    IHaleDAO dao = new IHaleDAO();

    // Test getEntry and putEntry methods.
    String system = aquaponics;
    String device = arduino1;
    long timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 7.5);
    entry.putDoubleValue("oxygen", 6.2);
    entry.putLongValue(temp,  25);
    dao.putEntry(entry);
    IHaleSystemStateEntry iHaleEntry = IHaleDB.getEntry(system, device, timestamp);
    assertEquals("Testing storing via DAO and retrieving an entry straight from the repository: ",
        entry.toString(), iHaleEntry.toString());

    // Test getEntry and putEntry methods.
    system = aquaponics;
    device = arduino1;
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry2 = new SystemStateEntry(system, device, timestamp);
    entry2.putDoubleValue("pH", 11.5);
    entry2.putDoubleValue("oxygen", 100.2);
    entry2.putLongValue(temp,  98);
    dao.putEntry(entry2);
    SystemStateEntry entry3 = dao.getEntry(system, device, timestamp);
    assertEquals("Testing storing via DAO and retrieving an entry via DAO: ", entry2.toString(),
        entry3.toString());

    // Add hvac entry.
    system = hvac;
    device = "arduino-3";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry4 = new SystemStateEntry(system, device, timestamp);
    entry4.putLongValue(temp,  25);
    dao.putEntry(entry4);

    // Add lighting entry.
    system = lighting;
    device = "arduino-8";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry5 = new SystemStateEntry(system, device, timestamp);
    entry5.putLongValue("level", 10);
    dao.putEntry(entry5);

    // Add photovoltaics entry.
    system = "photovoltaics";
    device = "egauge-1";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry6 = new SystemStateEntry(system, device, timestamp);
    entry6.putLongValue("power", 10);
    entry6.putLongValue("energy", 10234);
    dao.putEntry(entry6);

    // Add electrical entry.
    system = "electrical";
    device = "egauge-2";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry7 = new SystemStateEntry(system, device, timestamp);
    entry7.putLongValue("power", 50);
    entry7.putLongValue("energy", 15000);
    dao.putEntry(entry7);

    // Assert that 5 systems have been added into the database.
    assertEquals("Checking for number of systems, should be 5: ", 5, dao.getSystemNames().size());

    // Add lighting entry.
    system = lighting;
    device = "arduino-7";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry8 = new SystemStateEntry(system, device, timestamp);
    entry8.putLongValue(level, 20);
    dao.putEntry(entry8);

    // Add lighting entry.
    system = lighting;
    device = "arduino-6";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry9 = new SystemStateEntry(system, device, timestamp);
    entry9.putLongValue(level, 30);
    dao.putEntry(entry9);

    // Add lighting entry.
    system = lighting;
    device = "arduino-5";
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry10 = new SystemStateEntry(system, device, timestamp);
    entry10.putLongValue(level, 30);
    dao.putEntry(entry10);

    // Assert that 4 lighting systems have been added into the database.
    try {
      assertEquals("Checking for number of devices for Lighting System, should be 4: ", 4, dao
          .getDeviceNames(lighting).size());
    }
    catch (SystemStateEntryDBException e) {
      e.printStackTrace();
    }
    String setLevel = "setLevel";

    // Test the doCommand which sends XML to devices.
    List<String> args = new ArrayList<String>();
    args.add("75");
    dao.doCommand(aquaponics, "arduino-2", "setTemp", args);
    args.clear();
    args.add("88");
    dao.doCommand(hvac, "arduino-4", "setTemp", args);
    args.clear();
    args.add("22");
    dao.doCommand(lighting, "arduino-6", setLevel, args);
    args.clear();
    args.add("6.3");
    dao.doCommand(aquaponics, "arduino-2", "setPH", args);
    args.clear();
    args.add("0.336");
    dao.doCommand(aquaponics, "arduino-2", "setOxygen", args);
    args.clear();
    args.add("98");
    dao.doCommand(lighting, "arduino-5", setLevel, args);
    args.clear();
    args.add("25");
    dao.doCommand(lighting, "arduino-8", setLevel, args);
    args.clear();
    args.add("12");
    dao.doCommand(lighting, "arduino-7", setLevel, args);

    // Test deleteEntry method.
    List<String> devices = new ArrayList<String>(0);
    dao.deleteEntry(system, device, timestamp);
    try {
      devices = dao.getDeviceNames(system);
    }
    catch (SystemStateEntryDBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("Checking for number of devices for Lighting System, should be 3: ", 3,
        devices.size());

    // Add aquaponics entry.
    system = aquaponics;
    device = arduino1;
    timestamp = (new Date()).getTime() + counter++;
    SystemStateEntry entry9000 = new SystemStateEntry(system, device, timestamp);
    entry9000.putDoubleValue("pH", 9000.0);
    entry9000.putDoubleValue("oxygen", 9000.0);
    entry9000.putLongValue(temp,  9000);
    entry9000.putLongValue(temp,  9000);
    dao.putEntry(entry9000);

    // Test getEntries method.
    List<SystemStateEntry> entries = new ArrayList<SystemStateEntry>(0);
    try {
      entries =
          dao.getEntries(aquaponics, arduino1, entry.getTimestamp(), entry9000.getTimestamp());
    }
    catch (SystemStateEntryDBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("Checking for number of devices for Aquaponics System, should be 3: ", 3,
        entries.size());
    assertEquals("Testing for correct return of 1st aquaponics entry on arduino-1: ",
        entry.toString(), entries.get(0).toString());
    assertEquals("Testing for correct return of 2nd aquaponics entry on arduino-1: ",
        entry2.toString(), entries.get(1).toString());
    assertEquals("Testing for correct return of 3rd aquaponics entry on arduino-1: ",
        entry9000.toString(), entries.get(2).toString());
  }
}
