package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

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

import edu.hawaii.ihale.api.repository.impl.Repository;;

/**
 * Handles parsing XML documents and storing to the repository.
 * @author Tony Gaskell
 */
public class XmlTranslator {
  private static XPathFactory factory = XPathFactory.newInstance(); 
  private static XPath xpath = factory.newXPath();
  
  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
    String stateSample =
      "<state-data system=\"SystemName\" device=\"DeviceName\" timestamp=\"2345789\">" +
      "<state key=\"key1\" value=\"value1\"/>" +
      "<state key=\"key2\" value=\"value2\"/>" +
      "<state key=\"key3\" value=\"value3\"/>" +
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
    "</state-history>";
    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();

    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document =
     builder.parse(new InputSource(new StringReader(stateSample)));

    xml2StateEntry(document);
    
  }
  
  public static Boolean xml2StateEntry(Document doc)
    throws IOException, XPathExpressionException {

//    Repository repository = new Repository();
//    DomRepresentation dom = new DomRepresentation(rep);
//    Document doc = dom.getDocument();
//    Map<String,String> ret = new HashMap <String,String>();  // Don't need this

    XPathExpression systemPath = xpath.compile("//state-data");
    Object result = systemPath.evaluate(doc, XPathConstants.NODESET);
    NodeList stateData = (NodeList) result;
    String system;
    Double timestamp;
    NodeList state;
    String key;
    String value;
    for (int i = 0; i < stateData.getLength(); i++) {
      system = stateData.item(i).getAttributes().getNamedItem("system").getTextContent();
      timestamp = Double.parseDouble(stateData.item(i).getAttributes().getNamedItem("timestamp").getTextContent());
      System.out.print (system + " " + timestamp);
      state = stateData.item(i).getChildNodes();
      System.out.println(state.getLength());
      for (int j = 0; j < state.getLength(); j++) {
        key = state.item(j).getAttributes().getNamedItem("key").getTextContent();
        value = state.item(j).getAttributes().getNamedItem("value").getTextContent();
        System.out.println(key + " " + value);
//        ret.put(stateData.item(j).getAttributes().getNamedItem("key").getTextContent(), // test
//          stateData.item(j).getAttributes().getNamedItem("value").getTextContent()); // test
      }
    }
//    System.out.println(ret); //test
    return true;
  }
}