package edu.hawaii.ihale.housesimulator.aquaponics;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the Aquaponics system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class AquaponicsData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();
  /** The current temperature. */
  private static long temperature = randomGenerator.nextInt(11) + 60;
  /** The current pH. */
  private static double ph = (randomGenerator.nextDouble() * 3) + 6.5;
  /** The current oxygen. */
  private static double oxygen = (randomGenerator.nextDouble() * 9) + 1;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Temperature will change by random value between -1 and 1.
    long currentTemperature = getTemperature();
    if (currentTemperature > 60 && currentTemperature < 70) {
      setTemperature(currentTemperature + (randomGenerator.nextInt(2) * 2) - 1);
    }
    else if (currentTemperature < 60) {
      setTemperature(currentTemperature + randomGenerator.nextInt(2));
    }
    else {
      setTemperature(currentTemperature - (randomGenerator.nextInt(2)));
    }

    // pH will change randomly between 6.5 and 9.5
    double currentPh = getPh();
    if (currentPh > 6.5 && currentPh < 9.5) {
      if (randomGenerator.nextBoolean()) {
        setPh(currentPh + randomGenerator.nextDouble());
      }
      else {
        setPh(currentPh - randomGenerator.nextDouble());
      }
    }
    else if (currentPh < 6.5) {
      setPh(currentPh + randomGenerator.nextDouble());
    }
    else {
      setPh(currentPh - randomGenerator.nextDouble());
    }

    // Oxygen will change randomly between 1 and 10
    double currentOxygen = getOxygen();
    if (currentOxygen > 1 && currentOxygen < 10) {
      if (randomGenerator.nextBoolean()) {
        setOxygen(currentOxygen + randomGenerator.nextDouble());
      }
      else {
        setOxygen(currentOxygen - randomGenerator.nextDouble());
      }
    }
    else if (currentOxygen < 1) {
      setOxygen(currentOxygen + randomGenerator.nextDouble());
    }
    else {
      setOxygen(currentOxygen - randomGenerator.nextDouble());
    }

    System.out.println("----------------------");
    System.out.println("System: Aquaponics");
    System.out.println("Temperature: " + getTemperature());
    System.out.println("pH: " + roundSingleDecimal(getPh()));
    System.out.println("Oxygen: " + roundSingleDecimal(getOxygen()));
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
  public static void setTemperature(long newTemperature) {
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
   * Rounds double value to a single decimal place.
   * 
   * @param doubleValue A double value
   * @return Rounded value
   */
  static double roundSingleDecimal(double doubleValue) {
    DecimalFormat singleDecimal = new DecimalFormat("#.#");
    return Double.valueOf(singleDecimal.format(doubleValue));
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
    oxygenState.setAttribute("value", String.valueOf(roundSingleDecimal(getOxygen())));
    root.appendChild(oxygenState);

    // Create state tag.
    Element phState = doc.createElement("state");
    phState.setAttribute("key", "pH");
    phState.setAttribute("value", String.valueOf(roundSingleDecimal(getPh())));
    root.appendChild(phState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}
