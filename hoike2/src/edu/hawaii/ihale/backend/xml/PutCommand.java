package edu.hawaii.ihale.backend.xml;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.api.ApiDictionary;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;

/**
 * Creates the XML used for the PUT command. This class ensures that arguments can be easily
 * implemented to the command XML data for future changes.
 * 
 * @author Bret K. Ikehara
 */
public class PutCommand {

  private Document doc;
  private Element rootElement;
  private IHaleState state;

  /**
   * Default Constructor.
   * 
   * @param command IHaleCommandType
   * @throws ParserConfigurationException Thrown when XML document builder fails.
   */
  public PutCommand(IHaleCommandType command) throws ParserConfigurationException {

    state = ApiDictionary.iHaleCommandType2State(command);
    
    // Create the new document.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    doc = docBuilder.newDocument();

    // Creates the root tag
    rootElement = doc.createElement("command");
    rootElement.setAttribute("name", command.toString());
    doc.appendChild(rootElement);
  }

  /**
   * Adds the only valid argument to the XML command document.
   * 
   * @param tagName String
   * @param value String
   * @return boolean
   * @throws Exception Thrown when argument value is invalid.
   */
  public void addArgument(String tagName, Object value) throws ValidTypeException  {
    
    boolean valid = this.state.isType(value.toString());
    
    if (valid) {
      throw new ValidTypeException();
    }
  
    Element temperatureElement = doc.createElement(tagName);
    temperatureElement.setAttribute("value", value.toString());
    rootElement.appendChild(temperatureElement);
  }

  /**
   * Creates the DomRepresentation of Document. This is sent to the various Arduino devices to be parsed
   * using Restlet's org.restlet.resource.ClientResource class.
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