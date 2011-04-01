package edu.hawaii.ihale.backend.restserver.resource.hvac;

import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the HVAC system. Supported operations: GET.
 * Supported representations: XML.
 * 
 * @author Michael Cera
 */
public class HvacState extends ServerResource {
  /**
   * Returns the data requested through the URI.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Return the representation.
    return new EmptyRepresentation();
  }
}