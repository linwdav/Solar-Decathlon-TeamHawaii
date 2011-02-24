package edu.hawaii.ihale.housesimulator.aquaponics;

import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;

/**
 * Provides data on the Aquaponics system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class AquaponicsData {
  /** The current temperature. */
  private static double temperature;
  /** The current pH. */
  private static double ph;
  /** The current oxygen. */
  private static double oxygen;

  /**
   * Accessor for temperature.
   * 
   * @return temperature
   */
  public static double getTemperature() {
    return temperature;
  }

  /**
   * Accessor for ph.
   * 
   * @return ph
   */
  public static double getPh() {
    return ph;
  }

  /**
   * Accessor for oxygen.
   * 
   * @return oxygen
   */
  public static double getOxygen() {
    return oxygen;
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
   * Sets the pH.
   * 
   * @param newPh the ph
   */
  public static void setPh(double newPh) {
    ph = newPh;
  }

  /**
   * Sets the oxygen.
   * 
   * @param newOxygen the oxygen
   */
  public static void setOxygen(double newOxygen) {
    oxygen = newOxygen;
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
    root.setAttribute("system", "aquaponics");
    root.setAttribute("device", "arduino-1");
    root.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(root);

    // Create state tag.
    Element tempState = doc.createElement("state");
    tempState.setAttribute("key", "temp");
    tempState.setAttribute("value", String.valueOf(getTemperature()));
    root.appendChild(tempState);

    // Create state tag.
    Element oxygenState = doc.createElement("state");
    oxygenState.setAttribute("key", "oxygen");
    oxygenState.setAttribute("value", String.valueOf(getOxygen()));
    root.appendChild(oxygenState);

    // Create state tag.
    Element phState = doc.createElement("state");
    phState.setAttribute("key", "pH");
    phState.setAttribute("value", String.valueOf(getPh()));
    root.appendChild(phState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}
