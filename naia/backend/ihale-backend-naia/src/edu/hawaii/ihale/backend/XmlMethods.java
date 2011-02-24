package edu.hawaii.ihale.backend;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * A class that has methods that parse and create XML document.
 * 
 * @author Christopher Ramleb, Kevin Leong and Emerson Tabucol
 */
public class XmlMethods {

  /**
   * A method to parse the XML Document from the house's systems
   * and store it as a SystemStateEntry object.
   * @param doc The xml document from the house's systems
   * @return The populated entry
   */
  
  public static SystemStateEntry parseXML(Document doc) {

    // Initialize
    String system = ""; 
    String device = "";
    long timestamp = 0;
    
    try {
      
      // Normalize text representation
      doc.getDocumentElement().normalize();

      // Get root element name of the xml document
      Node root = doc.getDocumentElement();
      //System.out.println("Root element is " + root.getNodeName());

      // Get attributes of root element <state-data>
      NamedNodeMap rootAttributes = root.getAttributes();
      for (int i = 0; i < rootAttributes.getLength(); i++) {
        Node rootAttribute = rootAttributes.item(i);
        //System.out.println("AttributeName: " + rootAttribute.getNodeName() + ", attributeValue: "
          //  + rootAttribute.getNodeValue());
        String label = rootAttribute.getNodeName();
        String value = rootAttribute.getNodeValue();
        
        
        // Checks the label and store 
        if ("system".equals(label)) {
          system = value;
        }
        
        if ("device".equals(label)) {
          device = value;
        }
        
        if ("timestamp".equals(label)) {
          timestamp = Long.parseLong(value.trim());
        }
        
      }
      
      // Create new SystemStateEntry object
      SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
      
      // Get list of child elements
      NodeList children = root.getChildNodes();

      // Iterate through the child elements
      for (int i = 0; i < children.getLength(); i++) {
        Node child = children.item(i);
     
        if (child.getNodeType() == Node.ELEMENT_NODE) {

          // Get attributes of child element <state>
          NamedNodeMap childAttributes = child.getAttributes();
          for (int x = 0; x < childAttributes.getLength(); x++) {
            Node childAttribute = childAttributes.item(x);
            
            String key = childAttribute.getNodeName();
            String value = childAttribute.getNodeValue();
            
            if ("pH".equals(key) || "Oxygen".equals(key) ) {
              // Then set double
              entry.putDoubleValue(key, Double.parseDouble(value));
            }
            else {
              // Set long
              entry.putLongValue(key, Long.parseLong(value.trim()));
            }
            
          }
        }
      }
      return entry;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    
    
    
  } // end of method
  
  /**
   * Returns PUT command as an XML Document instance. For example:
   * 
   * <pre>
   * {@code
   * <command name="setTemp">
   *  <arg value="27"/>
   * </command>
   * }
   * </pre>
   * 
   * @param deviceName The device name ex. arduino-1
   * @param command The command name ex. setTemp
   * @param value The value ex. 75
   * @return doc 
   */
  
  public static Document createXml(String deviceName, String command, List<String> value) {

    // Initialize Document
    Document doc = null;

    try {
      // Create the Document instance representing this XML.
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.newDocument();

      // Create and attach the root element <command> and name attribute.
      Element rootElement = doc.createElement("command");
      rootElement.setAttribute("name", command);
      doc.appendChild(rootElement);

      // Loop through list of arguments.
      for (String s : value) {
        // Create child element, add an attribute, and add to root <arg> 
        Element child = doc.createElement("arg");
        child.setAttribute("value", s);
        rootElement.appendChild(child);
      }

    }
    catch (Exception e) {
      System.out.println(e);
    }

    return doc;

  } // end of createXml method
  
} // end of class
