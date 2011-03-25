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
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class ElectricalData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();
  
  /** Each index represents an estimated hourly average of electricity consumption. 
  Values based around those found in the following link and scalled from MW to W- 
  http://www.ferc.gov/market-oversight/mkt-electric/california/CAISO-rto-dly-rpt.pdf.*/
  private static long[] hourlyAverage = {
    164, 162, 155, 150, 158, 164, 189, 212, 
    199, 191, 197, 198, 191, 190, 189, 188, 
    185, 186, 191, 223, 208, 207, 198, 188};

  /** The current energy. */
  private static long energy = hourlyAverage[0];//randomGenerator.nextInt(1001) + 1000;
  /** The current power. */
  private static long power = hourlyAverage[0];//randomGenerator.nextInt(1001) + 1000;

  /** The max value energy will increment by. */
  private static final long energyIncrement = 50;
  /** The max value power will increment by. */
  private static final long powerIncrement = 50;
  
  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

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
    long calcCpower;
    Document doc = docBuilder.newDocument();
    String cpower = "cpower";
    String srcTag = "src";
    String meterTag = "meter";
    String energyTag = "energy";
    String energyWsTag = "energyWs";
    String powerTag = "power";
    String solarTag = "Solar";
    String gridTag = "Grid";
    String titleAttr = "title";
    String typeAttr = "type";
    String voltageTag = "voltage";
    String currentTag = "current";
    
    doc.setXmlVersion("1.0");

    // Create root tag.
    Element rootElement = doc.createElement("measurements");
    doc.appendChild(rootElement);

    
    // Create timestamp tag.
    Element timestampElement = doc.createElement("timestamp");
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(timestampElement);

    // Create first cpower tag.
    Element cpowerElementA = doc.createElement(cpower);
    cpowerElementA.setAttribute(srcTag, gridTag);
    cpowerElementA.setAttribute("i", "2");
    cpowerElementA.setAttribute("u", "8");
    calcCpower = (long) (1000 + (Math.random() * ((2000 - 1000) + 1)));
    cpowerElementA.setTextContent(String.valueOf(calcCpower));
    rootElement.appendChild(cpowerElementA);
    
    // Second cpower tag.
    Element cpowerElementB = doc.createElement(cpower);
    cpowerElementB.setAttribute(srcTag, solarTag);
    cpowerElementB.setAttribute("i", "4");
    cpowerElementB.setAttribute("u", "8");
    calcCpower = (long) (1000 + (Math.random() * ((2000 - 1000) + 1)));
    cpowerElementB.setTextContent(String.valueOf(calcCpower));
    rootElement.appendChild(cpowerElementB);     

    // Third cpower tag.
    Element cpowerElementC = doc.createElement(cpower);
    cpowerElementC.setAttribute(srcTag, gridTag);
    cpowerElementC.setAttribute("i", "3");
    cpowerElementC.setAttribute("u", "8");
    calcCpower = (long) (1000 + (Math.random() * ((2000 - 1000) + 1)));
    cpowerElementC.setTextContent(String.valueOf(calcCpower));
    rootElement.appendChild(cpowerElementC);
    
    // Fourth cpower tag.
    Element cpowerElementD = doc.createElement(cpower);
    cpowerElementD.setAttribute(srcTag, solarTag);
    cpowerElementD.setAttribute("i", "4");
    cpowerElementD.setAttribute("u", "8");
    calcCpower = (long) (1000 + (Math.random() * ((2000 - 1000) + 1)));
    cpowerElementD.setTextContent(String.valueOf(calcCpower));
    rootElement.appendChild(cpowerElementD);  
    
    // First meter tag for Grid.
    Element meterElementA = doc.createElement(meterTag);
    meterElementA.setAttribute(titleAttr, gridTag);
    rootElement.appendChild(meterElementA);
    // Create energy tag.
    Element energyElementA = doc.createElement(energyTag);
    energyElementA.setTextContent(String.valueOf(energy));
    meterElementA.appendChild(energyElementA);
    // Create energyWs tag.
    Element energyWsElementA = doc.createElement(energyWsTag);
    long conversionRatioA = (long) 2.777777;
    energyWsElementA.setTextContent(String.valueOf(energy / conversionRatioA));
    meterElementA.appendChild(energyWsElementA);
    // Create power tag.
    Element powerElementA = doc.createElement(powerTag);
    powerElementA.setTextContent(String.valueOf(power));
    meterElementA.appendChild(powerElementA);

    
    // Second meter tag for Solar.
    Element meterElementB = doc.createElement(meterTag);
    meterElementB.setAttribute(titleAttr, solarTag);
    rootElement.appendChild(meterElementB);
    // Create energy tag.
    Element energyElementB = doc.createElement(energyTag);
    energyElementB.setTextContent(String.valueOf(energy));
    meterElementB.appendChild(energyElementB);
    // Create energyWs tag.
    Element energyWsElement = doc.createElement(energyWsTag);
    long conversionRatioB = (long) 2.777777;
    energyWsElement.setTextContent(String.valueOf(energy / conversionRatioB));
    meterElementB.appendChild(energyWsElement);
    // Create power tag.
    Element powerElementB = doc.createElement(powerTag);
    powerElementB.setTextContent(String.valueOf(power));
    meterElementB.appendChild(powerElementB);
    
    
    // Third meter tag for Grid.
    Element meterElementC = doc.createElement(meterTag);
    meterElementC.setAttribute(typeAttr, "total");
    meterElementC.setAttribute("title", "Total Usage");
    rootElement.appendChild(meterElementC);
    // Create energy tag.
    Element energyElementC = doc.createElement(energyTag);
    energyElementC.setTextContent(String.valueOf(energy));
    meterElementC.appendChild(energyElementC);
    // Create energyWs tag.
    Element energyWsElementC = doc.createElement(energyWsTag);
    long conversionRatioC = (long) 2.777777;
    energyWsElementC.setTextContent(String.valueOf(energy / conversionRatioC));
    meterElementC.appendChild(energyWsElementC);
    // Create power tag.
    Element powerElementC = doc.createElement(powerTag);
    powerElementC.setTextContent(String.valueOf(power));
    meterElementC.appendChild(powerElementC);
    
    
    // Fourth meter tag for Solar.
    Element meterElementD = doc.createElement(meterTag);
    meterElementD.setAttribute(typeAttr, "total");
    meterElementD.setAttribute("title", "Total Generation");
    rootElement.appendChild(meterElementD);
    // Create energy tag.
    Element energyElementD = doc.createElement(energyTag);
    energyElementD.setTextContent(String.valueOf(energy));
    meterElementD.appendChild(energyElementD);
    // Create energyWs tag.
    Element energyWsElementD = doc.createElement(energyWsTag);
    long conversionRatioD = (long) 2.777777;
    energyWsElementD.setTextContent(String.valueOf(energy / conversionRatioD));
    meterElementD.appendChild(energyWsElementD);
    // Create power tag.
    Element powerElementD = doc.createElement(powerTag);
    powerElementD.setTextContent(String.valueOf(power));
    meterElementD.appendChild(powerElementD);
    
    
    // Frequency tag.
    Element frequencyElement = doc.createElement("frequency");
    frequencyElement.setTextContent("value here");
    rootElement.appendChild(frequencyElement);
    // First voltage tag.
    Element voltageElementA = doc.createElement(voltageTag);
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(voltageElementA);
    // Second voltage tag.
    Element voltageElementB = doc.createElement(voltageTag);
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(voltageElementB);
    // First current tag.
    Element currentElementA = doc.createElement(currentTag);
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(currentElementA);
    // Second current tag.
    Element currentElementB = doc.createElement(currentTag);
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(currentElementB);
    // Third current tag.
    Element currentElementC = doc.createElement(currentTag);
    timestampElement.setTextContent(String.valueOf(new Date().getTime()));
    rootElement.appendChild(currentElementC);
    
    
    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
      return result;

  }

}