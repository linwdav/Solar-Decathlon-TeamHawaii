package edu.hawaii.ihale.housesimulator.photovoltaics;

import static org.junit.Assert.assertEquals;

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
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class TestPhotovoltaics {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    SimulatorServer.runServer();
  }
  
  /**
   * Tests that we can GET a value from the system.
   * @throws Exception if GET fails
   */
  @Test
  public void testGet() throws Exception {

      PhotovoltaicsData.modifySystemState();

    
    // Set up the GET client
    //String getUrl = "http://localhost:7001/photovoltaic/state";
    String getUrl = "http://localhost:7001/cgi-bin/egauge?tot";
    ClientResource getClient = new ClientResource(getUrl);
    
    // Get the XML representation.
    DomRepresentation domRep = new DomRepresentation(getClient.get());
    Document domDoc = domRep.getDocument();

    // Grabs tags from XML.
    NodeList meterList = domDoc.getElementsByTagName("meter");
    NodeList energyList = domDoc.getElementsByTagName("energy");
    NodeList powerList = domDoc.getElementsByTagName("power");

    // Grabs attributes from tags.
    String title = ((Element) meterList.item(1)).getAttribute("title");

    // Grabs value from tags.
    String energy = ((Element) energyList.item(1)).getTextContent();
    String power = ((Element) powerList.item(1)).getTextContent();

    // Check that we are returning the correct title.
    assertEquals("Checking that title is \"Solar\"", title, "Solar");
    
    // Check that the returned value is within a delta of our value.
    assertEquals(1500, Double.parseDouble(energy), 1750); 
    assertEquals(700, Double.parseDouble(power), 700); 

  }
}