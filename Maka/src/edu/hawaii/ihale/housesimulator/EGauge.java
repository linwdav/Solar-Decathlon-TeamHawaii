package edu.hawaii.ihale.housesimulator;
 
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
import edu.hawaii.ihale.photovoltaics.PhotovoltaicRepository;


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
  protected Date date = new Date();
  /** Magic map that holds all the data.*/
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
    Element cpower = doc.createElement("cpower");
    cpower.appendChild(doc.createTextNode(
        String.valueOf(mt.nextDouble(-100,100))));
    cpower.setAttribute("src", meterName);
    cpower.setAttribute("i", String.valueOf((int) mt.nextDouble(1.0,10.0)));
    cpower.setAttribute("u", String.valueOf((int) mt.nextDouble(1.0,10.0)));
    rootElement.appendChild(cpower);
    //loop through meters and append data. 
    // Create meter element
    Element meter = doc.createElement("meter");
    meter.setAttribute("title", meterName);
    //Create energy and power child elements
    /*
    for (String s : list) {
      Element e = doc.createElement(s);
      e.appendChild(doc.createTextNode(data.get(meterName).get(s)));
      meter.appendChild(e);
    } 
    */
    //PhotovoltaicRepository repository = PhotovoltaicRepository.getInstance();
    Element energyElement = doc.createElement("energy");
    energyElement.appendChild(doc.createTextNode(PhotovoltaicRepository.getEnergy()));
    meter.appendChild(energyElement);
    
    Element powerElement = doc.createElement("power");
    powerElement.appendChild(doc.createTextNode(PhotovoltaicRepository.getPower()));
    meter.appendChild(powerElement);
    
    Element wattsecondsElement = doc.createElement("energyWs");
    wattsecondsElement.appendChild(doc.createTextNode(PhotovoltaicRepository.getJoules()));
    meter.appendChild(wattsecondsElement);
    
    rootElement.appendChild(meter);
    //<frequency>59.98</frequency>
    //<voltage>119.0</voltage>
    //<voltage>118.3</voltage>
    //<current>5.495</current>
    Element frequency = doc.createElement("frequency");
    frequency.appendChild(doc.createTextNode(
        String.valueOf(mt.nextDouble(0,120))));
    rootElement.appendChild(frequency);
    
    Element voltage = doc.createElement("voltage");
    voltage.appendChild(doc.createTextNode(
        String.valueOf(mt.nextDouble(0,120))));
    rootElement.appendChild(voltage);
    
    Element current = doc.createElement("current");
    current.appendChild(doc.createTextNode(
        String.valueOf(mt.nextDouble(0,100))));
    rootElement.appendChild(current);
    
    doc.appendChild(rootElement);
    result.setDocument(doc);
    return result;
  }
}