package edu.hawaii.systemh.model.behavior; 

import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

/**
 * A Class that provides meteorological data from Weather Underground 
 * http://www.wunderground.com/.
 * @author Greg Burgess 
 */
public class WeatherReport { 
  private static XPathFactory factory = XPathFactory.newInstance();
  private static XPath xpath = factory.newXPath(); 
  private static ClientResource weatherClient = null; 
  private static ClientResource astrologicalClient = null;
  private static final String weatherUrl = 
    "http://api.wunderground.com/auto/wui/geo/WXCurrentObXML/index.xml?query=";
  private static final String astrologicalUrl =
    "http://api.wunderground.com/auto/wui/geo/ForecastXML/index.xml?query=";
  
  private Map<String,Double> conditionMap;

  double cloudCover;
  double fTemp;
  double sunRise = -6;
  double sunSet = 6;
  Long timestamp; 
  
  /**
   * 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   * @throws Exception - Generic type Exception for XPath parsing and Restlet.
   */
  public WeatherReport(String location, String station) throws Exception {
    //initalize the object
    init(location, station);
    //poll the first set of data
    refresh();
  }
  
  /**
   * Initalizes the object. 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   */
  private final void init(String location, String station) {
    weatherClient = new ClientResource(weatherUrl + location);
    astrologicalClient = new ClientResource(astrologicalUrl + station);
    //init the condition map
    conditionMap = new HashMap<String,Double>();
    conditionMap.put("Clear", (double) 1.0);
    conditionMap.put("Cloudy", (double) .25);
    conditionMap.put("Mostly Cloudy", (double) .50);
    conditionMap.put("Partly Sunny", (double) .50);
    conditionMap.put("Mostly Sunny", (double) .90);
    conditionMap.put("Partly Cloudy", (double) .80);
    conditionMap.put("Scattered Clouds", (double) .80);
    conditionMap.put("Overcast", (double) .55);
  }

  /**
   * Refreshes the data and parses it.
   * @throws Exception Generic Exception for XPath and Restlet.
   */
  public final void refresh() throws Exception {
    //do a get
    Representation weatherRep = weatherClient.get();
    Representation astroRep = astrologicalClient.get();
    
    // parse the weather document for local time, temperature, and cloud cover.
    DomRepresentation dom = new DomRepresentation(weatherRep);
    Document doc = dom.getDocument();
    String weather;
    String root = "//current_observation/";
    
    Double time = (Double) xpath.evaluate(root + "local_epoch",doc,XPathConstants.NUMBER); 
    fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
    weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING); 
    cloudCover = conditionMap.get(weather);
    timestamp = time.longValue();
    
    // parse the astrological client for sunrise and sun set times.
    dom = new DomRepresentation(astroRep);
    doc = dom.getDocument();
    root = "//forecast/moon_phase/";
    
    //pasre sunrise
    double hour = (Double) xpath.evaluate(root + "sunrise/hour", doc, XPathConstants.NUMBER) - 12.0;
    double min = (Double) xpath.evaluate(root + "sunrise/minute", doc, XPathConstants.NUMBER);
    if (hour < 0) {
      min *= -1;
    }
    sunRise = hour + (min / 60.0);
    
    //parse sunset
    hour = (Double) xpath.evaluate(root + "sunset/hour", doc, XPathConstants.NUMBER) - 12.0;
    min = (Double) xpath.evaluate(root + "sunset/minute", doc, XPathConstants.NUMBER);
    if (hour < 0) {
      min *= -1;
    }
    sunSet = hour + (min / 60.0);
  }
}
