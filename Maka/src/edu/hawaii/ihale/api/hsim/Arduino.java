package edu.hawaii.ihale.api.hsim;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import edu.hawaii.contactservice.common.contact.Contact;
import edu.hawaii.contactservice.server.db.Contacts;

/**
 * Simulates the Arduino boards used by Team Hawaii's Solar Decathlon entry.
 * Supported operations: GET, PUT.
 * Supported representations: XML.
 * @author Team Maka
 */
public class Arduino extends ServerResource {

  public static Map<String, String> buffer = new ConcurrentHashMap<String, String>();
  public String[] keys;
  
  /**
   * Updates the buffer.
   */
  public void getState() {
    //  Get's overridden.
    //  Should throw an error if not implemented.
  }
  public void set(String key, String value) {
    //  Implement 
  }
  
  /**
   * Returns the Contact instance requested by the URL. 
   * @return The XML representation of the contact, or CLIENT_ERROR_NOT_ACCEPTABLE if the 
   * unique ID is not present.
   * @throws Exception If problems occur making the representation. Shouldn't occur in 
   * practice but if it does, Restlet will set the Status code. 
   */
  @Get
  public Representation getResource() throws Exception {
    // Create an empty XML representation.
    DomRepresentation result = new DomRepresentation();
    // Get the contact's uniqueID from the URL.
    String uniqueID = (String)this.getRequestAttributes().get("uniqueID");
    // Look for it in the Contacts database.
    Contact contact = ContactDAO.getContact(uniqueID);
    if (contact == null) {
      // The requested contact was not found, so set the Status to indicate this.
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    } 
    else {
      // The requested contact was found, so add the Contact's XML representation to the response.
      result.setDocument(contact.toXml());
      // Status code defaults to 200 if we don't set it.
      }
    // Return the representation.  The Status code tells the client if the representation is valid.
    return result;
  }
  
  /**
   * Adds the passed Contact to our internal database of Contacts.
   * @param representation The XML representation of the new Contact to add.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putContact(Representation representation) throws Exception {
    // Get the XML representation of the Contact.
    DomRepresentation domRepresentation = new DomRepresentation(representation);
    // Convert the XML representation to the Java representation.
    Contact contact = new Contact(domRepresentation.getDocument());
    // Add the Contact to our repository.
    Contacts.getInstance().addContact(contact);
    // No need to return a representation to the client.
    return null;
  }
}
