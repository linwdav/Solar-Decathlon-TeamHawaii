package edu.hawaii.ihale.backend.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests for the photovoltaics system.
 * Supported operations: GET and PUT.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class PhotovoltaicsResource extends ServerResource {

  /**
   * Returns the current state of the photovoltaics system.
   * In the actual implementation, an XML representation will be returned instead of a String.
   * @return Current state of the photovoltaics system.
   */
  @Get
  public String getPhotovoltaicsState() {
    return "This is the photovoltaics resource!";
  }
}
