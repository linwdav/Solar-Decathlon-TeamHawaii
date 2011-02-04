package edu.hawaii.ihale.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An Object that represents a whole subsystem, and has access to all applicable sensors. 
 * This object collects and stores all data for sensors within it's scope.  
 * @author Team Maka
 */
  public abstract class SubSystemObject extends ServerResource{
  /** A List of Environment in this System's scope.*/
  protected Map<String,Environment> environmentList;
  /** The name of this System (e.g. "Aquaponics").*/
  protected String subSystemName;
  /** The URL Base for use by REST. */
  protected String urlRoot = "http://localhost:";
  /** The port this object will run on. */
  protected int port;
  /** The time interval in milliseconds for data polling.*/
  protected long delay;
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (e.g. "Aquaponics").
   * @param port The port number the REST interface will run on.
   */
  public SubSystemObject(String subSystemName,int port) {
    this.subSystemName = subSystemName;
    this.port = port;
  }
  
  public Environment getEnvrionment(String key) { 
    return environmentList.get(key);
  }
  
  /**
   * Returns the object's URL for use by REST.
   * @return A String representing the URL.
   */
  public String getSSOURL() {
    return urlRoot + port + "/" + subSystemName;
  }
 
  /**
   * Tells the object to poll data from the sensors and put them in the DataBase.
   */
  public abstract void run();
  
  
  /**
   * Returns the Contact instance requested by the URL. 
   * @return The XML representation of the contact, or CLIENT_ERROR_NOT_ACCEPTABLE if the 
   * unique ID is not present.
   * @throws Exception If problems occur making the representation. Shouldn't occur in 
   * practice but if it does, Restlet will set the Status code. 
   */
  @Get
  public Representation getDevices() throws Exception {
    // Create an empty XML representation.
    DomRepresentation result = new DomRepresentation();
    // Get the contact's uniqueID from the URL.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    // Create and attach the root element <contact>.
    Element rootElement = doc.createElement("Environments");
    List<Environment> list = new ArrayList<Environment>(environmentList.values());
    for (Environment environ : list) {
      attachElement(doc, rootElement, "Environment",environ.getEnvironmentName());
    }
    doc.appendChild(rootElement);
    result.setDocument(doc);
    return result;
  } 
  
  /**
   * Helper function that creates a child element and attaches it to the passed parent element.
   * @param doc The document for creating elements. 
   * @param parent The parent element. 
   * @param childName The name of the child element.
   * @param childValue The text value for the child element. 
   */
  private void attachElement(Document doc, Element parent, String childName, String childValue) {
    Element childElement = doc.createElement(childName);
    childElement.setTextContent(childValue);
    parent.appendChild(childElement);
  }
}
