package edu.hawaii.ihale.housesimulator.lighting.bathroom;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * PUT. Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class LightingBathroomPutResource extends ServerResource {

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
    
    String putCommand = (String) this.getRequestAttributes().get("putcommand");

    //allows user to set the level
    String setLevel = "setLevel";
    
    //allows user to turn (enable) lights on/off
    String setEnable = "setEnable";
    
    //allows user to set the colors
    String setColor = "setColor";
    
    // Call mutator corresponding to room.
    if (setLevel.equalsIgnoreCase(putCommand) && "SET_LIGHTING_LEVEL".equalsIgnoreCase(command)) {
      LightingData.setBathroomLevel(Long.parseLong(arg));
    }
    // Call lighting mutator corresponding to room.
    else if (setColor.equalsIgnoreCase(command) && "SET_LIGHTING_COLOR".equalsIgnoreCase(command)) {
      LightingData.setBathroomColor(setColor);
    }    
    // Call enable mutator corresponding to room.
    else if (setEnable.equalsIgnoreCase(command) && 
        "SET_LIGHTING_ENABLED".equalsIgnoreCase(command) ) {
      LightingData.setBathroomEnabled(true);
    }
    else { 
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    
    return null;
  }

}