package edu.hawaii.ihale.housesimulator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.io.File;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Verify that the simulator server writes a properties file, creates initial data, 
 * and verify that the server runs.
 * 
 * @author Nathan Dorman, Leonardo Nguyen
 *
 */
public class TestSimulator {
  
  private static final long initialTimestamp = new Date().getTime();
  private static final String dir = ".ihale";
  private static final String propFilename = "device-urls.properties";
  private static final String xmlFilename = "initial-data.xml";
  
  /**
   * Delete device-urls.properties and initial-data.xml files and start up the server
   * before performing any further testing actions.
   * 
   * @throws Exception If problems occur starting up the server.
   */
  @BeforeClass
  public static void startServer() throws Exception {
    
    // Get the users home directory and "dir" sub-directory
    File theDir = new File(System.getProperty("user.home"), dir);
    File propFile = new File(theDir, propFilename);
    File xmlFile = new File(theDir, xmlFilename);
    
    if (propFile.exists() && !propFile.delete()) {
      System.out.println("Could not delete device-url.properties file, test ending");
      System.exit(1);
    }
    if (xmlFile.exists() && !xmlFile.delete()) {
      System.out.println("Could not delete initial-data.xml file, test ending");
      System.exit(1);
    }
    
    assertFalse("Check device-urls.properties does not exist or was successfully deleted.", 
        propFile.exists());
    assertFalse("Check initial-device.xml does not exist or was successfully deleted.", 
        xmlFile.exists());  }
  
  /**
   * Verify that both device-urls.properties and initial-data.xml file are created properly.
   * 
   * @throws Exception If problems occur generating data.
   */
  @Test
  public void testStartUpFiles() throws Exception {
    
    String[] args = {"-stepinterval", "1000"};
    SimulatorServer.main(args);

    File theDir = new File(System.getProperty("user.home"), dir);
    File xmlFile = new File(theDir, xmlFilename);
    assertTrue("Checking if initial-data.xml exists.", xmlFile.exists());
    
    File propFile = new File(theDir, propFilename);
    assertTrue("Checking if device-urls.properties exists.", propFile.exists());
    
    /** TO-DO:
     *  Open up the device-urls.properties file and confirm various keys and associated values 
     *  were properly established within the file.
     *  
     *  Open up the initial-data.xml file and confirm at least a record for each system was created
     *  or possible count the number of state-data elements and assert it matches up or possibly
     *  for various timestamps for a system and assert historical records are taken at appropriate
     *  time snap-shots.
     */
    System.out.println(initialTimestamp); // To abide QA rules. Will utilize the field efficiently.
  }
}
