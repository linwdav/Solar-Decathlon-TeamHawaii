package edu.hawaii.ihale.api;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** 
 * A Class representing a Sensor Object, returns data in XML Format.
 * @author Team Maka
 *
 */
public class Sensor extends Device {
  
  /**
   * Constructor.
   * @param environmentName String representing the name of the Environment (e.g. "livingroom").
   * @param deviceDescription String describing the location and function of the device.
   */
  public Sensor(String environmentName, String deviceDescription) {
    super(environmentName, deviceDescription);
  }
  
  /**
   * Returns an XML Document containing the Sensor's Data.
   * @return XML Document with the subSystemName as the root Element, and {subSystemName}{ID} 
   * as the child element.
   * @throws ParserConfigurationException From Document Creation.
   */
  public Document getData() throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    // Create and attach the root element <contact>.
    Element rootElement = doc.createElement(environmentName);
    doc.appendChild(rootElement);
    // Now create and attach the fields for this contact.
    attachElement(doc, rootElement, environmentName + ID, "DATA GOES HERE");
    return doc;
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
