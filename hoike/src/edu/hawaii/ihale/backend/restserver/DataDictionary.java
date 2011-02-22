package edu.hawaii.ihale.backend.restserver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A data dictionary that defines the various systems, devices, fields of the devices such as their
 * keys and data type (i.e., pH=Double, Oxygen=Double) in the solar decathlon home. Use for dynamic
 * validation when converting XML data to object fields.
 * 
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class DataDictionary {

  // Map of a Map containing all the system data defined in the data dictionary.
  private static Map<String, Map<String, ArrayList<String>>> systemData =
      new HashMap<String, Map<String, ArrayList<String>>>();

  // Path to where the data dictionary XML file is.
  private static String currentDirectory = System.getProperty("user.dir");
  // The data dictionary xml file name.
  private static String configurationFile = "dataDictionary.xml";
  // Full path to the file.
  private static String configFilePath = currentDirectory + "/" + configurationFile;

  /** Constructor. */
  public DataDictionary() {

    if (systemData.isEmpty()) {
      try {
        File file = new File(configFilePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        // A list of system elements and their associated child elements
        NodeList systemList = doc.getElementsByTagName("system");

        // For each system in the solar decathlon home. . .
        for (int i = 0; i < systemList.getLength(); i++) {

          Element element = (Element) systemList.item(i);
          String systemName = element.getAttribute("name");

          // Retrieve the devices of the system.
          NodeList deviceList = element.getElementsByTagName("device");

          // For each device of a system. . .
          for (int j = 0; j < deviceList.getLength(); j++) {

            String deviceName = ((Element) deviceList.item(j)).getAttribute("name");
            element = (Element) deviceList.item(j);

            // Retrieve the fields of the device.
            NodeList fieldList = element.getElementsByTagName("field");

            Map<String, ArrayList<String>> devices = new HashMap<String, ArrayList<String>>();
            ArrayList<String> keyTypePairList = new ArrayList<String>();

            String key = "";
            String type = "";
            // For each fields of a device, retrieve the key and data type and combine as a
            // single string to be parsed and divided in a method.
            for (int k = 0; k < fieldList.getLength(); k++) {
              key = ((Element) fieldList.item(k)).getAttribute("key");
              type = ((Element) fieldList.item(k)).getAttribute("type");
              keyTypePairList.add(key + "||" + type);
            }

            devices.put(deviceName, keyTypePairList);
            // j is added to systemName to have unique keys.
            systemData.put(systemName + j, devices);

            // System.out.println(systemData);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    // System.out.println(getTypeList("Aquaponics", "Arduino-1"));
  }

  /**
   * Returns a Map such that the key value is the device state key (i.e., Temp, Oxygen) and the
   * value mapped to the key is the value type (i.e., Double, Long).
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @return The key type pair map.
   */
  public Map<String, String> getTypeList(String systemName, String deviceName) {

    // Required to add a 0 character to systemName to avoid unique map key conflicts
    // from above code implementation. Refer to line 69.
    Map<String, ArrayList<String>> devices = systemData.get(systemName + "0");
    ArrayList<String> keyTypePairList = devices.get(deviceName);

    Map<String, String> keyTypePairMap = new HashMap<String, String>();
    String[] temp = new String[2];
    for (int i = 0; i < keyTypePairList.size(); i++) {
      // Character | are special, need to be escaped with \\
      temp = keyTypePairList.get(i).split("\\|\\|");
      keyTypePairMap.put(temp[0], temp[1]);
    }
    return keyTypePairMap;
  }

  /**
   * Returns a complicated Map of a Map containing all the system data defined in the data
   * dictionary.
   * 
   * @return The system data.
   */
  public Map<String, Map<String, ArrayList<String>>> getSystemData() {
    return systemData;
  }
}
