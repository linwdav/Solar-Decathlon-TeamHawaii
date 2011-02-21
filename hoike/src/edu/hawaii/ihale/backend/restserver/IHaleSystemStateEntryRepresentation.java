package edu.hawaii.ihale.backend.restserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides a record of system state information, as well as an XML representation.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class IHaleSystemStateEntryRepresentation {

  /** The name of the System. Example: "Aquaponics". */
  private String systemName;
  /** The name of the Device. Example: "Arduino-23". */
  private String deviceName;
  /** The timestamp (UTC format) indicating when this state info was collected. */
  private long timestamp;
  
  private Map<String, Long> longData = new HashMap<String, Long>();
  private Map<String, String> stringData = new HashMap<String, String>();
  private Map<String, Double> doubleData = new HashMap<String, Double>();
  
  /**
   * Creates a IHaleSystemStateEntryRepresentation instance without any data values. 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public IHaleSystemStateEntryRepresentation(String systemName, String deviceName, long timestamp) {
    this.systemName = systemName;
    this.deviceName = deviceName;
    this.timestamp = timestamp;
  }
  
  /**
   * Creates a IHaleSystemStateEntryRepresentation instance given its representation in XML.
   * @param doc The XML document. 
   */
  public IHaleSystemStateEntryRepresentation(Document doc) {

    /** TO-DO: Define the implementation after defining a method that handles attribute
     *         retrieval from a XML document.
     */

  }
  
  /**
   * Returns the system name associated with this entry.
   * @return The system name.
   */
  public String getSystemName() {
    return this.systemName;
  }
  
  /**
   * Returns the device name associated with this entry.
   * @return The device name.
   */
  public String getDeviceName() {
    return this.deviceName;
  }
  
  /**
   * Returns the timestamp associated with this entry.
   * @return The timestamp.
   */
  public long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Returns a double state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The double state value.
   */
  public double getDoubleValue(String key) {
    return this.doubleData.get(key);
  }
  
  /**
   * Returns a long state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The long state value.
   */
  public long getLongValue(String key) {
    return this.longData.get(key);
  }
  
  /**
   * Returns a string state value associated with this entry, or null if not found.
   * @param key The key string.
   * @return The double state value.
   */
  public String getStringValue(String key) {
    return this.stringData.get(key);
  }
  
  /**
   * Sets the double value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putDoubleValue(String key, Double value) {
    this.doubleData.put(key, value);
  }
  
  /**
   * Sets the long value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putLongValue(String key, long value) {
    this.longData.put(key, value);
  }
  
  /**
   * Sets the double value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  public void putStringValue(String key, String value) {
    this.stringData.put(key, value);
  }
  
  /**
   * Return this state entry as a formatted string.
   * @return The state entry as a string. 
   */
  @Override
  public String toString() {
    return String.format(
        "[StateEntry System: %s Device: %s Time: %s (%s) State: %s %s %s]", 
        this.systemName, this.deviceName, this.timestamp, new Date(this.timestamp), 
        this.stringData, this.longData, this.doubleData);
  }
  
  /**
   * Helper function that creates a child element and attaches it to the passed parent element.
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
   * Under consideration if this method is necessary.
   */
  private void attachAttribute() {
    // To remove PMD complaints.
    System.out.println("");
  }
  
  /**
   * Helper method that returns the text content of an interior element of this XML document. 
   * @param doc The XML document. 
   * @param elementName The element name whose text content is to be retrieved.
   * @return The text content
   */
  private String getElementTextContent(Document doc, String elementName) {
    Element element = (Element) doc.getElementsByTagName(elementName).item(0);
    return element.getTextContent();
  }
}
