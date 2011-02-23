package edu.hawaii.ihale.api.hsim;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.api.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.api.hvac.HVACResource;
import edu.hawaii.ihale.api.lights.*;

//import edu.hawaii.contactservice.server.resource.contact.ContactResource;
//import edu.hawaii.contactservice.server.resource.contacts.ContactsResource;

/**
 * A simple HTTP server that provides access to a "Contacts Database" via a REST interface.
 * This is an example Restlet system that is just one step beyond HelloWorld.
 * This class does two things: (1) it sets up and runs a web application (via the main() method), 
 * and (2) it defines how URLs sent to this web application get dispatched to ServerResources that
 * handle them.
 * @author Philip Johnson, Kurt Teichman
 */

public class HsimServer extends Application {
  public static int port;
  /**
   * Starts a server running on the specified port.
   * The context root will be "contactserver".
   * We create a separate runServer method, rather than putting this code into the main() method,
   * so that we can run tests on a separate port.  This illustrates one way to 
   * provide a "test" configuration that differs from the "production" configuration.
   * @param port The port on which this server should run.
   * @throws Exception if problems occur starting up this server. 
   */
  public static void runServer(String contextRoot, int port) throws Exception {
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
            router.attach("/state", AquaponicsResource.class);
            router.attach("/{key}", AquaponicsResource.class);
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
            router.attach("/state", HVACResource.class);
            router.attach("/{key}", HVACResource.class);
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
            router.attach("/state", LivingroomLightsResource.class);
            router.attach("/{key}", LivingroomLightsResource.class);
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
            router.attach("/state", DiningroomLightsResource.class);
            router.attach("/{key}", DiningroomLightsResource.class);
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
            router.attach("/state", KitchenLightsResource.class);
            router.attach("/{key}", KitchenLightsResource.class);
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
            router.attach("/state", BathroomLightsResource.class);
            router.attach("/{key}", BathroomLightsResource.class);
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
    runServer("/aquaponics",8011);
    runServer("/hvac",8012);
    runServer("/lighting",8013);
    //runServer("/photovoltaics",8014);
    //runServer("/electrical",8015);
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