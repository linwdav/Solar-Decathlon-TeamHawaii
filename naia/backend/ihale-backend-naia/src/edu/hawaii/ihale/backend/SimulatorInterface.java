package edu.hawaii.ihale.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
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
  static SystemStateEntryBerkeleyDB db = new SystemStateEntryBerkeleyDB();

  static {
    // Load properties file into host hashmap
    parsePropertiesFile(); 
  }
  
  /**
   * Reads XML from all applicable devices.
   */
  public static void readFromDevices() {
    
    for (Map.Entry<String, String> location : host.entrySet()) {
      String url = location.getKey();
      String actualURL = location.getValue();

      // If these are state devices, then request XML
      if (! (url.contains("temp") || url.contains("level")) ) {
        
        try {
          // Need to parse to determine what type of device this is.  E.g., 
          // aquaponics, etc.
          int deviceNumber;
          
          if (url.charAt(16) == '.') {
            if (url.charAt(17) == 2) {
              deviceNumber = 10;
            }
            else {
              deviceNumber = 9;
            }
          }
          else {
            deviceNumber = url.charAt(16);
          }
          
          // Send the XML representation of the command to the appropriate device 
          ClientResource  client = new ClientResource(actualURL);
          DomRepresentation representation = new DomRepresentation(client.get());
          
          // Insert Information into database
          loadSystemState(representation.getDocument(), deviceNumber);

        } // End Try
        catch (IOException e) {
          e.printStackTrace();
        }
        
      } // End If
    } // End For
  } // End read from devices
  
  /**
   * Loads the XML resource into the database.
   * 
   * @param doc The XML resource to be loaded
   * @param deviceNumber The device being read
   */
  public static void loadSystemState(Document doc, int deviceNumber) {
    
    // Populate SystemStateEntry with XML information from the device
    SystemStateEntry entry = XmlMethods.parseXML(doc);
    
    // Set the Device Type
    db.setDevice(deviceNumber);
    
    // Insert information into database
    db.putEntry(entry);
    
  } // End Load System State
  
  /**
   * Parses a property file and converts it to a HashMap.
   */
  public static void parsePropertiesFile() {
    // Get Path to file
    String path = System.getProperty("user.dir") + "/sims.properties";
    
    try {
      // Load properties file
      Properties props = new Properties();
      FileInputStream file = new FileInputStream(path);
      props.load(file);
      
      Enumeration<Object> enums = props.keys();
      
      // Loop through all keys in properties file
      while (enums.hasMoreElements()) {
        String key = (String)enums.nextElement();
        
        // Store in hashmap
        host.put(key, props.getProperty(key));
      }
      file.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Accessor method for hosts key-value pairs.
   * 
   * @return map of all hosts pairs.
   */
  public static Map<String, String> getHosts () {
    return host;
  }
}
