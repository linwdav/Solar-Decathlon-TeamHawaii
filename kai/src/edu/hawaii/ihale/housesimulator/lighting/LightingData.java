package edu.hawaii.ihale.housesimulator.lighting;

import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the Lighting system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class LightingData {

  /** The living room lighting level. */
  private static int livingLevel = 100;
  /** The dining room lighting level. */
  private static int diningLevel = 80;
  /** The kitchen lighting level. */
  private static int kitchenLevel = 0;
  /** The bathroom lighting level. */
  private static int bathroomLevel = 0;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Living Room lights will be on ~60% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    double randLivingLevel = Math.random();
    if (randLivingLevel > 0.4) {
      setLivingLevel(new Random().nextInt(100));
    }
    else {
      setLivingLevel(0);
    }

    // Living Room lights will be on ~20% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    double randDiningLevel = Math.random();
    if (randDiningLevel > 0.8) {
      setDiningLevel(new Random().nextInt(100));
    }
    else {
      setDiningLevel(0);
    }
    
    // Kitchen lights will be on ~15% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    double randKitchenLevel = Math.random();
    if (randKitchenLevel > 0.85) {
      setKitchenLevel(new Random().nextInt(100));
    }
    else {
      setKitchenLevel(0);
    }
    
    // Bathroom lights will be on ~15% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    double randBathroomLevel = Math.random();
    if (randBathroomLevel > 0.85) {
      setBathroomLevel(new Random().nextInt(100));
    }
    else {
      setBathroomLevel(0);
    }

    System.out.println("----------------------");
    System.out.println("System: Lighting");
    System.out.println("Living Room Level: " + getLivingLevel());
    System.out.println("Dining Room Level: " + getDiningLevel());
    System.out.println("Kitchen Room Level: " + getKitchenLevel());
    System.out.println("Bathroom Level: " + getBathroomLevel());
  }

  /**
   * Accessor for the living room lighting level.
   * 
   * @return livingLevel
   */
  public static int getLivingLevel() {
    return livingLevel;
  }

  /**
   * Accessor for the dining room lighting level.
   * 
   * @return diningLevel
   */
  public static int getDiningLevel() {
    return diningLevel;
  }

  /**
   * Accessor for the kitchen lighting level.
   * 
   * @return kitchenLevel
   */
  public static int getKitchenLevel() {
    return kitchenLevel;
  }

  /**
   * Accessor for the bathroom lighting level.
   * 
   * @return bathroomLevel
   */
  public static int getBathroomLevel() {
    return bathroomLevel;
  }

  /**
   * Sets the living room lighting level.
   * 
   * @param newLivingLevel the level
   */
  public static void setLivingLevel(int newLivingLevel) {
    livingLevel = newLivingLevel;
  }

  /**
   * Sets the dining room lighting level.
   * 
   * @param newDiningLevel the level
   */

  public static void setDiningLevel(int newDiningLevel) {
    diningLevel = newDiningLevel;
  }

  /**
   * Sets the kitchen lighting level.
   * 
   * @param newKitchenLevel the level
   */

  public static void setKitchenLevel(int newKitchenLevel) {
    kitchenLevel = newKitchenLevel;
  }

  /**
   * Sets the bathroom lighting level.
   * 
   * @param newBathroomLevel the level
   */
  public static void setBathroomLevel(int newBathroomLevel) {
    bathroomLevel = newBathroomLevel;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @param room the room.
   * @return The data as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public static DomRepresentation toXml(String room) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element root = doc.createElement("state-data");
    root.setAttribute("system", "lighting");

    String device = "device";
    // Set attribute according to room.
    if ("living".equalsIgnoreCase(room)) {
      root.setAttribute(device, "arduino-5");
    }
    else if ("dining".equalsIgnoreCase(room)) {
      root.setAttribute(device, "arduino-6");
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      root.setAttribute(device, "arduino-7");
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      root.setAttribute(device, "arduino-8");
    }

    root.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(root);

    // Create state tag.
    Element levelState = doc.createElement("state");
    levelState.setAttribute("key", "level");

    String value = "value";
    // Retrieve lighting level according to room.
    if ("living".equalsIgnoreCase(room)) {
      levelState.setAttribute(value, String.valueOf(getLivingLevel()));
    }
    else if ("dining".equalsIgnoreCase(room)) {
      levelState.setAttribute(value, String.valueOf(getDiningLevel()));
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      levelState.setAttribute(value, String.valueOf(getKitchenLevel()));
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      levelState.setAttribute(value, String.valueOf(getBathroomLevel()));
    }

    root.appendChild(levelState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
