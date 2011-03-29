package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * JUnit tests the IHaleBackend.
 * 
 * @author Bret K. Ikehara
 */
public class TestIHaleBackend {

  private static IHaleBackend backend;

  /**
   * Cleans up the backend.
   */
  @BeforeClass
  public static void beforeClass() {
    backend = new IHaleBackend();
  }

  /**
   * Tests the HVAC doCommand for null input.
   */
  @Test(expected = RuntimeException.class)
  public void doCommandNullIHaleCommandType() {
    backend.doCommand(IHaleSystem.HVAC, null, null, null);
  }

  /**
   * Tests the HVAC doCommand for null input.
   */
  @Test(expected = RuntimeException.class)
  public void doCommandNullValue() {
    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, null);
  }

  /**
   * Tests the HVAC doCommand for null input.
   */
  @Test(expected = RuntimeException.class)
  public void doCommandNullRoom() {
    backend.doCommand(IHaleSystem.LIGHTING, null, IHaleCommandType.SET_LIGHTING_LEVEL, new Double(
        0.0));
  }

  /**
   * Tests the HVAC doCommand for a successful PUT.
   * 
   * @throws IOException Thrown when URL connection fails.
   * @throws ParserConfigurationException Thrown when building XML document.
   * @throws SAXException Thrown when parsing XML input stream.
   */
  @Test
  public void doCommand() throws IOException, ParserConfigurationException, SAXException {

    URL url = null;
    URLConnection conn = null;
    DocumentBuilderFactory factory = null;
    DocumentBuilder docBuilder = null;
    
    @SuppressWarnings("unused")
    Document doc = null;

    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, new Double(45));

    url = new URL(backend.uris.get(IHaleSystem.HVAC) + "hvac/temp");

    conn = url.openConnection();

    factory = DocumentBuilderFactory.newInstance();
    docBuilder = factory.newDocumentBuilder();
    doc = docBuilder.parse(conn.getInputStream());
    
    //TODO finish parsing simulator state.
  }
}
