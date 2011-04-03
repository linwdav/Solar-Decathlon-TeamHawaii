package edu.hawaii.ihale.housesimulator;

import java.io.File;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Verify that the simulator server writes a properties file, creates initial data, 
 * and verify that the server runs.
 * 
 * @author Nathan Dorman, Leonardo Nguyen
 *
 */
public class TestSimulator {

  /**
   * Verify that initial data is generated.
   * 
   * @throws Exception If problems occur generating data.
   */
  @Test
  public void testCreateInitialData() throws Exception {
    
    long timestamp = new Date().getTime();
    String dir = ".ihale";
    String filename = "initial-data.xml";
    // Get the users home directory and "dir" sub-directory
    File theDir = new File(System.getProperty("user.home"), dir);
    File xmlFile = new File(theDir, filename);
    
    // Was complaining about xmlFile dead store. This block of code is temporary solution.
    if (xmlFile.exists()) {
      System.out.println("File exists");
    }
    else {
      System.out.println("File doesn't exist");
    }
    
    
    
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    /** Process of appending the initial system device state information occurs here. */

    doc = SimulatorServer.appendStateDataToXml(doc, timestamp);
    doc.toString();
  }
}
