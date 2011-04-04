package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

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
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.restserver.RestServer;
import edu.hawaii.ihale.backend.restserver.resource.SystemData;

/**
 * Tests the aquaponics data to ensure that the XML representation is correct.
 * 
 * @author Bret K. Ikehara
 * @author Michael Cera
 */
public class TestAquaponics {

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXml() throws Exception {

    NodeList nl = null;

    DomRepresentation dom = (DomRepresentation) AquaponicsData.toXml();
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, rootEl.getTagName());
    assertEquals("Aquaponic System", IHaleSystem.AQUAPONICS.toString(),
        rootEl.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Aquaponics timestamp", rootEl.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    // Check all state attributes
    nl = rootEl.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Element el = (Element) nl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.CIRCULATION.toString().equals(keyAttr)
          || IHaleState.DEAD_FISH.toString().equals(keyAttr)
          || IHaleState.ELECTRICAL_CONDUCTIVITY.toString().equals(keyAttr)
          || IHaleState.TEMPERATURE.toString().equals(keyAttr)
          || IHaleState.TURBIDITY.toString().equals(keyAttr)
          || IHaleState.WATER_LEVEL.toString().equals(keyAttr)
          || IHaleState.PH.toString().equals(keyAttr)
          || IHaleState.OXYGEN.toString().equals(keyAttr)) {
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

    DomRepresentation dom = (DomRepresentation) AquaponicsData.toXmlSince(1L);
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_HISTORY, rootEl.getTagName());

    // Check all state attributes
    NodeList stateDataNl = rootEl.getChildNodes();
    Element stateDataNode = (Element) stateDataNl.item(0);

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, stateDataNode.getTagName());
    assertEquals("Aquaponics System", IHaleSystem.AQUAPONICS.toString(),
        stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Timestamp", stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    NodeList stateNl = stateDataNode.getChildNodes();
    for (int i = 0; i < stateNl.getLength(); i++) {
      Element el = (Element) stateNl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.CIRCULATION.toString().equals(keyAttr)
          || IHaleState.DEAD_FISH.toString().equals(keyAttr)
          || IHaleState.ELECTRICAL_CONDUCTIVITY.toString().equals(keyAttr)
          || IHaleState.TEMPERATURE.toString().equals(keyAttr)
          || IHaleState.TURBIDITY.toString().equals(keyAttr)
          || IHaleState.WATER_LEVEL.toString().equals(keyAttr)
          || IHaleState.PH.toString().equals(keyAttr)
          || IHaleState.OXYGEN.toString().equals(keyAttr)) {
        assertNotNull("State value", el.getAttribute(SystemData.XML_ATTRIBUTE_VALUE));
      }
      else {
        throw new Exception("State key is not recognized: " + keyAttr);
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
    AquaponicsData.toXmlSince(null);
  }

  /**
   * Tests PUT command with aquaponics. Command: SET_TEMPERATURE; Arg: 25.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Test
  public void testPut() throws Exception {

    Repository repository = new Repository();

    // Run the REST server.
    RestServer.runServer(8111);

    // Send PUT command to server.
    String uri = "http://localhost:8111/AQUAPONICS/command/SET_TEMPERATURE?arg=25";
    ClientResource client = new ClientResource(uri);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(25), repository
        .getAquaponicsTemperatureCommand().getValue());

    RestServer.stopServer();
  }

  /**
   * Tests GET command with aquaponics. Won't work until we test with a simulator.
   * 
   * @throws Exception Thrown when server initialization fails or XMl doucment creation fails.
   */
  @Test
  public void testGet() throws Exception {

    RestServer.runServer(8111);

    // Send GET command to server to retrieve XML of the current state.
    String uri = "http://localhost:8111/AQUAPONICS/state";
    ClientResource client = new ClientResource(uri);

    DomRepresentation stateRepresentation = new DomRepresentation(client.get());
    Document stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    String rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the current state.", SystemData.XML_TAG_STATE_DATA,
        rootNodeName);

    // Send GET command to server to retrieve XML of the state history.
    uri = "http://localhost:8111/AQUAPONICS/state?since=1";
    client = new ClientResource(uri);

    stateRepresentation = new DomRepresentation(client.get());
    stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    rootNodeName = stateDocument.getFirstChild().getNodeName();

    // Just need to check whether state-history is there
    // the rest should be there if testToXmlSince method passes.
    assertEquals("Checking that this is XML for the state history.",
        SystemData.XML_TAG_STATE_HISTORY, rootNodeName);

    RestServer.stopServer();
  }
}