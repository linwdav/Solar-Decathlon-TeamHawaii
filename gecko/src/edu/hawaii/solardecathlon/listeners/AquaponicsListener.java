package edu.hawaii.solardecathlon.listeners;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * Creates the Aquaponics listener.
 * 
 * @author Bret K. Ikehara
 */
public class AquaponicsListener extends SystemStateListener {

  private Long timestamp;
  private Long temp;
  private Double ph;
  private Double oxygen;

  /**
   * Default Constructor.
   * 
   * @param systemName String
   */
  public AquaponicsListener(String systemName) {
    super(systemName);
    this.timestamp = 0L;
    this.temp = 0L;
    this.ph = 0.0;
    this.oxygen = 0.0;
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
    this.ph = entry.getDoubleValue("ph");
    this.oxygen = entry.getDoubleValue("oxygen");
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

  /**
   * Gets this Aquaponics model pH level.
   * 
   * @return double
   */
  public Double getPh() {
    return ph;
  }

  /**
   * Gets this Aquaponics model oxygen level.
   * 
   * @return Double
   */
  public Double getOxygen() {
    return oxygen;
  }
}
