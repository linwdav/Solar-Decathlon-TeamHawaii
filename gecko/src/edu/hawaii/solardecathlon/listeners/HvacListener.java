package edu.hawaii.solardecathlon.listeners;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Creates the HVAC listener.
 * 
 * @author Bret K. Ikehara
 */
public class HvacListener extends SystemStateListener {

  private Long timestamp;
  private Long temp;
  
  /**
   * Default Constructor.
   * 
   * @param systemName String
   */
  public HvacListener(String systemName) {
    super(systemName);
    this.timestamp = 0L;
    this.temp = 0L;
  }


  /**
   * Handles the a new entry. This is called by the backend api to update the values for the user
   * interface.
   * 
   * @param entry SystemStateEntry
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println(entry);
    this.timestamp = entry.getTimestamp();
    this.temp = entry.getLongValue("temp");
  }
  
  /**
   * Gets the System Model time stamp.
   * 
   * @return Long
   */
  public Long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Gets this Aquaponics model temperature.
   * 
   * @return long
   */
  public Long getTemp() {
    return temp;
  }
}
