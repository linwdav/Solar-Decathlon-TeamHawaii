package edu.hawaii.ihale.housesimulator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    Application application = null;
    switch (port) {

      case 8001:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
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
   * This main method starts up a web application that will listen on port 8111. Properties file
   * code inspired by team kai. Thankyou team kai!
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
    
    // properties file stuff
    File directoryFile = new File(System.getProperty("user.home"), ".ihale");
    File propertiesFile = new File(directoryFile, "device-urls.properties");
    Properties properties = new Properties();

    String aquaponics = "http://localhost:8001/";
    String hvac = "http://localhost:8002/";
    String pv = "http://localhost:8003/";
    String electrical = "http://localhost:8004/";
    String lightingLiving = "http://localhost:8005/";
    String lightingDining = "http://localhost:8006/";
    String lightingKitchen = "http://localhost:8007/";
    String lightingBathroom = "http://localhost:8008/";
   
    properties.setProperty("aquaponics-state", aquaponics);
    properties.setProperty("aquaponics-control", aquaponics);
    properties.setProperty("hvac-state", hvac);
    properties.setProperty("hvac-control", hvac);
    properties.setProperty("lighting-living-state", lightingLiving);
    properties.setProperty("lighting-living-control", lightingLiving);
    properties.setProperty("lighting-dining-state", lightingDining);
    properties.setProperty("lighting-dining-control", lightingDining);
    properties.setProperty("lighting-kitchen-state", lightingKitchen);
    properties.setProperty("lighting-kitchen-control", lightingKitchen);
    properties.setProperty("lighting-bathroom-state", lightingBathroom);
    properties.setProperty("lighting-bathroom-control", lightingBathroom);
    properties.setProperty("pv-state", pv);
    properties.setProperty("electrical-state", electrical);
    
    if (!directoryFile.exists()) {
      directoryFile.mkdir();
    }
    
    if (propertiesFile.exists()) {
      // delete old properties file if it exists and create a new one
      propertiesFile.delete();
      propertiesFile.createNewFile();
      properties.store(new FileOutputStream(propertiesFile), null);
    }
    else {
      propertiesFile.createNewFile();
      properties.store(new FileOutputStream(propertiesFile), null);  
    }
    
    if (args.length == 2 && args[0].equalsIgnoreCase("-stepinterval")
        && args[1].matches("\\d+")) {
      Refresher refresh = new Refresher();
      refresh.start(Integer.parseInt(args[1]));
    }
    else {
      System.err.println("Invalid parameters: " + Arrays.toString(args));
      System.err.println("Usage: java -jar ihale-housesim-maka.jar -stepinterval 5");
      System.exit(1);
    }

  }
}