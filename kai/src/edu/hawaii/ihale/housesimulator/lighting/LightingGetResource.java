package edu.hawaii.ihale.housesimulator.lighting;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class LightingGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    String room = (String) this.getRequestAttributes().get("room");

    if ("living".equalsIgnoreCase(room) || "dining".equalsIgnoreCase(room)
        || "kitchen".equalsIgnoreCase(room) || "bathroom".equalsIgnoreCase(room)) {
      // Return the representation.
      return LightingData.toXml(room);
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    return null;
  }
}
