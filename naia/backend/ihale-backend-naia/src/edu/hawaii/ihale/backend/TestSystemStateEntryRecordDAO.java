package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Simple unit test of the Contact data access object.
 * 
 * @author Team Nai'a
 */
public class TestSystemStateEntryRecordDAO {

  /**
   * Test the DAO interface.
   */
  @Test
  public void testSystemStateEntryRecordDatabase() {

    String temp = "Temp";
    String check = "Check Conversion";

    // Create list of keys based on Java API Data Dictionary
    List<String> aquaponicsDouble = Arrays.asList("pH", "Oxygen");
    List<String> aquaponicsLong = Arrays.asList(temp);
    List<String> HVACLong = Arrays.asList(temp);

    // Create API Objects
    SystemStateEntry state1 = new SystemStateEntry("Aquaponics", "Arduino-2", 111222333);
    SystemStateEntry state2 = new SystemStateEntry("HVAC", "Arduino-3", 333222111);

    // Populate state information on API objects
    state1.putDoubleValue("pH", 7.0);
    state1.putDoubleValue("Oxygen", 55.0);
    state1.putLongValue(temp, 65);
    state2.putLongValue(temp, 72);

    // Create DAO-Entity Objects from API objects
    SystemStateEntryRecord stateRecord1 =
        new SystemStateEntryRecord(state1, aquaponicsLong, aquaponicsDouble, null);

    SystemStateEntryRecord stateRecord2 = new SystemStateEntryRecord(state2, HVACLong, null, null);

    // Check to ensure all key-value pairs were transferred over
    assertEquals(check, stateRecord1.getDoubleData().get("pH") == 7.0, true);
    assertEquals(check, stateRecord1.getDoubleData().get("Oxygen") == 55.0, true);
    assertEquals(check, stateRecord1.getLongData().get(temp) == 65, true);
    assertEquals(check, stateRecord2.getLongData().get(temp) == 72, true);

    // Insert into database
    SystemStateEntryRecordDAO.putSystemStateEntryRecord(stateRecord1);
    SystemStateEntryRecordDAO.putSystemStateEntryRecord(stateRecord2);

    // Create Composite Keys
    SystemStateAttributes attributes1 =
        new SystemStateAttributes("Aquaponics", "Arduino-2", 111222333);
    SystemStateAttributes attributes2 = new SystemStateAttributes("HVAC", "Arduino-3", 333222111);

    // Grab records from database
    SystemStateEntryRecord stateRecord1a =
        SystemStateEntryRecordDAO.getSystemStateEntryRecord(attributes1);
    SystemStateEntryRecord stateRecord2a =
        SystemStateEntryRecordDAO.getSystemStateEntryRecord(attributes2);

    // Check to ensure records inserted correctly
    assertEquals("Check state1", stateRecord1.toString(), stateRecord1a.toString());
    assertEquals("Check state2", stateRecord2.toString(), stateRecord2a.toString());

    // Check to ensure deletion works properly
    SystemStateEntryRecordDAO.deleteSystemStateEntryRecord(attributes1);
    assertNull("Check deletion", SystemStateEntryRecordDAO.getSystemStateEntryRecord(attributes1));

  } // Test method

} // End Test Class
