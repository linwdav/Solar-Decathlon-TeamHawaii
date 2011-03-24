package edu.hawaii.ihale.housesimulator.hvac;

import static org.junit.Assert.assertEquals;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.housesimulator.SimulatorServer;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class TestHVAC {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * 
   * @throws Exception If problems occur starting up the server.
   */
  @BeforeClass
  public static void startServer() throws Exception {
    SimulatorServer.runServer();
  }

  /**
   * Tests that are our GET and PUT operations are compliant with milestone 2's HTTP API 2.0.
   * 
   * @throws Exception If GET or PUT fails
   */
  @Test
  public void testGetAndPut() throws Exception {
    
    String putUrl = "http://localhost:7102/hvac/temperature";
    ClientResource putClient = new ClientResource(putUrl);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    String command = IHaleCommandType.SET_TEMPERATURE.toString();
    
    // Create root tag
    Element rootElement = doc.createElement("command");
    rootElement.setAttribute("name", command);
    doc.appendChild(rootElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("arg");
    temperatureElement.setAttribute("value", "24");
    rootElement.appendChild(temperatureElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    putClient.put(result);
    
    // Set up the GET client
    String getUrl = "http://localhost:7102/hvac/state";
    ClientResource getClient = new ClientResource(getUrl);

    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    String systemNameAttributeName = "system";
    String deviceNameAttributeName = "device";
    
    // Get the root element, in this case would be state-data element.
    Element stateData = domDoc.getDocumentElement();
    // Retrieve the attributes from state-data element, the system and device name.
    String systemName = stateData.getAttribute(systemNameAttributeName);
    String deviceName = stateData.getAttribute(deviceNameAttributeName);
    
    assertEquals("Checking for system name", IHaleSystem.HVAC.toString(), systemName);
    assertEquals("Checking for device name", "arduino-3", deviceName);
    
    // Retrieve a child node from the Document object. Represents state data.
    NodeList xmlList = domDoc.getElementsByTagName("state");

    // Retrieve an attributes key.
    String key = ((Element) xmlList.item(0)).getAttribute("key");
    String temperatureString = IHaleState.TEMPERATURE.toString();
    
    assertEquals("Checking that key is temp", key, temperatureString);
  }
}