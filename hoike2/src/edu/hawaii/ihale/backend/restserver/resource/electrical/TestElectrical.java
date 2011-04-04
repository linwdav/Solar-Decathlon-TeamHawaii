package edu.hawaii.ihale.backend.restserver.resource.electrical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.backend.restserver.resource.SystemData;
import edu.hawaii.ihale.backend.restserver.resource.SystemDataTest;

/**
 * Tests the eletrical data to ensure that the XML representation is correct.
 * 
 * @author Bret K. Ikehara
 * @author Michael Cera
 */
public class TestElectrical extends SystemDataTest {

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXml() throws Exception {

    NodeList nl = null;

    DomRepresentation dom = (DomRepresentation) ElectricalData.toXml();
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, rootEl.getTagName());
    assertEquals("Photovoltaic System", IHaleSystem.ELECTRIC.toString(),
        rootEl.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Timestamp", rootEl.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    // Check all state attributes
    nl = rootEl.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Element el = (Element) nl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.ENERGY.toString().equals(keyAttr)
          || IHaleState.POWER.toString().equals(keyAttr)) {
        assertNotNull("State value", el.getAttribute(SystemData.XML_ATTRIBUTE_VALUE));
      }
      else {
        throw new Exception("State key is not recognized.");
      }
    }
  }

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXmlSince() throws Exception {

    DomRepresentation dom = (DomRepresentation) ElectricalData.toXmlSince(1L);
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_HISTORY, rootEl.getTagName());

    // Check all state attributes
    NodeList stateDataNl = rootEl.getChildNodes();

    Element stateDataNode = (Element) stateDataNl.item(0);

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, stateDataNode.getTagName());
    assertEquals("ELECTRIC System", IHaleSystem.ELECTRIC.toString(),
        stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Timestamp", stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    NodeList stateNl = stateDataNode.getChildNodes();
    for (int i = 0; i < stateNl.getLength(); i++) {
      Element el = (Element) stateNl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.ENERGY.toString().equals(keyAttr)
          || IHaleState.POWER.toString().equals(keyAttr)) {
        assertNotNull("State value", el.getAttribute(SystemData.XML_ATTRIBUTE_VALUE));
      }
      else {
        throw new Exception("State key is not recognized.");
      }
    }
  }

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test(expected = RuntimeException.class)
  public void testToXmlSinceNull() throws Exception {
    ElectricalData.toXmlSince(null);
  }

  /**
   * Tests GET command with aquaponics. Won't work until we test with a simulator.
   * 
   * @throws Exception Thrown when server initialization fails or XMl doucment creation fails.
   * 
   */
  @Test
  public void testGet() throws Exception {

    // Send GET command to server to retrieve XML of the current state.
    String uri = "http://localhost:8111/ELECTRIC/state";
    ClientResource client = new ClientResource(uri);

    DomRepresentation stateRepresentation = new DomRepresentation(client.get());
    Document stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    String rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the current state.", SystemData.XML_TAG_STATE_DATA,
        rootNodeName);

    // Test the since parameter
    uri = "http://localhost:8111/ELECTRIC/state?since=1";
    client = new ClientResource(uri);

    stateRepresentation = new DomRepresentation(client.get());
    stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the current state.",
        SystemData.XML_TAG_STATE_HISTORY, rootNodeName);
  }
}
