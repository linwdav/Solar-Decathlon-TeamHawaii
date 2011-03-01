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
 * @version Java 1.6.0_21
 */
public class IHaleServer implements Runnable {
  
  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.dir");
  // Restlet server properties file name.
  private static String configurationFile = "configuration-kai.properties";

  // Full path to the Restlet server properties file.
  private static String configFilePath = currentDirectory + "/" + configurationFile;

  // The interval at which to perform GET requests on house devices.
//  private static long interval;
  
  // Contains the mapping of device urls to port numbers as defined in the properties file.
  // i.e., key = http://arduino-1.halepilihonua.hawaii.edu/aquaponics/state
  //       value = http://localhost:8001/aquaponics/state
  private static final Map<String, String> uris = new HashMap<String, String>();
  
  /**
   * Constructor sets the time interval between polling.
   */
  public IHaleServer() {
    // Empty constructor.
  }
  
//  /**
//   * Sets the poll interval.
//   * 
//   * @param interval The time interval in milliseconds.
//   */
//  public void setTimeInterval(long interval) {
//    this.interval = interval;
//  }
  
  /**
   * Attimed intervals send GET HTTP requests to each system device defined in the properties and 
   * on the port connection mapped to those device URLs.
   * 
   * @throws Exception If problems occur.
   */
  public static void pollDevices() throws Exception {
    // Perform GETS on all devices at a specified interval.
    while (true) {
      // For each URL entry defined in a configuration properties file, if the URL contains 
      // a string phrase /state (indication to a GET URL to a system device) send a HTTP GET
      // on that URL.
      for (Map.Entry<String, String> entry : uris.entrySet()) {
        // Currently doesn't support eguage API XML keys and value types.
        if (entry.getValue().contains("/state") && (!(entry.getKey().contains("egauge")))) {
          String url = entry.getValue();
          // Some configuration properties mapped devices to URLs that look like
          // localhost:7103/lighting/living/state without the protocol name.
          if (!(url.contains("http://"))) {
            url = "http://" + url;
          }
          
          // For console debugging.
          System.out.println(url);
          
          ClientResource client = new ClientResource(url);
          // client.get should return a Representation of a xmlDocument.
          DomRepresentation representation = new DomRepresentation(client.get());
            
          // For console debugging.
          System.out.println(getStringFromDocument(representation.getDocument()));
          
          // From the XML information returned regarding the state of the system device,
          // create an entry and put it into the database repository.
          IHaleDAO dao = new IHaleDAO();
          SystemStateEntry entryFromGet = dao.xmlToSystemStateEntry(representation.getDocument());
          dao.putEntry(entryFromGet);
         
          // Test Case: Retrieve the entry that was stored in the database repository.
          
          SystemStateEntry returnedEntry = 
            dao.getEntry(entryFromGet.getSystemName(), entryFromGet.getDeviceName(), 
                entryFromGet.getTimestamp());
          System.out.println(returnedEntry.getSystemName() + "\t" + returnedEntry.getDeviceName() 
              + "\t" + returnedEntry.getTimestamp());
            
          // Finite amount of connections and transactions allowed, must release.
          client.release();
        }
      }
      Thread.sleep(9000);
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
   * Returns a String representation of a XML document. Useful for debugging responses from
   * system devices.
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
   * Returns the mapping of device urls to port numbers as defined in the properties file.
   *  i.e., key = http://arduino-1.halepilihonua.hawaii.edu/aquaponics/state
   *        value = http://localhost:8001/aquaponics/state
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

  @Override
  public void run() {
    try {
      pollDevices();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
