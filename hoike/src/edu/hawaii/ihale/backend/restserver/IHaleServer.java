package edu.hawaii.ihale.backend.restserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * A HTTP server that continously send GET requests to system devices for their current state and
 * store their information into the iHale's database repository.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class IHaleServer extends Application {
  
  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.dir");
  // Restlet server properties file name.
  private static String configurationFile = "configuration.properties";
  // Full path to the Restlet server properties file.
  private static String configFilePath = currentDirectory + "/" + configurationFile;

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
      System.out.println("failed to read properties file");
      System.out.println(configFilePath);
    }
    
    System.out.println(uris);

    /** TO-DO: Now that we have the mappings we must create a timer method that will at
     *         intervals send GET requests to each system device on that port connection.
     *         (i.e., arduino-1.halepilihonua.hawaii.edu/aquaponics/state=7000 was mapped
     *          as so in the properties file, http://localhost:7000 is representative of
     *          the URL to Aquaponics system device, Arduino-1.)
     */
  }
  
  // NOTE: Since we aren't running a server the overridden method createInboundRoot below
  //       is no longer necessary.
  
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * 
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {

    // Define the systems that support resource handling by the Restlet HTTP server.
    String aquaponicsSystem = "aquaponics";

    // Create a router restlet.
    Router router = new Router(getContext());
    // Attach the resources to the router.
    router.attach("/" + aquaponicsSystem + "/{request}", AquaponicsResource.class);
    /**
     * TO-DO: Need to figure out a solution for PV and Electrical since they share the same ending
     * URI patterns. i.e., PV: http://egauge-1.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
     * Electrical: http://egauge-2.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
     */

    // Return the root router
    return router;
  }
}
