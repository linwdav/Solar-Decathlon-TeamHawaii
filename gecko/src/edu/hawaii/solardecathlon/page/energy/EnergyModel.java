package edu.hawaii.solardecathlon.page.energy;

import edu.hawaii.solardecathlon.components.BaseModel;

/**
 * Property Model for the Electricty/Photovoltaics System.
 *  
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class EnergyModel extends BaseModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Long power;
  private Long energy;
  
  /**
   * Default Constructor.
   */
  public EnergyModel() {
    this.power = 0L;
    this.energy = 0L;
  }
  
  /**
   * Gets this Electricity model time stamp.
   * 
   * @return String
   */
  @Override
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets this Electricity model time stamp.
   * 
   * @param timestamp Long
   */
  @Override
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Gets this Electricity model power level.
   * 
   * @return Long
   */
  public Long getPower() {
    return power;
  }

  /**
   * Sets this Electricity model power level.
   * 
   * @param power Long
   */
  public void setPower(Long power) {
    this.power = power;
  }

  /**
   * Gets this Electricity model energy level.
   * 
   * @return Long
   */
  public Long getEnergy() {
    return energy;
  }

  /**
   * Sets this Electricity model energy level.
   * 
   * @param energy Long
   */
  public void setEnergy(Long energy) {
    this.energy = energy;
  }
}
