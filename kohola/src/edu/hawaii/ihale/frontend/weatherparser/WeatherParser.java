package edu.hawaii.ihale.frontend.weatherparser;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * A parser used to read in the weather forecast from Google Weather API.
 * 
 * @author Chuan Lun Hung
 * 
 */
public class WeatherParser implements Serializable {
  
  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private static final String SOURCE_ROOT = "http://www.google.com";
  private static final String WEATHER_API_URL = "/ig/api?weather=";
  private String postalCodeData;

  /**
   * The constructor to stores a proper url (sourceURL + postalCodeData) to retrieve the weather
   * forecast.
   * 
   * @param postalCodeData It can be a city name (Honolulu), or a zip Code (96816)
   */
  public WeatherParser(String postalCodeData) {
    this.postalCodeData = postalCodeData;
  }

  /**
   * Returns the source URL where we retrieved the weather info.
   * 
   * @return The source URL.
   */
  public String getSourceURL() {
    return SOURCE_ROOT + WEATHER_API_URL + postalCodeData;
  }

  /**
   * Sets the postal code data.
   * 
   * @param postalCodeData The data can be a city name or a zip code.
   */
  public void setPostalCodeData(String postalCodeData) {
    this.postalCodeData = postalCodeData;
  }

  /**
   * Retrieves the current weather from Google Weather API, parse the information, and returns it as
   * a CurrentWeather object.
   * 
   * @return The CurrentWeather instance.
   */
  public CurrentWeather getCurrentWeather() {

    // perform a GET command to the source URL to retrieve the current weather.
    ClientResource client = new ClientResource(getSourceURL());
    DomRepresentation representation = new DomRepresentation(client.get());
    try {

      String condition;
      int tempF;
      int tempC;
      String humidity;
      String imageURL;
      String windCondition;
      Document doc;
      NodeList root;
      NodeList nodes;

      doc = representation.getDocument();
      // normalize xml document and get root element for current weather
      doc.getDocumentElement().normalize();
      root = doc.getElementsByTagName("current_conditions");

      nodes = root.item(0).getChildNodes();
      // store condition
      condition = nodes.item(0).getAttributes().item(0).getNodeValue();
      // store tempF
      tempF = Integer.parseInt(nodes.item(1).getAttributes().item(0).getNodeValue());
      // store tempC
      tempC = Integer.parseInt(nodes.item(2).getAttributes().item(0).getNodeValue());
      // store humidity
      humidity = nodes.item(3).getAttributes().item(0).getNodeValue();
      // store image source
      imageURL = SOURCE_ROOT + nodes.item(4).getAttributes().item(0).getNodeValue();
      // store wind condition
      windCondition = nodes.item(5).getAttributes().item(0).getNodeValue();

      // create the instance and return it
      CurrentWeather currentWeather =
          new CurrentWeather(condition, tempF, tempC, humidity, imageURL, windCondition);
      return currentWeather;
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves the four days weather forecast from Google Weather API, parse the information, and
   * returns it as a ArrayList that contains the weather forecast for each day.
   * 
   * @return The ArrayList<WeatherForecast> that contains the weather forecast for the following
   * days.
   */
  public List<WeatherForecast> getWeatherForecast() {
    List<WeatherForecast> weathers = new ArrayList<WeatherForecast>();

    String dayOfWeek;
    int lowTemp;
    int highTemp;
    String imageURL;
    String condition;

    ClientResource client = new ClientResource(getSourceURL());
    DomRepresentation representation = new DomRepresentation(client.get());
    Document doc = null;
    try {
      doc = representation.getDocument();
      doc.getDocumentElement().normalize();
      NodeList root = doc.getElementsByTagName("forecast_conditions");
      int numberOfChilds = root.getLength();

      NodeList nodes;
      for (int i = 0; i < numberOfChilds; i++) {
        // increment day
        nodes = root.item(i).getChildNodes();
        // read in the info for each day's weather forecast
        dayOfWeek = nodes.item(0).getAttributes().item(0).getNodeValue();
        lowTemp = Integer.parseInt(nodes.item(1).getAttributes().item(0).getNodeValue());
        highTemp = Integer.parseInt(nodes.item(2).getAttributes().item(0).getNodeValue());
        imageURL = SOURCE_ROOT + nodes.item(3).getAttributes().item(0).getNodeValue();
        condition = nodes.item(4).getAttributes().item(0).getNodeValue();
        // create the instance and add it to the list.
        WeatherForecast weather =
            new WeatherForecast(dayOfWeek, lowTemp, highTemp, imageURL, condition);      
        weathers.add(weather);
      }    
      return weathers;
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }   
  }
}
