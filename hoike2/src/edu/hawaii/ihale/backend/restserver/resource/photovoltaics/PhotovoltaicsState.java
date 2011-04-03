package edu.hawaii.ihale.backend.restserver.resource.photovoltaics;

import java.util.Map;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Photovoltaics system. Supported
 * operations: GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class PhotovoltaicsState extends ServerResource {
  /**
   * Returns the data requested through the URI.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {

    Map<String, String> queryMap = getQuery().getValuesMap();
    Long timestamp;
    Representation rep = null;
    Status status = Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY;

    if (queryMap.containsKey("since")) {
      timestamp = Long.valueOf(queryMap.get("since"));

      if (timestamp != null) {
        rep = PhotovoltaicsData.toXmlSince(timestamp);
        status = Status.SUCCESS_OK;
      }
    }
    else {
      rep = PhotovoltaicsData.toXml();
      status = Status.SUCCESS_OK;
    }
    
    // Default case
    if (rep == null) {
      rep = new EmptyRepresentation();
    }

    getResponse().setStatus(status);
    
    return rep;
  }
}
