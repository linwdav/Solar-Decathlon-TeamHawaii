package edu.hawaii.ihale.wicket.model;

/**
 * Property Model for the HVac System.
 *  
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class HVacModel extends SystemModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Long temp;
  
  /**
   * Default Constructor.
   */
  public HVacModel() {
    this.temp = 0L;
  }
  
  /**
   * Gets this HVac model time stamp.
   * 
   * @return String
   */
  @Override
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets this HVac model time stamp.
   * 
   * @param timestamp Long
   */
  @Override
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Gets this HVac model temperature.
   * 
   * @return long
   */
  public Long getTemp() {
    return temp;
  }

  /**
   * Sets this HVac model temperature.
   * 
   * @param temp Long
   */
  public void setTemp(Long temp) {
    this.temp = temp;
  }

}
