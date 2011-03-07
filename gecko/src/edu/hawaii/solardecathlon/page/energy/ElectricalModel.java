package edu.hawaii.solardecathlon.page.energy;

import edu.hawaii.solardecathlon.components.BaseModel;

/**
 * Property Model for the Electricty/Photovoltaics System.
 *  
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class ElectricalModel extends BaseModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Long power;
  private Long energy;
  
  /**
   * Default Constructor.
   */
  public ElectricalModel() {
    this.power = 0L;
    this.energy = 0L;
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
