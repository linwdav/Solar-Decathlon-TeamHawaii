package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDBException;

/**
 * Tests the DAO class.
 * 
 * @author Team Nai'a
 * 
 */
public class TestSystemStateEntryDAO {
  SystemStateEntryDAO db = new SystemStateEntryDAO();
  String aqua = "aquaponics";

  /**
   * Tests the Do Command.
   */
  @Test
  public void testDoCommand() {
    List<String> list = Arrays.asList("75");
    List<String> list2 = Arrays.asList("88");
    List<String> list3 = Arrays.asList("22");
    List<String> list4 = Arrays.asList("6.3");
    List<String> list5 = Arrays.asList("0.336");

    db.doCommand(aqua, "arduino-2", "setTemp", list);
    db.doCommand("hvac", "arduino-4", "setTemp", list2);
    db.doCommand("lighting", "arduino-6", "setLevel", list3);
    db.doCommand(aqua, "arduino-2", "setPh", list4);
    db.doCommand(aqua, "arduino-2", "setOxygen", list5);

  }

  /**
   * Test getEntries function.
   * 
   * @throws SystemStateEntryDBException Exception for the db
   */
  @Test
  public void testGetEntries() throws SystemStateEntryDBException {

    // Initialize
    String aqua = "aquaponics";
    String ard1 = "arduino-1";
    String o2 = "Oxygen";
    String temp = "Temp";

    // Create SystemStateEntry objects
    SystemStateEntry entry1 = new SystemStateEntry(aqua, ard1, 111111111);
    SystemStateEntry entry2 = new SystemStateEntry(aqua, ard1, 222222222);
    SystemStateEntry entry3 = new SystemStateEntry(aqua, ard1, 333333333);
    SystemStateEntry entry4 = new SystemStateEntry(aqua, ard1, 444444444);

    // Insert information for each entry
    entry1.putDoubleValue("pH", 4.5);
    entry1.putDoubleValue( o2, 4.5);
    entry1.putLongValue(temp, 75);
    entry2.putDoubleValue("pH", 5.5);
    entry2.putDoubleValue(o2, 5.5);
    entry2.putLongValue(temp, 76);
    entry3.putDoubleValue("pH", 6.5);
    entry3.putDoubleValue(o2, 6.5);
    entry3.putLongValue(temp, 77);
    entry4.putDoubleValue("pH", 7.5);
    entry4.putDoubleValue(o2, 7.5);
    entry4.putLongValue(temp, 78);

    // Add all entries into the Database
    SystemStateEntryDAO db = new SystemStateEntryDAO();

    db.setDevice(aqua);
    db.putEntry(entry1);
    db.setDevice(aqua);
    db.putEntry(entry2);
    db.setDevice(aqua);
    db.putEntry(entry3);
    db.setDevice(aqua);
    db.putEntry(entry4);

    // Put all entries in a list
    List<SystemStateEntry> listOriginals = new ArrayList<SystemStateEntry>();

    listOriginals.add(entry1);
    listOriginals.add(entry2);
    listOriginals.add(entry3);
    listOriginals.add(entry4);

    List<SystemStateEntry> listReturned = db.getEntries(aqua, ard1, 111111111, 555555555);

    for (SystemStateEntry enOrig : listOriginals) {
      for (SystemStateEntry enRet : listReturned) {
        long original = enOrig.getTimestamp();
        long returned = enRet.getTimestamp();

        if (original == returned) {
          System.out.println(original + " found in the list!");
        }
      }
    }

    // Clean DB
    long numDeleted = SystemStateEntryBerkeleyDB.deleteDB();
    System.out.println(numDeleted + " entries deleted.");

  }

