package edu.hawaii.ihale.housesimulator;

import java.util.ArrayList;
import java.util.List;
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
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, HVACResource.class);
            router.attach(key, HVACResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8003:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, PhotovoltaicResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8004:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, ElectricalResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8005:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, LivingroomLightsResource.class);
            router.attach(key, LivingroomLightsResource.class);
            //router.attach("/kitchen", LivingRoomResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8006:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, DiningroomLightsResource.class);
            router.attach(key, DiningroomLightsResource.class);
            //router.attach("/kitchen", LivingRoomResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8007:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, KitchenLightsResource.class);
            router.attach(key, KitchenLightsResource.class);
            //router.attach("/kitchen", LivingRoomResource.class);
            // Return the root router
            return router;  
          }   
        }; break;
      case 8008:
        application = new Application() {  
          @Override  
          public Restlet createInboundRoot() {  
            // Create a router restlet.
            Router router = new Router(getContext());
            // Attach the resources to the router.
            router.attach(state, BathroomLightsResource.class);
            router.attach(key, BathroomLightsResource.class);
            //router.attach("/kitchen", LivingRoomResource.class);
            // Return the root router
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
    //start refreshing
    Refresher refresh = new Refresher();
    refresh.start(5000);
  }
}