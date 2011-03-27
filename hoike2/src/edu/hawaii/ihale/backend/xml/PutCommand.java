package edu.hawaii.ihale.backend.xml;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;

/**
 * Creates the XML used for the PUT command.
 * 
 * @author Bret K. Ikehara
 */
public class PutCommand {

  private Document doc;
  private Element rootElement;

  /**
   * Default Constructor.
   * 
   * @param command IHaleCommandType
   * @throws ParserConfigurationException Thrown when XML document builder fails.
   */
  public PutCommand(IHaleCommandType command) throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    doc = docBuilder.newDocument();

    // Creates the root tag
    rootElement = doc.createElement("command");
    rootElement.setAttribute("name", command.toString());
    doc.appendChild(rootElement);
  }

  /**
   * Adds the argument values to the document.
   * 
   * @param tagName String
   * @param value String
   */
  public void addArgument(String tagName, Object value) {
    Element temperatureElement = doc.createElement(tagName);
    temperatureElement.setAttribute("value", value.toString());
    rootElement.appendChild(temperatureElement);
  }

  /**
   * Creates the DomRepresentation of Document.
   * 
   * @return DomRepresentation
   * @throws IOException Thrown when DomRepresentation instantiation fails.
   */
  public DomRepresentation getRepresentation() throws IOException {

    DomRepresentation representation = new DomRepresentation();
    representation.setDocument(doc);

    return representation;
  }
}