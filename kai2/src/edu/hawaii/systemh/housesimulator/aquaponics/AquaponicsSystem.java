package edu.hawaii.systemh.housesimulator.aquaponics;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Provides access to the Aquaponics system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
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
    router.attach("/{putcommand}", AquaponicsPutResource.class);
    router.attach("/fish/{putcommand}", AquaponicsPutResource.class);
    router.attach("/water/{putcommand}", AquaponicsPutResource.class);
    // Return the root router
    return router;
  }
}