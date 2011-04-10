package edu.hawaii.ihale.backend.restserver.resource.lighting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull; 
import java.util.logging.Level;

import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
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
public class TestLighting {

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXml() throws Exception {

    NodeList nl = null;

    DomRepresentation dom = LightingData.toXml(IHaleRoom.LIVING);
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, rootEl.getTagName());
    assertEquals("Lighting System", IHaleSystem.LIGHTING.toString(),
        rootEl.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Lighting timestamp", rootEl.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    // Check all state attributes
    nl = rootEl.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Element el = (Element) nl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.LIGHTING_LEVEL.toString().equals(keyAttr)
          || IHaleState.LIGHTING_ENABLED.toString().equals(keyAttr)
          || IHaleState.LIGHTING_COLOR.toString().equals(keyAttr)) {
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
  public void testToXmlNullRoom() throws Exception {
    boolean expectedThrown = false;
    
    try {
      LightingData.toXml(null);
    }
    catch (RuntimeException e) {
      expectedThrown = true;
    }
    
    assertEquals("Caught the error",expectedThrown,true);
  }

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXmlSince() throws Exception {

    DomRepresentation dom = (DomRepresentation) LightingData.toXmlSince(IHaleRoom.BATHROOM, 1L);
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_HISTORY, rootEl.getTagName());

    // Check all state attributes
    NodeList stateDataNl = rootEl.getChildNodes();
    Element stateDataNode = (Element) stateDataNl.item(0);

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, stateDataNode.getTagName());
    assertEquals("Aquaponics System", IHaleSystem.LIGHTING.toString(),
        stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("Timestamp", stateDataNode.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    NodeList stateNl = stateDataNode.getChildNodes();
    for (int i = 0; i < stateNl.getLength(); i++) {
      Element el = (Element) stateNl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.LIGHTING_LEVEL.toString().equals(keyAttr)
          || IHaleState.LIGHTING_ENABLED.toString().equals(keyAttr)
          || IHaleState.LIGHTING_COLOR.toString().equals(keyAttr)) {
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
  @Test
  public void testToXmlSinceNullTimestamp() throws Exception {
    boolean expectedThrown = false;
    
    try {
      LightingData.toXmlSince(IHaleRoom.BATHROOM, null);
    }
    catch (RuntimeException e) {
      expectedThrown = true;
    }
    
    assertEquals("Caught the error",expectedThrown,true);
  }

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXmlSinceNullRoom() throws Exception {
    boolean expectedThrown = false;
    
    try {
      LightingData.toXmlSince(null, 1L);
    }
    catch (RuntimeException e) {
      expectedThrown = true;
    }
    
    assertEquals("Caught the error",expectedThrown,true);
  }

  /**
   * Tests PUT command with lighting. Command: SET_LIGHTING_LEVEL; Arg: 50; Room: LIVING. Won't work
   * until we test with a simulator.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Test
  public void testPut() throws Exception {

    // Start the REST server.
    RestServer.runServer(8111);

    Repository repository = new Repository();

    // Send PUT command to server.
    String uri = "http://localhost:8111/LIGHTING/command/SET_LIGHTING_LEVEL?arg=50&room=LIVING";
    ClientResource client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(50),
        repository.getLightingLevelCommand(IHaleRoom.LIVING).getValue());

    // Shut down the REST server.
    RestServer.stopServer();
  }

  /**
   * Tests GET command with lighting. Won't work until we test with a simulator.
   * 
   * @throws Exception Thrown if Document creation or server fails.
   */
  @Test
  public void testGet() throws Exception {

    // Start the REST server.
    RestServer.runServer(8112);

    // Send GET command to server to retrieve XML of the current state.
    String uri = "http://localhost:8112/LIGHTING/state?room=LIVING";
    ClientResource client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);

    DomRepresentation stateRepresentation = new DomRepresentation(client.get());
    Document stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    String rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the current state.", SystemData.XML_TAG_STATE_DATA,
        rootNodeName);

    // Send GET command to server to retrieve XML of the state history.
    uri = "http://localhost:8112/LIGHTING/state?room=LIVING&since=1";
    client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);

    stateRepresentation = new DomRepresentation(client.get());
    stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    rootNodeName = stateDocument.getFirstChild().getNodeName();

    // Just need to check whether state-history is there
    // the rest should be there if testToXmlSince method passes.
    assertEquals("Checking that this is XML for the state history.",
        SystemData.XML_TAG_STATE_HISTORY, rootNodeName);

    // Shut down the REST server.
    RestServer.stopServer();
  }
}