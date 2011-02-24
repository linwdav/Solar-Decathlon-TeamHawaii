package edu.hawaii.ihale.housesimulator.photovoltaics;

import java.util.Date;
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
  /** The current energy. */
  private static double energy = 5000 + (Math.random() * ((6500 - 5000) + 1));
  /** The current power. */
  private static double power = -(Math.random() * 101);

  /**
   * Accessor for energy.
   * 
   * @return energy
   */
  public static double getEnergy() {
    return energy;
  }

  /**
   * Accessor for power.
   * 
   * @return power
   */
  public static double getPower() {
    return power;
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
    double calcCpower = -(Math.random() * 101);
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
    energyWs.setTextContent(String.valueOf(getEnergy()/conversionRatio));
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
