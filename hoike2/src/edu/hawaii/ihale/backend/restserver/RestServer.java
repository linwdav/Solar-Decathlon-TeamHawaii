package edu.hawaii.ihale.backend.restserver;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.backend.restserver.system.AquaponicsResource;
import edu.hawaii.ihale.backend.restserver.system.ElectricityResource;
import edu.hawaii.ihale.backend.restserver.system.HVACResource;
import edu.hawaii.ihale.backend.restserver.system.LightingResource;
import edu.hawaii.ihale.backend.restserver.system.PhotovoltaicsResource;

/**
 * A simple HTTP server that provides external devices with access to the iHale system.
 * 
 * @author Michael Cera
 */
public class RestServer extends Application {

  /**
   * Starts a server running on the specified port. 
   * 
   * @param port The port on which this server should run.
   * @throws Exception if problems occur starting up this server.
   */
  public static void runServer(int port) throws Exception {
    // Create a component.
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, port);
    // Create an application (this class).
    Application application = new RestServer();
    // Attach the application to the component with a defined contextRoot.
    String contextRoot = "";
    component.getDefaultHost().attach(contextRoot, application);
    component.start();
  }

  /**
   * This main method starts up a web application that will listen on port 8111.
   * 
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    runServer(8111);
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
    router.attach("/AQUAPONICS", AquaponicsResource.class);
    router.attach("/HVAC", HVACResource.class);
    router.attach("/ELECTRICITY", ElectricityResource.class);
    router.attach("/PHOTOVOLTAICS", PhotovoltaicsResource.class);
    router.attach("/LIGHTING", LightingResource.class);
    // Return the root router
    return router;
  }
}