  /**
   * Test the getEntry function.
   */
  @Test
  public void testGetEntry() {

    // Initialize
    String aqua = "aquaponics";
    String hvac = "hvac";
    String light = "lighting";
    String photo = "photovoltaics";
    String elec = "electrical";
    String ard5 = "arduino-5";
    String o2 = "Oxygen";
    String temp = "Temp";
    
    // Create SystemStateEntry objects
    SystemStateEntry entry1 = new SystemStateEntry(aqua, "arduino-1", 111111111);
    SystemStateEntry entry2 = new SystemStateEntry(hvac, "arduino-3", 333333333);
    SystemStateEntry entry3 = new SystemStateEntry(light, ard5, 555555555);
    SystemStateEntry entry4 = new SystemStateEntry(photo, "eguage-1", 666666666);
    SystemStateEntry entry5 = new SystemStateEntry(elec, "eguage-2", 77777777);

    // Insert information for each entry
    entry1.putDoubleValue("pH", 4.5);
    entry1.putDoubleValue(o2, 4.5);
    entry1.putLongValue(temp, 75);
    entry2.putLongValue(temp, 72);
    entry3.putLongValue("Level", 10);
    entry4.putLongValue("Energy", 1234);
    entry4.putLongValue("Power", 111);
    entry5.putLongValue("Energy", 4321);
    entry5.putLongValue("Power", 222);

    // Add all entries into the Database
    SystemStateEntryDAO db = new SystemStateEntryDAO();

    db.setDevice(aqua);
    db.putEntry(entry1);
    db.setDevice(hvac);
    db.putEntry(entry2);
    db.setDevice(light);
    db.putEntry(entry3);
    db.setDevice(photo);
    db.putEntry(entry4);
    db.setDevice(elec);
    db.putEntry(entry5);

    // String representation for each entry
    String en1 = entry1.toString();
    String en2 = entry2.toString();
    String en3 = entry3.toString();
    String en4 = entry4.toString();
    String en5 = entry5.toString();

    // Test getEntry
    String return1 = db.getEntry(aqua, "arduino-1", 111111111).toString();
    String return2 = db.getEntry(hvac, "arduino-3", 333333333).toString();
    String return3 = db.getEntry(light, ard5, 555555555).toString();
    String return4 = db.getEntry(photo, "eguage-1", 666666666).toString();
    String return5 = db.getEntry(elec, "eguage-2", 77777777).toString();

    // Check if both entries are equal
    assertEquals(return1, en1, en1);
    assertEquals(return2, en2, en2);
    assertEquals(return3, en3, en3);
    assertEquals(return4, en4, en4);
    assertEquals(return5, en5, en5);

    // Clean DB
    long numDeleted = SystemStateEntryBerkeleyDB.deleteDB();
    System.out.println(numDeleted + " entries deleted.");

  }

  /**
   * Tests the names (Device and System) retrieval methods.
   */
  @Test
  public void testGetNames() {
    String temp = "Temp";
    String level = "Level";
    String light = "lighting";
    String o2 = "Oxygen";

    // Create list of keys based on Java API Data Dictionary
    List<String> aquaponicsDouble = Arrays.asList("pH", o2);
    List<String> aquaponicsLong = Arrays.asList(temp);
    List<String> HVACLong = Arrays.asList(temp);
    List<String> lightingLong = Arrays.asList(level);

    // Create API Objects
    SystemStateEntry state1 = new SystemStateEntry(aqua, "Arduino-2", 111222333);
    SystemStateEntry state2 = new SystemStateEntry("hvac", "arduino-3", 333222111);
    SystemStateEntry state3 = new SystemStateEntry(light, "arduino-5", 444555666);
    SystemStateEntry state4 = new SystemStateEntry(light, "arduino-5", 444555333);
    SystemStateEntry state5 = new SystemStateEntry(light, "arduino-6", 555666777);

    // Populate state information on API objects
    state1.putDoubleValue("pH", 7.0);
    state1.putDoubleValue(o2, 55.0);
    state1.putLongValue(temp, 65);
    state2.putLongValue(temp, 72);
    state3.putLongValue(level, 89);
    state4.putLongValue(level, 32);
    state5.putLongValue(level, 29);

    // Create DAO-Entity Objects from API objects
    SystemStateEntryRecord stateRecord1 =
        new SystemStateEntryRecord(state1, aquaponicsLong, aquaponicsDouble, null);
    SystemStateEntryRecord stateRecord3 =
        new SystemStateEntryRecord(state3, lightingLong, null, null);
    SystemStateEntryRecord stateRecord4 =
        new SystemStateEntryRecord(state4, lightingLong, null, null);
    SystemStateEntryRecord stateRecord5 =
        new SystemStateEntryRecord(state5, lightingLong, null, null);
    SystemStateEntryRecord stateRecord2 = new SystemStateEntryRecord(state2, HVACLong, null, null);

    // Insert into database
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(stateRecord1);
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(stateRecord2);
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(stateRecord3);
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(stateRecord4);
    SystemStateEntryBerkeleyDB.putSystemStateEntryRecord(stateRecord5);

    // Compose List of all system names
    List<String> systemNamesList = db.getSystemNames();

    // Combine all system names into a string
    StringBuffer result = new StringBuffer();
    for (String s : systemNamesList) {
      String tempString = s + " ";
      result.append(tempString);
    }
    System.out.println("System Names: " + result);

    // Compose List of all device names
    String systemName = light;
    try {
      List<String> deviceNamesList = db.getDeviceNames(systemName);

      // Combine all system names into a string
      StringBuffer resultTwo = new StringBuffer();
      for (String s : deviceNamesList) {
        String tempString = s + " ";
        resultTwo.append(tempString);
      }

      System.out.println("Device Names for " + systemName + ": " + resultTwo);

    }
    catch (SystemStateEntryDBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Clean DB
    long numDeleted = SystemStateEntryBerkeleyDB.deleteDB();
    System.out.println(numDeleted + " entries deleted.");

  }

}
