package edu.hawaii.systemh.housesimulator.lighting;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.systemh.api.ApiDictionary.SystemHCommandType;
import edu.hawaii.systemh.api.ApiDictionary.SystemHState;
import edu.hawaii.systemh.housesimulator.SimulatorServer;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class TestLighting {

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
   * Tests that we can PUT a value and then GET the value and check that it is within a certain
   * delta of the PUT value.
   * 
   * @throws Exception If there is a problem with PUT or GET
   */
  
  @Test
  public void testGetAndPut() throws Exception {
    
    String levelString = "level";
    // Put the values to our system.
    putValue(7103, levelString, "70");
    putValue(7104, levelString, "80");
    putValue(7105, levelString, "90");
    putValue(7106, levelString, "100");
    
    String colorString = "color";
    putValue(7103, colorString, "#FF0000");
    putValue(7104, colorString, "#00FFFF");
    putValue(7105, colorString, "#0000FF");
    putValue(7106, colorString, "#0000A0");

    String enabledString = "enabled";
    putValue(7103, enabledString, "true");
    putValue(7104, enabledString, "false");
    putValue(7105, enabledString, "true");
    putValue(7106, enabledString, "false");

    // Check that the returned GET value matches out PUT value.
    assertEquals("Checking that living room level is 70", getValue(7103), "70");
    assertEquals("Checking that dining room level is 80", getValue(7104), "80");
    assertEquals("Checking that kitchen level is 90", getValue(7105), "90");
    assertEquals("Checking that bathroom level is 100", getValue(7106), "100");
  }

  /**
   * Helper function that GETs a value from the system given the room.
   * 
   * @param port the port of the room to retrieve the lighting level from
   * @throws IOException If there is a problem getting the document
   * @return the value of the lighting level in the specified room
   */
  public static String getValue(int port) throws IOException {
    // Set up the GET client
    String getUrl = "http://localhost:" + port + "/lighting/state";
    ClientResource getClient = new ClientResource(getUrl);

    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList xmlList = domDoc.getElementsByTagName("state");

    // Grabs attributes from tags.
    String key = ((Element) xmlList.item(0)).getAttribute("key");
    String value = ((Element) xmlList.item(0)).getAttribute("value");    
    
    // Check the key.
    assertEquals("Checking that key is level", key, SystemHState.LIGHTING_LEVEL.toString());

    // Return the level value for the specified room.
    return value;
  }

  /**
   * Helper function that PUTs a value to the system.
   * 
   * @param port The port of the lighting device associated with the room that we are performing a 
   *             PUT request to.
   * @param command The property of the Lighting system we want to affect, i.e., level, color, etc.
   * @param value The value we want the lighting property to be set to.
   * @throws ParserConfigurationException If there is a problem with the parser
   * @throws IOException If there is a problem building the document
   */
  public static void putValue(int port, String command, String value) 
    throws ParserConfigurationException, IOException {
    
    String putUrl = "http://localhost:" + port + "/lighting/" + command;
    ClientResource putClient = new ClientResource(putUrl);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();
    
    Element rootElement = doc.createElement("command");
    String name = "name";
    
    if ("level".equals(command)) {
      rootElement.setAttribute(name, SystemHCommandType.SET_LIGHTING_LEVEL.toString());
    }
    else if ("color".equals(command)) {
      rootElement.setAttribute(name, SystemHCommandType.SET_LIGHTING_COLOR.toString());
    }
    else if ("enabled".equals(command)) {
      rootElement.setAttribute(name, SystemHCommandType.SET_LIGHTING_ENABLED.toString());
    }

    doc.appendChild(rootElement);

    // Create state tag.
    Element argElement = doc.createElement("arg");
    argElement.setAttribute("value", value);
    rootElement.appendChild(argElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    putClient.put(result);
  }
}