package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.ihale.backend.restserver.resource.SystemData;

/**
 * Provides data on the Aquaponics system, as well as an XML representation. Adapted from the Team
 * Kai's source code.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 * @author Bret K. Ikehara
 */
public class AquaponicsData extends SystemData {
  
  /**
   * Returns the data as an XML Document instance.
   * 
   * @return The data as XML.
   * @throws ParserConfigurationException Thrown when a problem occurs when creating the XML.
   * @throws IOException Thrown when a problem occurs creating the DomRepresentation object.
   */
  public static DomRepresentation toXml() throws ParserConfigurationException, IOException {

    TimestampDoublePair circulation = repository.getAquaponicsCirculation();
    TimestampDoublePair ec = repository.getAquaponicsElectricalConductivity();
    TimestampIntegerPair deadFish = repository.getAquaponicsDeadFish();
    TimestampIntegerPair temp = repository.getAquaponicsTemperature();
    TimestampDoublePair turbidity = repository.getAquaponicsTurbidity();
    TimestampIntegerPair waterLevel = repository.getAquaponicsWaterLevel();
    TimestampDoublePair ph = repository.getAquaponicsPh();
    TimestampDoublePair oxygen = repository.getAquaponicsOxygen();

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Creates the state-data root node.
    Node rootNode =
        appendStateDataNode(doc, doc, IHaleSystem.AQUAPONICS, circulation.getTimestamp());

    // Appends the state values.
    appendStateNode(doc, rootNode, IHaleState.CIRCULATION, circulation.getValue());
    appendStateNode(doc, rootNode, IHaleState.ELECTRICAL_CONDUCTIVITY, ec.getValue());
    appendStateNode(doc, rootNode, IHaleState.DEAD_FISH, deadFish.getValue());
    appendStateNode(doc, rootNode, IHaleState.TEMPERATURE, temp.getValue());
    appendStateNode(doc, rootNode, IHaleState.TURBIDITY, turbidity.getValue());
    appendStateNode(doc, rootNode, IHaleState.WATER_LEVEL, waterLevel.getValue());
    appendStateNode(doc, rootNode, IHaleState.PH, ph.getValue());
    appendStateNode(doc, rootNode, IHaleState.OXYGEN, oxygen.getValue());

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @param timestamp The timestamp to be used as the "begin" time.
   * @return The data as XML.
   * @throws ParserConfigurationException Thrown when a problem occurs when creating the XML.
   * @throws IOException Thrown when a problem occurs creating the DomRepresentation object.
   */
  public static DomRepresentation toXmlSince(Long timestamp) throws ParserConfigurationException,
      IOException {

    List<TimestampDoublePair> circulation =
        repository.getAquaponicsCirculationSince(timestamp);
    List<TimestampDoublePair> ec =
        repository.getAquaponicsElectricalConductivitySince(timestamp);
    List<TimestampIntegerPair> deadFish =
        repository.getAquaponicsDeadFishSince(timestamp);
    List<TimestampIntegerPair> temp =
        repository.getAquaponicsTemperatureSince(timestamp);
    List<TimestampDoublePair> turbidity =
        repository.getAquaponicsTurbiditySince(timestamp);
    List<TimestampIntegerPair> waterLevel =
        repository.getAquaponicsWaterLevelSince(timestamp);
    List<TimestampDoublePair> ph = repository.getAquaponicsPhSince(timestamp);
    List<TimestampDoublePair> oxygen = repository.getAquaponicsOxygenSince(timestamp);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Creates the state-history root node.
    Node rootNode = appendStateHistoryNode(doc, doc);

    Node stateDataNode;

    // Appends the state-data for each entry.
    int size = circulation.size();
    for (int i = 0; i < size; i++) {
      stateDataNode =
          appendStateDataNode(doc, rootNode, IHaleSystem.AQUAPONICS, circulation.get(i)
              .getTimestamp());
      appendStateNode(doc, stateDataNode, IHaleState.CIRCULATION, circulation.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.ELECTRICAL_CONDUCTIVITY, ec.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.DEAD_FISH, deadFish.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.TEMPERATURE, temp.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.TURBIDITY, turbidity.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.WATER_LEVEL, waterLevel.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.PH, ph.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.OXYGEN, oxygen.get(i).getValue());
    }
    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
