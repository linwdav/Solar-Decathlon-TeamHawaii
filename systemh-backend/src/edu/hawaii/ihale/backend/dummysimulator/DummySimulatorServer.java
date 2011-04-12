package edu.hawaii.ihale.backend.dummysimulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.logging.Level;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 * A "dummy" server for test cases only. Provides a server to "talk" to and allows the backend to
 * specify their own initial-data.xml, thus allowing testing without the need for the actual
 * simulator.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 * 
 * @version 2.0
 */
public class DummySimulatorServer extends Application {

  private static Component component = new Component();

  /**
   * Creates the properties file, initial data XML, then runs the server on specified port.
   * 
   * @param args Ignored.
   * @throws Exception If server fails to start.
   */
  public static void main(String[] args) throws Exception {
    createDevicePropertiesFile(".ihale", "device-urls.properties");
    createInitialDataXml(".ihale", "initial-data.xml");
    runServer(7001);
  }

  /**
   * Start server on specified port.
   * 
   * @return boolean Returns true if server successfully runs.
   * @param port Port number to run the server.
   * @throws Exception If problems occur starting up this server.
   */
  public static boolean runServer(int port) throws Exception {

    boolean status = component.isStopped();

    // When server has not been started before, initialize values.
    if (status) {
      Component component = new Component();
      component.getLogger().setLevel(Level.OFF);

      component.getServers().add(Protocol.HTTP, port);
      component.getClients().add(Protocol.HTTP);

      Application application = new DummySimulatorServer();

      String contextRoot = "";
      component.getDefaultHost().attach(contextRoot, application);
      component.start();
    }
    return status;
  }

  /**
   * Shuts down this REST server.
   * 
   * @return Return true if server is was running and has been stopped.
   * @throws Exception Thrown when server fails to shut down.
   */
  public static boolean stopServer() throws Exception {

    boolean status = component.isStarted();

    if (status) {
      component.stop();
    }

    return status;
  }

  /**
   * Maps all requests to our dummy resource class.
   * 
   * @return A Router Restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {

    Router router = new Router(getContext());

    // Attach resources to router.
    router.attachDefault(DummySimulatorResource.class);

    // Return the root router
    return router;
  }

  /**
   * Creates a properties file that maps the system devices to HTTP URLs. The file will be created
   * in a user-defined sub-directory within the user's home directory.
   * 
   * @param dir Sub-directory name.
   * @param filename Properties file name.
   * @throws IOException If the properties file already exists and can't be overwritten or if the
   * properties file can't be created.
   */
  private static void createDevicePropertiesFile(String dir, String filename) throws IOException {

    // Get the users home directory and "dir" sub-directory
    File theDir = new File(System.getProperty("user.home"), dir);
    File propFile = new File(theDir, filename);
    FileOutputStream fis;
    // Create the properties object to write to file.
    Properties prop = new Properties();

    // System URI's
    String uri = "http://localhost:7001/";

    // Set the properties value.
    prop.setProperty("aquaponics-state", uri);
    prop.setProperty("aquaponics-control", uri);
    prop.setProperty("hvac-state", uri);
    prop.setProperty("hvac-control", uri);
    prop.setProperty("lighting-living-state", uri);
    prop.setProperty("lighting-living-control", uri);
    prop.setProperty("lighting-dining-state", uri);
    prop.setProperty("lighting-dining-control", uri);
    prop.setProperty("lighting-kitchen-state", uri);
    prop.setProperty("lighting-kitchen-control", uri);
    prop.setProperty("lighting-bathroom-state", uri);
    prop.setProperty("lighting-bathroom-control", uri);
    prop.setProperty("lighting-bathroom-color", uri);
    prop.setProperty("photovoltaic-state", uri);
    prop.setProperty("electric-state", uri);

    // Create the Directory if doesn't exist.
    if (!theDir.exists() && !theDir.mkdir()) {
      System.out.println("Could not create the directory, exiting.");
      System.exit(1);
    }

    try {
      fis = new FileOutputStream(propFile);
      prop.store(fis, null);
      fis.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }

  }

  /**
   * Copies our history XML file to the iHale user directory.
   * 
   * @param dir Sub-directory name.
   * @param filename XML file name.
   * @throws Exception If errors occur when creating the XML file, parsing the Document object, or
   * transforming the Document object.
   */
  private static void createInitialDataXml(String dir, String filename) throws Exception {
    // Create XML file in the .ihale directory.
    File ihaleDirectory = new File(System.getProperty("user.home"), dir);
    File historyXml = new File(ihaleDirectory, filename);

    // Location of our test XML files.
    String historyTestXml = System.getProperty("user.dir") + "/test-history.xml";

    // Copy our history XML file used for test cases into the iHale directory.
    FileChannel source = null;
    FileChannel destination = null;
    source = new FileInputStream(historyTestXml).getChannel();
    destination = new FileOutputStream(historyXml).getChannel();
    if (destination != null && source != null) {
      destination.transferFrom(source, 0, source.size());
    }
    if (source != null) {
      source.close();
    }
    if (destination != null) {
      destination.close();
    }
  }
}
