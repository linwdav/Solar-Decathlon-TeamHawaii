package edu.hawaii.ihale.backend.restserver;

import java.util.logging.Level;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.backend.restserver.resource.aquaponics.AquaponicsCommand;
import edu.hawaii.ihale.backend.restserver.resource.aquaponics.AquaponicsState;
import edu.hawaii.ihale.backend.restserver.resource.electrical.ElectricalState;
import edu.hawaii.ihale.backend.restserver.resource.hvac.HvacCommand;
import edu.hawaii.ihale.backend.restserver.resource.hvac.HvacState;
import edu.hawaii.ihale.backend.restserver.resource.lighting.LightingCommand;
import edu.hawaii.ihale.backend.restserver.resource.lighting.LightingState;
import edu.hawaii.ihale.backend.restserver.resource.photovoltaics.PhotovoltaicsState;

/**
 * A simple HTTP server that provides external devices with access to the iHale system.
 * 
 * @author Philip Johnson
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class RestServer extends Application {

  /**
   * The REST server.
   */
  private static Component component = new Component();

  /**
   * Starts the REST server running on the specified port.
   * 
   * @param port The port on which this server should run.
   * @return Return true if server is not already running and has been started.
   * @throws Exception if problems occur starting up this server.
   */
  public static boolean runServer(int port) throws Exception {

    boolean status = component.isStopped();
    
    // When server has not been started before, initialize values.
    if (status) {
      component.getLogger().setLevel(Level.OFF);
      
      component.getServers().add(Protocol.HTTP, port);
      component.getClients().add(Protocol.HTTP);
  
      Application application = new RestServer();
  
      String contextRoot = "";
      component.getDefaultHost().attach(contextRoot, application);
      component.start();
    }

    return status;
  }

  /**
   * Shuts down this REST server.
   * 
   * @return Return true if server is was running and has been stopped.
   * @throws Exception Thrown when server fails to shut down.
   */
  public static boolean startServer() throws Exception {

    boolean status = component.isStopped();

    if (status) {
      component.start();
    }
    
    return status;
  }

  /**
   * Shuts down this REST server.
   * 
   * @return Return true if server is was running and has been stopped.
   * @throws Exception Thrown when server fails to shut down.
   */
  public static boolean stopServer() throws Exception {

    boolean status = component.isStarted();

    if (status) {
      component.stop();
    }
    
    return status;
  }

  /**
   * Runs this REST server on port 8111.
   * 
   * @param args Not Used.
   * @throws Exception Thrown when server fails to initialize.
   */
  public static void main(String[] args) throws Exception {
    System.out.println(runServer(8111));
  }

  /**
   * Specifies the URI map for the respectful REST server resource.
   * 
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {

    Router router = new Router(getContext());

    // Attach resources to router.
    router.attach("/AQUAPONICS/state", AquaponicsState.class);
    router.attach("/AQUAPONICS/command/{command}", AquaponicsCommand.class);

    router.attach("/HVAC/state", HvacState.class);
    router.attach("/HVAC/command/{command}", HvacCommand.class);

    router.attach("/ELECTRIC/state", ElectricalState.class);

    router.attach("/PHOTOVOLTAIC/state", PhotovoltaicsState.class);

    router.attach("/LIGHTING/state", LightingState.class);
    router.attach("/LIGHTING/command/{command}", LightingCommand.class);

    // Return the root router
    return router;
  }
}
