package edu.hawaii.ihale.backend.rest;

import static org.junit.Assert.assertEquals;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Unit test of the IHale HTTP server's processing ability.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TestIHaleServer {
  
  /**
   * Test XML parsing and creating an instance of IHaleSystemStateEntryRepresentation.
   */
  @Test
  public void testXML() {
        
    try {
      
      /** The device name attribute name. */
      String deviceNameAttributeName = "device";
      /** The timestamp attribute name. */
      String timestampAttributeName = "timestamp";
      /** The state element name. */
      String stateElementName = "state";
      /** The key attribute name */
      String keyAttributeName = "key";
      /** The value attribute name */
      String valueAttributeName = "value";
      
      // Create the Document instance representing this XML.
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();

      // Create and attach the root element <state-data>.
      Element rootElement = doc.createElement("state-data");
      doc.appendChild(rootElement);
      
      Attr attribute = doc.createAttribute("system");
      attribute.setValue("aquaponics");
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute(deviceNameAttributeName);
      attribute.setValue("arduino-1");
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute(timestampAttributeName);
      attribute.setValue("1297446335");
      rootElement.setAttributeNode(attribute);
      
      // Attach the first state element (temp field) to state-data parent
      Element stateElement1 = doc.createElement(stateElementName);
      rootElement.appendChild(stateElement1);
      attribute = doc.createAttribute(keyAttributeName);
      attribute.setValue("temp");
      stateElement1.setAttributeNode(attribute);
      attribute = doc.createAttribute(valueAttributeName);
      attribute.setValue("25");
      stateElement1.setAttributeNode(attribute);
      
      // Attach the second state element (oxygen field) to state-data parent
      Element stateElement2 = doc.createElement(stateElementName);
      rootElement.appendChild(stateElement2);
      attribute = doc.createAttribute("key");
      attribute.setValue("oxygen");
      stateElement2.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("100");
      stateElement2.setAttributeNode(attribute);
      
      // Attach the third state element (pH field) to state-data parent.
      Element stateElement3 = doc.createElement(stateElementName);
      rootElement.appendChild(stateElement3);
      attribute = doc.createAttribute("key");
      attribute.setValue("pH");
      stateElement3.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("7");
      stateElement3.setAttributeNode(attribute);
      
      IHaleDAO dao = new IHaleDAO();
      SystemStateEntry xmlEntry = dao.xmlToSystemStateEntry(doc);
      dao.putEntry(xmlEntry);
      
      SystemStateEntry returnedEntry = dao.getEntry("aquaponics", "arduino-1", 1297446335);
      
      assertEquals("Checking for storing an entry created from a XML document and retrieving" +
          "it from the database repository.", xmlEntry.toString(), returnedEntry.toString());

    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Test the timed interval thread that cycles through and sends GET HTTP requests to
   * the system devices defined in the properties file. Handles the responses by storing
   * the entries in the database repository.
   */
  @Test
  public void testGetThread() {
    IHaleServer serverThread = new IHaleServer(10000);
    try {
      Thread controlThread = new Thread(serverThread);
      controlThread.start();
      Thread.sleep(15000);
      serverThread.done();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
