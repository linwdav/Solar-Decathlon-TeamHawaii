package edu.hawaii.ihale.housesimulator.lighting;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class LightingSystem extends Application {
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {
      // Create a router restlet.
      Router router = new Router(getContext());
      // Attach the resources to the router.
      router.attach("/state", LightingResource.class);
      router.attach("/level", LightingResource.class);
      // Return the root router
      return router;
  }
}