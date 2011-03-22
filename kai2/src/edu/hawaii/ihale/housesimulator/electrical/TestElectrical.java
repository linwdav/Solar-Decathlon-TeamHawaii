package edu.hawaii.ihale.housesimulator.electrical;

import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.housesimulator.SimulatorServer;
import edu.hawaii.ihale.housesimulator.aquaponics.AquaponicsData;
import edu.hawaii.ihale.housesimulator.hvac.HVACData;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsData;

/**
 * Tests the HTTP operations of the system.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class TestElectrical {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server.
   */
  @BeforeClass
  public static void startServer() throws Exception {
    SimulatorServer.runServer();
  }

  /**
   * Tests that we can GET a value from the system.
   * @throws Exception If GET fails
   */
  @Test
  public void testGet() throws Exception {

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
    String getUrl = "http://localhost:7002/electrical/state";
    ClientResource getClient = new ClientResource(getUrl);

    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList meterList = domDoc.getElementsByTagName("meter");
    NodeList energyList = domDoc.getElementsByTagName("energy");
    NodeList powerList = domDoc.getElementsByTagName("power");

    // Grabs attributes from tags.
    String title = ((Element) meterList.item(0)).getAttribute("title");

    // Grabs value from tags.
    String energy = ((Element) energyList.item(0)).getTextContent();
    String power = ((Element) powerList.item(0)).getTextContent();

    // Check that we are returning the correct title.
    assertEquals("Checking that title is \"Grid\"", title, "Grid");

    // Check that the returned value is within a delta of our value.
    assertEquals(1500.0, Double.parseDouble(energy), 1000);
    assertEquals(1500.0, Double.parseDouble(power), 1000);

  }
}