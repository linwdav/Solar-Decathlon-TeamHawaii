package edu.hawaii.ihale.backend.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests for the electrical system.
 * Supported operations: GET and PUT.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class ElectricalResource extends ServerResource {

  /**
   * Returns the current state of the electrical system.
   * In the actual implementation, an XML representation will be returned instead of a String.
   * @return Current state of the electrical system.
   */
  @Get
  public String getElectricalState() {
    return "This is the electrical resource!";
  }
}
