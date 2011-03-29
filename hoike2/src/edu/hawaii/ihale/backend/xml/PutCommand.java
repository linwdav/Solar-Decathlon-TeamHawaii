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
 * Creates the XML used for the PUT command. This class ensures that arguments can be easily
 * implemented to the command XML data for future changes.
 * 
 * @author Bret K. Ikehara
 */
public class PutCommand {

  private IHaleCommandType command;
  private Document document;
  private Element rootElement;

  /**
   * Default Constructor.
   * 
   * @param command IHaleCommandType
   * @throws ParserConfigurationException Thrown when XML document builder fails.
   */
  public PutCommand(IHaleCommandType command) throws ParserConfigurationException {

    if (command == null) {
      throw new NullPointerException("IHaleCommandType was null.");
    }
    
    this.command = command;

    // Create the new document.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    document = docBuilder.newDocument();

    // Creates the root tag
    rootElement = document.createElement("command");
    rootElement.setAttribute("name", this.command.toString());
    document.appendChild(rootElement);
  }

  /**
   * Adds the only valid argument to the XML command document.
   * 
   * @param tagName String
   * @param value String
   */
  public void addElement(String tagName, Object value) {
    Element temperatureElement = document.createElement(tagName);
    temperatureElement.setAttribute("value", value.toString());
    rootElement.appendChild(temperatureElement);
  }

  /**
   * Creates the DomRepresentation of Document. This is sent to the various Arduino devices to be
   * parsed using Restlet's org.restlet.resource.ClientResource class.
   * 
   * @return DomRepresentation
   * @throws IOException Thrown when DomRepresentation instantiation fails.
   */
  public DomRepresentation getDomRepresentation() throws IOException {

    DomRepresentation representation = new DomRepresentation();
    representation.setDocument(document);
    return representation;
  }
  
  /**
   * Represents the command as a String.
   * 
   * @return String
   */
  @Override
  public String toString() {
    return this.command.toString();
  }
}