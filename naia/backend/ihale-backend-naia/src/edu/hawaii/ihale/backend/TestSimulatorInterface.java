package edu.hawaii.ihale.backend;

import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Tests the Simulator Interface class.
 * 
 * @author Team Nai'a
 * 
 */
public class TestSimulatorInterface {

  /**
   * Test parsing of Properties File. This prints statements only.
   */
  @Test
  public void testParsePropertiesFile() {
    // Parse and store the system configuration file in the
    // host hashmap
    SimulatorInterface.parsePropertiesFile();

    // Grab the hash map for the hosts
    Map<String, String> hosts = SimulatorInterface.getHosts();

    // Loop through the hash map
    for (Map.Entry<String, String> entry : hosts.entrySet()) {
      System.out.print(entry.getKey() + "  ||  ");
      System.out.println(entry.getValue());
    }
    System.out.println();

  } // End testParsePropertiesFile

 
  /**
   * Tests the reading of items from the devices. Prints out results only.
   */
  @Test
  public void testReadFromDevices() {
    // Parse and store the system configuration file in the
    // host hashmap
    SimulatorInterface.parsePropertiesFile();
    try {
      // Reads from devices
      SimulatorInterface.readFromDevices();

      // Tests which systems are in the database
      printList(SystemStateEntryBerkeleyDB.getSystemNames());
      printList(SystemStateEntryBerkeleyDB.getDeviceNames("hvac"));
      printList(SystemStateEntryBerkeleyDB.getDeviceNames("aquaponics"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  } // End testReadFromDevices

  /**
   * Prints the contents of a list.
   * 
   * @param list The list to print.
   */
  public void printList(List<String> list) {
    for (String s : list) {
      System.out.println(s);
    }
  } // End PrintList

} // End TestSimulatorInterface Class
