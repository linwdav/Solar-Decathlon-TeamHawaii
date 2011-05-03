package edu.hawaii.systemh.housesimulator.hvac;
 
import java.util.Calendar;
import java.util.Date; 
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.systemh.api.ApiDictionary.SystemHState;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem; 
import edu.hawaii.systemh.model.behavior.DailyTemperatureHistory;
import edu.hawaii.systemh.model.behavior.Simulator; 
import edu.hawaii.systemh.model.behavior.TempPair;
import edu.hawaii.systemh.model.behavior.WeatherReport;

/**
 * Provides data on the HVAC system, as well as an XML representation. Temperature values returned
 * in the XML representation are in Celsius.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class HVACDataWeb {
  
  private static Simulator sim; 
  static WeatherReport weather;
  /** The current date defaulted to a value when this class is first instantiated. **/
  private static Date currentTime = new Date();  
  
  /** Flag for when the HVAC system is to set the home to a certain temperature via PUT method. **/
  private static boolean desiredTempHasBeenSet = false;
  
  /** Time-stamp for when the HVAC system needs to regulate the home to a certain temperature. **/
  private static long whenDesiredTempCommandIssued = 0;
  
  /** Time-stamp for when the HVAC system was last changed. **/
  private static long lastUpdate = 0;
  
  /** The temperature the HVAC system has been commanded to maintain the home at. **/
  private static int desiredTemp = 21;
  
  /** Flag for when occupants are within the home or away. **/ 
  private static boolean occupantsHome = false; 
   
  private static boolean HVACOn = true;
  
  /** The current temperature outside the home. **/
  private static int currentOutsideTemp;
  
  /** The current home temperature, defaulted to imply it hasn't been initialized to a
   *  valid value. **/
  private static double currentHomeTemp = 0;
  private static HVACDataWeb instance;
  
  static {
    try {
        instance = new HVACDataWeb();
    } 
    catch (Exception e) {
        throw new ExceptionInInitializerError(e);
    }
  }
  
  /**
   * Singleton Constructor.
   * @throws Exception if no internet connection available.
   */
  private HVACDataWeb() throws Exception {
    init();
  }
   
  /**
   * Initializes the singleton, and sets up all helper classes.
   * @throws Exception If an internet connection is not available.
   */
  private static final void init() throws Exception {
    sim = Simulator.getInstance();
    weather = sim.getWeatherReport(); 
    whenDesiredTempCommandIssued = System.currentTimeMillis() - (1000 * 60 * 10);
    currentHomeTemp = ((sim.fToC(weather.getHighTemp()) + 
                      sim.fToC(weather.getLowTemp())) / 2.0 
                      + sim.getOutsideTemp()) / 2.0; 
  }
  
  /**
   * Returns the singleton Instance.
   * @return the singleton Instance of the class.
   */
  public  static synchronized HVACDataWeb getInstance() {
    return instance;
  }
  
  /**
   * Determines the goalTemp for a given timestamp.  Takes into account 
   * whether or not users are home, their sleep habits, the time of day,
   * and the day of the week.
   * @param timestamp long representing the time in question.
   * @return a double representing the goalTemp for the given time.
   */
  @SuppressWarnings("deprecation")
  public static double getGoalTemp(long timestamp) {
    
    Date date = new Date(timestamp);
    int hour = date.getHours(); 
    int day = date.getDay();
    double goalTemp = 0;
    double outsideTemp = sim.getOutsideTemp(sim.getSystemTime(timestamp));
    outsideTemp = sim.fToC(outsideTemp);
    boolean peopleHome = false;

    //if Saturday or Sunday
    if (day % 6 == 0) {
      peopleHome = true;
    }
    //between 4pm and 6 am
    else if (hour > 16 || hour < 6 ) {
      peopleHome = true;
    }
    
    if (desiredTempHasBeenSet) {
      if (peopleHome) { 
        goalTemp = desiredTemp;
      }
      else {
        goalTemp = outsideTemp;
      }
    }
    else {
        goalTemp = outsideTemp;
    }
    //if cold out and occupant asleep, produce less heat
    if (sim.getOutsideTemp(sim.getSystemTime(timestamp)) < currentHomeTemp
          && (hour > 23 || hour < 5)) { 
      goalTemp -= 3;
    } 
    return goalTemp;
  }
  
  /**
   * Modifies the state of the system. Resulting temperature units are in Celsius.
   * Outside and home temperatures are influenced by time of day and the corresponding current 
   * month's average high and average low temperatures as reflected by temperature data gathered
   * from 2010 for Washington D.C. 
   * The coldest part of the day is defined as just before and during sunrise. 
   * The hottest part of the day is defined to be at 3:00 PM or 15th hour of the day.
   * HVAC system should maintain temperatures approximately 78 in the summer and 68 in the winter,
   * adjusted by 10 degrees F higher or lower if occupants aren't home for energy usage efficiency
   * stated by some HVAC web-sites.
   * Lacking the home insulation value (R-value), this model assumes a static 3 min/C degree change
   * for simplicity.
   * Home temperature values will change over time to meet the desired temperature when commanded.
   * Otherwise the home will have its home temperature influenced purely by the outside temperature
   * and hour of the day.
   */
  
  public void modifySystemState() {
    modifySystemState(sim.getOutsideTemp() ,currentTime.getTime());
  }
  
  /** Approximates the inside temp for a time given a timestamp
   * and the outside temp at that time.
   * @param outsideTemp a double in C
   * @param timestamp a long representing the time in question.
   */
  public static void modifySystemState(double outsideTemp, long timestamp) { 
    Date test = new Date(timestamp);
    //System.out.println("HOUR = " +test.getHours());
    double goalTemp = getGoalTemp(timestamp); 
    //System.out.println("GoalTemp = "+goalTemp);
    //System.out.println("outsidetemp =" +outsideTemp);
    long timeSinceSet = timestamp - lastUpdate;
    double tempDifferenceInside = goalTemp - currentHomeTemp;
    double tempDifferenceOutside = outsideTemp - currentHomeTemp;
    double outsideEffect = timeSinceSet * (1 / (60.0 * 7.0 * 1000.0));
    
    //System.out.println("Temp diff inside =" +tempDifferenceInside);
    //System.out.println("Temp diff outside =" +tempDifferenceOutside);

    Random rand = new Random();
    double offset = 2 * rand.nextDouble() - 3;
    // offset used to be this, but it got stuck as a static val when 
    //doing generation from timestamps. 
    //Math.sin(10 * (sim.getSystemTime(timestamp))) - 0.75;
            
    //System.out.println("offset =" + offset);
    double hvacEffect = timeSinceSet * (1
                          / (60.0 * 5.0 * 1000.0)); 
    //System.out.println("hvac = " + (hvacEffect) + "outside =" +outsideEffect);
    boolean hvacBigger = Math.abs(hvacEffect) > Math.abs(outsideEffect);
    
    //if its been off too long the outside temp wins
    if (Math.abs(outsideEffect) >= Math.abs(tempDifferenceOutside)) {
      if (!hvacBigger) {
        currentHomeTemp = outsideTemp + offset / 3;
        //System.out.println("maxed");
      }
      else {
        currentHomeTemp = goalTemp + offset;
        //System.out.println("mined");
      }
    }
    //outside effect
    else {
      currentHomeTemp += outsideEffect;
      //System.out.println("outsideEffect");
    }
    
    
    
     
      //if were not already there, hvac effect
      if (Math.abs(currentHomeTemp - goalTemp) >= 1) {
        //if we over shot it, set to goal
        if (Math.abs(hvacEffect) >= Math.abs(tempDifferenceInside)) {
          if (!hvacBigger) {
            currentHomeTemp = outsideTemp + offset / 3;
            //System.out.println("maxed");
          }
          else {
            currentHomeTemp = goalTemp + offset;
            //System.out.println("mined");
          }
        }
        //otherwise just cool
        else {
          currentHomeTemp += hvacEffect; 
          //System.out.println("hvacEffect");
        }
      }  
    lastUpdate = timestamp; 
  }
   
  
  /**
   * Determine if the HVAC system has been issued a command to maintain the home at a specified 
   * temperature.
   *
   * @return Returns true if the HVAC system has been issued a command to maintain the home at a 
   *         specific temperature, false otherwise.
   */
  public boolean desiredTempCommandIssued() {
    return desiredTempHasBeenSet;
  }
  
  /**
   * Sets the current time to a new time. Used for reproducing historical or future temperature
   * records.
   *
   * @param time The new date in milliseconds that have passed since 
   *             January 1, 1970 00:00:00 GMT. 
   */
  public void setCurrentTime(long time) {
    currentTime = new Date(time);
    lastUpdate = time;
  }
  
  /**
   * Sets the desired temperature in Farenheit for the HVAC system to maintain the home at.
   * 
   * @param newDesiredTemp The temperature for the HVAC system to maintain the home at.
   */
  public void setDesiredTemp(int newDesiredTemp) {
    desiredTemp = newDesiredTemp;
    desiredTempHasBeenSet = true;
    whenDesiredTempCommandIssued = (new Date()).getTime();
  }

  /**
   * Returns the milliseconds since elapsed past January 1, 1970 00:00:00 GMT that a PUT
   * request has been sent to the HVAC system to maintain the home temperature at.
   *
   * @return The timestamp of when the HVAC PUT request was successfully accepted.
   */
  public long getWhenDesiredTempCommandIssued() {
    return whenDesiredTempCommandIssued;
  }
  
  /**
   * Returns the current home temperature in Celsius.
   *
   * @return Current home temperature.
   */
  public static int getCurrentHomeTemp() {
    return (int)currentHomeTemp;
  }
  
  /**
   * Enables/disables HVAC.
   * @param state the value to assign, true for on, false for off.
   */
  public static void setHVACState(boolean state) {
    if (state) {
      whenDesiredTempCommandIssued = new Date().getTime();
    }
    HVACOn = state;
  }
  
  /**
   * Returns the desired temperature the HVAC system is to maintain the home at.
   *
   * @return The desired temperature the HVAC system is to maintain the home at.
   */
  public int getDesiredTemp() {
    return desiredTemp;
  }
  
  /**
   * Returns the current temperature outside the home.
   *
   * @return The current temperature outside the home.
   */
  public int currentOutsideTemp() {
    return currentOutsideTemp;
  }
  /**
   * Sets static fields that assist to simulate HVAC temperature control within the home by setting
   * them to false to emulate a HVAC turn off/reset. Useful for debugging purposes such as JUnit
   * testing.
   */
  public static void resetHVACSystem() {
    whenDesiredTempCommandIssued = new Date().getTime() - 1000 * 60 * 20;
    desiredTempHasBeenSet = false;
    desiredTemp = -1; 
    currentHomeTemp = ((weather.getHighTemp() + weather.getLowTemp()) / 2.0 
                      + sim.getOutsideTemp()) / 2.0;
  }
  

  
  /**
   * Prints the current state of the HVAC system.
   */
  public static void printHVACSystemState() {
    
    System.out.println("----------------------");
    System.out.println("System: HVAC");
    System.out.println("Current time is: " + currentTime);
    System.out.println("Temperature: " + currentHomeTemp + "C " + "(Desired: " + desiredTemp + ")");
    if (occupantsHome) {
      System.out.println("The occupants are home.");
    }
    else {
      System.out.println("The occupants are not home.");
    }
    if (desiredTempHasBeenSet) {
      System.out.print("Desired temperature has been issued at: ");
      System.out.println(new Date(whenDesiredTempCommandIssued));
    }
    else {
      System.out.println("No desired temperature has been set.");
    }
    System.out.println("currentOutsideTemp is: " + currentOutsideTemp + "C");
  }
  
  /**
   * Returns the data as an XML Document instance.
   * 
   * @return HVAC state data in XML representation.
   * @throws Exception If problems occur creating the XML.
   */
  public DomRepresentation toXml() throws Exception {  
    
    // Re-initialize temperature values.
    modifySystemState();
    
    String system = SystemHSystem.HVAC.toString();
    String device = "arduino-3";
    //String timestampString = timestamp.toString();
    String timestampString = String.valueOf(currentTime.getTime());
    String temperatureString = SystemHState.TEMPERATURE.toString();
    int celsiusTemp = (int) currentHomeTemp;

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
   * Appends HVAC state data at a specific timestamp snap-shot to the Document object
   * passed to this method.
   *
   * @param doc Document object to append HVAC state data as child nodes.
   * @param timestamp The specific time snap-shot the HVAC state data interested to be appended.
   * @return Document object with appended HVAC state data.
   */
  @SuppressWarnings("deprecation")
  public Document toXmlByTimestamp(Document doc, Long timestamp) {

    // Set the current HVAC system to reflect the state of the passed timestamp.
    // Re-initialize temperature values.
    Date date = new Date(timestamp);
    int dateMonth = date.getMonth();
    int dateDay = date.getDate();
    GregorianCalendar cal = new GregorianCalendar();
    int currentMonth =  cal.get(Calendar.MONTH);
    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
    
    //return today's values.
    if (currentDay == dateDay && currentMonth == dateMonth) { 
      double outsideTemp = sim.getOutsideTemp(sim.getSystemTime(timestamp));
      modifySystemState(sim.fToC(outsideTemp),new Long(timestamp));
    }
    //return historical data.
    else {
      DailyTemperatureHistory history = DailyTemperatureHistory.getInstance();
      TempPair tempPair = history.getTemp(dateMonth, dateDay); 
      double historicTemp = sim.getOutsideTemp(sim.getSystemTime(timestamp)
                          ,tempPair.getLow(),tempPair.getHigh());
      historicTemp = sim.fToC(historicTemp); 
     // System.out.println("historicTemp =" +historicTemp);
      modifySystemState(historicTemp, new Long(timestamp));
    } 
    System.out.print( currentHomeTemp + ",");
    String system = SystemHSystem.HVAC.toString();
    String device = "arduino-3";
    String timestampString = timestamp.toString();
    String temperatureString = SystemHState.TEMPERATURE.toString();
    int celsiusTemp = (int) currentHomeTemp;

    // Get the root element, in this case would be <state-history> element.
    Element rootElement = doc.getDocumentElement();
    
    // Create state-data tag.
    Element stateDataElement = doc.createElement("state-data");
    stateDataElement.setAttribute("system", system);
    stateDataElement.setAttribute("device", device);
    stateDataElement.setAttribute("timestamp", timestampString);
    rootElement.appendChild(stateDataElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("state");
    temperatureElement.setAttribute("key", temperatureString);
    temperatureElement.setAttribute("value", String.valueOf(celsiusTemp));
    stateDataElement.appendChild(temperatureElement);
    
    return doc;
  }
  
  /**
   * Test main method.
   * @throws Exception if no internet connection.
   */
  public static void main2() throws Exception {
    init();  
    for (int i = 72; i > 0; i --) {
    long time = new Date().getTime() - (1000 * 60 * 20) * i; 
    double temp = sim.getOutsideTemp(sim.getSystemTime(time),
        sim.fToC(weather.getLowTemp()),sim.fToC(weather.getHighTemp()));
    modifySystemState(temp,time);
    System.out.println (sim.getSystemTime(time) + "outside = " + temp + 
        " temp = " + currentHomeTemp);
    System.out.println();
    } 
  }
}