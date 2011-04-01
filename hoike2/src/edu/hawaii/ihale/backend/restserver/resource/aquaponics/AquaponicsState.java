package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

import java.util.Map;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Aquaponics system. Supported
 * operations: GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class AquaponicsState extends ServerResource {
    
  /**
   * Returns the data requested through the URI.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    
    Map<String, String> queryMap = getQuery().getValuesMap();

    if (queryMap.containsKey("since")) {
      // TODO Return state history using value assocaited with "since".
      return new EmptyRepresentation();
    }
    else {
      return AquaponicsData.toXml();
    }
  }
}
