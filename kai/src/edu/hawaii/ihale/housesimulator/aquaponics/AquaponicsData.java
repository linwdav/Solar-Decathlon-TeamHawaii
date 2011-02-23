package edu.hawaii.ihale.housesimulator.aquaponics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the Aquaponics system, as well as an XML representation.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class AquaponicsData {
  /** The system name. */
  private String systemName = "Aquaponics";
  /** The current temperature. */
  private String temperature;
  /** The current pH. */
  private String ph;
  /** The current dissolved oxygen. */
  private String dissolvedOxygen;
  /** This data's timestamp. */
  private String timestamp;
  /** The temperature element name. */
  private static final String systemNameElementName = "system-name";
  /** The temperature element name. */
  private static final String temperatureElementName = "temperature";
  /** The pH element name. */
  private static final String phElementName = "pH";
  /** The dissolved oxygen element name. */
  private static final String dissolvedOxygenElementName = "dissolved-oxygen";
  /** The timestamp element name. */
  private static final String timestampElementName = "timestamp";

  /**
   * Creates a AquaponicsData instance given its field values as strings.
   * 
   * @param temperature The temperature.
   * @param ph The pH.
   * @param dissolvedOxygen The dissolved oxygen.
   * @param timestamp The timestamp.
   */
  public AquaponicsData(String temperature, String ph, String dissolvedOxygen, String timestamp) {
    this.temperature = temperature;
    this.ph = ph;
    this.dissolvedOxygen = dissolvedOxygen;
    this.timestamp = timestamp;
  }

  /**
   * Creates a AquaponicsData instance given its representation in XML.
   * 
   * @param doc The XML document.
   */
  public AquaponicsData(Document doc) {
    this.systemName = getElementTextContent(doc, systemNameElementName);
    this.temperature = getElementTextContent(doc, temperatureElementName);
    this.ph = getElementTextContent(doc, phElementName);
    this.dissolvedOxygen = getElementTextContent(doc, dissolvedOxygenElementName);
    this.timestamp = getElementTextContent(doc, timestampElementName);
  }

  /**
   * Return the data as a formatted string.
   * 
   * @return The data as a string.
   */
  @Override
  public String toString() {
    return String.format(
        "[System Name: %s Temperature: %s pH: %s Dissolved Oxygen: %s Timestamp: %s]",
        this.systemName, this.temperature, this.ph, this.dissolvedOxygen, this.timestamp);
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @return The data as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public Document toXml() throws Exception {
    // Create the Document instance representing this XML.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    // Create and attach the root element <contact>.
    Element rootElement = doc.createElement(systemName);
    doc.appendChild(rootElement);
    // Now create and attach the fields for this contact.
    attachElement(doc, rootElement, temperatureElementName, this.temperature);
    attachElement(doc, rootElement, phElementName, this.ph);
    attachElement(doc, rootElement, dissolvedOxygenElementName, this.dissolvedOxygen);
    attachElement(doc, rootElement, timestampElementName, this.timestamp);
    return doc;
  }

  /**
   * Helper function that creates a child element and attaches it to the passed parent element.
   * 
   * @param doc The document for creating elements.
   * @param parent The parent element.
   * @param childName The name of the child element.
   * @param childValue The text value for the child element.
   */
  private void attachElement(Document doc, Element parent, String childName, String childValue) {
    Element childElement = doc.createElement(childName);
    childElement.setTextContent(childValue);
    parent.appendChild(childElement);
  }

  /**
   * Helper method that returns the text content of an interior element of this XML document.
   * 
   * @param doc The XML document.
   * @param elementName The element name whose text content is to be retrieved.
   * @return The text content
   */
  private String getElementTextContent(Document doc, String elementName) {
    Element element = (Element) doc.getElementsByTagName(elementName).item(0);
    return element.getTextContent();
  }
}
