package edu.hawaii.ihale.api.hsim;
 
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Simulates the Arduino boards used by Team Hawaii's Solar Decathlon entry.
 * Supported operations: GET, PUT.
 * Supported representations: XML.
 * @author Team Maka
 */
public abstract class EGauge extends ServerResource {
  /** Name of this meter. */
  public String meterName;
  /** The random number generator.*/
  public static final MT mt = new MT(Calendar.MILLISECOND);
  Date date = new Date();
  /** Magic map that holds all the data.*/
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "MS_SHOULD_BE_FINAL", 
    justification = "data Map should definately not be final...")
  public static Map<String, Map<String,String>> data = 
    new ConcurrentHashMap<String, Map<String,String>>();
  /** The array of keys for use in the system.*/
  public String[] keys;
  /** A list of all the keys for use in the system. */
  public List<String> list; 
  /**
   * Initializes the object. 
   */
  public EGauge() {
    //Empty.
  }
  /**
   * Updates the buffer.
   */
  public abstract void poll();
  
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
    //create root measurements and child timestamp.
    Element rootElement = doc.createElement("measurements");
    Element timestamp = doc.createElement("timestamp");
    timestamp.appendChild(doc.createTextNode(String.valueOf(date.getTime())));
    rootElement.appendChild(timestamp);
 
    //loop through meters and append data. 
    // Create meter element
    Element meter = doc.createElement("meter");
    meter.setAttribute("title", meterName);
    //Create energy and power child elements
    for (String s : list) {
      Element e = doc.createElement(s);
      e.appendChild(doc.createTextNode(data.get(meterName).get(s)));
      meter.appendChild(e);
    } 
    rootElement.appendChild(meter);
    doc.appendChild(rootElement);
    result.setDocument(doc);
    return result;
  }
}