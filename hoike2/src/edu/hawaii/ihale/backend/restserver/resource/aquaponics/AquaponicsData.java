package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

import java.io.IOException;
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
import edu.hawaii.ihale.backend.IHaleBackend;
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

    TimestampDoublePair circulation = IHaleBackend.repository.getAquaponicsCirculation();
    TimestampDoublePair ec = IHaleBackend.repository.getAquaponicsElectricalConductivity();
    TimestampIntegerPair deadFish = IHaleBackend.repository.getAquaponicsDeadFish();
    TimestampIntegerPair temp = IHaleBackend.repository.getAquaponicsTemperature();
    TimestampDoublePair turbidity = IHaleBackend.repository.getAquaponicsTurbidity();
    TimestampIntegerPair waterLevel = IHaleBackend.repository.getAquaponicsWaterLevel();
    TimestampDoublePair ph = IHaleBackend.repository.getAquaponicsPh();
    TimestampDoublePair oxygen = IHaleBackend.repository.getAquaponicsOxygen();

    Node rootNode = null;

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Creates the state-data root node.
    rootNode = appendStateDataNode(doc, doc, IHaleSystem.AQUAPONICS, circulation.getTimestamp());

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
}
