package edu.hawaii.ihale.housesimulator.hvac;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A server resource that will handle requests regarding the HVAC system. Supported operations: PUT.
 * Supported representations: XML.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class HVACPutResource extends ServerResource {

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

    if ("temp".equalsIgnoreCase(putCommand) && "setTemp".equalsIgnoreCase(command)) {
      HVACData.setDesiredTemperature(Long.parseLong(arg));
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }

    return null;
  }

}