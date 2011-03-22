package edu.hawaii.ihale.housesimulator.aquaponics;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
import edu.hawaii.ihale.housesimulator.electrical.ElectricalData;
import edu.hawaii.ihale.housesimulator.hvac.HVACData;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsData;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class TestAquaponics {

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
   * @throws Exception If GET or PUT fails
   */
  @Test
  public void testGetAndPut() throws Exception {
    // Put the values to our system.
    putValue("temp", "setTemp", "70");
    putValue("oxygen", "setOxygen", "5.5");
    putValue("ph", "setPh", "7.4");

    // Speed up time simulation to see if our value falls within the desired range.
    for (int i = 0; i < 50; i++) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
      Date date = new Date();
      System.out.println("**********************");
      System.out.println(dateFormat.format(date));
      AquaponicsData.modifySystemState();
      HVACData.modifySystemState();
      LightingData.modifySystemState();
      PhotovoltaicsData.modifySystemState();
      ElectricalData.modifySystemState();
    }

    // Set up the GET client
    String getUrl = "http://localhost:7101/aquaponics/state";
    ClientResource getClient = new ClientResource(getUrl);

    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList xmlList = domDoc.getElementsByTagName("state");

    // Grabs attributes from tags.
    String keyStr = "key"; // PMD WHY ARE YOU SO PICKY? :(
    String valStr = "value";
    String tempKey = ((Element) xmlList.item(0)).getAttribute(keyStr);
    String tempValue = ((Element) xmlList.item(0)).getAttribute(valStr);
    String oxygenKey = ((Element) xmlList.item(1)).getAttribute(keyStr);
    String oxygenValue = ((Element) xmlList.item(1)).getAttribute(valStr);
    String phKey = ((Element) xmlList.item(2)).getAttribute(keyStr);
    String phValue = ((Element) xmlList.item(2)).getAttribute(valStr);

    // Check that we are returning the correct key
    assertEquals("Checking that key is temp", tempKey, "temp");
    assertEquals("Checking that key is oxygen", oxygenKey, "oxygen");
    assertEquals("Checking that key is pH", phKey, "ph");

    // Check that the returned value is within a delta of our PUT value.
    assertEquals(70.0, Double.parseDouble(tempValue), 3);
    assertEquals(5.5, Double.parseDouble(oxygenValue), 0.5);
    assertEquals(7.4, Double.parseDouble(phValue), 0.7);

  }

  /**
   * Helper function that puts a value to the system.
   * 
   * @param uri The ending of the uri to call PUT on
   * @param command The name of the command being performed
   * @param value The value we want the to be set
   * @throws ParserConfigurationException If parser fails
   * @throws IOException If there is a problem building the document
   */
  public static void putValue(String uri, String command, String value)
      throws ParserConfigurationException, IOException {
    String putUrl = "http://localhost:7101/aquaponics/" + uri;
    ClientResource putClient = new ClientResource(putUrl);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("command");
    rootElement.setAttribute("name", command);
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