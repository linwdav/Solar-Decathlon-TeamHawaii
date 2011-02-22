package edu.hawaii.ihale.wicket.model;

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

  private Long temp;
  private Double ph;
  private Double oxygen;
  
  /**
   * Default Constructor.
   */
  public AquaponicsModel() {
    this.temp = 0L;
    this.ph = 0.0;
    this.oxygen = 0.0;
  }
  
  /**
   * Gets this Aquaponics model time stamp.
   * 
   * @return String
   */
  @Override
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets this Aquaponics model time stamp.
   * 
   * @param timestamp Long
   */
  @Override
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
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
   * Sets this Aquaponics model temperature.
   * 
   * @param temp Long
   */
  public void setTemp(Long temp) {
    this.temp = temp;
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
   * Sets this Aquaponics model pH level.
   * 
   * @param ph Double
   */
  public void setPh(Double ph) {
    this.ph = ph;
  }

  /**
   * Gets this Aquaponics model oxygen level.
   * 
   * @return Double
   */
  public Double getOxygen() {
    return oxygen;
  }

  /**
   * Sets this Aquaponics model oxygen level.
   * 
   * @param oxygen Double
   */
  public void setOxygen(Double oxygen) {
    this.oxygen = oxygen;
  }
}
