package edu.hawaii.ihale.backend;

import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * A class that has methods that parse and create XML document.
 * 
 * @author Christopher Ramelb, Kevin Leong and Emerson Tabucol
 */
public class XmlMethods {

  /**
   * A method to parse the XML Document from the house's systems and store it as a SystemStateEntry
   * object.
   * 
   * @param doc The xml document from the house's systems
   * @return The populated entry
   */

  public static SystemStateEntry parseXML(Document doc) {

    // System State Attributes
    String system = "";
    String device = "";
    long timestamp = 0;

    // Normalize text representation
    doc.getDocumentElement().normalize();

    // Get the root element
    Element root = doc.getDocumentElement();

    // Get all attributes from root
    NamedNodeMap attributesMap = root.getAttributes();

    // Loop through all attributes
    for (int i = 0; i < attributesMap.getLength(); i++) {

      Node rootAttribute = attributesMap.item(i);
      String label = rootAttribute.getNodeName();
      String value = rootAttribute.getNodeValue();

      // Checks the label and store in corresponding variable
      if ("system".equalsIgnoreCase(label)) {
        system = value;
      }
      else if ("deviceName".equalsIgnoreCase(label)) {
        device = value;
      }
      else if ("device".equalsIgnoreCase(label)) {
        device = value;
      }
      else if ("timestamp".equalsIgnoreCase(label)) {
        timestamp = Long.parseLong(value.trim());
      }
    }

    System.out.println("System: " + system + "\nDevice: " + device + "\nTimestamp: " + timestamp);

    // Create new SystemStateEntry object
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);

    // Get list of child elements
    NodeList children = root.getChildNodes();

    // Loop through all children.
    for (int i = 0; i < children.getLength(); i++) {

      // Perform action on each child node
      Node node = children.item(i);

      // Get attributes associated with node
      NamedNodeMap labels = node.getAttributes();

      // Holds name (e.g., "pH") and value (e.g., 7.0)
      String name = "";
      String value = "";

      // Loop through all attributes. There will be two
      // name and value.
      for (int j = 0; j < 2; j++) {
        if (j == 0) {
          name = labels.item(j).getTextContent();

        }
        else {
          value = labels.item(j).getTextContent();

          if ("ph".equalsIgnoreCase(name)) {
            entry.putDoubleValue("ph", Double.parseDouble(value));
          }
          else if ("oxygen".equalsIgnoreCase(name)) {
            entry.putDoubleValue("oxygen", Double.parseDouble(value));
          }
          else if ("level".equalsIgnoreCase(name)) {
            entry.putLongValue("level", (long) Double.parseDouble(value));
          }
          else if ("temp".equalsIgnoreCase(name)) {
            entry.putLongValue("temp", (long) Double.parseDouble(value));
          }
        } // End Else
      } // End For loop

      System.out.println(name + " = " + value);

    } // End for loop

