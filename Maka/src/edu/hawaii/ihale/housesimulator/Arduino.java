package edu.hawaii.ihale.housesimulator;
 
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;


/**
 * Simulates the Arduino boards used by Team Hawaii's Solar Decathlon entry.
 * Supported operations: GET, PUT.
 * Supported representations: XML.
 * @author Team Maka
 */
public abstract class Arduino extends ServerResource {
  /** Local Stings for future implementation and local use. */
  public String systemName, deviceName, room;
  /** The random number generator.*/
  public static final MT mt = new MT(Calendar.MILLISECOND);
  protected Date date = new Date();
  /** The array of keys for use in the system.*/
  public String[] keys;
  /** A list of all the keys for use in the system. */
  public List<String> list; 
  /**
   * Initializes the object.
   * @param systemName  SubSystem name.
   * @param deviceName  Arduino device name.
   */
  public Arduino(String systemName, String deviceName) {
    this.systemName = systemName;
    this.deviceName = deviceName; 
  }

  /**
   * Updates information.
   */
  public abstract void poll();
  
  /**
   * Updates a child's local variable.
   * @param key the item's key.
   * @param value the item's value.
   */
  public abstract void set(String key, String value);
  
  /**
   * Adds the passed Contact to our internal database of Contacts.
   * @param representation The XML representation of the new Contact to add.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public void putVal(Representation representation) throws Exception {
    // Get the XML representation of the Contact.
    String key = (String)this.getRequestAttributes().get("key");
    if (!list.contains(key)) {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    DomRepresentation domRepresentation = new DomRepresentation(representation);
    Document doc = domRepresentation.getDocument();
    String value = doc.getFirstChild().getFirstChild().
        getAttributes().getNamedItem("value").getNodeValue();
    //calls overridden set method to set local variables in children.
    set(key,value);
  }
}