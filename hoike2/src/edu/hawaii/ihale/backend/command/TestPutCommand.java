package edu.hawaii.ihale.backend.command;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;

/**
 * Tests the PutCommand class to ensure that the it correctly creates the command XML.
 * 
 * @author Bret K. Ikehara
 */
public class TestPutCommand {

  /**
   * Tests whether element was attached to the document through the DomRepresentation.
   * 
   * @throws ParserConfigurationException Thrown when command XML initiation fails.
   * @throws IOException Thrown when the command XML DomRepresentation fails to be initiated.
   */
  @Test
  public void testAddElement() throws ParserConfigurationException, IOException {

    Element root = null;
    Node arg = null;
    NamedNodeMap attributes = null;
    Double obj = new Double(1);

    PutCommand cmd = new PutCommand(IHaleCommandType.SET_TEMPERATURE);
    cmd.addElement("arg", obj);

    Document doc = cmd.getDomRepresentation().getDocument();

    // Check the root element
    root = doc.getDocumentElement();
    assertEquals("Check command root node", "command", root.getTagName());
    assertEquals("Check command root node's name attribute",
        IHaleCommandType.SET_TEMPERATURE.toString(), root.getAttribute("name"));

    // Check the argument tag.
    arg = root.getFirstChild();
    assertEquals("Check argument tag", "arg", arg.getNodeName());

    // check the argument value.
    attributes = arg.getAttributes();
    assertEquals("Check argument value", obj.toString(), attributes.getNamedItem("value")
        .getNodeValue());
  }
}