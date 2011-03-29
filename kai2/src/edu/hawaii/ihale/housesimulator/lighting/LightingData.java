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
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class LightingData {

  /** The living room lighting level. */
  private static long livingLevel = 70;
  /** The dining room lighting level. */
  private static long diningLevel = 80;
  /** The kitchen lighting level. */
  private static long kitchenLevel = 90;
  /** The bathroom lighting level. */
  private static long bathroomLevel = 100;
  
  /** The dining room lighting color. */
  private static String diningColor = "#FF01FF";
  /** The bathroom lighting color. */
  private static String bathroomColor = "#FF02FF";
  /** The living room lighting color. */
  private static String livingColor = "#FF03FF";
  /** The kitchen lighting color. */
  private static String kitchenColor = "#FF04FF";
  
  /** The living light switch. */
  private static boolean livingEnabled = false;  
  /** The kitchen light switch. */
  private static boolean kitchenEnabled = false;
  /** The bathroom lighting switch. */
  private static boolean bathroomEnabled = false;
  /** The dining light switch. */
  private static boolean diningEnabled = false;

  
  /** Flag for if the occupants are home or away. **/
  //private static boolean occupantsHome  = false;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {
    final Random randomGenerator = new Random();

    // Living Room lights will be on ~60% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    if (randomGenerator.nextDouble() > 0.4) {
      if (randomGenerator.nextBoolean()) {
        livingLevel = randomGenerator.nextInt(101);
      }
    }
    else {
      livingLevel = 0;
    }

    // Dining Room lights will be on ~20% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    if (randomGenerator.nextDouble() > 0.8) {
      if (randomGenerator.nextBoolean()) {
        diningLevel = randomGenerator.nextInt(101);
      }
    }
    else {
      diningLevel = 0;
    }

    // Kitchen lights will be on ~15% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    if (randomGenerator.nextDouble() > 0.85) {
      if (randomGenerator.nextBoolean()) {
        kitchenLevel = randomGenerator.nextInt(101);
      }
    }
    else {
      kitchenLevel = 0;
    }

    // Bathroom lights will be on ~15% of the time
    // When they are turned on they will be set to a
    // random value, just to show fluctuation in data
    if (randomGenerator.nextDouble() > 0.85) {
      if (randomGenerator.nextBoolean()) {
        bathroomLevel = randomGenerator.nextInt(101);
      }
    }
    else {
      bathroomLevel = 0;
    }

    System.out.println("----------------------");
    System.out.println("System: Lighting");
    System.out.println("Living Room Level: " + livingLevel);
    System.out.println("Dining Room Level: " + diningLevel);
    System.out.println("Kitchen Room Level: " + kitchenLevel);
    System.out.println("Bathroom Level: " + bathroomLevel);
  }

  /**
   * Sets the living room lighting level.
   * 
   * @param newLivingLevel the level
   */
  public static void setLivingLevel(long newLivingLevel) {
    livingLevel = newLivingLevel;
  }

  /**
   * Sets the dining room lighting level.
   * 
   * @param newDiningLevel the level
   */

  public static void setDiningLevel(long newDiningLevel) {
    diningLevel = newDiningLevel;
  }

  /**
   * Sets the kitchen lighting level.
   * 
   * @param newKitchenLevel the level
   */

  public static void setKitchenLevel(long newKitchenLevel) {
    kitchenLevel = newKitchenLevel;
  }

  /**
   * Sets the bathroom lighting level.
   * 
   * @param newBathroomLevel the level
   */
  public static void setBathroomLevel(long newBathroomLevel) {
    bathroomLevel = newBathroomLevel;
  }
  
  /**
   * Sets the bathroom lighting colors.
   * 
   * @param newBathroomColor the colors
   */
  public static void setBathroomColor(String newBathroomColor) {
    bathroomColor = newBathroomColor;
  }
  
  /**
   * Sets the bathroom lighting to on or off.
   * 
   * @param newBathroomEnabled the colors
   */
  public static void setBathroomEnabled(Boolean newBathroomEnabled) {
    bathroomEnabled = newBathroomEnabled;
  }
  

  /**
   * Sets the living lighting colors.
   * 
   * @param newLivingColor the colors
   */
  public static void setLivingColor(String newLivingColor) {
    livingColor = newLivingColor;
  }
  
  /**
   * Sets the living room lighting to on or off.
   * 
   * @param newLivingEnabled the colors
   */
  public static void setLivingEnabled(Boolean newLivingEnabled) {
    livingEnabled = newLivingEnabled;
  }
  
  /**
   * Sets the living lighting colors.
   * 
   * @param newKitchenColor the colors
   */
  public static void setKitchenColor(String newKitchenColor) {
   kitchenColor = newKitchenColor;
  }
  
  /**
   * Sets the living room lighting to on or off.
   * 
   * @param newKitchenEnabled the colors
   */
  public static void setKitchenEnabled(Boolean newKitchenEnabled) {
    kitchenEnabled = newKitchenEnabled;
  }

  /**
   * Sets the living lighting colors.
   * 
   * @param newDiningColor the colors
   */
  public static void setDiningColor(String newDiningColor) {
   diningColor = newDiningColor;
  }
  
  /**
   * Sets the living room lighting to on or off.
   * 
   * @param newDiningEnabled the colors
   */
  public static void setDiningEnabled(Boolean newDiningEnabled) {
    diningEnabled = newDiningEnabled;
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
    //modifySystemState();

    // Create root tag
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", "lighting");

    String device = "device";

    // Set attribute according to room.
    if ("living".equalsIgnoreCase(room)) {
      rootElement.setAttribute(device, "arduino-5");
    }
    else if ("dining".equalsIgnoreCase(room)) {
      rootElement.setAttribute(device, "arduino-6");
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      rootElement.setAttribute(device, "arduino-7");
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      rootElement.setAttribute(device, "arduino-8");
    }

    rootElement.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(rootElement);

    // Create state tag.
    Element levelElement = doc.createElement("state");
    levelElement.setAttribute("key", "level");

    String value = "value";
    // Retrieve lighting level according to room.
    if ("living".equalsIgnoreCase(room)) {
      levelElement.setAttribute(value, String.valueOf(livingLevel));
    }
    else if ("dining".equalsIgnoreCase(room)) {
      levelElement.setAttribute(value, String.valueOf(diningLevel));
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      levelElement.setAttribute(value, String.valueOf(kitchenLevel));
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      levelElement.setAttribute(value, String.valueOf(bathroomLevel));
    }

    rootElement.appendChild(levelElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
