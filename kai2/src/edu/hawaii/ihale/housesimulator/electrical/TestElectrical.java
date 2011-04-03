package edu.hawaii.ihale.housesimulator.electrical;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
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

    // Hourly average is a static value, and can range from -50 to +50 of any average.
    long hourlyAverage = ElectricalData.getHourlyAverage(Calendar.HOUR_OF_DAY);
    long hourlyRange = 50;
    // Power ranges from -25 to 25 and is centered around 0.
    long powerValue = 0;
    long powerRange = 25;
    
    ElectricalData.modifySystemState();

    // Set up the GET client
    //String getUrl = "http://localhost:7002/electric/state";
    String getUrl = "http://localhost:7002/cgi-bin/egauge?tot";
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
    assertEquals(hourlyAverage, Double.parseDouble(energy), hourlyRange);
    assertEquals(powerValue, Double.parseDouble(power), powerRange);

  }
}