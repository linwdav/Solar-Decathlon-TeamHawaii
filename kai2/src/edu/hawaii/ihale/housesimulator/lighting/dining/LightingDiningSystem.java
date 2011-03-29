package edu.hawaii.ihale.housesimulator.lighting.dining;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import edu.hawaii.ihale.housesimulator.lighting.bathroom.LightingBathroomPutResource;

/**
 * Provides access to the Lighting system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class LightingDiningSystem extends Application {
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
    router.attach("/state", LightingDiningGetResource.class);
    router.attach("/level", LightingDiningPutResource.class);
    router.attach("/color", LightingBathroomPutResource.class);
    router.attach("/enable", LightingBathroomPutResource.class);
    // Return the root router
    return router;
  }
}
