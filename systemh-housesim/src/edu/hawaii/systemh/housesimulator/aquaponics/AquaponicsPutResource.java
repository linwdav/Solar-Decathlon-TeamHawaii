package edu.hawaii.systemh.housesimulator.aquaponics;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A server resource that will handle requests regarding the Aquaponics system. Supported
 * operations: PUT. Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class AquaponicsPutResource extends ServerResource {

  /**
   * Changes the values of house systems based on received XML.
   * 
   * @param representation The XML representation of the new Contact to add.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putValue(Representation representation) throws Exception {
    // Get the XML representation of the Contact.
    DomRepresentation domRep = new DomRepresentation(representation);
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList commandList = domDoc.getElementsByTagName("command");
    NodeList argList = domDoc.getElementsByTagName("arg");

    // Grabs attributes from tags.
    String command = ((Element) commandList.item(0)).getAttribute("name");
    String arg = ((Element) argList.item(0)).getAttribute("value");

    String putCommand = (String) this.getRequestAttributes().get("putcommand");

    if ("temperature".equalsIgnoreCase(putCommand) && "SET_TEMPERATURE".equalsIgnoreCase(command)) {
      AquaponicsData.setDesiredTemperature(Integer.parseInt(arg));
    }
    else if ("feed".equalsIgnoreCase(putCommand) && "FEED_FISH".equalsIgnoreCase(command)) {
      AquaponicsData.addFishFeed(Double.parseDouble(arg));
    }
    else if ("harvest".equalsIgnoreCase(putCommand)
        && "HARVEST_FISH".equalsIgnoreCase(command)) {
      AquaponicsData.harvestFish(Integer.parseInt(arg));
    }
    else if ("nutrients".equalsIgnoreCase(putCommand)
        && "SET_NUTRIENTS".equalsIgnoreCase(command)) {
      AquaponicsData.setNutrients(Double.parseDouble(arg));
    }
    else if ("ph".equalsIgnoreCase(putCommand) && "SET_PH".equalsIgnoreCase(command)) {
      AquaponicsData.setDesiredPh(Double.parseDouble(arg));
    }
    else if ("level".equalsIgnoreCase(putCommand)
        && "SET_WATER_LEVEL".equalsIgnoreCase(command)) {
      AquaponicsData.setDesiredWaterLevel(Integer.parseInt(arg));
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }

    return null;
  }

}