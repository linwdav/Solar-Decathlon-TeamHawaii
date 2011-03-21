package edu.hawaii.ihale.housesimulator.lighting.kitchen;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Provides access to the Lighting system.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class LightingKitchenSystem extends Application {
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
    router.attach("/state", LightingKitchenGetResource.class);
    router.attach("/level", LightingKitchenPutResource.class);
    // Return the root router
    return router;
  }
}
