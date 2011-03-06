package edu.hawaii.ihale.housesimulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.electrical.ElectricalResource;
import edu.hawaii.ihale.hvac.HVACResource;
import edu.hawaii.ihale.lights.BathroomLightsResource;
import edu.hawaii.ihale.lights.DiningroomLightsResource;
import edu.hawaii.ihale.lights.KitchenLightsResource;
import edu.hawaii.ihale.lights.LivingroomLightsResource;
import edu.hawaii.ihale.photovoltaics.PhotovoltaicResource;



//import edu.hawaii.contactservice.server.resource.contact.ContactResource;
//import edu.hawaii.contactservice.server.resource.contacts.ContactsResource;

/**
 * A simple HTTP server that sets up the routing for the SolarDecathlon house.
 * The program refreshes all ports every 5 seconds.
 * @author Team Maka
 */

public class HsimServer extends Application {
  /** Port value.*/
  public static int port;
  /** Timer to refresh the data.*/
  public static Timer time;
  static List<String> ports;
  static List<String> names;
  /**
   * Starts a server running on the specified port.
   * The context root will be "contactserver".
   * We create a separate runServer method, rather than putting this code into the main() method,
   * so that we can run tests on a separate port.  This illustrates one way to 
   * provide a "test" configuration that differs from the "production" configuration.
   * @param port The port on which this server should run.
   * @param contextRoot The uri root to route.
   * @throws Exception if problems occur starting up this server. 
   */
  public static void runServer(String contextRoot, int port) throws Exception {

    final String state = "/state";
    final String key = "/{key}"; 
    names.add(contextRoot.substring(1));
    ports.add("" + port);
    // Create a component.  
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, port);
    // Create an application (this class).
    // Create an application  
    Application application = null;
    switch (port) {

      case 8001:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            //router.attach("/phMeter/{uniqueID}", DayResource.class);
            router.attach(state, AquaponicsResource.class);
            router.attach(key, AquaponicsResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8002:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, HVACResource.class);
            router.attach(key, HVACResource.class);
            return router;  
          }   
        }; break;
      case 8003:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, PhotovoltaicResource.class);
            return router;  
          }   
        }; break;
      case 8004:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, ElectricalResource.class);
            return router;  
          }   
        }; break;
      case 8005:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, LivingroomLightsResource.class);
            router.attach(key, LivingroomLightsResource.class);
            return router;  
          }   
        }; break;
      case 8006:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, DiningroomLightsResource.class);
            router.attach(key, DiningroomLightsResource.class);
            return router;  
          }   
        }; break;
      case 8007:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, KitchenLightsResource.class);
            router.attach(key, KitchenLightsResource.class);
            return router;  
          }   
        }; break;
      case 8008:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            Router router = new Router(getContext());
            router.attach(state, BathroomLightsResource.class);
            router.attach(key, BathroomLightsResource.class);
            return router;  
          }   
        }; break;
        default:
          System.out.println("error, no matching port");
          return;
    }
    // Attach the application to the component with a defined contextRoot.
    component.getDefaultHost().attach(contextRoot, application);
    component.start();
  }
  
  /**
   * This main method starts up a web application that will listen on port 8111.
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    names = new ArrayList<String>();
    ports = new ArrayList<String>();
    String lighting = "/lighting";
    runServer("/aquaponics",8001);
    runServer("/hvac",8002);
    runServer("/photovoltaics",8003);    
    runServer("/electrical",8004);   
    runServer(lighting,8005);
    runServer(lighting,8006);
    runServer(lighting,8007);
    runServer(lighting,8008);
    
    // Get the users home directory and establish the ".ihale" directory
    File theDir = new File(System.getProperty("user.home"), ".ihale");
    // Create the properties file in the ".ihale" directory
    File propFile = new File(theDir, "device-urls.properties");
    // Create the properties object to write to file.
    Properties prop = new Properties();

    // System URI's
    String aquaponics = "http://localhost:8001/";
    String hvac = "http://localhost:8002/";
    String pv = "http://localhost:8003/";
    String electrical = "http://localhost:8004/";
    String lightingLiving = "http://localhost:8005/";
    String lightingDining = "http://localhost:8006/";
    String lightingKitchen = "http://localhost:8007/";
    String lightingBathroom = "http://localhost:8008/";
    /*
    // Set the properties value.
    prop.setProperty("aquaponics-state", aquaponics);
    prop.setProperty("aquaponics-control", aquaponics);
    prop.setProperty("hvac-state", hvac);
    prop.setProperty("hvac-control", hvac);
    prop.setProperty("lighting-living-state", lightingLiving);
    prop.setProperty("lighting-living-control", lightingLiving);
    prop.setProperty("lighting-dining-state", lightingDining);
    prop.setProperty("lighting-dining-control", lightingDining);
    prop.setProperty("lighting-kitchen-state", lightingKitchen);
    prop.setProperty("lighting-kitchen-control", lightingKitchen);
    prop.setProperty("lighting-bathroom-state", lightingBathroom);
    prop.setProperty("lighting-bathroom-control", lightingBathroom);
    prop.setProperty("pv-state", pv);
    prop.setProperty("electrical-state", electrical);
    
    
    if (theDir.mkdir()) {
      // Create the Properties file.
      if (propFile.createNewFile()) {
        // Try to store the properties object in the properties file.
        try {
          prop.store(new FileOutputStream(propFile), null);
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.out.println("Failed to create properties file: " + propFile.getAbsolutePath());
        System.exit(1);
      }
    }
    else {
      System.out.println("Failed to create directory: " + theDir.getAbsolutePath());
      System.exit(1);
    }
    */
    //start refreshing
    Refresher refresh = new Refresher();
    refresh.start(5000);
  }
}