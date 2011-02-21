package edu.hawaii.ihale.api.hsim;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
//import edu.hawaii.contactservice.server.resource.contact.ContactResource;
//import edu.hawaii.contactservice.server.resource.contacts.ContactsResource;

/**
 * A simple HTTP server that provides access to a "Contacts Database" via a REST interface.
 * This is an example Restlet system that is just one step beyond HelloWorld.
 * This class does two things: (1) it sets up and runs a web application (via the main() method), 
 * and (2) it defines how URLs sent to this web application get dispatched to ServerResources that
 * handle them.
 * @author Philip Johnson
 */
public class HsimServer extends Application {
  
  /**
   * Starts a server running on the specified port.
   * The context root will be "contactserver".
   * We create a separate runServer method, rather than putting this code into the main() method,
   * so that we can run tests on a separate port.  This illustrates one way to 
   * provide a "test" configuration that differs from the "production" configuration.
   * @param port The port on which this server should run.
   * @throws Exception if problems occur starting up this server. 
   */
  public static void runServer(int port) throws Exception {
    // Create a component.  
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, port);
    // Create an application (this class).
    Application application = new HsimServer();
    String contextRoot = null;
    // Attach the application to the component with a defined contextRoot.
    if (port == 8111) {
      contextRoot = "/aquaponics";
    }
    else if (port == 8112) {
      contextRoot = "/hvac";
    }
    else if (port == 8113) {
      contextRoot = "/lighting";
    }
    else if (port == 8114) {
      contextRoot = "/water";
    }
   
    component.getDefaultHost().attach(contextRoot, application);

    component.start();
  }

  /**
   * This main method starts up a web application that will listen on port 8111.
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    runServer(8111);
    runServer(8112);
    runServer(8113);
    runServer(8114);
  }
  
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {
      // Create a router restlet.
      Router router = new Router(getContext());
      // Attach the resources to the router.
      router.attach("/contact/{uniqueID}", ContactResource.class);
      router.attach("/contacts", ContactsResource.class);
      // Return the root router
      return router;
  }
}