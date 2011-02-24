package edu.hawaii.ihale.housesimulator.aquaponics;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Provides access to the Aquaponics system.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class AquaponicsSystem extends Application {
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
    router.attach("/state", AquaponicsGetResource.class);
    // Changed the following classes to the new AquaponicsPutResource.class once implemented.
    // Leaving as is to pass ant verify tests.
    router.attach("/{putcommand}", AquaponicsPutResource.class);
    // Return the root router
    return router;
  }
}