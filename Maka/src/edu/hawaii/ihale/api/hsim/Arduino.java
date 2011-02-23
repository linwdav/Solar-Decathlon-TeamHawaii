package edu.hawaii.ihale.api.hsim;

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
public class Arduino extends ServerResource {
  String systemName, deviceName;
  Date date = new Date();
  public static Map<String, String> data = new ConcurrentHashMap<String, String>();
  public String[] keys;
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
   * Updates the buffer.
   */
  public void poll() {
    //  Get's overridden.
  }
  
  /**
   * Adds an item to the map.
   * @param key the item's key.
   * @param value the item's value.
   */
  public void set(String key, String value) { 
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
    //refresh values
    // Create an empty XML representation.
    DomRepresentation result = new DomRepresentation();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    //create root element
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", systemName);
    rootElement.setAttribute("deviceName", deviceName);
    rootElement.setAttribute("timeStamp", ""+date.getTime());
    //refresh data
    poll();
    //loop through states and attach
    for (String s : list) {
      Element e = doc.createElement("state");
      e.setAttribute(s.substring(2),data.get(s));
      rootElement.appendChild(e);
    }
    doc.appendChild(rootElement);
    result.setDocument(doc);
    return  result;
  }
  
  /**
   * Adds the passed Contact to our internal database of Contacts.
   * @param representation The XML representation of the new Contact to add.
   * @return null.
   * @throws Exception If problems occur unpacking the representation.
   */
  @Put
  public void putVal(Representation representation) throws Exception {
    // Get the XML representation of the Contact.
    String key = (String)this.getRequestAttributes().get("key");
    if(!list.contains(key)) {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    DomRepresentation domRepresentation = new DomRepresentation(representation);
    Document doc = domRepresentation.getDocument();
    String value = doc.getFirstChild().getFirstChild().getAttributes().getNamedItem("value").getNodeValue();
    //calls overridden set method to set local variables in children.
    set(key,value);
    data.put(key, value);
  }
}
