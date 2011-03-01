package edu.hawaii.ihale.backend;

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
  @Test public void testDoCommand() {
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
  
  /* 
   * @Test public void testGetDeviceNames() { fail("Not yet implemented"); }
   * 
   * @Test public void testGetEntries() { fail("Not yet implemented"); }
   * 
   * @Test public void testGetEntry() { fail("Not yet implemented"); }
   */

  /**
   * Tests the names (Device and System) retrieval methods.
   */
  @Test
  public void testGetNames() {
    String temp = "Temp";
    String level = "Level";
    String light = "lighting";

    // Create list of keys based on Java API Data Dictionary
    List<String> aquaponicsDouble = Arrays.asList("pH", "Oxygen");
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
    state1.putDoubleValue("Oxygen", 55.0);
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

  }
  /*
   * @Test public void testConvertEntry() { fail("Not yet implemented"); }
   */
    
}
