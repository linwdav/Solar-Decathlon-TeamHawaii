package edu.hawaii.ihale.hvac;
 
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.housesimulator.Arduino;

/**
 * Simulates the HVAC System, holds values for the inside temperature.
 * @author Team Maka.
 *
 */
public class HVACResource extends Arduino { 
  //Maps need to be non-final...
  //These hold the goal state defined by the user.
  static double goalTemp = 79.4;
  HVACRepository repository;
  String temp = "temp";
  //Array of known keys 
  String[] localKeys = {temp}; 
  
  /**
   * Constructor.
   */
  public HVACResource() {
    super("hvac","arduino-3");
    repository = HVACRepository.getInstance();
    keys = localKeys; 
    list = Arrays.asList(keys);
  }
  
  /**
   * Refreshes the data.
   */
  @Override
  public void poll() {
    HVACRepository.setTemp(String.valueOf(getTemp()));
  }
  
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
    //create root element
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", "hvac");
    rootElement.setAttribute("device", deviceName);
    rootElement.setAttribute("timestamp", String.valueOf(date.getTime()));

    //AquaponicsRepository repository = AquaponicsRepository.getInstance();
    Element temperatureElement = doc.createElement("state");
    temperatureElement.setAttribute("key", "temp");
    //System.err.println(repository.valuesMap.get(item));
    temperatureElement.setAttribute("value", HVACRepository.getTemp());
    rootElement.appendChild(temperatureElement);

    doc.appendChild(rootElement);
    result.setDocument(doc);
    return result;
  }
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    double v = sToD(val);
      HVACRepository.setGoalTemp(v);
  }
  
  /**
   * Converts a String to a double.
   * @param val String to convert.
   * @return The double represented by the String.
   */
  private Double sToD(String val) {
    double v = 0;
    try {
      v = Double.valueOf(val);
   } 
    catch (NumberFormatException e) {
      System.out.println(e);
   }
   return v;
  }
  
  /**
   * Simulates the temperature changing slowly over time.
   * @return An updated temp value.
   */
  private double getTemp() {
    double currentTemp = sToD(HVACRepository.getTemp());
    return currentTemp + (HVACRepository.getGoalTemp() - currentTemp) / 100 + mt.nextDouble(-.05,.05); 
  }
  
  /**
   * Simulates the outdoor temperature changing slowly over time based on
   * the time of day.
   * @return An updated temp value.
   */
  /*
  private double getOutdoorTemp() {
    double hour = Calendar.HOUR_OF_DAY;
    double min = Calendar.MINUTE / 60;
    hour += min;
    double baseTemp = 78.5;
    double rate = 2.5 / 12.0 + mt.nextDouble(0,.05);
    //night
    if (hour <= 6 || hour >= 18) {
      return (baseTemp - (hour % 18) * rate);
    }
    //day
    else {
      return (baseTemp + (hour - 6) * rate);
    }
  }
  */
}
