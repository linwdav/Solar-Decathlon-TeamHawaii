package edu.hawaii.ihale.housesimulator.photovoltaics;

import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the Photovoltaics system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class PhotovoltaicsData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();

  /** The current energy. */
  private static long energy = randomGenerator.nextInt(1501) + 5000;
  /** The current power. */
  private static long power = randomGenerator.nextInt(101) - 100;

  /** The max value energy will increment by. */
  private static final long energyIncrement = 200;
  /** The max value power will increment by. */
  private static final long powerIncrement = 20;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Energy will change by random value between 200 and -200.
    // Increments energy randomly
    if (energy > 5000 && energy < 6500) {
      energy += (randomGenerator.nextInt((int) energyIncrement * 2) - energyIncrement);
    }
    else if (energy < 5000) {
      energy += randomGenerator.nextInt((int) energyIncrement + 1);
    }
    else {
      energy -= randomGenerator.nextInt((int) energyIncrement + 1);
    }

    // Increments power randomly
    if (power > -100 && power < 0) {
      power += randomGenerator.nextInt((int) powerIncrement * 2) - powerIncrement;
    }
    else if (power < -100) {
      power = power + randomGenerator.nextInt((int) powerIncrement + 1);
    }
    else {
      power = power - randomGenerator.nextInt((int) powerIncrement + 1);
    }

    System.out.println("----------------------");
    System.out.println("System: Photovoltaics");
    System.out.println("Energy: " + energy);
    System.out.println("Power: " + power);
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

    // Create root tag.
    Element rootElement = doc.createElement("measurements");
    doc.appendChild(rootElement);

    // Create timestamp tag.
    Element timestampElement = doc.createElement("timestamp");
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(timestampElement);

    // // Create cpower tag.
    // Element cpowerElement = doc.createElement("cpower");
    // double calcCpower = -(Math.random() * 101);
    // cpowerElement.setTextContent(String.valueOf(calcCpower));
    // rootElement.appendChild(cpowerElement);

    // Create meter tag.
    Element meterElement = doc.createElement("meter");
    meterElement.setAttribute("title", "Solar");
    rootElement.appendChild(meterElement);

    // Create energy tag.
    Element energyElement = doc.createElement("energy");
    energyElement.setTextContent(String.valueOf(energy));
    meterElement.appendChild(energyElement);

    // // Create energyWs tag.
    // Element energyWsElement = doc.createElement("energyWs");
    // double conversionRatio = 2.7777777777778E-7;
    // energyWsElement.setTextContent(String.valueOf(energy / conversionRatio));
    // meterElement.appendChild(energyWsElement);

    // Create power tag.
    Element powerElement = doc.createElement("power");
    powerElement.setTextContent(String.valueOf(power));
    meterElement.appendChild(powerElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}