package edu.hawaii.ihale.housesimulator.hvac;

import java.util.Date;
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
  /** The current temperature. */
  private static double temperature;

  /**
   * Accessor for temperature.
   * 
   * @return temperature
   */
  public static double getTemperature() {
    return temperature;
  }

  /**
   * Sets the temperature.
   * 
   * @param newTemperature the temperature
   */
  public static void setTemperature(double newTemperature) {
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
