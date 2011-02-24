package edu.hawaii.ihale.housesimulator.lighting;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * PUT. Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class LightingPutResource extends ServerResource {

  /**
   * Changes the values of house systems based on received XML.
   * 
   * @param representation The XML representation.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putValue(Representation representation) throws Exception {
    // Get the XML representation of the data.
    DomRepresentation domRep = new DomRepresentation(representation);
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList commandList = domDoc.getElementsByTagName("command");
    NodeList argList = domDoc.getElementsByTagName("arg");

    // Grabs attributes from tags.
    String command = ((Element) commandList.item(0)).getAttribute("name");
    String arg = ((Element) argList.item(0)).getAttribute("value");

    String room = (String) this.getRequestAttributes().get("room");

    String setLevel = "setLevel";
    // Call mutator corresponding to room.
    if ("living".equalsIgnoreCase(room) && setLevel.equalsIgnoreCase(command)) {
      LightingData.setLivingLevel(Integer.parseInt(arg));
    }
    else if ("dining".equalsIgnoreCase(room) && setLevel.equalsIgnoreCase(command)) {
      LightingData.setDiningLevel(Integer.parseInt(arg));
    }
    else if ("kitchen".equalsIgnoreCase(room) && setLevel.equalsIgnoreCase(command)) {
      LightingData.setKitchenLevel(Integer.parseInt(arg));
    }
    else if ("bathroom".equalsIgnoreCase(room) && setLevel.equalsIgnoreCase(command)) {
      LightingData.setBathroomLevel(Integer.parseInt(arg));
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }

    return null;
  }

}