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
public class IHaleServer {
  
  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.dir");
  // Restlet server properties file name.
  private static String configurationFile = "configuration-kai.properties";
  //private static String configurationFile = "configuration.properties";

  // Full path to the Restlet server properties file.
  private static String configFilePath = currentDirectory + "/" + configurationFile;

  // The interval at which to perform GET requests on house devices.
  private static final long interval = 5000;
  
  // Contains the mapping of device urls to port numbers as defined in the properties file.
  private static final Map<String, String> uris = new HashMap<String, String>();
  
  /**
   * This main method starts up a web application that will on timed intervals send GET HTTP
   * requests to each system device defined in the properties and on the port connection 
   * mapped to those device URLs.
   * 
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    readProperties();
    // Perform GETS on all devices at a specified interval.
    while (true) {
      // For each URL entry defined in a configuration properties file, if the URL contains 
      // a string phrase /state (indication to a GET URL to a system device) send a HTTP GET
      // on that URL.
      for (Map.Entry<String, String> entry : uris.entrySet()) {
        if (entry.getValue().contains("/state")) {
          String url = entry.getValue();
          
          
          
          ClientResource client = new ClientResource(url);
          // client.get should return a Representation of a xmlDocument.
          DomRepresentation representation = new DomRepresentation(client.get());
          
          // Currently doesn't support eguage API XML keys and value types.
          if (!(entry.getKey().contains("egauge"))) {
            // For console debugging.
            System.out.println(url);
            System.out.println(getStringFromDocument(representation.getDocument()));
          
            // From the XML information returned regarding the state of the system device,
            // create an entry and put it into the database repository.
            IHaleDAO dao = new IHaleDAO();
            SystemStateEntry entryFromGet = dao.xmlToSystemStateEntry(representation.getDocument());
            dao.putEntry(entryFromGet);
         
            // Finite amount of connections and transactions allowed, must release.
            client.release();
          }
        }
      }
      Thread.sleep(interval);
    }
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
}
