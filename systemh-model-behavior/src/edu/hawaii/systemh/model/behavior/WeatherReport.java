package edu.hawaii.systemh.model.behavior; 

import java.io.IOException; 
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory; 
import org.w3c.dom.Document;

/**
 * A Class that provides meteorological data from Weather Underground 
 * http://www.wunderground.com/.
 * @author Greg Burgess 
 */
public class WeatherReport { 
  private static XPathFactory factory = XPathFactory.newInstance();
  private static XPath xpath = factory.newXPath();  
  //private static final String weatherUrlTxt = 
    //"http://api.wunderground.com/auto/wui/geo/WXCurrentObXML/index.xml?query=";
  //private static final String astrologicalUrlTxt =
    //"http://api.wunderground.com/auto/wui/geo/ForecastXML/index.xml?query=";
  private static URL weatherUrl;
  private static URL astroUrl;
  private static String location;
  private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  private static DocumentBuilder db;
  private Map<String,Double> conditionMap;
  /** The String value of the area of interest, 
   * given by WeatherUndergound's API.**/
  private  static final String DC = "Washington%2c%20District%20of%20Columbia";
  /** The weather station nearest DC that we will pull data from. **/
  private static final String ECKINGTON = "Eckington%20Pl%2c%20NE%2c%20Washington";
  private  double cloudCover = -1;
  private  double fTemp = -1;
  private  double sunRise = -6;
  private  double sunSet = 6;
  private  double lowTemp = -1;
  private  double highTemp = -1;
  private  Long timestamp = null; 
  private long lastUpdate = 0;
  /** The singleton Instance. */
  private static WeatherReport instance;
  
  static {
    try {
        instance = new WeatherReport(DC,ECKINGTON);
    } 
    catch (Exception e) {
        throw new ExceptionInInitializerError(e);
    }
  }
  
  
  /**
   * 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   * @param station - the identifier for the desired weatherstation to use,
   * defined by Weather Undergound.
   * @throws Exception - Generic type Exception for XPath parsing and Restlet.
   */
  private WeatherReport(String location, String station) throws Exception {
    //initalize the object
    init(location, station);
    //poll the first set of data
    refresh();
  }
  
  /**
   * Returns the Singelton instance of WeatherReport.
   * @return a Singleton instance initialized to the current instant.
   */
  public static synchronized WeatherReport getInstance() {
    return instance;
  }
  
  /**
   * Initalizes the object. 
   * @param loc - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   * @param station - the identifier for the desired weatherstation to use,
   * defined by Weather Undergound.
   * @throws XPathExpressionException 
   * @throws IOException 
   * @throws ParserConfigurationException 
   */
  private final void init(String loc, String station) 
                      throws XPathExpressionException, IOException, ParserConfigurationException {
    db = dbf.newDocumentBuilder(); 
    weatherUrl = new URL("http","api.wunderground.com",
                          "/auto/wui/geo/WXCurrentObXML/index.xml?query=" + loc);
    astroUrl = new URL("http","api.wunderground.com",
                          "/auto/wui/geo/ForecastXML/index.xml?query=" + loc);
    location = loc;
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
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document weatherDoc = null,astroDoc = null;
    try {
      URLConnection weatherURLConnection = weatherUrl.openConnection();
      weatherURLConnection.addRequestProperty("query", location );  
      weatherURLConnection.connect();
      InputStream weatherIS = weatherURLConnection.getInputStream();
      weatherDoc = db.parse(weatherIS);
      weatherIS.close();
      
      URLConnection astrologicalURLConnection = astroUrl.openConnection();
      astrologicalURLConnection.addRequestProperty("query",location); 
      astrologicalURLConnection.connect(); 
      InputStream astroIS = astrologicalURLConnection.getInputStream();
      astroDoc = db.parse(astroIS); 
      astroIS.close();
    }
    catch ( Exception e1) { 
      e1.printStackTrace();
    }
    //cast the documents back to representations 
    
    // parse the weather document for local time, temperature, and cloud cover. 
    Document doc = weatherDoc;
    String weather;
    String root = "/current_observation/"; 
    fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
    weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING); 
    try {
      cloudCover = conditionMap.get(weather);
    }
    catch (Exception e) {
      cloudCover = .5;
    }
    timestamp = System.currentTimeMillis() + 1000 * 60 * 60 * 5;
    
    
    
    // parse the astrological client for sunrise and sun set times. 
    doc = astroDoc;
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
      // parse the weather document for local time, temperature, and cloud cover. 
      Document doc = get(weatherUrl );
      String weather;
      String root = "/current_observation/"; 
      fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
      weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING); 
      try {
        cloudCover = conditionMap.get(weather);
      }
      catch (Exception e) {
        cloudCover = .5;
      }
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
  
  /**
   * Performs a "get" request on a URL, passing additional 
   * data as a property map.
   * @param url a fully formed url, with designated protocol, domain, and file.
   * @return a document containing the result.
   * @throws Exception If url is unreachable.
   */
  private Document get(URL url) throws Exception { 
    URLConnection wurlcon = url.openConnection();
    wurlcon.addRequestProperty("query", location);  
    wurlcon.connect();
    return db.parse(wurlcon.getInputStream()); 
  }
  
}
 
