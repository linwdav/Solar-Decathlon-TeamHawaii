package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.FileRepresentation;

/**
 * JUnit tests for the Parser class.
 * 
 * @author Bret K. Ikehara
 */
public class TestParser {

  //TODO finalize Parser class to ensure that JUnit tests are valid.
  
  /**
   * Tests the parse an empty representation.
   * 
   * @throws IOException Thrown when getting the XML document from the Representation parameter.
   * @throws XPathExpressionException Thrown when XPath parsing fails.
   */
  @Test(expected = java.net.MalformedURLException.class)
  public void parseEmptyRepresentation() throws XPathExpressionException, IOException {
    Parser parser = new Parser();

    DomRepresentation representation = null;

    // Try parse empty representation.
    representation = new DomRepresentation(new EmptyRepresentation());

    parser.parse(representation);
  }
  
  /**
   * Tests the parse an empty representation.
   * 
   * @throws IOException Thrown when getting the XML document from the Representation parameter.
   * @throws XPathExpressionException Thrown when XPath parsing fails.
   */
  @Test(expected = java.lang.NullPointerException.class)
  public void parseNullRepresentation() throws XPathExpressionException, IOException {
    Parser parser = new Parser();

    DomRepresentation representation = null;

    // Try parse a null representation.
    parser.parse(representation);
  }

  /**
   * Tests the parse an representation as an XML file.
   */
  @Test
  public void parseXMLRepresentation() {
    Parser parser = new Parser();

    Map<String, String> attr = null;
    DomRepresentation representation = null;

    // Mock a Restlet GET resource as a XML file.
    FileRepresentation fr =
        new FileRepresentation(new File("./junit.aquaponics.state.xml"), MediaType.TEXT_XML);
    representation = new DomRepresentation(fr);

    // clear Map before adding the new attribute results
    try {
      attr = parser.parse(representation);
    }
    catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("Oxygen Level", "1.8", attr.get("oxygen"));
    assertEquals("Temperature", "62", attr.get("temp"));
    assertEquals("pH Level", "8.5", attr.get("ph"));
  }
}