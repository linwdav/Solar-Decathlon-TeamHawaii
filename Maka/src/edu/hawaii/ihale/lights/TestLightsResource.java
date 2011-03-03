package edu.hawaii.ihale.lights;

import static org.junit.Assert.assertTrue; 
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
import org.junit.BeforeClass;
import org.junit.Test; 
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.housesimulator.HsimServer;
import edu.hawaii.ihale.housesimulator.MT;

/**
 * Tests the operations supported for the Hsim simulator.
 * This test may take up to 30 seconds.
 * @author TeamMaka
 */
public class TestLightsResource {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    HsimServer.main(null);
  }
  
  /**
   * Test if you can retrieve the key attatched to the end of the aquaponics URI.
   * @throws Exception If problems occur.
   */
  @Test
  public void testGetPut() throws Exception {
     
    //TEST PUT a random value in
    MT mt = new MT(Calendar.MILLISECOND);
    for ( int port = 8005; port <= 8008; port ++) {
      int testVal =  (int) mt.nextDouble(0,100);
      String tUrl = String.format("http://localhost:%s/lighting/%s", port,
      "level");
      ClientResource c2 = new ClientResource(tUrl);
      DomRepresentation rslt = new DomRepresentation();
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();
      Element rootElement = doc.createElement("state-data");
      Element e = doc.createElement("state");
      e.setAttribute("key"," ");
      e.setAttribute("value", String.valueOf(testVal));
      rootElement.appendChild(e);
      doc.appendChild(rootElement);
      rslt.setDocument(doc);
      c2.put(rslt);
      
      
      //wait for the refresh to occur...
      Thread.sleep(5000);
    
      
      //TEST GET
      String testUrl = String.format("http://localhost:%s/lighting/%s", port,
          "state");
      ClientResource client = new ClientResource(testUrl);  
      DomRepresentation r2 = new DomRepresentation(client.get()); 
      doc = r2.getDocument();
      String value = doc.getFirstChild().getFirstChild().
          getAttributes().getNamedItem("value").getNodeValue();
      Integer val = new Integer(value); 
      assertTrue("Test that light level value is correct.",
          (val == testVal));
    }
  }
}
