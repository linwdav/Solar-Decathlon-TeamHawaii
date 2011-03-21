package edu.hawaii.ihale.housesimulator.hvac;

import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.housesimulator.SimulatorServer;
import edu.hawaii.ihale.housesimulator.aquaponics.AquaponicsData;
import edu.hawaii.ihale.housesimulator.electrical.ElectricalData;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsData;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey
 * @author Michael Cera
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
   * Tests that we can PUT a value and then GET the value and check that it is within a certain
   * delta of the PUT value.
   * 
   * @throws Exception If GET or PUT fails
   */
  @Test
  public void testGetAndPut() throws Exception {
    String putUrl = "http://localhost:7102/hvac/temp";
    ClientResource putClient = new ClientResource(putUrl);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("command");
    rootElement.setAttribute("name", "setTemp");
    doc.appendChild(rootElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("arg");
    temperatureElement.setAttribute("value", "80");
    rootElement.appendChild(temperatureElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    putClient.put(result);

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
    String getUrl = "http://localhost:7102/hvac/state";
    ClientResource getClient = new ClientResource(getUrl);

    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList xmlList = domDoc.getElementsByTagName("state");

    // Grabs attributes from tags.
    String key = ((Element) xmlList.item(0)).getAttribute("key");
    String value = ((Element) xmlList.item(0)).getAttribute("value");

    // Check that we are returning the correct key
    assertEquals("Checking that key is temp", key, "temp");

    // Check that the returned value is within a delta of our PUT value.
    assertEquals(80.0, Double.parseDouble(value), 3);

  }
}