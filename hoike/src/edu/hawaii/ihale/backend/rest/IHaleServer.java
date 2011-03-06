package edu.hawaii.ihale.backend.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * A HTTP server that continously send GET requests to system devices for their current state and
 * store their information into the iHale's database repository.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 */
public class IHaleServer implements Runnable {

  // Enable debug print statements or not.
  private static boolean debugMode = true;

  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.home");
  // Folder containing properties file.
  private static String folder = ".ihale";
  // Restlet server properties file name.
  private static String configurationFile = "device-urls.properties";

  // Full path to the Restlet server properties file.
  private static String configFilePath = currentDirectory + "/" + folder + "/" + configurationFile;

  // The interval at which to perform GET requests on house devices.
  private static long interval = 10000;

  // Determinant for if the thread is done running or not.
  private boolean isDone = false;

  // Contains the mapping of device urls to port numbers as defined in the properties file.
  // i.e., key = http://arduino-1.halepilihonua.hawaii.edu/aquaponics/state
  // value = http://localhost:8001/aquaponics/state
  private static final Map<String, String> uris = new HashMap<String, String>();

  /**
   * Constructor sets the time interval between polling.
   * 
   * @param interval The time interval in milliseconds.
   */
  public IHaleServer(long interval) {
    // Empty constructor.
  }

  /**
   * At timed intervals send GET HTTP requests to each system device defined in the properties and
   * on the port connection mapped to those device URLs.
   * 
   * @throws Exception If problems occur.
   */
  public static void pollDevices() throws Exception {
    String key = "";
    String value = "";
    String url = "";
    String context = "";
    // Perform GETS on all devices at a specified interval.
    while (true) {
      for (Map.Entry<String, String> entry : uris.entrySet()) {
        key = entry.getKey();
        value = entry.getValue();
        if (key.contains("state")) {
          context = key.replace('-', '/');
          url = value + context;
          // For console debugging.
          if (debugMode) {
            System.out.println(url);
          }
        }
        
        // Connect to device and retrieve a Representation of a xmlDocument
        ClientResource client = new ClientResource(url);
        DomRepresentation representation = new DomRepresentation(client.get());
        
        // For console debugging.
        if (debugMode) {
          System.out.println(getStringFromDocument(representation.getDocument()));
        }
  
        // From the XML information returned regarding the state of the system device,
        // create an entry and put it into the database repository.
        IHaleDAO dao = new IHaleDAO();
        if (!key.contains("lighting") && !key.contains("electrical")) {
          SystemStateEntry entryFromGet = dao.xmlToSystemStateEntry(representation.getDocument());
          dao.putEntry(entryFromGet);
  
          // Test Case: Retrieve the entry that was stored in the database repository.
          /*
           * if (debugMode) { SystemStateEntry returnedEntry =
           * dao.getEntry(entryFromGet.getSystemName(), entryFromGet.getDeviceName(),
           * entryFromGet.getTimestamp()); System.out.println(returnedEntry.getSystemName() + "\t"
           * + returnedEntry.getDeviceName() + "\t" + returnedEntry.getTimestamp()); }
           */
        }
        // Special case for lighting since it uses e-gauge device.
        else if (key.contains("lighting")) {
          SystemStateEntry entryFromGet =
              dao.xmlEgaugeToSystemStateEntry(representation.getDocument(), "photovoltaics",
                  "egauge-1");
          dao.putEntry(entryFromGet);
  
          // Test Case: Retrieve the entry that was stored in the database repository.
          /*
           * if (debugMode) { SystemStateEntry returnedEntry =
           * dao.getEntry(entryFromGet.getSystemName(), entryFromGet.getDeviceName(),
           * entryFromGet.getTimestamp()); System.out.println(returnedEntry.getSystemName() + "\t"
           * + returnedEntry.getDeviceName() + "\t" + returnedEntry.getTimestamp() + "\t" +
           * returnedEntry.getLongValue("energy") + "\t" + returnedEntry.getLongValue("power")); }
           */
        }
        else if (key.contains("electrical")) {
          SystemStateEntry entryFromGet =
              dao.xmlEgaugeToSystemStateEntry(representation.getDocument(), "electrical",
                  "egauge-2");
          dao.putEntry(entryFromGet);
  
          // Test Case: Retrieve the entry that was stored in the database repository.
          /*
           * if (debugMode) { SystemStateEntry returnedEntry =
           * dao.getEntry(entryFromGet.getSystemName(), entryFromGet.getDeviceName(),
           * entryFromGet.getTimestamp()); System.out.println(returnedEntry.getSystemName() + "\t"
           * + returnedEntry.getDeviceName() + "\t" + returnedEntry.getTimestamp() + "\t" +
           * returnedEntry.getLongValue("energy") + "\t" + returnedEntry.getLongValue("power")); }
           */
        }
        // Finite amount of connections and transactions allowed, must release.
        client.release();
      }
      Thread.sleep(interval);
    }
  }

  static {
    readProperties();
  }

  /**
   * Reads the configuration file properties.
   */
  public static void readProperties() {
    try {
      FileInputStream is = new FileInputStream(configFilePath);
      Properties prop = new Properties();
      prop.load(is);
      String key = "";
      String value = "";
      for (Map.Entry<Object, Object> propItem : prop.entrySet()) {
        key = (String) propItem.getKey();
        value = (String) propItem.getValue();
        uris.put(key, value);
      }
      System.out.println(configurationFile);
      is.close();
    }
    catch (IOException e) {
      System.out.println("Failed to read properties file.");
      System.out.println(configFilePath);
    }
  }

  /**
   * Returns a String representation of a XML document. Useful for debugging responses from system
   * devices.
   * 
   * @param doc The XML document.
   * @return String representation of a XML document.
   */
  public static String getStringFromDocument(Document doc) {
    try {
      DOMSource domSource = new DOMSource(doc);
      StringWriter writer = new StringWriter();
      StreamResult result = new StreamResult(writer);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(domSource, result);
      return writer.toString();
    }
    catch (TransformerException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * Returns the mapping of device urls to port numbers as defined in the properties file. i.e., key
   * = http://arduino-1.halepilihonua.hawaii.edu/aquaponics/state value =
   * http://localhost:8001/aquaponics/state
   * 
   * @return Map of devices to port.
   */
  public static Map<String, String> getUris() {
    return uris;
  }

  /**
   * Returns the configuration file name for use in the IHaleDAO class.
   * 
   * @return Returns the configuration file name.
   */
  public static String getConfigurationFileName() {
    return configurationFile;
  }

  /**
   * Required method to implement Runnable. Polls the system devices regularly for system device
   * information.
   */
  @Override
  public void run() {
    try {
      while (!isDone) {
        pollDevices();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Ends this thread instance.
   */
  public void done() {
    this.isDone = true;
  }

  /**
   * Sets the debugging on or off for this class. True if debug statements are permitted, false
   * otherwise.
   * 
   * @param status True if debug statements permitted, false otherwise.
   */
  public static void setDebugMode(boolean status) {
    debugMode = status;
  }
}
