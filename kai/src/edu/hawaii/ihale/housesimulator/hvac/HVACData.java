package edu.hawaii.ihale.housesimulator.hvac;

import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the HVAC system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class HVACData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();

  /** The current temperature. */
  private static long temperature = (long) randomGenerator.nextInt(41) + 60;

  /** The desired temperature. */
  private static long desiredTemperature = (long) randomGenerator.nextInt(41) + 60;

  /** The max value temperature will increment by. */
  private static final long temperatureIncrement = 1;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Increments temperature within range of the desired temperature.
    if (temperature > (desiredTemperature - temperatureIncrement)
        && temperature < (desiredTemperature + temperatureIncrement)) {
      temperature +=
          randomGenerator.nextInt(((int) temperatureIncrement * 2) + 1) - temperatureIncrement;
    }
    else if (temperature < desiredTemperature) {
      temperature += randomGenerator.nextInt((int) temperatureIncrement + 1);
    }
    else {
      temperature -= (randomGenerator.nextInt((int) temperatureIncrement + 1));
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
  public static void setDesiredTemperature(long newDesiredTemperature) {
    desiredTemperature = newDesiredTemperature;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @return The data as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public static DomRepresentation toXml() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element root = doc.createElement("state-data");
    root.setAttribute("system", "hvac");
    root.setAttribute("device", "arduino-3");
    root.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(root);

    // Create state tag.
    Element tempState = doc.createElement("state");
    tempState.setAttribute("key", "temp");
    tempState.setAttribute("value", String.valueOf(temperature));
    root.appendChild(tempState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
