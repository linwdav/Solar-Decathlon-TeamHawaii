package edu.hawaii.ihale.housesimulator.lighting;

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
import edu.hawaii.ihale.housesimulator.SimulatorServer;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey
 * @author Michael Cera
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
    // Put the values to our system.
    putValue(7103, "70");
    putValue(7104, "80");
    putValue(7105, "90");
    putValue(7106, "100");

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
    assertEquals("Checking that key is level", key, "level");

    // Return the level value for the specified room.
    return value;
  }

  /**
   * Helper function that PUTs a value to the system.
   * 
   * @param port the port of the room that we are doing a PUT to
   * @param value The value we want the to be set
   * @throws ParserConfigurationException If there is a problem with the parser
   * @throws IOException If there is a problem building the document
   */
  public static void putValue(int port, String value) throws ParserConfigurationException,
      IOException {
    String putUrl = "http://localhost:" + port + "/lighting/level";
    ClientResource putClient = new ClientResource(putUrl);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("command");
    rootElement.setAttribute("name", "setLevel");
    doc.appendChild(rootElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("arg");
    temperatureElement.setAttribute("value", value);
    rootElement.appendChild(temperatureElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    putClient.put(result);
  }
}