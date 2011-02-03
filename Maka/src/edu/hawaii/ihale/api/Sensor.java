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
  /** The sensor's data.*/
  private Object data;
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (eg "Aquaponics").
   * @param deviceDescription String describing the location and function of the device.
   */
  Sensor(String subSystemName, String deviceDescription) {
    super(subSystemName, deviceDescription);
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
    Element rootElement = doc.createElement(subSystemName);
    doc.appendChild(rootElement);
    // Now create and attach the fields for this contact.
    attachElement(doc, rootElement, subSystemName + ID, data.toString());
    return doc;
  }
  
  
  /** 
   * Setter method for HSIM use.
   * @param data The new Data to insert.
   */
  public void setData(Object data) {
    this.data = data;
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
