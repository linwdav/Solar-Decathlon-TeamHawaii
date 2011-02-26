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

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Energy will change by random value between 50 and -50.
    long currentEnergy = getEnergy();
    if (currentEnergy > 1000 && currentEnergy < 2000) {
      setEnergy(currentEnergy + (randomGenerator.nextInt(101) - 50));
    }
    else if (currentEnergy < 1000) {
      setEnergy(currentEnergy + randomGenerator.nextInt(51));
    }
    else {
      setEnergy(currentEnergy - randomGenerator.nextInt(51));
    }

    // Power will change by random value between 50 and -50.
    long currentPower = getPower();
    if (currentPower > 1000 && currentPower < 2000) {
      setPower(currentPower + (randomGenerator.nextInt(101) - 50));
    }
    else if (currentPower < 1000) {
      setPower(currentPower + randomGenerator.nextInt(51));
    }
    else {
      setPower(currentPower - randomGenerator.nextInt(51));
    }

    System.out.println("----------------------");
    System.out.println("System: Electrical");
    System.out.println("Energy: " + getEnergy());
    System.out.println("Power: " + getPower());
  }

  /**
   * Accessor for energy.
   * 
   * @return energy
   */
  public static long getEnergy() {
    return energy;
  }

  /**
   * Accessor for power.
   * 
   * @return power
   */
  public static long getPower() {
    return power;
  }

  /**
   * Sets the energy.
   * 
   * @param newEnergy the energy
   */
  public static void setEnergy(long newEnergy) {
    energy = newEnergy;
  }

  /**
   * Sets the power.
   * 
   * @param newPower the power
   */
  public static void setPower(long newPower) {
    power = newPower;
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
    energy.setTextContent(String.valueOf(getEnergy()));
    meter.appendChild(energy);

    // // Create energyWs tag.
    // Element energyWs = doc.createElement("energyWs");
    // long conversionRatio = 2.7777777777778E-7;
    // energyWs.setTextContent(String.valueOf(getEnergy() / conversionRatio));
    // meter.appendChild(energyWs);

    // Create power tag.
    Element power = doc.createElement("power");
    power.setTextContent(String.valueOf(getPower()));
    meter.appendChild(power);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}
