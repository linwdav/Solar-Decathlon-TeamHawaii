package edu.hawaii.ihale.hvac;

import static org.junit.Assert.assertTrue; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
import org.junit.BeforeClass;
import org.junit.Test; 
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.housesimulator.HsimServer;

/**
 * Tests the operations supported for the Hsim simulator.
 * @author TeamMaka
 */
public class TestHVACResource {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    String[] args = {"-stepinterval","5000"};
    HsimServer.main(args);
  }
  
  /**
   * Test to see that the put and get functions work correctly.
   * @throws Exception If problems occur.
   */
  @Test
  public void testGetPut() throws Exception {

    //TEST PUT 100 in for temp
    String tUrl = String.format("http://localhost:%s/hvac/%s", 8002,
    "temp");
    ClientResource c2 = new ClientResource(tUrl);
    DomRepresentation rslt = new DomRepresentation();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    Element rootElement = doc.createElement("state-data");
    Element e = doc.createElement("state");
    e.setAttribute("key"," ");
    e.setAttribute("value", "100");
    rootElement.appendChild(e);
    doc.appendChild(rootElement);
    rslt.setDocument(doc);
    c2.put(rslt);
    
    
    //wait for the refresh to occur...
    Thread.sleep(5000);
  
    
    //TEST GET
    String testUrl = String.format("http://localhost:%s/hvac/%s", 8002,
        "state");
    ClientResource client = new ClientResource(testUrl);  
    DomRepresentation r2 = new DomRepresentation(client.get()); 
    doc = r2.getDocument();
    String value = doc.getFirstChild().getFirstChild().
        getAttributes().getNamedItem("value").getNodeValue();
    Double val = new Double(value); 
    assertTrue("temp is within an acceptable variance ",
        (val - 79.4 <= (100 - 79.4) / 100.0 + .05) && (val - 79.4 >= (100 - 79.4) / 100.0 - .05));
  }
 
}