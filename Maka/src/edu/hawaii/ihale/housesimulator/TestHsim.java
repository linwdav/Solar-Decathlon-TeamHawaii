package edu.hawaii.ihale.housesimulator;

import static org.junit.Assert.assertNotNull; 
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource; 

/**
 * Tests the operations supported for the Hsim simulator.
 * @author TeamMaka
 */
public class TestHsim {

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
  public void testGets() throws Exception {
    String lighting = "lighting";
     String[] systemNames = {"aquaponics", "hvac", "photovoltaics", "electrical"
         , lighting, lighting, lighting, lighting};
     
    for (int i = 0; i < 8; i++) {
    //TEST GET
    String testUrl = String.format("http://localhost:%s/%s/state", 8001 + i,
    systemNames[i]); 
    ClientResource client = new ClientResource(testUrl);
    DomRepresentation rep = new DomRepresentation(client.get());
    // make sure we get something
    assertNotNull("Get works",rep);
    }
  }
}
