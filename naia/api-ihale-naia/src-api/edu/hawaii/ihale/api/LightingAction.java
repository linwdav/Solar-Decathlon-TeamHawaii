package edu.hawaii.ihale.api;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class that describes actions taken by the user to change settings within the Lighting system.
 * 
 * @author Team Naia
 */
public class LightingAction {

  // Holds action information to be displayed to screen
  private String statusMessage;

  /**
   * Given a setting to change, this returns a DOM representation for XML conversion to convey
   * setting change to Hvac sensor system.
   * 
   * To change the kWh/h to 1 kWh/h the following parameters are used.
   * 
   * @param settingName E.g., air-temp.
   * @param settingDescription E.g., kWh/h.
   * @param value E.g., 1
   * @return DOM representation of setting change request.
   */
  public Document changeSetting(String settingName, String settingDescription, String value) {
    this.statusMessage = settingDescription + " changed to " + value;
    
    try {
      return this.createDOM(settingName, value);
    }
    catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates DOM state-action object.  This is the XML structured representation 
   * of the request to change an hvac setting.  Example of a request to change
   * the Air Temperature to 70 degrees Fahrenheit.
   * 
   * <pre>
   *   <state-action system="lighting" name="power-consumption">
   *     <state>1</state>
   *   </state-action>
   * </pre>
   * 
   * @param category The setting to change.
   * @param value The desired value.
   * @throws ParserConfigurationException
   */
  public Document createDOM(String category, String value) throws ParserConfigurationException {
    
    // Create DOM Object
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.newDocument();
    
    // Create Root Element
    Element rootElement = document.createElement("state-action");
    
    // Set Root Element Attributes
    rootElement.setAttribute("system", "lighting");
    rootElement.setAttribute("name", category);
    
    // Add root to DOM
    document.appendChild(rootElement);
    
    // Create text node to hold value
    Element element = document.createElement("state");
    element.appendChild(document.createTextNode(value));
    
    // Add child node to root to hold state information
    rootElement.appendChild(element);
    
    return document;
  }

  /**
   * Gets status message for informational purposes.
   * 
   * @return current status message.
   */
  public String getStatusMsg() {
    return this.statusMessage;
  }
} // End Lighting class
