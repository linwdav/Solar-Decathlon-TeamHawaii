package edu.hawaii.ihale.backend.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.restlet.resource.ClientResource;

/**
 * A HTTP server that continously send GET requests to system devices for their current state and
 * store their information into the iHale's database repository.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class IHaleServer {
  
  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.dir");
  // Restlet server properties file name.
  private static String configurationFile = "configuration.properties";
  // Full path to the Restlet server properties file.
  private static String configFilePath = currentDirectory + "/" + configurationFile;

  // The interval at which to perform GET requests on house devices.
  private static final long interval = 5000;
  
  // Contains the mapping of device urls to port numbers as defined in the properties file.
  private static final Map<String, String> uris = new HashMap<String, String>();
  
  /**
   * This main method starts up a web application that will on timed intervals send GET HTTP
   * requests to each system device defined in the properties and on the port connection 
   * mapped to those device URLs.
   * 
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    readProperties();
    // Perform GETS on all devices at a specified interval.
    while (true) {
      for (Map.Entry<String, String> entry : uris.entrySet()) {
        String[] url = entry.getValue().split("/");
        if ("state".equals(url[url.length - 1])) {
          ClientResource client = new ClientResource("http://" + entry.getValue());
          System.out.format("GET %s: %s%n", entry.getValue(), client.get().getText());
        }
      }
//      ClientResource client = new ClientResource("http://localhost:8001/aquaponics/state");
//      System.out.format("GET %s: %s%n", "aquaponics/state", client.get().getText());
      Thread.sleep(interval);
    }
  }
  
  /**
   * Reads the config file properties.
   */
  public static void readProperties() {
    try {
      FileInputStream is = new FileInputStream(configFilePath);
      Properties prop = new Properties();
      prop.load(is);
      String key = "";
      String value = "";
      for (Map.Entry<Object, Object> propItem : prop.entrySet()) {
        key = (String) propItem.getKey();
        value = (String) propItem.getValue();
        uris.put(key, value);
      }

      is.close();
    }
    catch (IOException e) {
      System.out.println("Failed to read properties file.");
      System.out.println(configFilePath);
    }
  }
}
