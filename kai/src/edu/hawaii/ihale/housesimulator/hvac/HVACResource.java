package edu.hawaii.ihale.housesimulator.hvac;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the HVAC system. Supported operations: GET,
 * PUT. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class HVACResource extends ServerResource {
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

  /**
   * Receives the desired temperature to set the system to.
   * 
   * @param representation The XML representation of the command.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putTemp(Representation representation) throws Exception {
    return null;
  }
}
