package edu.hawaii.ihale.housesimulator;

import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Verify that the simulator server writes a properties file, creates initial data, 
 * and verify that the server runs.
 * 
 * @author Nathan
 *
 */
public class TestSimulator {

  /**
   * Verify that initial data is being generated.
   * 
   * @throws Exception If problems occur generating data.
   */
  @Test
  public void testCreateInitialData() throws Exception {
    long timestamp = new Date().getTime();
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    /** Process of appending the initial system device state information occurs here. */

    doc = SimulatorServer.appendStateDataToXml(doc, timestamp);
    doc.toString();
  }
}
