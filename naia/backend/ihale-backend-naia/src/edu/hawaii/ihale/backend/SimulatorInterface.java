package edu.hawaii.ihale.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Interface between the backend and simulator.
 * 
 * @author Team Nai'a
 * 
 */
public class SimulatorInterface {

  // Maps actual ip addresses to URIs
  static Map<String, String> host = new HashMap<String, String>();
  static SystemStateEntryDAO db = new SystemStateEntryDAO();

  static {
    // Load properties file into host hashmap
    parsePropertiesFile();
  }

  /**
   * Reads XML from all applicable devices.
   */
  public static void readFromDevices() {

    // Loop through entire host HashMap
    for (Map.Entry<String, String> location : host.entrySet()) {

      // Rest API URL
      String device = location.getKey();

      // URI. e.g., http://localhost:3000/
      String uri = location.getValue();

      // Parse the system name
      String[] temp = device.split("-");

      // System name
      String deviceType = "";

      // System name/device type
      if ("pv".equals(temp[0])) {
        deviceType = "photovoltaics";
      }
      else {
        deviceType = temp[0];
      }

      // Initialize url string
      String url = "";

      // If these are state devices, then request XML.
      // State URLs are denoted by the word "state" at the end of the
      // URL path name.
      if (device.contains("state")) {

        try {

          // Generate the url for all system state
          url = uri + deviceType + "/state";

          System.out.println("\n" + url);

          // Send the XML representation of the command to the appropriate device
          ClientResource client = new ClientResource(url);
          DomRepresentation representation = new DomRepresentation(client.get());

          // Insert Information into database
          loadSystemState(representation.getDocument(), deviceType);

        } // End Try

        catch (IOException e) {
          e.printStackTrace();
        }
        catch (ResourceException e) {
          System.out.println("Error getting State Data: " + e.getStatus());
          continue;
        }

      } // End If
    } // End For
  } // End read from devices

  /**
   * Loads the XML resource into the database.
   * 
   * @param doc The XML System state resource to be loaded
   * @param deviceType The system of the device being read
   */
  public static void loadSystemState(Document doc, String deviceType) {

    // Return System State Entry object
    SystemStateEntry entry;

    // Handles eGauge parsing
    if ("photovoltaics".equalsIgnoreCase(deviceType) || "electrical".equalsIgnoreCase(deviceType)) {

      entry = XmlMethods.parseEgaugeXML(deviceType, doc);
    }

    else {
      // Populate SystemStateEntry with XML information from the device
      entry = XmlMethods.parseXML(doc);
    }

    if (entry == null) {
      System.out.println("Entry is null!");
    }
    else {
      // Printing Debugging
      System.out.println (entry);

      // Set the Device Type
      db.setDevice(deviceType);

      // Insert information into database
      db.putEntry(entry);
    }

  } // End Load System State

  /**
   * Parses a property file and converts it to a HashMap.
   */
  public static void parsePropertiesFile() {

    // Properties file filename
    String filePath = "/.ihale/device-urls.properties";

    // Get Path to file
    String path = System.getProperty("user.home") + "/" + filePath;

    try {
      // Load properties file
      Properties props = new Properties();
      FileInputStream file = new FileInputStream(path);
      props.load(file);

      Enumeration<Object> enums = props.keys();

      // Loop through all keys in properties file
      while (enums.hasMoreElements()) {
        String key = (String) enums.nextElement();

        // Store in hashmap
        host.put(key, props.getProperty(key));
      }
      file.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  } // End parse properties file

  /**
   * Accessor method for hosts key-value pairs.
   * 
   * @return map of all hosts pairs.
   */
  public static Map<String, String> getHosts() {
    return host;
  }

  /**
   * Prints out the hosts HashMap.
   */
  public static void printHosts() {

    // Loop through the hash map and print key-value pairs
    for (Map.Entry<String, String> entry : host.entrySet()) {
      System.out.print(entry.getKey() + "  ||  ");
      System.out.println(entry.getValue());
    }
    System.out.println();

  } // End printHosts

} // End Simulator Interface
