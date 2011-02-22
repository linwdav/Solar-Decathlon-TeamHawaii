package edu.hawaii.ihale.ui.model;

/**
 * Property Model for the Aquaponics System.
 *  
 * @author Bret Ikehara
 */
public class AquaponicsModel extends SystemModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  /**
   * Gets this Aquaponics time stamp.
   * 
   * @return String
   */
  @Override
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets this Aquaponics time stamp.
   * 
   * @param timestamp Long
   */
  @Override
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }
}
