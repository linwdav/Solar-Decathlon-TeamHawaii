package edu.hawaii.ihale.backend.restserver.resource.hvac;

import java.util.Map;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
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

    Representation rep = null;
    Long timestamp;
    Map<String, String> queryMap = getQuery().getValuesMap();
    Status status = Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY;

    if (queryMap.containsKey("since")) {
      timestamp = Long.valueOf(queryMap.get("since"));
      
      if (timestamp != null) {
        rep = HvacData.toXmlSince(timestamp);
        status = Status.SUCCESS_OK;
      }
    }
    else {
      rep = HvacData.toXml();
      status = Status.SUCCESS_OK;
    }

    // Default case.
    if (rep == null) {
      rep = new EmptyRepresentation();
    }
    
    getResponse().setStatus(status);
    
    return rep;
  }
}
