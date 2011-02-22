package edu.hawaii.ihale.backend.client;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;

/**
 * A simple example of a "client" class for the DateServer service. 
 * @author Philip Johnson
 */
public class BackendApplication {
  /**
   * Connect to a running ContactService and perform the requested operation. 
   * @param args The first arg is the host (such as "http://localhost:8112/").
   * The second arg is the operation, either "put", "get", or "delete". 
   * The third arg is the unique ID of the contact.
   * If the operation is a "put", then there must be three more arguments for 
   * first name, last name, and info. 
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    
    String url = "http://localhost";
    ClientResource client = new ClientResource(url);
    
    // Interval to check device readings (in milliseconds)
    int interval = 300000;
    
    while (true) {
    	// Loop through entire list of devices every interval
        // store state in database
    	DomRepresentation representation = new DomRepresentation(client.get());
    	
    	// Filler method call to eliminate warnings
    	representation.getSize();
    	
    	Thread.sleep(interval);
    }

    // (2) Now do the right thing depending on the operation.
    /*String operation = args[1];
    try {
      if ("get".equals(operation)) {
        DomRepresentation representation = new DomRepresentation(client.get());
        Contact contact = new Contact(representation.getDocument());
        System.out.println(contact.toString());
      }

      else if ("put".equals(operation)) {
        Contact contact = new Contact(args[3], args[4], args[5], uniqueID);
        DomRepresentation representation = new DomRepresentation();
        representation.setDocument(contact.toXml());
        client.put(representation);
      }
    }
    catch (ResourceException e) {
      // If the operation didn't succeed, indicate why here.
      System.out.println("Error: " + e.getStatus());
    }*/
  }
  
  /**
   * Reads in the properties file that contains the mapping of addresses for each
   * device.s
   */
  void readPropertiesFile() {
	  
  }
  
}

