package edu.hawaii.ihale.lights;
import static org.junit.Assert.assertNotNull; 
import org.junit.BeforeClass;
import org.junit.Test; 
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource; 
import edu.hawaii.ihale.housesimulator.HsimServer; 

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
    String[] args = {"-stepinterval","5000"};
    HsimServer.main(args);
  }
  
  /**
   * Test if you can retrieve the key attatched to the end of the aquaponics URI.
   * @throws Exception If problems occur.
   */
  @Test
  public void testGetPut() throws Exception {
   //check it works
    for ( int port = 8005; port <= 8008; port ++) {    
      String testUrl = String.format("http://localhost:%s/lighting/%s", port,
        "state");
    System.out.println("TEST GET\n" + testUrl);
    ClientResource client = new ClientResource(testUrl);  
    DomRepresentation r2 = new DomRepresentation(client.get());
    // make sure we get something
    assertNotNull("Get works",r2); }
    
  }
}
