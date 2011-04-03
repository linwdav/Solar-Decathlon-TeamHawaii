package edu.hawaii.ihale.backend.restserver.resource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;

/**
 * Creates the abstract object to reference the data in the repository.
 * 
 * @author Bret K. Ikehara
 * @author Michael Cera
 */
public class SystemData {

  /**
   * Reference to the IHaleBackend static Repository object.
   */
  protected static final Repository repository;

  /**
   * XML state tag name.
   */
  public static final String XML_TAG_STATE;

  /**
   * XML state-data tag name.
   */
  public static final String XML_TAG_STATE_DATA;

  /**
   * XML state-data tag name.
   */
  public static final String XML_TAG_STATE_HISTORY;

  /**
   * XML key attribute name.
   */
  public static final String XML_ATTRIBUTE_KEY;

  /**
   * XML value attribute name.
   */
  public static final String XML_ATTRIBUTE_VALUE;

  /**
   * XML system attribute name.
   */
  public static final String XML_ATTRIBUTE_SYSTEM;

  /**
   * XML timestamp attribute name.
   */
  public static final String XML_ATTRIBUTE_TIMESTAMP;

  /**
   * Initiate the static values.
   */
  static {
    XML_TAG_STATE = "state";
    XML_TAG_STATE_DATA = "state-data";
    XML_TAG_STATE_HISTORY = "state-history";

    XML_ATTRIBUTE_KEY = "key";
    XML_ATTRIBUTE_VALUE = "value";
    XML_ATTRIBUTE_SYSTEM = "system";
    XML_ATTRIBUTE_TIMESTAMP = "timestamp";

    repository = new Repository();
  }

  /**
   * Appends a state key-value pair to the state-data root element, then returns that node.
   * 
   * @param doc Document
   * @param root Node
   * @param key IHaleState
   * @param value Object
   * @return Node
   */
  protected static Node appendStateNode(Document doc, Node root, IHaleState key, Object value) {
    
    Element element = doc.createElement(XML_TAG_STATE);
    element.setAttribute(XML_ATTRIBUTE_KEY, key.toString());
    element.setAttribute(XML_ATTRIBUTE_VALUE, String.valueOf(value));
    root.appendChild(element);

    return element;
  }

  /**
   * Creates the state-data as the root element, then returns that node.
   * 
   * @param doc Document
   * @param root Node
   * @param system IHaleSystem
   * @param timestamp long
   * @return Node
   */
  protected static Node appendStateDataNode(Document doc, Node root, IHaleSystem system,
      long timestamp) {
    Element element = doc.createElement(XML_TAG_STATE_DATA);
    element.setAttribute(XML_ATTRIBUTE_SYSTEM, system.toString());
    element.setAttribute(XML_ATTRIBUTE_TIMESTAMP, String.valueOf(timestamp));
    root.appendChild(element);

    return element;
  }

  /**
   * Creates the state-history as the root element, then returns that node.
   * 
   * @param doc Document
   * 
   * @return Node
   */
  protected static Node appendStateHistoryNode(Document doc) {
    Element element = doc.createElement(XML_TAG_STATE_HISTORY);
    doc.appendChild(element);

    return element;
  }
  
  /**
   * Creates the document.
   * 
   * @return Document
   * 
   * @throws ParserConfigurationException Thrown when document fails to build.
   */
  protected static Document createDocument() throws ParserConfigurationException {
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    final DocumentBuilder docBuilder = factory.newDocumentBuilder();
    
    return docBuilder.newDocument();
  }
}