package edu.hawaii.ihale.housesimulator.hvac;

import java.util.Calendar;
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

  /** START - NEW FIELDS. **/
  
  private static Map<String, TemperatureRecord> washingtonMonthlyTemps = 
    new HashMap<String, TemperatureRecord>();  
 
  private static Map<String, Integer> washingtonMonthlySunrise = new HashMap<String, Integer>();
  
  private static boolean desiredTempHasBeenSet = false;
  
  private static int desiredTempValue = 0;
  
  private static boolean occupantsHome = false;
  
  private static final int summerEfficientTempWhenOccupantHome = 78;
  
  private static final int summerEfficientTempWhenOccupantNotHome = 88;
  
  private static final int winterEfficientTempWhenOccupantHome = 68;
  
  private static final int winterEfficientTempWhenOccupantNotHome = 58;
  
  private static int currentHomeTemp = 0;
  
  /** END - NEW FIELDS. **/
  
  static {
    // Washington D.C. monthly weather history taken from: 
    // http://www.weather.com/weather/wxclimatology/monthly/graph/USDC0001
    // Initialize the monthly average high and low temperatures in F.
    washingtonMonthlyTemps.put("JANUARY", new TemperatureRecord(42, 27));
    washingtonMonthlyTemps.put("FEBRUARY",  new TemperatureRecord(47, 30));
    washingtonMonthlyTemps.put("MARCH", new TemperatureRecord(56, 37));
    washingtonMonthlyTemps.put("APRIL", new TemperatureRecord(66, 46));
    washingtonMonthlyTemps.put("MAY", new TemperatureRecord(75, 56));
    washingtonMonthlyTemps.put("JUNE", new TemperatureRecord(84, 65));
    washingtonMonthlyTemps.put("JULY", new TemperatureRecord(88, 76));
    washingtonMonthlyTemps.put("AUGUST", new TemperatureRecord(86, 69));
    washingtonMonthlyTemps.put("SEPTEMBER", new TemperatureRecord(79, 62));
    washingtonMonthlyTemps.put("OCTOBER", new TemperatureRecord(68, 50));
    washingtonMonthlyTemps.put("NOVEMBER", new TemperatureRecord(57, 40));
    washingtonMonthlyTemps.put("DECEMBER", new TemperatureRecord(47, 32));
    
    // Generalize sunrise hour for Washington D.C. provided by aid of:
    // http://www.timeanddate.com/worldclock/astronomy.html?n=263
    washingtonMonthlySunrise.put("JANURARY", 7);
    washingtonMonthlySunrise.put("FEBRUARY", 7);
    washingtonMonthlySunrise.put("MARCH", 7);
    washingtonMonthlySunrise.put("APRIL", 6);
    washingtonMonthlySunrise.put("MAY", 6);
    washingtonMonthlySunrise.put("JUNE", 5);
    washingtonMonthlySunrise.put("JULY", 6);
    washingtonMonthlySunrise.put("AUGUST", 6);
    washingtonMonthlySunrise.put("SEPTEMBER", 7);
    washingtonMonthlySunrise.put("OCTOBER", 7);
    washingtonMonthlySunrise.put("NOVEMBER", 7);
    washingtonMonthlySunrise.put("DECEMBER", 7);
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
    // Coldest part of the day is just before and during sunrise.
    // Hottest part of the day is on average 3:00 PM or 1500 hour.
    // Need to check if occupants are home.
    // HVAC system should maintain temperatures approximately 78 in the summer and 68 in the
    // winter, adjusted by 10 degrees higher or lower if occupants aren't home for energy
    // efficiency.
    // Lacking home insulation value (the R-value); will assume static 5 min/degree change
    // for simplicity.
    // Lacking specifications of HVAC system, primarily its BTU/hour to determine energy usage
    // to heat and cool the home.
    // Need to model temperature delta over time if occupants have set a desired temperature
    // and current home temperature is different.
    // If occupants haven't set a desired temperature, HVAC will undergo automation process,
    // home temperature needs to then have a relationship with current outside temperature.
  
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2011);
    int monthNum = calendar.get(Calendar.MONTH);
    String month = "";
    
    switch (monthNum) {
      case 0: month = "JANUARY"; break;
      case 1: month = "FEBRUARY"; break;
      case 2: month = "MARCH"; break;
      case 3: month = "APRIL"; break;
      case 4: month = "MAY"; break;
      case 5: month = "JUNE"; break;
      case 6: month = "JULY"; break;
      case 7: month = "AUGUST"; break;
      case 8: month = "SEPTEMBER"; break;
      case 9: month = "OCTOBER"; break;
      case 10: month = "NOVEMBER"; break;
      case 11: month = "DECEMBER"; break;
      // Should never reach this point, valid month from Calendar object is 0 to 11.
      default: month = ""; return;
    }
    
    TemperatureRecord record = washingtonMonthlyTemps.get(month);
    int avgLowTemp = record.getAvgLowTemp();
    int avgHighTemp = record.getAvgHighTemp();
    int sunriseHour = washingtonMonthlySunrise.get(month);
    int hottestHourInDay = 15;
    
    // The rate the outside temperature increases from sunrise (coldest part of the day) to the
    // hottest point in the day (3:00 PM or 1500 hour). Degree unit is in Farenheit.
    double degreeChangeFromSunriseToHighTempPt = 
      (avgHighTemp - avgLowTemp) / (double) (hottestHourInDay - sunriseHour);    
    
    // The rate the outside temperature decreases from the hottest point in the day to the next
    // day before the new sunrise.
    double degreeChangeFromHighTempPtToSunrise = 
      (avgHighTemp - avgLowTemp) / (double) (24 - hottestHourInDay - sunriseHour);
    
    // Situation 1:
    // If the HVAC system has had a desired temperature to maintain the home at.
    if (desiredTempHasBeenSet) {
     
      
      
      
      
      System.out.println(); // QA complaints.
      
    }
    // Situation 2:
    // No desired temperature to maintain the home at, HVAC system will undergo self-automation.
    // Home temperatures will be influenced by the outside temperature.
    else {
      
      /** Initialize fields to generate the home temperature for Situation 2. **/
      
      int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
      int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
      int currentOutsideTemp;
      
      // Trend for currentTemp is to rise, beginning from sunrise to the hottest point in the day.
      if (currentHour >= sunriseHour && currentHour <= hottestHourInDay) {
        currentOutsideTemp = (int) ((currentHour - sunriseHour) * 
            degreeChangeFromSunriseToHighTempPt) + avgLowTemp;
      }
      // Trend for currentTemp is to fall, beginning from the hottest point in the day until
      // to the sunrise of the next day.
      else {
        
        // Handle the case when the currentHour of the day is within the next day, beyond
        // midnight.
        if (currentHour < sunriseHour) {
          currentOutsideTemp = (int) (avgHighTemp - 
              ((24 - hottestHourInDay + currentHour) * degreeChangeFromHighTempPtToSunrise));
        }
        // The currentHour is still within the current day, after the hottest point in the day, but
        // before midnight.
        else {
          currentOutsideTemp = (int) (avgHighTemp - 
              ((24 - currentHour) * degreeChangeFromHighTempPtToSunrise));
        }
      }
            
      // Occupants are assumed to be out of the home for work on the weekdays from 9:00 AM to 
      // 5:00 PM for AM/PM system or 0900 to 1700 hour system.
      if ((currentHour >= 9 && currentHour <= 17) && (currentDay > 1 && currentDay < 7)) {
        occupantsHome = false;
      }
      
      /** End of the initialize of fields to generate the home temperature for Situation 2. **/

      // Situation 2a:
      // If the home has occupants, the home temperature should be maintained at a comfortable
      // level.
      if (occupantsHome) {
        
        // Most HVAC systems can't keep a difference in outside and inside temperature of greater
        // than ~15-20 degrees F when high temperatures exceed 95 degrees F.
        if (currentOutsideTemp > 95) {
          currentHomeTemp = currentOutsideTemp - 18;
        }
        // Some HVAC sites suggest 78 degrees F is ideal to maintain the home at for energy 
        // efficiency when the outside weather is hot.
        else if (currentOutsideTemp >= 78 && currentOutsideTemp <= 95) { 
          currentHomeTemp = summerEfficientTempWhenOccupantHome;
        }
        // This is the ideal home temperature ranges for the solar decathlon contest. No need to
        // run the HVAC strongly and instead allow the outside and inside home temperatures to
        // converge to equilibrium temperature state.
        else if (currentOutsideTemp < 78 && currentOutsideTemp >= 72) {
          currentHomeTemp = currentOutsideTemp;
        }
        // Some HVAC sites suggest 68 degrees F is ideal to maintain the home at for energy
        // energy efficiency when the outside weather is cold.
        else if (currentOutsideTemp < 72) {
          currentHomeTemp = winterEfficientTempWhenOccupantHome;
        }
      }
      
      // Situation 2b:
      // If occupants aren't home, then the HVAC system can be energy efficient by setting the
      // home temperature higher (in the summer) or lower (in the dinner).
      else {
       
        if (currentOutsideTemp > 95) {
          currentHomeTemp = currentOutsideTemp - 18;
        }
        // Since the occupants are not home, the home temperature can be hotter than the ideal
        // home temperature to conserve energy. It is suggested to be 10 degrees F higher than
        // normal.
        else if (currentOutsideTemp >= 88 && currentOutsideTemp <= 95) {
          currentHomeTemp = summerEfficientTempWhenOccupantNotHome;
        }
        // Since the occupants are not home, the home temperature can be colder than the ideal
        // home temperature to conserve energy, but not too cold so that instruments are damaged.
        // It is suggested to be 10 degrees F lower than normal.
        else if (currentOutsideTemp <= 68) {
          currentHomeTemp = winterEfficientTempWhenOccupantNotHome;
        }
        else {
          currentHomeTemp = currentOutsideTemp;
        }
      }
    }
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
