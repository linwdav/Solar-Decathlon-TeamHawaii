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
  private static ClientResource client = null; 
  private static final String url = 
    "http://api.wunderground.com/auto/wui/geo/WXCurrentObXML/index.xml?query=";
  private Map<String,Double> conditionMap;

  double cloudCover;
  double fTemp;
  Long timestamp; 
  
  /**
   * 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   * @throws Exception - Generic type Exception for XPath parsing and Restlet.
   */
  public WeatherReport(String location) throws Exception {
    //initalize the object
    init(location);
    //poll the first set of data
    refresh();
  }
  
  /**
   * Initalizes the object. 
   * @param location - Geographic Location defined by Weather Underground 
   * http://www.wunderground.com/.
   */
  private final void init(String location) {
    client = new ClientResource(url + location);
    //init the condition map
    conditionMap = new HashMap<String,Double>();
    conditionMap.put("Clear", (double) 100);
    conditionMap.put("Cloudy", (double) 25);
    conditionMap.put("Mostly Cloudy", (double) 50);
    conditionMap.put("Partly Sunny", (double) 50);
    conditionMap.put("Mostly Sunny", (double) 75);
    conditionMap.put("Partly Cloudy", (double) 75);
    conditionMap.put("Scattered Clouds", (double) 85);
  }

  /**
   * Refreshes the data and parses it.
   * @throws Exception Generic Exception for XPath and Restlet.
   */
  public final void refresh() throws Exception {
    String root = "//current_observation/";
    //do a get
    Representation representation = client.get();
    DomRepresentation dom = new DomRepresentation(representation);
    Document doc = dom.getDocument();
    
    // parse the document
    String weather;
    Double time = (Double) xpath.evaluate(root + "local_epoch",doc,XPathConstants.NUMBER); 
    fTemp = (Double) xpath.evaluate(root + "temp_f",doc,XPathConstants.NUMBER); 
    weather = (String) xpath.evaluate(root + "weather", doc, XPathConstants.STRING);
    cloudCover = conditionMap.get(weather);
    timestamp = time.longValue();
  }
  
}
