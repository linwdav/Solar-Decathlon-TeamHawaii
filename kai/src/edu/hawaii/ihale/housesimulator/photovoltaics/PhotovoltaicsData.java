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
    Element root = doc.createElement("measurements");
    doc.appendChild(root);

    // Create timestamp tag.
    Element timestamp = doc.createElement("timestamp");
    timestamp.setTextContent(String.valueOf(new Date().getTime()));
    root.appendChild(timestamp);

    // // Create cpower tag.
    // Element cpower = doc.createElement("cpower");
    // double calcCpower = -(Math.random() * 101);
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
    // double conversionRatio = 2.7777777777778E-7;
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