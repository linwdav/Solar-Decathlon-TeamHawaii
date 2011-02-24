package edu.hawaii.ihale.housesimulator.lighting;

import java.util.Date;
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
  private static int livingLevel = 10;
  /** The dining room lighting level. */
  private static int diningLevel = 5;
  /** The kitchen lighting level. */
  private static int kitchenLevel = 8;
  /** The bathroom lighting level. */
  private static int bathroomLevel = 6;

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
   * @param newLevel the level
   */
  public static void setLivingLevel(int newLivingLevel) {
    livingLevel = newLivingLevel;
  }

  /**
   * Sets the dining room lighting level.
   * 
   * @param newLevel the level
   */

  public static void setDiningLevel(int newDiningLevel) {
    diningLevel = newDiningLevel;
  }

  /**
   * Sets the kitchen lighting level.
   * 
   * @param newLevel the level
   */

  public static void setKitchenLevel(int newKitchenLevel) {
    kitchenLevel = newKitchenLevel;
  }

  /**
   * Sets the bathroom lighting level.
   * 
   * @param newLevel the level
   */
  public static void setBathroomLevel(int newBathroomLevel) {
    bathroomLevel = newBathroomLevel;
  }

  /**
   * Returns the data as an XML Document instance.
   * 
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

    // Set attribute according to room.
    if ("living".equalsIgnoreCase(room)) {
      root.setAttribute("device", "arduino-5");
    }
    else if ("dining".equalsIgnoreCase(room)) {
      root.setAttribute("device", "arduino-6");
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      root.setAttribute("device", "arduino-7");
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      root.setAttribute("device", "arduino-8");
    }

    root.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(root);

    // Create state tag.
    Element levelState = doc.createElement("state");
    levelState.setAttribute("key", "level");

    // Retrieve lighting level according to room.
    if ("living".equalsIgnoreCase(room)) {
      levelState.setAttribute("value", String.valueOf(getLivingLevel()));
    }
    else if ("dining".equalsIgnoreCase(room)) {
      levelState.setAttribute("value", String.valueOf(getDiningLevel()));
    }
    else if ("kitchen".equalsIgnoreCase(room)) {
      levelState.setAttribute("value", String.valueOf(getKitchenLevel()));
    }
    else if ("bathroom".equalsIgnoreCase(room)) {
      levelState.setAttribute("value", String.valueOf(getBathroomLevel()));
    }

    root.appendChild(levelState);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }
}
