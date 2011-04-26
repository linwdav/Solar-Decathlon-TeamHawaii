package edu.hawaii.systemh.housesimulator.hvac;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.systemh.api.ApiDictionary.SystemHCommandType;

/**
 * A server resource that will handle requests regarding the HVAC system. 
 * Supported operations: PUT.
 * Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class HVACPutResource extends ServerResource {

  /**
   * Changes the values of HVAC house system based on received XML. Currently only supports the
   * setting of the desired home temperature.
   * 
   * @param representation The XML representation of the command to execute on the HVAC system.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putValue(Representation representation) throws Exception {
    
    DomRepresentation domRep = new DomRepresentation(representation);
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList commandList = domDoc.getElementsByTagName("command");
    NodeList argList = domDoc.getElementsByTagName("arg");

    // Grabs attributes from tags.
    String command = ((Element) commandList.item(0)).getAttribute("name");
    String arg = ((Element) argList.item(0)).getAttribute("value");

    String putCommand = (String) this.getRequestAttributes().get("putcommand");
    
    if ("temperature".equals(putCommand) && 
        SystemHCommandType.SET_TEMPERATURE.toString().equals(command)) {
      HVACData.setDesiredTemp(Integer.parseInt(arg));
      HVACData.modifySystemState();
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    
    return null;
  }
}