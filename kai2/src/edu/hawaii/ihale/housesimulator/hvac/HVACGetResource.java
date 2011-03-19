package edu.hawaii.ihale.housesimulator.hvac;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the HVAC system. Supported operations: GET.
 * Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class HVACGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Return the representation.
    return HVACData.toXml();
  }
}
