package edu.hawaii.ihale.housesimulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.housesimulator.aquaponics.AquaponicsData;
import edu.hawaii.ihale.housesimulator.aquaponics.AquaponicsSystem;
import edu.hawaii.ihale.housesimulator.electrical.ElectricalData;
import edu.hawaii.ihale.housesimulator.electrical.ElectricalSystem;
import edu.hawaii.ihale.housesimulator.hvac.HVACData;
import edu.hawaii.ihale.housesimulator.hvac.HVACSystem;
import edu.hawaii.ihale.housesimulator.lighting.bathroom.LightingBathroomSystem;
import edu.hawaii.ihale.housesimulator.lighting.dining.LightingDiningSystem;
import edu.hawaii.ihale.housesimulator.lighting.kitchen.LightingKitchenSystem;
import edu.hawaii.ihale.housesimulator.lighting.living.LightingLivingSystem;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsData;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsSystem;
import edu.hawaii.ihale.housesimulator.simulationtimer.SimulationTimer;

/**
 * An HTTP server that provides access to simulator data via a REST interface.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class SimulatorServer extends Application {

  /**
   * This main method starts up a web application.
   * 
   * @param args Requires the first argument to be "-stepinterval" followed by a positive integer to
   * denote the interval (in seconds) between each system device data refresh.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {

    if (args.length == 2 && "-stepinterval".equalsIgnoreCase(args[0])) {
      createDevicePropertiesFile(".ihale", "device-urls.properties");
      createInitialDataXml(".ihale", "initial-data.xml");
      runServer();
      SimulationTimer.startTimer(Integer.parseInt(args[1]));
    }
    else {
      System.out.println("Usage: java -jar <jar filename> -stepinterval N");
      System.out.println("Where N is the step interval value, in seconds.");
      System.out.println("New sensor data will be updated every N seconds.");
      System.exit(0);
    }
  }

  /**
   * Start servers running beginning on ports. Applications and their resources are specified in
   * their respective classes.
   * 
   * @throws Exception If problems occur starting up this server.
   */
  public static void runServer() throws Exception {
    // Create a component and open several ports.
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, 7001);
    component.getServers().add(Protocol.HTTP, 7002);
    component.getServers().add(Protocol.HTTP, 7101);
    component.getServers().add(Protocol.HTTP, 7102);
    component.getServers().add(Protocol.HTTP, 7103);
    component.getServers().add(Protocol.HTTP, 7104);
    component.getServers().add(Protocol.HTTP, 7105);
    component.getServers().add(Protocol.HTTP, 7106);

    // Create virtual hosts. E-Gauge boards will be on port ranges 7001-7100, Arduino boards on port
    // ranges 7101+.
    VirtualHost host = new VirtualHost(component.getContext());
    host.setHostPort("7001");
    //host.attach("/photovoltaic", new PhotovoltaicsSystem());
    host.attach("/cgi-bin/egauge", new PhotovoltaicsSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7002");
    //host.attach("/electric", new ElectricalSystem());
    host.attach("/cgi-bin/egauge", new ElectricalSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7101");
    host.attach("/aquaponics", new AquaponicsSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7102");
    host.attach("/hvac", new HVACSystem());
    component.getHosts().add(host);

    String lighting = "/lighting"; // To satisfy PMD

    host = new VirtualHost(component.getContext());
    host.setHostPort("7103");
    host.attach(lighting, new LightingLivingSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7104");
    host.attach(lighting, new LightingDiningSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7105");
    host.attach(lighting, new LightingKitchenSystem());
    component.getHosts().add(host);

    host = new VirtualHost(component.getContext());
    host.setHostPort("7106");
    host.attach(lighting, new LightingBathroomSystem());
    component.getHosts().add(host);

    component.start();
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
    String aquaponics = "http://localhost:7101/";
    String hvac = "http://localhost:7102/";
    String lightingLiving = "http://localhost:7103/";
    String lightingDining = "http://localhost:7104/";
    String lightingKitchen = "http://localhost:7105/";
    String lightingBathroom = "http://localhost:7106/";
    String pv = "http://localhost:7001/";
    String electrical = "http://localhost:7002/";

    // Set the properties value.
    prop.setProperty("aquaponics-state", aquaponics);
    prop.setProperty("aquaponics-control", aquaponics);
    prop.setProperty("hvac-state", hvac);
    prop.setProperty("hvac-control", hvac);
    prop.setProperty("lighting-living-state", lightingLiving);
    prop.setProperty("lighting-living-control", lightingLiving);
    prop.setProperty("lighting-dining-state", lightingDining);
    prop.setProperty("lighting-dining-control", lightingDining);
    prop.setProperty("lighting-kitchen-state", lightingKitchen);
    prop.setProperty("lighting-kitchen-control", lightingKitchen);
    prop.setProperty("lighting-bathroom-state", lightingBathroom);
    prop.setProperty("lighting-bathroom-control", lightingBathroom);
    prop.setProperty("lighting-bathroom-color", lightingBathroom);
    prop.setProperty("photovoltaic-state", pv);
    prop.setProperty("electric-state", electrical);

    // The properties file already exists.
    if (propFile.exists()) {

      System.out.println("File already exists: " + propFile.getAbsolutePath());
      // Initialize scanner and input string.
      Scanner sc;
      String input = "";

      // Keep asking user if they want to overwrite the file if they don't say y or n.
      do {

        System.out.println("Would you like to overwrite this properties file? y/n");
        sc = new Scanner(System.in);
        input = sc.next();

        // Overwrite the file.
        if ("y".equalsIgnoreCase(input)) {
          // Try to store the properties object in the properties file.
          try {
            System.out.println("Overwriting properties file: " + propFile.getAbsolutePath());
            fis = new FileOutputStream(propFile);
            prop.store(fis, null);
            fis.close();
          }
          catch (IOException ex) {
            ex.printStackTrace();
          }
        }
        // Leave existing file.
        else if ("n".equalsIgnoreCase(input)) {
          System.out.println("Starting simulation using exisiting properties file.");
        }
      }
      while (!"y".equalsIgnoreCase(input) && !"n".equalsIgnoreCase(input));
    }
    // The property file does not exist.
    else {

      System.out.println("Creating properties file: " + propFile.getAbsolutePath());
      
      // Create the Directory if doesn't exist.      
      if (!theDir.exists() && !theDir.mkdir()) {
        System.out.println("Could not create the directory, exiting.");
        System.exit(1);
      }

      // Create the Properties file.
      if (propFile.createNewFile()) {
        // Try to store the properties object in the properties file.
        try {
          fis = new FileOutputStream(propFile);
          prop.store(fis, null);
          fis.close();
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.out.println("Failed to create properties file: " + propFile.getAbsolutePath());
        System.exit(1);
      }
    }
  }

  /**
   * Creates a XML file that contains state data that the back-end system initializes to its
   * repository to simulate past system device state returns.
   * 
   * @param dir Sub-directory name.
   * @param filename XML file name.
   * @throws Exception If errors occur when creating the XML file, parsing the Document object, or
   * transforming the Document object.
   */
  private static void createInitialDataXml(String dir, String filename) throws Exception {
     long timestamp = new Date().getTime();
    // Get the users home directory and "dir" sub-directory
    File theDir = new File(System.getProperty("user.home"), dir);
    File xmlFile = new File(theDir, filename);

    // The XML file exists.
    if (xmlFile.exists()) {

      System.out.println("File already exists: " + xmlFile.getAbsolutePath());
      // Initialize scanner and input string.
      Scanner sc;
      String input = "";

      // Keep asking user if they want to overwrite the file if they don't say y or n.
      do {

        System.out.println("Would you like to overwrite this XML file? y/n");
        sc = new Scanner(System.in);
        input = sc.next();

        // Overwrite the file.
        if ("y".equalsIgnoreCase(input)) {

          try {

          DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
          Document doc = docBuilder.newDocument();

          /** Process of appending the initial system device state information occurs here. */

          doc = appendStateDataToXml(doc, timestamp);
          
          /** End of appending the initial system device state information. **/
          
          // Transform the document object to a XML file stored within the user-defined directory.
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
          DOMSource source = new DOMSource(doc);
          StreamResult result = new StreamResult(xmlFile);
          transformer.transform(source, result);

           }
           catch (ParserConfigurationException pce) {
           pce.printStackTrace();
           }
           catch (TransformerException tfe) {
           tfe.printStackTrace();
           }
        }
        // Leave existing file alone.
        else if ("n".equalsIgnoreCase(input)) {
          System.out.println("Starting simulation using exisiting XML file.");
        }
      }
      while (!"y".equalsIgnoreCase(input) && !"n".equalsIgnoreCase(input));
    }
    // The XML file does not exist.
    else {

      // Create the Directory if doesn't exist.      
      if (!theDir.exists() && !theDir.mkdir()) {
        System.out.println("Could not create the directory, exiting.");
        System.exit(1);
      }
      
      System.out.println("Creating properties file: " + xmlFile.getAbsolutePath());

      try {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        /** Process of appending the initial system device state information occurs here. */
        
        doc = appendStateDataToXml(doc, timestamp);
        
        /** End of appending the initial system device state information. **/

        // Transform the document object to a XML file stored within the user-defined directory.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
      }
      catch (ParserConfigurationException pce) {
        pce.printStackTrace();
      }
      catch (TransformerException tfe) {
        tfe.printStackTrace();
      }
    }
  }
  
  /**
   * Appends system state information to the passed-in Document object.
   *
   * @param doc Document object to append house system state data as child nodes.
   * @param timestamp The timestamp of when the appending process started at. Influences the
   *                  snap-shots of state data of each house system.
   * @return Document object with appended house system state data.
   */
  public static Document appendStateDataToXml(Document doc, long timestamp) {
    
    Document returnDoc = doc;
    Element rootElement = returnDoc.createElement("state-history");
    returnDoc.appendChild(rootElement);
    
    // Decrement a timestamp by 5 minutes.
    long timestampDecrement = 1000 * 60 * 5;
    // timestampPast is a timestamp value that gets decremented on to reflect
    // past timestamp values.
    long timestampPast = timestamp;
    
    // Append 12 state points of 5 minute intervals to represent 1 hour of past state data 
    // for all house system but Lighting.
    for (int i = 0; i < 12; i++) {
      returnDoc = AquaponicsData.toXmlByTimestamp(doc, timestampPast);
      returnDoc = HVACData.toXmlByTimestamp(doc, timestampPast);   
      timestampPast -= timestampDecrement;
    }
    
    // Decrement a timestamp by 60 minutes or 1 hour.
    timestampDecrement = 1000 * 60 * 60;
    timestampPast = timestamp;
    
    // Append 24 state points of 1 hour intervals to represent 1 day of past state data for
    // all house system but Lighting.
    for (int i = 0; i < 24; i++) {
      returnDoc = AquaponicsData.toXmlByTimestamp(doc, timestampPast);
      returnDoc = HVACData.toXmlByTimestamp(doc, timestampPast);          
      timestampPast -= timestampDecrement;
    }
    
    // Decrement a timestamp by 1 day.
    timestampDecrement = 1000 * 60 * 60 * 24;
    timestampPast = timestamp;
    
    // Append 31 state points of 1 day intervals to represent both at least 1 week of past 
    // state data and for a total of 1 month of state data for all house systems but 
    // Lighting.
    for (int i = 0; i < 31; i++) {
      returnDoc = AquaponicsData.toXmlByTimestamp(doc, timestampPast);
      returnDoc = HVACData.toXmlByTimestamp(doc, timestampPast);      
      timestampPast -= timestampDecrement;
    }
              
    Map<String, Integer> baseTime = new HashMap<String, Integer>();
    baseTime.put("year", Calendar.YEAR);
    baseTime.put("month", Calendar.MONTH);
    baseTime.put("date", Calendar.DATE);
    baseTime.put("hour", Calendar.HOUR_OF_DAY);
    baseTime.put("minute", Calendar.MINUTE);
    baseTime.put("second", Calendar.SECOND);
    baseTime.put("timestamp", (int) (timestamp / 1000));

    // Historic points are appended while passed to electric and photovoltaic data generation.
    returnDoc = ElectricalData.generateHistoricData(baseTime, returnDoc);
    returnDoc = PhotovoltaicsData.generateHistoricData(baseTime, returnDoc);
    return returnDoc;
  }
}
