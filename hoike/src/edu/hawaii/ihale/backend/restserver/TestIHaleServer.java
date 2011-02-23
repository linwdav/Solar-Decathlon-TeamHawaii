package edu.hawaii.ihale.backend.restserver;

/*
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
*/

import org.junit.Test;

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
    
    /*
    
    try {
      // Create the Document instance representing this XML.
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();

      // Create and attach the root element <state-data>.
      Element rootElement = doc.createElement("state-data");
      doc.appendChild(rootElement);
      
      Attr attribute = doc.createAttribute("system");
      attribute.setValue(systemNameAquaponics);
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute("device");
      attribute.setValue("Arduino-1");
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute("timestamp");
      attribute.setValue("1297446335");
      rootElement.setAttributeNode(attribute);
      
      // Attach the first state element to state-data parent
      Element stateElement1 = doc.createElement("state");
      rootElement.appendChild(stateElement1);
      attribute = doc.createAttribute("key");
      attribute.setValue("Temp");
      stateElement1.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("25");
      stateElement1.setAttributeNode(attribute);
      
      // Attach the second state element to state-data parent
      Element stateElement2 = doc.createElement("state");
      rootElement.appendChild(stateElement2);
      attribute = doc.createAttribute("key");
      attribute.setValue("Oxygen");
      stateElement2.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("100");
      stateElement2.setAttributeNode(attribute);
      
      CustomSystemStateEntry entryViaXML = new CustomSystemStateEntry(doc);
      System.out.println("THIS IS BY XML:\n" + entryViaXML);
      
      IHaleDAO dao = new IHaleDAO();
      dao.putEntry(entryViaXML);
      CustomSystemStateEntry testEntry = 
        (CustomSystemStateEntry) dao.getEntry(systemNameAquaponics, "Arduino-1", 
            Long.parseLong("1297446335"));
      
      System.out.println("From XML TO DB AND BACK:\n" + testEntry);
      System.out.println("\n\n\n\n\n");
    }
    catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */
  }
}
