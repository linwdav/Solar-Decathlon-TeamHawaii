package edu.hawaii.ihale.api.hsim;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import edu.hawaii.ihale.api.hsim.Arduino;

public class AquaponicsResource extends Arduino {
  @Get
  public String getContact() throws Exception {
    Calendar currentDate = Calendar.getInstance(Locale.US);
    return String.valueOf(currentDate.get(Calendar.MONTH));
  }
  
  /**
   * Adds the passed Contact to our internal database of Contacts.
   * @param representation The XML representation of the new Contact to add.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public Representation putContact(Representation representation) throws Exception {
    String key = (String)this.getRequestAttributes().get("key");
    System.out.println(key);
    /*
    // Get the XML representation of the Contact.
    DomRepresentation domRepresentation = new DomRepresentation(representation);
    // Convert the XML representation to the Java representation.
    Contact contact = new Contact(domRepresentation.getDocument());
    // Create a pattern to match a phone numer requiring numbers or numbers - numbers
    Pattern p = Pattern.compile("^\\d+(-\\d+)+");
    // Create a matcher with an input string
    Matcher m = p.matcher(contact.getPhoneNumber());
    
    // Exit early and error if the phone number is not of proper format
    if (!m.matches()) {
      // The requested contact was not found, so set the Status to indicate this.
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
      return null;
    }
    
    // Add the Contact to our repository.
    Contacts.getInstance().addContact(contact);
    // No need to return a representation to the client.
    */
    return null;
  }
}
