package edu.hawaii.ihale.backend.restserver.resource.lighting;

import java.util.Map;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;

/**
 * A server resource that will handle requests regarding the Aquaponics system. Supported
 * operations: GET. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class LightingState extends ServerResource {

  /**
   * Returns the data requested through the URI.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {

    Map<String, String> queryMap = getQuery().getValuesMap();

    if (queryMap.containsKey("room")) {
      IHaleRoom room = IHaleRoom.valueOf(queryMap.get("room"));
      if (room.equals(IHaleRoom.LIVING) || room.equals(IHaleRoom.DINING)
          || room.equals(IHaleRoom.KITCHEN) || room.equals(IHaleRoom.BATHROOM)) {
        if (queryMap.containsKey("since")) {
          Long timestamp = Long.valueOf(queryMap.get("since"));
          return LightingData.toXmlSince(room, timestamp);
        }
        else {
          return LightingData.toXml(room);
        }
      }
      else {
        throw new NullPointerException("IHaleRoom is invalid.");
      }
    }
    throw new NullPointerException("IHaleRoom is invalid.");
  }
}
