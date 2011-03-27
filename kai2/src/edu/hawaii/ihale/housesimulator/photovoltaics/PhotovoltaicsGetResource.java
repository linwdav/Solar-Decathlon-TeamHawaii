package edu.hawaii.ihale.housesimulator.photovoltaics;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Photovoltaics system. Supported
 * operations: GET. Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class PhotovoltaicsGetResource extends ServerResource {
  /**
   * Returns the data requested by the HTTP request.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Return the representation.
    return PhotovoltaicsData.toXml();
  }
}