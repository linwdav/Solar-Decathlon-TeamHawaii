package edu.hawaii.ihale.backend.restserver.resource.hvac;

import java.util.Map;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the HVAC system. Supported operations: GET.
 * Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
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

    Map<String, String> queryMap = getQuery().getValuesMap();

    if (queryMap.containsKey("since")) {
      Long timestamp = Long.valueOf(queryMap.get("since"));
      return HvacData.toXmlSince(timestamp);
    }
    else {
      return HvacData.toXml();
    }
  }
}
