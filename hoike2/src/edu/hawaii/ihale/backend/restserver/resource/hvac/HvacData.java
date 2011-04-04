package edu.hawaii.ihale.backend.restserver.resource.hvac;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
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
public class HvacData extends SystemData {

  /**
   * Returns the data as an XML Document instance.
   * 
   * @return The data as XML.
   * @throws ParserConfigurationException Thrown when a problem occurs when creating the XML.
   * @throws IOException Thrown when a problem occurs creating the DomRepresentation object.
   */
  public static DomRepresentation toXml() throws ParserConfigurationException, IOException {

    TimestampIntegerPair temperature = repository.getHvacTemperature();

    Document doc = createDocument();

    // Creates the state-data root node.
    Node rootNode = appendStateDataNode(doc, doc, IHaleSystem.HVAC, temperature.getTimestamp());

    // Appends the state values.
    appendStateNode(doc, rootNode, IHaleState.TEMPERATURE, temperature.getValue());

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
  public static Representation toXmlSince(Long timestamp) throws ParserConfigurationException,
      IOException {

    if (timestamp == null) {
      throw new RuntimeException("Aquaponics timestamp is invalid.");
    }

    List<TimestampIntegerPair> temperature = repository.getHvacTemperatureSince(timestamp);

    Document doc = createDocument();

    // Creates the state-history root node.
    Node rootNode = appendStateHistoryNode(doc);

    Node stateDataNode;

    // Appends the state-data for each entry.
    int size = temperature.size();
    for (int i = 0; i < size; i++) {
      stateDataNode =
          appendStateDataNode(doc, rootNode, IHaleSystem.HVAC, temperature.get(i).getTimestamp());
      appendStateNode(doc, stateDataNode, IHaleState.TEMPERATURE, temperature.get(i).getValue());
    }
    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
