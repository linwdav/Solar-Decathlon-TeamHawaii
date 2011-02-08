package edu.hawaii.ihale.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A room-based sub-grouping that allows interaction between 
 * Sensors and Actuators of various SubSystems.  This class
 * should be extended, and all Devices should be instantiated
 * in the new Class.  This means that any Device defined should 
 * have a controller/accessor method.
 * 
 * @author Team Maka
 *
 */
public abstract class Environment extends ServerResource {

  /** Unique sensor for GET requests on the Environment. */
  private Map<String,Sensor> sensors;
  /** Unique actuator for PUT requests on the Environment. */
  private Map<String,Actuator> actuators;
  /** String holding the Environment name (e.g. "livingroom").*/
  private String environmentName;

  /**
   * Returns the environment's name as a String.
   * @return A String containing the environment's name.
   */
  public String getEnvironmentName() {
    return environmentName;
  }
  
  /**
   * Returns a list of Devices in the Environment. 
   * @return An XML list of all devices in the Environment.
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
    // Create and attach the root element <device>.
    Element rootElement;
    String device = (String)this.getRequestAttributes().get("device");
	rootElement = doc.createElement("devices");
	List<Sensor> list = new ArrayList<Sensor>(sensors.values());
	for (Sensor sensor : list) {
	Element node = doc.createElement("sensors");
	attachElement(doc, node, "id", "" + sensor.getID());
	attachElement(doc, node, "description", "" + sensor.getDeviceDescription());
	attachElement(doc, node, "id", "" + sensor.getID());
	}
	List<Actuator> list = new ArrayList<Actuator>(actuators.values());
	for (Actuator actuator : list) {
	Element node = doc.createElement("actuators");
	attachElement(doc, node, "id", "" + actuator.getID());
	attachElement(doc, node, "description", "" + actuator.getDeviceDescription());
	attachElement(doc, node, "id", "" + actuator.getID());
	}
    doc.appendChild(rootElement);
    // The requested contact was found, so add the Contact's XML representation to the response.
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