    return entry;
  } // end of method parse XML

  /**
   * Parse eGauge Readings.
   * 
   * @param systemName The System Name.
   * @param doc The XML document
   * @return The System State Entry object corresponding to these readings.
   */
  public static SystemStateEntry parseEgaugeXML(String systemName, Document doc) {

    // PARSE eGauge XML
    String eqString = " = ";
    String deviceName = "";
    long timestamp = 0;
    SystemStateEntry entry = null;

    if ("photovoltaics".equalsIgnoreCase(systemName)) {
      deviceName = "egauge-1";
    }
    else if ("electrical".equalsIgnoreCase(systemName)) {
      deviceName = "egauge-2";
    }

    System.out.println("System: " + systemName + "\nDevice: " + deviceName);

    // normalize xml document
    doc.getDocumentElement().normalize();

    // Get root element of the xml (<measurements>)
    Node root = doc.getDocumentElement();

    // Get children elements
    NodeList children = root.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {

      Node child = children.item(i);

      if (child.getNodeType() == Node.ELEMENT_NODE) {

        String schild = child.getNodeName();
        String cval = getElementValue(child);

        if ("timestamp".equalsIgnoreCase(schild)) {
          System.out.println(schild + eqString + cval);
          timestamp = Long.parseLong(cval.trim());
        }
        else if ("meter".equalsIgnoreCase(schild)) {

          // Create new SystemStateEntry object
          entry = new SystemStateEntry(systemName, deviceName, timestamp);

          if ("photovoltaics".equalsIgnoreCase(systemName)) {
            // Parse and add Power and Energy value from the PV system (Generation)
            processEnergyPower(entry, "Solar", child);
          }
          else if ("electrical".equalsIgnoreCase(systemName)) {
            // Parse and add Power and Energy value from the Electrical (Consumption)
            processEnergyPower(entry, "Grid", child);
          }
        }

      } // end if (child Node Type)
    } // end for loop (children)

    return entry;

  } // End Parse Egauge XML

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

    System.out.println("\n" + xmlToString(doc));
    return doc;

  } // end of createXml method

  /**
   * Displays XML document to display.
   * 
   * @param doc The XML documnt to print
   * @return String to display
   */
  public static String xmlToString(Document doc) {
    String xmlString = "";
    Transformer transformer;
    try {
      transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      // initialize StreamResult with File object to save to file
      StreamResult result = new StreamResult(new StringWriter());
      DOMSource source = new DOMSource(doc);

      transformer.transform(source, result);
      xmlString = result.getWriter().toString();
    }
    catch (TransformerConfigurationException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    catch (TransformerFactoryConfigurationError e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    catch (TransformerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return xmlString;
  } // End xmlToString

  /**
   * Returns element value.
   * 
   * @param elem element (it is XML tag)
   * @return Element value otherwise empty String
   */
  public static String getElementValue(Node elem) {
    Node kid;
    if (elem != null && elem.hasChildNodes()) {
      for (kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
        if (kid.getNodeType() == Node.TEXT_NODE) {
          return kid.getNodeValue();
        }
      }
    }
    return "";
  } // end getElementValue

  /**
   * Checks if the <meter> attributes is Solar or Grid then parse the <energy> and <power> element
   * value then add it in the SystemStateEntry.
   * 
   * @param entry The SystemStateEntry object
   * @param attribute The attribute "Solar" or "Grid"
   * @param node The parent node <measurement>
   */
  public static void processEnergyPower(SystemStateEntry entry, String attribute, Node node) {

    String eqString = " = ";

    // Get child element (<meter>)
    NamedNodeMap childAttributes = node.getAttributes();

    for (int x = 0; x < childAttributes.getLength(); x++) {
      Node childAttribute = childAttributes.item(x);

      String name = childAttribute.getNodeName();
      String val = childAttribute.getNodeValue();

      // Checks if attributes is either "Solar" or "Grid"
      // depending on the parameter
      if ("title".equalsIgnoreCase(name) && attribute.equalsIgnoreCase(val)) {

        NodeList grandchildren = node.getChildNodes();

        for (int y = 0; y < grandchildren.getLength(); y++) {

          Node grandchild = grandchildren.item(y);

          // Retrieve and store energy and power (<energy> & <power>)
          if (grandchild.getNodeType() == Node.ELEMENT_NODE) {

            String gcName = grandchild.getNodeName();
            String gcValue = getElementValue(grandchild);

            if ("energy".equalsIgnoreCase(gcName)) {
              System.out.println(gcName + eqString + gcValue);
              entry.putLongValue("energy", (long) Double.parseDouble(gcValue));
            }
            else if ("power".equalsIgnoreCase(gcName)) {
              System.out.println(gcName + eqString + gcValue);
              entry.putLongValue("power", (long) Double.parseDouble(gcValue));
            } // end if
          } // end if
        } // end for loop
      } // end if
    } // end for loop
  } // end of processEnergyPower

} // end of class
