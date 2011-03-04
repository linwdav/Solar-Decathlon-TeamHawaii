package edu.hawaii.ihale.housesimulator.lighting.dining;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class LightingDiningGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    return LightingData.toXml("dining");
  }
}
