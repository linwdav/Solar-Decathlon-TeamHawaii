package edu.hawaii.ihale.backend.restserver;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.backend.restserver.system.AquaponicsCommandResource;
import edu.hawaii.ihale.backend.restserver.system.AquaponicsStateResource;
import edu.hawaii.ihale.backend.restserver.system.ElectricityCommandResource;
import edu.hawaii.ihale.backend.restserver.system.ElectricityStateResource;
import edu.hawaii.ihale.backend.restserver.system.HVACCommandResource;
import edu.hawaii.ihale.backend.restserver.system.HVACStateResource;
import edu.hawaii.ihale.backend.restserver.system.LightingCommandResource;
import edu.hawaii.ihale.backend.restserver.system.LightingStateResource;
import edu.hawaii.ihale.backend.restserver.system.PhotovoltaicsCommandResource;
import edu.hawaii.ihale.backend.restserver.system.PhotovoltaicsStateResource;

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
    
    // Attach resources to router.
    router.attach("/AQUAPONICS/state", AquaponicsStateResource.class);
    router.attach("/AQUAPONICS/command/{command}", AquaponicsCommandResource.class);
    router.attach("/HVAC/state", HVACStateResource.class);
    router.attach("/HVAC/command/{command}", HVACCommandResource.class);
    router.attach("/ELECTRICITY/state", ElectricityStateResource.class);
    router.attach("/ELECTRICITY/command{command}", ElectricityCommandResource.class);
    router.attach("/PHOTOVOLTAICS/state", PhotovoltaicsStateResource.class);
    router.attach("/PHOTOVOLTAICS/command{command}", PhotovoltaicsCommandResource.class);
    router.attach("/LIGHTING/state", LightingStateResource.class);
    router.attach("/LIGHTING/command{command}", LightingCommandResource.class);
    // Return the root router
    return router;
  }
}
