package edu.hawaii.systemh.model.behavior; 

import java.io.IOException; 
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
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

  private  double cloudCover = -1;
  private  double fTemp = -1;
  private  double sunRise = -6;
  private  double sunSet = 6;
  private  double lowTemp = -1;
  private  double highTemp = -1;
  private  Long timestamp = null; 
  private long lastUpdate = 0;
  
  /**
   * 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   * @param station - the identifier for the desired weatherstation to use,
   * defined by Weather Undergound.
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
   * @param station - the identifier for the desired weatherstation to use,
   * defined by Weather Undergound.
   * @throws XPathExpressionException 
   * @throws IOException 
   */
  private final void init(String location, String station) 
                      throws XPathExpressionException, IOException {
    weatherClient = new ClientResource(weatherUrl + location);
    astrologicalClient = new ClientResource(astrologicalUrl + location);
    
    //init the condition map
    conditionMap = new HashMap<String,Double>();
    conditionMap.put("Clear", 1.0);
    conditionMap.put("Cloudy",  .25);
    conditionMap.put("Mostly Cloudy",  .50);
    conditionMap.put("Partly Sunny",  .50);
    conditionMap.put("Mostly Sunny",  .90);
    conditionMap.put("Partly Cloudy",  .80);
    conditionMap.put("Scattered Clouds",.80);
    conditionMap.put("Overcast",  .55);
    

    //do a get
    Representation weatherRep = weatherClient.get();
    Representation astroRep = astrologicalClient.get();
    
    // parse the weather document for local time, temperature, and cloud cover.
    DomRepresentation dom = new DomRepresentation(weatherRep);
    Document doc = dom.getDocument();
    String weather;
    String root = "/current_observation/";


    fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
    weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING); 
    cloudCover = conditionMap.get(weather);
    timestamp = System.currentTimeMillis() + 1000 * 60 * 60 * 5;
    
    // parse the astrological client for sunrise and sun set times.
    dom = new DomRepresentation(astroRep);
    doc = dom.getDocument();
    root = "/forecast/moon_phase/";
    
    //parse sunrise
    double hour = (Double) xpath.evaluate(root + "sunrise/hour", doc, XPathConstants.NUMBER);
    double min = (Double) xpath.evaluate(root + "sunrise/minute", doc, XPathConstants.NUMBER); 
    sunRise = (hour + (min / 60.0)) - 12.0;
    
    //parse sunset
    hour = (Double) xpath.evaluate(root + "sunset/hour", doc, XPathConstants.NUMBER);
    min = (Double) xpath.evaluate(root + "sunset/minute", doc, XPathConstants.NUMBER); 
    sunSet = (hour + (min / 60.0)) - 12.0;
     
    root = "/forecast/simpleforecast/forecastday[1]/";
    
    //parse temp
    highTemp = (Double) xpath.evaluate(root + "high/fahrenheit", doc, XPathConstants.NUMBER);
    lowTemp = (Double) xpath.evaluate(root + "low/fahrenheit", doc, XPathConstants.NUMBER); 
  }

  /**
   * Refreshes the data and parses it.
   * @throws Exception Generic Exception for XPath and Restlet.
   */
  public final void refresh() throws Exception { 
    timestamp = System.currentTimeMillis() + 1000 * 60 * 60 * 5;
    
    //if more than 15 mins since we updated, update again
    if (timestamp >= lastUpdate + 1000 * 60 * 15) {
      Representation weatherRep = weatherClient.get();  
      // parse the weather document for local time, temperature, and cloud cover.
      DomRepresentation dom = new DomRepresentation(weatherRep);
      Document doc = dom.getDocument();
      String weather;
      String root = "/current_observation/"; 
      fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
      weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING); 
      cloudCover = conditionMap.get(weather);
      lastUpdate = timestamp;
    }
  }
   /**
    * Returns the high temperature for the day.
    * @return the high temp in F as a double.
    */
  public double getHighTemp() {
    return highTemp;
  }
  /**
   * Returns the low temperature for the day.
   * @return the low temp in F as a double
   */
  public double getLowTemp() {
    return lowTemp;
  }

  /**
   * Returns a double representing the clearness of the sky.
   * @return a double between 0 and 1 representing solar occlusion.
   */
  public double getCloudCover() {
    return cloudCover;
  }
  
  /**
   * Returns the current temp in F.
   * @return a double representing the current temp.
   */
  public double getCurrentTemp() {
    return fTemp;
  }
  
  /**
   * Returns a double between -12 and 12 representing 
   * the sunrise time.
   * @return the sunrise time.
   */
  public double getSunRise() {
    return sunRise;
  }
  
  /**
   * Returns a double between -12 and 12 representing
   * the sunset time.
   * @return the sunset time.
   */
  public double getSunSet() {
    return sunSet;
  }
  
  /**
   * Returns a timestamp for the last time the data was updated.
   * @return a Long representing the last update.
   */
  public Long getTimeStamp() {
    return timestamp;
  }
}
 
