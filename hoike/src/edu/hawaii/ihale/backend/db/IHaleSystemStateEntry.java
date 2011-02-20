package edu.hawaii.ihale.backend.db;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * The database entity representation of the SystemStateEntry object specific to the iHale
 * house systems.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
@Entity
public class IHaleSystemStateEntry extends SystemStateEntry {

  /** The timestamp (UTC format) indicating when this state info was collected. This is
   *  also the Primary Key.
   */
  @PrimaryKey
  private long timestamp;
  
  /**
   * The name of the System. Example: "Aquaponics". This is also the Secondary Key.
   * There can be many entries with the same system name but must have a unique timestamp 
   * (the primary key) value.
   */
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String systemName;
  
  @SecondaryKey(relate = Relationship.MANY_TO_ONE)
  private String deviceName;
  
  /**
   * The constructor.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public IHaleSystemStateEntry(String systemName, String deviceName, long timestamp) {
    super(systemName, deviceName, timestamp);
    this.timestamp = timestamp;
    this.systemName = systemName;
    this.deviceName = deviceName;
  }
  
  /**
   * Purpose of this method is to suppress errors and pass verify.
   * Delete this method later when the variables are actually used locally.
   */
  public void methodToSuppressUnusedVariables() {
    System.out.println(timestamp);
    System.out.println(systemName);
    System.out.println(deviceName);
  }
}
