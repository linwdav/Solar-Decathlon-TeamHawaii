package edu.hawaii.ihale.backend.restserver.resource.hvac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
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
public class TestHvac {

  /**
   * Test toXML method.
   * 
   * @throws Exception Thrown when JUnit test fails.
   */
  @Test
  public void testToXml() throws Exception {

    NodeList nl = null;

    DomRepresentation dom = HvacData.toXml();
    Document doc = dom.getDocument();

    Element rootEl = doc.getDocumentElement();

    assertEquals("Root element", SystemData.XML_TAG_STATE_DATA, rootEl.getTagName());
    assertEquals("HVAC System", IHaleSystem.HVAC.toString(),
        rootEl.getAttribute(SystemData.XML_ATTRIBUTE_SYSTEM));
    assertNotNull("HVAC timestamp", rootEl.getAttribute(SystemData.XML_ATTRIBUTE_TIMESTAMP));

    // Check all state attributes
    nl = rootEl.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Element el = (Element) nl.item(i);
      String keyAttr = el.getAttribute(SystemData.XML_ATTRIBUTE_KEY);
      if (IHaleState.TEMPERATURE.toString().equals(keyAttr)) {
        assertNotNull("State value", el.getAttribute(SystemData.XML_ATTRIBUTE_VALUE));
      }
      else {
        throw new Exception("State key is not recognized.");
      }
    }
  }

  /**
   * Tests PUT command with HVAC. Command: SET_TEMPERATURE; Arg: 25. Won't work until we test with a
   * simulator.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Ignore
  @Test
  public void testPut() throws Exception {

    // Start the REST server.
    RestServer.runServer(8111);
    
    Repository repository = new Repository();

    // Send PUT command to server.
    String uri = "http://localhost:8111/HVAC/command/SET_TEMPERATURE?arg=25";
    ClientResource client = new ClientResource(uri);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(25), repository
        .getHvacTemperatureCommand().getValue());
    
    // Shut down the REST server.
    RestServer.stopServer();
  }

  /**
   * Tests GET command with HVAC. Won't work until we test with a simulator.
   * @throws Exception Thrown if document creation fails or failure with server.
   */
  @Ignore
  @Test
  public void testGet() throws Exception {

    // Start the REST server.
    RestServer.runServer(8111);
    
    // Send GET command to server to retrieve XML of the current state.
    String uri = "http://localhost:8111/HVAC/state";
    ClientResource client = new ClientResource(uri);

    DomRepresentation stateRepresentation = new DomRepresentation(client.get());
    Document stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    String rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the current state.", "state-data", rootNodeName);

    // Send GET command to server to retrieve XML of the state history.
    uri = "http://localhost:8111/HVAC/state?since=1";
    client = new ClientResource(uri);

    stateRepresentation = new DomRepresentation(client.get());
    stateDocument = stateRepresentation.getDocument();

    // Retrieve system name from XML.
    rootNodeName = stateDocument.getFirstChild().getNodeName();

    assertEquals("Checking that this is XML for the state history.", "state-history", rootNodeName);

    // Shut down the REST server.
    RestServer.stopServer();
  }
}
