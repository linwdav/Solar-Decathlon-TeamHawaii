package edu.hawaii.ihale.db;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

/**
 * Provides a simple record of information about a device/sensor/meter in a house system.
 * @author David Lin
 */
@Entity
public class IHaleEntry {

  /** The timestamp (UTC format) indicating when this state info was collected. */
  @PrimaryKey
  private long timestamp;

  /** The name of the System. Example: "Aquaponics". */
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String systemName;

  /** The name of the Device. Example: "Arduino-23". */
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String deviceName;

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

  private Map<String, Long> longData = new HashMap<String, Long>();
  private Map<String, String> stringData = new HashMap<String, String>();
  private Map<String, Double> doubleData = new HashMap<String, Double>();

  /**
   * Provide the default constructor required by BerkeleyDB.
   */
  public IHaleEntry() {
    // Do nothing.
  }

  /**
   * Create an IHaleEntry instance given data values.
   * @param systemName
   * @param deviceName
   * @param timestamp
   */
  public IHaleEntry(String systemName, String deviceName, long timestamp) {
    this.systemName = systemName;
    this.deviceName = deviceName;
    this.timestamp = timestamp;
  }

  /**
   * Create a IHaleEntry instance given its representation in XML.
   * @param doc
   */
  public IHaleEntry(Document doc) {
    this.systemName = getAttribute(doc, systemNameAttributeName);
    this.deviceName = getAttribute(doc, deviceNameAttributeName);
    this.timestamp = Long.valueOf(getAttribute(doc, timestampAttributeName));
    putKeyValuePair(doc);
  }

  /**
   * Helper method that returns the attribute of the root element of this XML document.
   * @param doc The XML document.
   * @param attributeName The attribute to return... system, device, or timestamp.
   * @return The attribute.
   */
  private String getAttribute(Document doc, String attributeName) {
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

  /**
   * Return this entry as a formatted string.
   * @return The entry as a string.
   */
  @Override
  public String toString() {
    return String.format("[System: %s Device: %s Time: %s State: %s %s %s]", this.systemName,
        this.deviceName, this.timestamp, this.stringData, this.longData, this.doubleData);
  }
}
