package edu.hawaii.ihale.housesimulator.hvac;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * Provides data on the HVAC system, as well as an XML representation. Temperature values returned
 * in the XML representation is in Celsius.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class HVACData {
  
  /** Random generator. */
  private static final Random randomGenerator = new Random();

  /** The current temperature in F. */
  private static int temperature = randomGenerator.nextInt(41) + 60;

  /** The desired temperature in F. */
  private static int desiredTemperature = randomGenerator.nextInt(41) + 60;

  /** The max value temperature will increment by in F. */
  private static final int temperatureIncrement = 1;

  private static Map<String, MonthlyTemperatureRecord> washingtonMonthlyWeather = 
    new HashMap<String, MonthlyTemperatureRecord>();  
  
  // Washington D.C. monthly weather history taken from: 
  // http://www.weather.com/weather/wxclimatology/monthly/graph/USDC0001
  // Initialize the monthly average high and low temperatures in F.
  static {
    washingtonMonthlyWeather.put("JANUARY", new MonthlyTemperatureRecord("JANUARY", 42, 27));
    washingtonMonthlyWeather.put("FEBRUARY", new MonthlyTemperatureRecord("FEBRUARY", 47, 30));
    washingtonMonthlyWeather.put("MARCH", new MonthlyTemperatureRecord("MARCH", 56, 37));
    washingtonMonthlyWeather.put("APRIL", new MonthlyTemperatureRecord("APRIL", 66, 46));
    washingtonMonthlyWeather.put("MAY", new MonthlyTemperatureRecord("MAY", 75, 56));
    washingtonMonthlyWeather.put("JUNE", new MonthlyTemperatureRecord("JUNE", 84, 65));
    washingtonMonthlyWeather.put("JULY", new MonthlyTemperatureRecord("JULY", 88, 76));
    washingtonMonthlyWeather.put("AUGUST", new MonthlyTemperatureRecord("AUGUST", 86, 69));
    washingtonMonthlyWeather.put("SEPTEMBER", new MonthlyTemperatureRecord("SEPTEMBER", 79, 62));
    washingtonMonthlyWeather.put("OCTOBER", new MonthlyTemperatureRecord("OCTOBER", 68, 50));
    washingtonMonthlyWeather.put("NOVEMBER", new MonthlyTemperatureRecord("NOVEMBER", 57, 40));
    washingtonMonthlyWeather.put("DECEMBER", new MonthlyTemperatureRecord("DECEMBER", 47, 32));
  }
  
  /**
   * Modifies the state of the system. F temperature units are used.
   */
  public static void modifySystemState() {

    // Increments temperature within range of the desired temperature.
    if (temperature > (desiredTemperature - temperatureIncrement)
        && temperature < (desiredTemperature + temperatureIncrement)) {
      temperature +=
          randomGenerator.nextInt((temperatureIncrement * 2) + 1) - temperatureIncrement;
    }
    else if (temperature < desiredTemperature) {
      temperature += randomGenerator.nextInt(temperatureIncrement + 1);
    }
    else {
      temperature -= (randomGenerator.nextInt(temperatureIncrement + 1));
    }

    System.out.println("----------------------");
    System.out.println("System: HVAC");
    System.out.println("Temperature: " + temperature + " (Desired: " + desiredTemperature + ")");
  
    /** This portion is additional to Kai's original HVACData class code and denotes the 
     *  realistic HVAC value modeling. **/
    
    // Temperature influenced by seasonal months primarily summer and winter.
    // Need to check if occupants are home.
    // HVAC system should maintain temperatures approximately 78 in the summer and 68 in the
    // winter, deviating by 10 degrees higher or lower if occupants aren't home.
    // Lacking home insulation value (the R-value); will assume static 5 min/degree change
    // for simplicity.
    // Lacking specifications of HVAC system, primarily its BTU/hour to determine energy usage
    // to heat and cool the home.
    // Need to model temperature delta over time if occupants have set a desired temperature
    // and current home temperature is different.
    // If occupants haven't set a desired temperature, HVAC will undergo automation process,
    // home temperature needs to then have a relationship with current outside temperature.
  
    /** End of additional portion. **/
  }

  /**
   * Sets the desired temperature.
   * 
   * @param newDesiredTemperature the desired temperature
   */
  public static void setDesiredTemperature(int newDesiredTemperature) {
    desiredTemperature = newDesiredTemperature;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @param timestamp The timestamp of when the state values were generated.
   * @return HVAC state data in XML representation.
   * @throws Exception If problems occur creating the XML.
   */
  public static DomRepresentation toXml(Long timestamp) throws Exception {
    
    String system = IHaleSystem.HVAC.toString();
    String device = "arduino-3";
    String timestampString = timestamp.toString();
    String temperatureString = IHaleState.TEMPERATURE.toString();
    int celsiusTemp = fahrenToCelsius(temperature);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", system);
    rootElement.setAttribute("device", device);
    rootElement.setAttribute("timestamp", timestampString);
    doc.appendChild(rootElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("state");
    temperatureElement.setAttribute("key", temperatureString);
    temperatureElement.setAttribute("value", String.valueOf(celsiusTemp));
    rootElement.appendChild(temperatureElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
  
  /**
   * Converts Fahrenheit to Celsius temperature.
   *
   * @param fahrenheit The Fahrenheit temperature to convert to Celsius.
   * @return The converted Fahrenheit to Celsius temperature.
   */
  private static int fahrenToCelsius(int fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
  }
}
