package edu.hawaii.ihale.housesimulator.electrical;

import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the ElectricalData system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class ElectricalData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();

  /** The current energy. */
  private static long energy = randomGenerator.nextInt(1001) + 1000;
  /** The current power. */
  private static long power = randomGenerator.nextInt(1001) + 1000;

  /** The max value energy will increment by. */
  private static final long energyIncrement = 50;
  /** The max value power will increment by. */
  private static final long powerIncrement = 50;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Increments energy randomly
    if (energy > 1000 && energy < 2000) {
      energy += (randomGenerator.nextInt((int) energyIncrement * 2) - energyIncrement);
    }
    else if (energy < 1000) {
      energy += randomGenerator.nextInt((int) energyIncrement + 1);
    }
    else {
      energy -= randomGenerator.nextInt((int) energyIncrement + 1);
    }

    // Increments power randomly
    if (power > 1000 && power < 2000) {
      power += randomGenerator.nextInt((int) powerIncrement * 2) - powerIncrement;
    }
    else if (power < 1000) {
      power = power + randomGenerator.nextInt((int) powerIncrement + 1);
    }
    else {
      power = power - randomGenerator.nextInt((int) powerIncrement + 1);
    }

    System.out.println("----------------------");
    System.out.println("System: Electrical");
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
    Element root = doc.createElement("measurements");
    doc.appendChild(root);

    // Create timestamp tag.
    Element timestamp = doc.createElement("timestamp");
    timestamp.setTextContent(String.valueOf(new Date().getTime()));
    root.appendChild(timestamp);

    // // Create cpower tag.
    // Element cpower = doc.createElement("cpower");
    // long calcCpower = 1000 + (Math.random() * ((2000 - 1000) + 1));
    // cpower.setTextContent(String.valueOf(calcCpower));
    // root.appendChild(cpower);

    // Create meter tag.
    Element meter = doc.createElement("meter");
    meter.setAttribute("title", "Solar");
    root.appendChild(meter);

    // Create energy tag.
    Element energy = doc.createElement("energy");
    energy.setTextContent(String.valueOf(energy));
    meter.appendChild(energy);

    // // Create energyWs tag.
    // Element energyWs = doc.createElement("energyWs");
    // long conversionRatio = 2.7777777777778E-7;
    // energyWs.setTextContent(String.valueOf(energy / conversionRatio));
    // meter.appendChild(energyWs);

    // Create power tag.
    Element power = doc.createElement("power");
    power.setTextContent(String.valueOf(power));
    meter.appendChild(power);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}