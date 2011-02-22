package edu.hawaii.ihale.housesimulator.photovoltaics;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Photovoltaic system. Supported
 * operations: GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class PhotovoltaicsResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Create an empty XML representation.
    DomRepresentation result = new DomRepresentation();
    // Return the representation. The Status code tells the client if the representation is valid.
    return result;
  }
}
