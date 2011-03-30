package edu.hawaii.ihale.housesimulator.photovoltaics;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * Provides data on the Photovoltaics system, as well as an XML representation.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class PhotovoltaicsData {

  /**
   * Wikipedia - http://en.wikipedia.org/wiki/Photovoltaic_array. A typical "150 watt" solar panel
   * is about a square meter in size. Such a panel may be expected to produce 1 kWh every day, on
   * average, after taking into account the weather and the latitude.
   * 
   * We will assume that we have 3 panels 1 meter wide and 2 meters long for 6 kWh to spread out
   * over a single day. We will also assume that 0 energy is generated between the hours of 6pm and
   * 6am. The 25th point represends the daily average.
   */
  private static long[] hourlyAverage = { 1, 1, 1, 1, 1, 1, 3000 / 64, 3000 / 32, 3000 / 16,
      3000 / 8, 3000 / 4, 3000 / 2, 3000 / 2, 3000 / 4, 3000 / 8, 3000 / 16, 3000 / 32, 3000 / 64,
      1, 1, 1, 1, 1, 1, 6000 / 24 };
  /** The current energy. */
  private static long energy = hourlyAverage[0];
  /** The current power. */
  private static long power = 0;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {
    changePoints(Calendar.HOUR_OF_DAY);

    System.out.println("----------------------");
    System.out.println("System: Photovoltaics");
    System.out.println("Energy: " + energy);
    System.out.println("Power: " + power);
  }

  /**
   * Returns the data as an XML Document instance. JavaApiDataDictionary only asks for power and
   * energy, so other e-gauge tags are currently commented out.
   * 
   * @return The data as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public static DomRepresentation toXml() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();
    String meterTag = "meter";
    String energyTag = "energy";
    String powerTag = "power";
    String solarTag = "Solar";
    String titleAttr = "title";
    String cpower = "cpower";
    String srcTag = "src";
    String energyWsTag = "energyWs";
    String gridTag = "Grid";
    String typeAttr = "type";
    String voltageTag = "voltage";
    String currentTag = "current";
    String blank = "blank";

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
     cpowerElementA.setTextContent(blank);
     rootElement.appendChild(cpowerElementA);
    
     // Second cpower tag.
     Element cpowerElementB = doc.createElement(cpower);
     cpowerElementB.setAttribute(srcTag, solarTag);
     cpowerElementB.setAttribute("i", "4");
     cpowerElementB.setAttribute("u", "8");
     cpowerElementB.setTextContent(blank);
     rootElement.appendChild(cpowerElementB);
    
     // Third cpower tag.
     Element cpowerElementC = doc.createElement(cpower);
     cpowerElementC.setAttribute(srcTag, gridTag);
     cpowerElementC.setAttribute("i", "3");
     cpowerElementC.setAttribute("u", "8");
     cpowerElementC.setTextContent(blank);
     rootElement.appendChild(cpowerElementC);
    
     // Fourth cpower tag.
     Element cpowerElementD = doc.createElement(cpower);
     cpowerElementD.setAttribute(srcTag, solarTag);
     cpowerElementD.setAttribute("i", "4");
     cpowerElementD.setAttribute("u", "8");
     cpowerElementD.setTextContent(blank);
     rootElement.appendChild(cpowerElementD);

     // First meter tag for Grid.
     Element meterElementA = doc.createElement(meterTag);
     meterElementA.setAttribute(titleAttr, gridTag);
     rootElement.appendChild(meterElementA);
     // Create energy tag.
     Element energyElementA = doc.createElement(energyTag);
     energyElementA.setTextContent(blank);
     meterElementA.appendChild(energyElementA);
     // Create energyWs tag.
     Element energyWsElementA = doc.createElement(energyWsTag);
     energyWsElementA.setTextContent(blank);
     meterElementA.appendChild(energyWsElementA);
     // Create power tag.
     Element powerElementA = doc.createElement(powerTag);
     powerElementA.setTextContent(blank);
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
     energyWsElement.setTextContent(blank);
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
     energyElementC.setTextContent(blank);
     meterElementC.appendChild(energyElementC);
     // Create energyWs tag.
     Element energyWsElementC = doc.createElement(energyWsTag);
     energyWsElementC.setTextContent(blank);
     meterElementC.appendChild(energyWsElementC);
     // Create power tag.
     Element powerElementC = doc.createElement(powerTag);
     powerElementC.setTextContent(blank);
     meterElementC.appendChild(powerElementC);
    
     // Fourth meter tag for Solar.
     Element meterElementD = doc.createElement(meterTag);
     meterElementD.setAttribute(typeAttr, "total");
     meterElementD.setAttribute("title", "Total Generation");
     rootElement.appendChild(meterElementD);
     // Create energy tag.
     Element energyElementD = doc.createElement(energyTag);
     energyElementD.setTextContent(blank);
     meterElementD.appendChild(energyElementD);
     // Create energyWs tag.
     Element energyWsElementD = doc.createElement(energyWsTag);
     energyWsElementD.setTextContent(blank);
     meterElementD.appendChild(energyWsElementD);
     // Create power tag.
     Element powerElementD = doc.createElement(powerTag);
     powerElementD.setTextContent(blank);
     meterElementD.appendChild(powerElementD);
    
     // Frequency tag.
     Element frequencyElement = doc.createElement("frequency");
     frequencyElement.setTextContent("value here");
     rootElement.appendChild(frequencyElement);
     // First voltage tag.
     Element voltageElementA = doc.createElement(voltageTag);
     voltageElementA.setTextContent(blank);
     rootElement.appendChild(voltageElementA);
     // Second voltage tag.
     Element voltageElementB = doc.createElement(voltageTag);
     voltageElementB.setTextContent(blank);
     rootElement.appendChild(voltageElementB);
     // First current tag.
     Element currentElementA = doc.createElement(currentTag);
     currentElementA.setTextContent(blank);
     rootElement.appendChild(currentElementA);
     // Second current tag.
     Element currentElementB = doc.createElement(currentTag);
     currentElementB.setTextContent(blank);
     rootElement.appendChild(currentElementB);
     // Third current tag.
     Element currentElementC = doc.createElement(currentTag);
     currentElementC.setTextContent(blank);
     rootElement.appendChild(currentElementC);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

  /**
   * Works backwards to generate historic electrical data.
   * 
   * @param baseTime The original time to work backwards from.
   * @param doc The document before historic data is appended.
   * @return doc The updated document..
   */
  public static Document generateHistoricData(Map<String, Integer> baseTime, Document doc) {
    int hour = baseTime.get("hour");
    int minute = baseTime.get("minute");
    int timestamp = baseTime.get("timestamp");
    String stateData = "state-data";
    String system = "system";
    String tStamp = "timestamp";
    String stateKey = "state-key";
    String pvString = IHaleSystem.PHOTOVOLTAIC.toString();
    String keyString = "key";
    String valueString = "value";
    String energyString = IHaleState.ENERGY.toString();
    String powerString = IHaleState.POWER.toString();
    
    changePoints(hour);
    Element parent = doc.getDocumentElement();
    long tempTime = timestamp;
    // 12 state data points at 5 min intervals (to compose 1 hour report)
    for (int i = 0; i < 12; i++) {
      Element temp = doc.createElement(stateData);
      temp.setAttribute(system, pvString);
      temp.setAttribute(tStamp, "" + tempTime);
      Element tempElectric = doc.createElement(stateKey);
      tempElectric.setAttribute(keyString, energyString);
      tempElectric.setAttribute(valueString, "" + energy);
      temp.appendChild(tempElectric);
      Element tempPower = doc.createElement(stateKey);
      tempPower.setAttribute(keyString, powerString);
      tempPower.setAttribute(valueString, "" + power);
      temp.appendChild(tempPower);
      parent.appendChild(temp);
      // subtract 5 minutes in e-gauge time
      tempTime = tempTime - 300;
      minute = minute - 5;
      // adjust in case hour must change
      if (minute < 0) {
        minute = 60 + minute;
        hour = hour - 1;
      }
      // adjust in case day must change
      if (hour < 0) {
        hour = 23;
      }
      changePoints(hour);
    }
    hour = baseTime.get("hour");
    tempTime = timestamp;
    changePoints(hour);
    // 24 state data points at 1 hour intervals (to compose 1 day report)
    for (int i = 0; i < 24; i++) {
      Element temp = doc.createElement(stateData);
      temp.setAttribute(system, pvString);
      temp.setAttribute(tStamp, "" + tempTime);
      Element tempElectric = doc.createElement(stateKey);
      tempElectric.setAttribute(keyString, energyString);
      tempElectric.setAttribute(valueString, "" + energy);
      temp.appendChild(tempElectric);
      Element tempPower = doc.createElement(stateKey);
      tempPower.setAttribute(keyString, powerString);
      tempPower.setAttribute(valueString, "" + power);
      temp.appendChild(tempPower);
      parent.appendChild(temp);
      tempTime = tempTime - 3600;
      hour = hour - 1;
      if (hour < 0) {
        hour = 23;
      }
      changePoints(hour);
    }
    // 7 state data points at 1 day intervals (to compose 1 week report)
    // 21-24 additional state data points at 1 day intervals (to compose 1 month report)
    // We know that the total is 6000 Wh a day, 6000/24 is the daily average.
    // We will fluctuate around the average.
    tempTime = timestamp;
    changePoints(24);
    for (int i = 0; i < 31; i++) {
      Element temp = doc.createElement(stateData);
      temp.setAttribute(system, pvString);
      temp.setAttribute(tStamp, "" + tempTime);
      Element tempElectric = doc.createElement(stateKey);
      tempElectric.setAttribute(keyString, pvString);
      tempElectric.setAttribute(valueString, "" + energy);
      temp.appendChild(tempElectric);
      Element tempPower = doc.createElement(stateKey);
      tempPower.setAttribute(keyString, powerString);
      tempPower.setAttribute(valueString, "" + power);
      temp.appendChild(tempPower);
      parent.appendChild(temp);

      tempTime = tempTime - 86400;
      changePoints(24);
    }

    return doc;
  }

  /**
   * Changes the data points based upon the hour provided.
   * 
   * @param hour The hour to base electricity consumption around.
   */
  public static void changePoints(int hour) {
    Random random = new Random();
    int randP = random.nextInt(10);
    int randE = random.nextInt(10);
    //System.out.println(hour);
    long changeValue = (long) (random.nextInt((int) hourlyAverage[hour]));
    if (hourlyAverage[hour] == 1) {
      energy = 1;
    }
    if (randP < 5) {
      energy = hourlyAverage[hour] - changeValue;
    }
    else {
      energy = hourlyAverage[hour] + changeValue;
    }

    changeValue = (long) (random.nextInt((int) hourlyAverage[hour]));

    if (hour < 6 || hour > 17) {
      power = 0;
    }
    else if (randE < 3) {
      power = changeValue * 3 / 4;
    }
    else if (randE < 6) {
      power = changeValue * 3 / 4;
    }
    else {
      power = changeValue / 4;
    }
    // Repetition of the previous code with variation for the daily average.
    if (hour == 24) {
      changeValue = (long) (random.nextInt((int) (hourlyAverage[hour] / 10)));
      if (randP < 5) {
        energy = hourlyAverage[hour] - changeValue;
      }
      else {
        energy = hourlyAverage[hour] + changeValue;
      }
      changeValue = (long) (random.nextInt((int) (hourlyAverage[hour] / 10)));
      if (randE < 3) {
        power = changeValue * 3 / 4;
      }
      else if (randE < 6) {
        power = changeValue * 3 / 4;
      }
      else {
        power = changeValue / 4;
      }
    }
  }

}