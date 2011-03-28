package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document; 
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.repository.impl.Repository;

/**
 * Handles parsing XML documents and storing to the repository.
 * @author Tony Gaskell
 */
public class XmlHandler {
  static Repository repository = new Repository();
  private static XPathFactory factory = XPathFactory.newInstance(); 
  private static XPath xpath = factory.newXPath();
  
/*
 * Test method
 * 
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
    String stateSample =
      "<state-data system=\"LIGHTING\" device=\"arduino-5\" timestamp=\"1297446335\">" +
        "<state key=\"LIGHTING_LEVEL\" value=\"20\"/>" +
        "<state key=\"LIGHTING_ENABLED\" value=\"true\"/>" +
        "<state key=\"LIGHTING_COLOR\" value=\"#FF0000\"/>" +
      "</state-data>";
    String historySample =
      "<state-history>" +
      "<state-data system=\"AQUAPONICS\" timestamp=\"1300165346944\">" +
        "<state key=\"CIRCULATION\" value=\"25.0\"/>" +
        "<state key=\"DEAD_FISH\" value=\"25\"/>" +
        "<state key=\"ELECTRICAL_CONDUCTIVITY\" value=\"25.0\"/>" +
        "<state key=\"TEMPERATURE\" value=\"25\"/>" +
        "<state key=\"TURBIDITY\" value=\"25.0\"/>" +
        "<state key=\"WATER_LEVEL\" value=\"25\"/>" +
        "<state key=\"PH\" value=\"4.5\"/>" +
        "<state key=\"OXYGEN\" value=\"7.0\"/>" +
      "</state-data>" +
      "<state-data system=\"AQUAPONICS\" timestamp=\"1300165348654\">" +
        "<state key=\"CIRCULATION\" value=\"26.2\"/>" +
        "<state key=\"DEAD_FISH\" value=\"23\"/>" +
        "<state key=\"ELECTRICAL_CONDUCTIVITY\" value=\"12.0\"/>" +
        "<state key=\"TEMPERATURE\" value=\"28\"/>" +
        "<state key=\"TURBIDITY\" value=\"21.3\"/>" +
        "<state key=\"WATER_LEVEL\" value=\"23\"/>" +
        "<state key=\"PH\" value=\"6.5\"/>" +
        "<state key=\"OXYGEN\" value=\"12.0\"/>" +
      "</state-data>" +
      "<state-data system=\"AQUAPONICS\" timestamp=\"130016535721\">" +
        "<state key=\"CIRCULATION\" value=\"28.4\"/>" +
        "<state key=\"DEAD_FISH\" value=\"12\"/>" +
        "<state key=\"ELECTRICAL_CONDUCTIVITY\" value=\"14.2\"/>" +
        "<state key=\"TEMPERATURE\" value=\"30\"/>" +
        "<state key=\"TURBIDITY\" value=\"22.9\"/>" +
        "<state key=\"WATER_LEVEL\" value=\"20\"/>" +
        "<state key=\"PH\" value=\"8.5\"/>" +
        "<state key=\"OXYGEN\" value=\"17.0\"/>" +
      "</state-data>" +
      "<state-data system=\"LIGHTING\" room=\"KITCHEN\" timestamp=\"130016535721\">" +
      "<state key=\"CIRCULATION\" value=\"28.4\"/>" +
      "<state key=\"DEAD_FISH\" value=\"12\"/>" +
      "<state key=\"ELECTRICAL_CONDUCTIVITY\" value=\"14.2\"/>" +
      "<state key=\"TEMPERATURE\" value=\"30\"/>" +
      "<state key=\"TURBIDITY\" value=\"22.9\"/>" +
      "<state key=\"WATER_LEVEL\" value=\"20\"/>" +
      "<state key=\"PH\" value=\"8.5\"/>" +
      "<state key=\"OXYGEN\" value=\"17.0\"/>" +
    "</state-data>" +
    "</state-history>";
    String eGaugeSample =
      "<measurements>" +
        "<timestamp>1284607004</timestamp>" +
        "<cpower src=\"Grg&amp;Bth (PHEV)\" i=\"11\" u=\"1\">-988.9</cpower>" +
        "<cpower src=\"Solar\" i=\"5\" u=\"8\">-1.9</cpower>" +
        "<cpower src=\"Grid\" i=\"3\" u=\"1\">1621.5</cpower>" +
        "<meter title=\"Grid\">" +
          "<energy>1443.5</energy>" +
          "<energyWs>5196771697</energyWs>" +
          "<power>2226.2</power>" +
        "</meter>" +
        "<meter title=\"Solar\">" +
          "<energy>5918.9</energy>" +
          "<energyWs>21308130148</energyWs>" +
          "<power>-3.5</power>" +
        "</meter>" +
        "<meter title=\"Grg&amp;Bth (PHEV)\">" +
        	"<energy>4889.2</energy>" +
        	"<energyWs>17601054087</energyWs>" +
        	"<power>-988.9</power>" +
        	"</meter>" +
        	"<frequency>59.98</frequency>" +
        	"<voltage>119.0</voltage>" +
        	"<voltage>118.3</voltage>" +
        	"<current>5.495</current>" +
        	"<current>14.152</current>" +
        	"<current>0.223</current>" +
        	"<current>0.136</current>" +
       "</measurements>";
    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();

    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document =
     builder.parse(new InputSource(new StringReader(eGaugeSample)));

    xml2StateEntry(document);

//    eGauge2StateEntry(document);
    
//    System.out.println(repository.getElectricalEnergy().getValue());
//    System.out.println(repository.getElectricalPower().getValue());
    
  }
  */
  
