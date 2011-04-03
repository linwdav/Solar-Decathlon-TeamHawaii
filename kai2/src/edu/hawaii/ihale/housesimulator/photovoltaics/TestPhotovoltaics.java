package edu.hawaii.ihale.housesimulator.photovoltaics;

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
   
    
    // Data validation block
    long hourlyAverage = PhotovoltaicsData.getHourlyAverage(Calendar.HOUR_OF_DAY);
    long hourlyRange = hourlyAverage / 10;
    // Check that the returned value is within a delta of our value.
    assertEquals(hourlyAverage, Double.parseDouble(energy), hourlyRange); 
    assertEquals(0, Double.parseDouble(power), (hourlyRange * 3 / 4)); 
    
//    // Repeat the data validation block with different values for coverage testing.
//    hourlyAverage = PhotovoltaicsData.getHourlyAverage(Calendar.HOUR_OF_DAY + 6);
//    hourlyRange = hourlyAverage / 10;
//    // Check that the returned value is within a delta of our value.
//    assertEquals(hourlyAverage, Double.parseDouble(energy), hourlyRange); 
//    assertEquals(0, Double.parseDouble(power), (hourlyRange * 3 / 4)); 
//    
//    hourlyAverage = PhotovoltaicsData.getHourlyAverage(Calendar.HOUR_OF_DAY + 12);
//    hourlyRange = hourlyAverage / 10;
//    // Check that the returned value is within a delta of our value.
//    assertEquals(hourlyAverage, Double.parseDouble(energy), hourlyRange); 
//    assertEquals(0, Double.parseDouble(power), (hourlyRange * 3 / 4)); 
//    
//    hourlyAverage = PhotovoltaicsData.getHourlyAverage(Calendar.HOUR_OF_DAY + 18);
//    hourlyRange = hourlyAverage / 10;
//    // Check that the returned value is within a delta of our value.
//    assertEquals(hourlyAverage, Double.parseDouble(energy), hourlyRange); 
//    assertEquals(0, Double.parseDouble(power), (hourlyRange * 3 / 4)); 
  
  }
}