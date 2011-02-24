package edu.hawaii.ihale.housesimulator.electrical;

import java.util.Date;
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
  /** The current energy. */
  private static double energy = 1000 + (Math.random() * ((2000 - 1000) + 1));
  /** The current power. */
  private static double power = 1000 + (Math.random() * ((2000 - 1000) + 1));

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Energy will change by random value between 50 and -50.
    double curEnergy = getEnergy();
    if (curEnergy > 1000 && curEnergy < 2000) {
      setEnergy(curEnergy + (Math.random() * 101) - 50);
    }
    else if (curEnergy < 1000) {
      setEnergy(curEnergy + (Math.random() * 50));
    }
    else {
      setEnergy(curEnergy - (Math.random() * 50));
    }

    // Power will change by random value between 50 and -50.
    double curPower = getPower();
    if (curPower > 1000 && curPower < 2000) {
      setPower(curPower + (Math.random() * 101) - 50);
    }
    else if (curPower < 1000) {
      setPower(curPower + (Math.random() * 50));
    }
    else {
      setPower(curPower - (Math.random() * 50));
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
  public static double getEnergy() {
    int tempValue = (int) (energy * 100.0);
    return ((double) tempValue) / 100.0;
  }

  /**
   * Accessor for power.
   * 
   * @return power
   */
  public static double getPower() {
    int tempValue = (int) (power * 100.0);
    return ((double) tempValue) / 100.0;
  }

  /**
   * Sets the energy.
   * 
   * @param newEnergy the energy
   */
  public static void setEnergy(double newEnergy) {
    energy = newEnergy;
  }

  /**
   * Sets the power.
   * 
   * @param newPower the power
   */
  public static void setPower(double newPower) {
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

    // Create cpower tag.
    Element cpower = doc.createElement("cpower");
    double calcCpower = 1000 + (Math.random() * ((2000 - 1000) + 1));
    cpower.setTextContent(String.valueOf(calcCpower));
    root.appendChild(cpower);

    // Create meter tag.
    Element meter = doc.createElement("meter");
    meter.setAttribute("title", "Solar");
    root.appendChild(meter);

    // Create energy tag.
    Element energy = doc.createElement("energy");
    energy.setTextContent(String.valueOf(getEnergy()));
    meter.appendChild(energy);

    // Create energyWs tag.
    Element energyWs = doc.createElement("energyWs");
    double conversionRatio = 2.7777777777778E-7;
    energyWs.setTextContent(String.valueOf(getEnergy() / conversionRatio));
    meter.appendChild(energyWs);

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