  /**
   * Takes in an XML document formatted according to the System-H REST API specified here:
   * <url>http://code.google.com/p/solar-decathlon-teamhawaii/wiki/HouseSystemRestAPI</url>
   * as of March 15, 2011, and stores it to the IHaleRepository.
   * 
   * @author Gregory Burgess, Tony Gaskell
   */
  public static Boolean xml2StateEntry(Document doc)
    throws IOException, XPathExpressionException {

//    This currently only works with getHistory() which may be all we need it to work with.

//    In theory this method should be all-or-nothing.
//    If something fails to load, they should ALL fail.  Source: ICS 321.

    XPathExpression systemPath = xpath.compile("//state-data");
    Object result = systemPath.evaluate(doc, XPathConstants.NODESET);
    NodeList stateData = (NodeList) result;

    // An unhealthy amount of temporary variables.
    String system;
    Long timestamp;
    NodeList state;
    String device;
    String key;
    String value;
    IHaleSystem systemEnum;
    IHaleState stateEnum;
    IHaleRoom roomEnum = null;
    Object finalVal = null;
    
    for (int i = 0; i < stateData.getLength(); i++) {
      system = stateData.item(i).getAttributes().getNamedItem("system").getTextContent();
      systemEnum = Enum.valueOf(IHaleSystem.class, system);
      device = stateData.item(i).getAttributes().getNamedItem("device").getTextContent();
      try {
        if (device.equals("arduino-5")) {
          roomEnum = IHaleRoom.LIVING;
        }
        else if (device.equals("arduino-6")) {
          roomEnum = IHaleRoom.DINING;
        }
        else if (device.equals("arduino-7")) {
          roomEnum = IHaleRoom.KITCHEN;
        }
        else if (device.equals("arduino-8")) {
          roomEnum = IHaleRoom.BATHROOM;
        }
      }
      catch (Exception e) {
        // No room attribute was found, skip.
      }

      timestamp = Long.parseLong(stateData.item(i).getAttributes().
          getNamedItem("timestamp").getTextContent());
//      System.out.println (system + " " + timestamp);
      state = stateData.item(i).getChildNodes();

      // Storing the XML node as a StateEntry.
      for (int j = 0; j < state.getLength(); j++) {
        key = state.item(j).getAttributes().getNamedItem("key").getTextContent();
        stateEnum = Enum.valueOf(IHaleState.class, key);
        value = state.item(j).getAttributes().getNamedItem("value").getTextContent();
        if (stateEnum.getType().equals(Double.class)) {
          finalVal = Double.parseDouble(value);
        }
        else if (stateEnum.getType().equals(Integer.class)) {
          finalVal = Integer.parseInt(value);
        }
        else if (stateEnum.getType().equals(Boolean.class)) {
          finalVal = Boolean.parseBoolean(value);
        }
        else if (stateEnum.getType().equals(String.class)) {
          finalVal = value;
        }
        else {
          System.err.println("ERROR: Could not parse value: " + value + 
              " for IHaleState: " + stateEnum.toString());
        }
//        System.out.println(key + " " + String.valueOf(finalVal));

        repository.store(systemEnum, roomEnum, stateEnum, timestamp, finalVal);
      }
      // Set the room to null since it is not guaranteed to be in the next state-data node.
      roomEnum = null;
    }
    // This return value is supposed to be for testing purposes,
    // but I need to come up with an instance of when this thing would fail.
    // It's useless right now.
    return true;
  }
  
  public Boolean xml2StateEntry(Representation rep)
  throws IOException, XPathExpressionException {

//  This should handle all poll() requests

//  In theory this method should be all-or-nothing.
//  If something fails to load, they should ALL fail.  Source: ICS 321.

    // Convert Representation to Document so we can operate on it with XPath.
    DomRepresentation dom = new DomRepresentation(rep);
    Document doc = dom.getDocument();
    return xml2StateEntry(doc);
  }

}