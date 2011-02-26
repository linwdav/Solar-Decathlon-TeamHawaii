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

  // Create variables with "random" values.
  private static Random randomGenerator = new Random();
  /** The current temperature. */
  private static long temperature = randomGenerator.nextInt(41) + 60;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Temperature will change by random value between -3 and 3.
    long currentTemperature = getTemperature();
    if (currentTemperature > 60 && currentTemperature < 100) {
      setTemperature(currentTemperature + (randomGenerator.nextInt(7) - 3));
    }
    else if (currentTemperature < 60) {
      setTemperature(currentTemperature + (randomGenerator.nextInt(4)));
    }
    else {
      setTemperature(currentTemperature - (randomGenerator.nextInt(4)));
    }

    System.out.println("----------------------");
    System.out.println("System: HVAC");
    System.out.println("Temperature: " + getTemperature());
  }

  /**
   * Accessor for temperature.
   * 
   * @return temperature
   */
  public static long getTemperature() {
    return temperature;
  }

  /**
   * Sets the temperature.
   * 
   * @param newTemperature the temperature
   */
  public static void setTemperature(long newTemperature) {
    temperature = newTemperature;
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
    tempState.setAttribute("value", String.valueOf(getTemperature()));
    root.appendChild(tempState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
