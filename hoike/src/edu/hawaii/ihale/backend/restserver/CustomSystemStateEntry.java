package edu.hawaii.ihale.backend.restserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Provides data about the state of a specific system in the Solar Decathlon house at a specific
 * point in time.
 * <p> 
 * Every SystemStateEntry consists of: (1) a timestamp (long) specifying when the state data
 * was collected; (2) a string indicating the name of the system; (3) a string indicating the 
 * device associated with the system; and (4) a set of key-value pairs that provide the state
 * data for this system.
 * <p> 
 * State data is maintained via key-value pairs where the keys are always strings and the 
 * values can be either Strings, Longs, or Doubles (i.e. floating point numbers).
 *
 * Addition to the SystemStateEntry class features: (1) Accessor to all 3 Map fields (2) Accessor
 * to each respective Map keys allowing easier XML document creation.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class CustomSystemStateEntry extends SystemStateEntry {
  
  private Map<String, Long> longData = new HashMap<String, Long>();
  private Map<String, String> stringData = new HashMap<String, String>();
  private Map<String, Double> doubleData = new HashMap<String, Double>();
  
  private List<String> longDataKey = new ArrayList<String>();
  private List<String> stringDataKey = new ArrayList<String>();
  private List<String> doubleDataKey = new ArrayList<String>();
  
  /** The state-data element name. */
  private static final String stateDataElementName = "state-data";

  /** The state element name. */
  private static final String stateElementName = "state";

  /** The timestamp attribute name. */
  private static final String timestampAttributeName = "timestamp";

  /** The system name attribute name. */
  private static final String systemNameAttributeName = "system";

  /** The device name attribute name. */
  private static final String deviceNameAttributeName = "device";
  
  /**
   * A simple constructor that has no initial Map data.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public CustomSystemStateEntry(String systemName, String deviceName, long timestamp) {
    super(systemName, deviceName, timestamp);
  }

  /**
   * A constructor with initial Map data.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @param longData  State information associated with this entry that are of Long value type.
   * @param stringData State information associated with this entry that are of String value type.
   * @param doubleData State information associated with this entry that are of Double value type.
   */
  public CustomSystemStateEntry(String systemName, String deviceName, long timestamp,
      Map<String, Long> longData, Map<String, String> stringData, Map<String, Double> doubleData) {
    super(systemName, deviceName, timestamp);
    this.longData = longData;
    this.stringData = stringData;
    this.doubleData = doubleData;
  }
  
  /**
   * Create a IHaleEntry instance given its representation in XML.
   * @param doc XML document.
   */
  public CustomSystemStateEntry(Document doc) {
    super(getAttribute(doc, systemNameAttributeName), getAttribute(doc, deviceNameAttributeName),
        Long.valueOf(getAttribute(doc, timestampAttributeName)));
    putKeyValuePair(doc);
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
  @Override
  public void putDoubleValue(String key, Double value) {
    this.doubleData.put(key, value);
    this.doubleDataKey.add(key);
  }
  
  /**
   * Sets the long value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  @Override
  public void putLongValue(String key, long value) {
    this.longData.put(key, value);
    this.longDataKey.add(key);
  }
  
  /**
   * Sets the double value of the passed key to value.
   * Any previous value will be overwritten. 
   * @param key The string key.
   * @param value The double value. 
   */
  @Override
  public void putStringValue(String key, String value) {
    this.stringData.put(key, value);
    this.stringDataKey.add(key);
  }
  
  /**
   * Returns the keys associated with value type Long.
   *
   * @return The keys.
   */
  public List<String> getLongDataKey() {
    return this.longDataKey;
  }
  
  /**
   * Returns the keys associated with value type Double.
   *
   * @return The keys.
   */
  public List<String> getDoubleDataKey() {
    return this.doubleDataKey;
  }
  
  /**
   * Returns the keys associated with value type String.
   *
   * @return The keys.
   */
  public List<String> getStringDataKey() {
    return this.stringDataKey;
  }
  
  /**
   * Returns a Map containing Long value types.
   *
   * @return Map of Long values. 
   */
  public Map<String, Long> getLongData() {
    return this.longData;
  }
  
  /**
   * Returns a Map containing String value types.
   *
   * @return Map of String values.
   */
  public Map<String, String> getStringData() {
    return this.stringData;
  }
  
  /**
   * Returns a Map containing Double value types.
   *
   * @return Map of Double values.
   */
  public Map<String, Double> getDoubleData() {
    return this.doubleData;
  }
  
  /**
   * Return this state entry as a formatted string.
   * @return The state entry as a string. 
   */
  @Override
  public String toString() {
    return String.format(
        "[StateEntry System: %s Device: %s Time: %s (%s) State: %s %s %s]", 
        super.getSystemName(), super.getDeviceName(), super.getTimestamp(), 
        new Date(super.getTimestamp()), this.stringData, this.longData, this.doubleData);
  }

  /**
   * Helper method that returns the attribute of the root element of this XML document.
   * @param doc The XML document.
   * @param attributeName The attribute to return... system, device, or timestamp.
   * @return The attribute.
   */
  private static String getAttribute(Document doc, String attributeName) {
    Element element = (Element) doc.getElementsByTagName(stateDataElementName).item(0);
    if (systemNameAttributeName.equals(attributeName)) {
      return element.getAttribute(systemNameAttributeName);
    }
    else if (deviceNameAttributeName.equals(attributeName)) {
      return element.getAttribute(deviceNameAttributeName);
    }
    else if (timestampAttributeName.equals(attributeName)) {
      return element.getAttribute(timestampAttributeName);
    }
    else {
      // Will never reach here.
      return null;
    }
  }

  /**
   * Places all key-value pairs into a map.
   * @param doc The XML document.
   */
  private void putKeyValuePair(Document doc) {
    NodeList keyValueList = doc.getElementsByTagName(stateElementName);
    for (int i = 0; i < keyValueList.getLength(); i++) {
      Element element = (Element) doc.getElementsByTagName(stateElementName).item(i);
      String key = element.getAttribute("key");
      String value = element.getAttribute("value");
      this.stringData.put(key, value);
    }
  }

}
