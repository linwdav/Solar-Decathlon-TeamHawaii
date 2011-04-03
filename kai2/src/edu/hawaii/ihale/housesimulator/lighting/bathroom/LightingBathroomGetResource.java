package edu.hawaii.ihale.housesimulator.lighting.bathroom;

import java.util.Date;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * GET. Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class LightingBathroomGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    
    System.out.println(new Date() + " --> Bathroom lighting state information was requested.");
    
    return LightingData.toXml("bathroom");
  }
}
