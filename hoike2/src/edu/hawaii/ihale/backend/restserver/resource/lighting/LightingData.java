package edu.hawaii.ihale.backend.restserver.resource.lighting;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.TimestampBooleanPair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.ihale.api.repository.TimestampStringPair;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.restserver.resource.SystemData;

/**
 * Provides data on the Lighting system, as well as an XML representation. Adapted from the Team
 * Kai's source code.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 * @author Bret K. Ikehara
 */
public class LightingData extends SystemData {
  private static Repository repository = new Repository();

  /**
   * Returns the data as an XML Document instance.
   * 
   * @param room The IHaleRoom.
   * @return The data as XML.
   * @throws ParserConfigurationException Thrown when a problem occurs when creating the XML.
   * @throws IOException Thrown when a problem occurs creating the DomRepresentation object.
   */
  public static DomRepresentation toXml(IHaleRoom room) throws ParserConfigurationException,
      IOException {

    TimestampIntegerPair level = repository.getLightingLevel(room);
    TimestampBooleanPair enabled = repository.getLightingEnabled(room);
    TimestampStringPair color = repository.getLightingColor(room);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Creates the state-data root node.
    Node rootNode = appendStateDataNode(doc, doc, IHaleSystem.LIGHTING, level.getTimestamp());

    // Appends the state values.
    appendStateNode(doc, rootNode, IHaleState.LIGHTING_LEVEL, level.getValue());
    appendStateNode(doc, rootNode, IHaleState.LIGHTING_ENABLED, enabled.getValue());
    appendStateNode(doc, rootNode, IHaleState.LIGHTING_COLOR, color.getValue());

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @param room The IHaleRoom.
   * @param timestamp The timestamp to be used as the "begin" time.
   * @return The data as XML.
   * @throws ParserConfigurationException Thrown when a problem occurs when creating the XML.
   * @throws IOException Thrown when a problem occurs creating the DomRepresentation object.
   */
  public static DomRepresentation toXmlSince(IHaleRoom room, Long timestamp)
      throws ParserConfigurationException, IOException {

    List<TimestampIntegerPair> level = repository.getLightingLevelSince(room, timestamp);
    List<TimestampBooleanPair> enabled = repository.getLightingEnabledSince(room, timestamp);
    List<TimestampStringPair> color = repository.getLightingColorSince(room, timestamp);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Creates the state-history root node.
    Node rootNode = appendStateHistoryNode(doc, doc);

    Node stateDataNode;

    // Appends the state-data for each entry.
    int size = level.size();
    for (int i = 0; i < size; i++) {
      stateDataNode =
          appendStateDataNode(doc, rootNode, IHaleSystem.LIGHTING, level.get(i).getTimestamp());
      appendStateNode(doc, stateDataNode, IHaleState.LIGHTING_LEVEL, level.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.LIGHTING_ENABLED, enabled.get(i).getValue());
      appendStateNode(doc, stateDataNode, IHaleState.LIGHTING_COLOR, color.get(i).getValue());
    }
    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
