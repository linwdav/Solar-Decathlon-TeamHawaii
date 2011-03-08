package edu.hawaii.solardecathlon.listeners;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Creates the Electrical listener.
 * 
 * @author Bret K. Ikehara
 */
public class ElectricalListener extends SystemStateListener {

  private Long timestamp;
  private Long power;
  private Long energy;
  
  /**
   * Default Constructor.
   * 
   * @param systemName String
   */
  public ElectricalListener(String systemName) {
    super(systemName);
    this.timestamp = 0L;
    this.power = 0L;
    this.energy = 0L;
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
    this.power = entry.getLongValue("power");
    this.energy = entry.getLongValue("energy");
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
   * Gets this power.
   * 
   * @return Long
   */
  public Long getPower() {
    return power;
  }

  /**
   * Gets this energy.
   * 
   * @return Long
   */
  public Long getEnergy() {
    return energy;
  }
}
