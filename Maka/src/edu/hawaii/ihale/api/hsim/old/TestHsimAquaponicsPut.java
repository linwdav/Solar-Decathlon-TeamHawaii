package edu.hawaii.ihale.api.hsim;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 * Tests the operations supported for the ContactService Contact resource.
 * @author Philip Johnson
 */
public class TestHsimAquaponicsPut {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    HsimServer.main(null);
  }
  
  /**
   * Test if you can retrieve the key attatched to the end of the aquaponics URI
   * @throws Exception If problems occur.
   */
  @Test
  public void testAddContact() throws Exception {
    // Construct the URL to test.
    //String uniqueID = "DS";
    String testUrl = String.format("http://localhost:%s/aquaponics/%s", 8011,
        "state");
    System.out.println(testUrl);
    ClientResource client = new ClientResource(testUrl);
    
    // Construct the payload: an XML representation of a Contact.
    //Contact contact = new Contact("Dan", "Suthers", "Professor", uniqueID, "808-808");
    //DomRepresentation representation = new DomRepresentation();
    //representation.setDocument(contact.toXml());
    
    // Now put the Contact to the server. 
    client.put(null);
    
    // Let's now try to retrieve the Contact instance we just put on the server. 
    //DomRepresentation representation2 = new DomRepresentation(client.get());
    //Contact contact2 = new Contact(representation2.getDocument());
    //assertEquals("Checking retrieved contact's ID", uniqueID, contact2.getUniqueID());
    
    // Now let's get rid of the sucker.
    //client.delete();
    
    // Make sure it's really gone.
    
    /*
    try {
      client.get();
      throw new Exception("Eek! We got a deleted Contact!");
    }
    catch (Exception e) { //NOPMD
      // It's all G. 
    }
    */
  }
  /**
   * Test the cycle of putting a new Contact on the server, retrieving it, then deleting it.
   * @throws Exception If problems occur.
   */
  /*
  @Test(expected = ResourceException.class)
  public void testAddBadContact() throws Exception {
    // Construct the URL to test.
    String uniqueID = "DS";
    String testUrl = String.format("http://localhost:%s/contactserver/contact/%s", testPort,
        uniqueID);
    ClientResource client = new ClientResource(testUrl);
    
    // Construct the payload: an XML representation of a Contact.
    // Inserting illegal phone number "absc".
    Contact contact = new Contact("Dan", "Suthers", "Professor", uniqueID, "absc");
    DomRepresentation representation = new DomRepresentation();
    representation.setDocument(contact.toXml());
    
    // Now put the Contact to the server. 
    client.put(representation);
  }
  */
}
