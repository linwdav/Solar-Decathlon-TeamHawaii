package edu.hawaii.ihale.backend.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Router;
import edu.hawaii.ihale.backend.resources.AquaponicsResource;
import edu.hawaii.ihale.backend.resources.ElectricalResource;
import edu.hawaii.ihale.backend.resources.HvacResource;
import edu.hawaii.ihale.backend.resources.LightingResource;
import edu.hawaii.ihale.backend.resources.PhotovoltaicsResource;

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

  // The interval at which to perform GET requests on house devices.
  private static final long interval = 10000;
  
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
    // Create a component.
    Component component = new Component();
    // Create an application (this class).
    Application application = null;
    List<String> urls = new ArrayList<String>();
    
    readProperties();
    for (Map.Entry<String, String> entry : uris.entrySet()) {
      final String key = entry.getKey();
      final String contextRoot = "/" + key.split("/")[1] + "/" + key.split("/")[2];
      final int port = Integer.valueOf(entry.getValue());
      //System.out.println(contextRoot);
      //System.out.println(port);
      urls.add("http://localhost:" + entry.getValue() + contextRoot);
      application = new Application() {
        @Override
        public Restlet createInboundRoot() {
          // Create a router restlet.
          Router router = new Router(getContext());
          // Attach the resources to the router.
          if ("aquaponics".equals(key.split("/")[1])) {
            router.attach(contextRoot, AquaponicsResource.class);
            router.attach("", AquaponicsResource.class);
          }
          else if ("hvac".equals(key.split("/")[1])) {
            router.attach(contextRoot, HvacResource.class);
            router.attach("", HvacResource.class);
          }
          else if ("lighting".equals(key.split("/")[1])) {
            router.attach(contextRoot, LightingResource.class);
            router.attach("", LightingResource.class);
          }
          else if ("egauge-1.halepilihonua.hawaii.edu".equals(key.split("/")[0])) {
            router.attach(contextRoot, PhotovoltaicsResource.class);
            router.attach("", PhotovoltaicsResource.class);
          }
          else if ("egauge-2.halepilihonua.hawaii.edu".equals(key.split("/")[0])) {
            router.attach(contextRoot, ElectricalResource.class);
            router.attach("", ElectricalResource.class);
          }
          else {
            System.out.println("Error with attaching to router.");
          }
          System.out.println(key.split("/")[0]);
          // Return the root router
          return router;
        }
      };
      component.getServers().add(Protocol.HTTP, port);
      // Attach the application to the component with a defined contextRoot.
      component.getDefaultHost().attach(contextRoot, application);
    }
    component.start();
    
    // Perform GETS on all devices at a specified interval.
    while (true) {
      for (int i = 0; i < urls.size(); i++) {
        ClientResource client = new ClientResource(urls.get(i));
        System.out.format("GET %s: %s%n", urls.get(i), client.get().getText());
      }
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
      System.out.println("failed to read properties file");
      System.out.println(configFilePath);
    }
  }
  
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * 
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {
    // Create a router restlet.
    Router router = new Router(getContext());
    // Attach the resources to the router.
    //router.attach("/aquaponics/{request}", AquaponicsResource.class);
    // Return the root router
    return router;
  }
}