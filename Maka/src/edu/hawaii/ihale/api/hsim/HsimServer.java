package edu.hawaii.ihale.api.hsim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Router;
import edu.hawaii.ihale.api.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.api.hvac.HVACResource;
import edu.hawaii.ihale.api.lights.BathroomLightsResource;
import edu.hawaii.ihale.api.lights.DiningroomLightsResource;
import edu.hawaii.ihale.api.lights.KitchenLightsResource;
import edu.hawaii.ihale.api.lights.LivingroomLightsResource;


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
    
    runServer("/aquaponics",8001);
    runServer("/hvac",8002);
    //runServer("/photovoltaics",8003);
    //runServer("/electrical",8004;
    runServer("/living",8005);
    runServer("/dining",8006);
    runServer("/kitchen",8007);
    runServer("/bath",8008);
    long delay = 5000; //milliseconds
    long period = 5000;
    time = new Timer();
    time.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        String tUrl;
        ClientResource client;
        System.out.println(names);
        System.out.println(ports);
        for (int i = 0; i < names.size(); i++) {
          tUrl = String.format("http://localhost:%s/%s/state", ports.get(i),
          names.get(i));
          System.out.println("Refreshing: " + names.get(i));
           client = new ClientResource(tUrl);
          client.get();
        }
      }
  }
    , delay, period);
// */
  }
  
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * @return A Router restlet that implements dispatching.
   */
  /*
  @Override
  public Restlet createInboundRoot() {
      // Create a router restlet.
      Router router = new Router(getContext());
      // Attach the resources to the router.
      router.attach("/Aquaponics/{uniqueID}", AquaponicsResource.class);
      router.attach("/Aquaponics", AquaponicsResource.class);
      // Return the root router
      return router;
  }
  */
}