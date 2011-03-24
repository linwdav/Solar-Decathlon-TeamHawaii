package edu.hawaii.ihale.housesimulator.hvac;

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
    int celsiusTemp = (temperature - 32) * 5 / 9;

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
}
