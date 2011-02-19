package edu.hawaii.ihale.backend.db;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
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
   * The constructor.
   * 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public IHaleSystemStateEntry(String systemName, String deviceName, long timestamp) {
    super(systemName, deviceName, timestamp);
    this.timestamp = timestamp;
  }
}
