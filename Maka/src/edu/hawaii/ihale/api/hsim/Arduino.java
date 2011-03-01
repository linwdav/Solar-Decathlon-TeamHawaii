package edu.hawaii.ihale.api.hsim;
 
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Simulates the Arduino boards used by Team Hawaii's Solar Decathlon entry.
 * Supported operations: GET, PUT.
 * Supported representations: XML.
 * @author Team Maka
 */
public abstract class Arduino extends ServerResource {
  public String systemName, deviceName, room;
  /** The random number generator.*/
  public static final MT mt = new MT(Calendar.MILLISECOND);
  Date date = new Date();
  /** Magic map that holds all the data.*/
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "MS_SHOULD_BE_FINAL", 
    justification = "data Map should definately not be final...")
  //public static Map<String, String> data = new ConcurrentHashMap<String,String>();
  public static Map<String, Map<String,String>> data2 = new ConcurrentHashMap<String, Map<String,String>>();
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
   * Returns the Contact instance requested by the URL. 
   * @return The XML representation of the contact, or CLIENT_ERROR_NOT_ACCEPTABLE if the 
   * unique ID is not present.
   * @throws Exception If problems occur making the representation. Shouldn't occur in 
   * practice but if it does, Restlet will set the Status code. 
   */
  @Get
  public Representation getResource() throws Exception {
    //refresh values
    // Create an empty XML representation.
    DomRepresentation result = new DomRepresentation();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    //create root element
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", systemName);
    rootElement.setAttribute("device", deviceName);
    rootElement.setAttribute("timestamp", String.valueOf(date.getTime()));
    
    //System.out.println("string: temp");
    //System.out.println("map: " + data2.get("aquaponics").get("temp"));
    //refresh data
   // poll();
    //loop through states and attach
    //System.out.println(Arrays.toString(list.toArray()));
    for (String s : list) {
      Element e = doc.createElement("state");
      e.setAttribute("key", s);
      //System.out.println("string: " + s);
      //System.out.println("map: " + data2.get("aquaponics").get(s));
      if (systemName.equalsIgnoreCase("lighting")) {
        e.setAttribute("value", data2.get(room).get(s));
      }
      else {
        e.setAttribute("value", data2.get(systemName).get(s));
      }
      rootElement.appendChild(e);
    }
    
    doc.appendChild(rootElement);
    result.setDocument(doc);
    return result;
  }
  
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