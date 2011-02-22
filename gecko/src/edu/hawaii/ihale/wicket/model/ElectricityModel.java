package edu.hawaii.ihale.wicket.model;

/**
 * Property Model for the Electricty/Photovoltaics System.
 *  
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class ElectricityModel extends SystemModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Double power;
  private Double energy;
  
  /**
   * Default Constructor.
   */
  public ElectricityModel() {
    this.power = 0.0;
    this.energy = 0.0;
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
   * @return double
   */
  public Double getPower() {
    return power;
  }

  /**
   * Sets this Electricity model power level.
   * 
   * @param power Double
   */
  public void setPower(Double power) {
    this.power = power;
  }

  /**
   * Gets this Electricity model energy level.
   * 
   * @return Double
   */
  public Double getEnergy() {
    return energy;
  }

  /**
   * Sets this Electricity model energy level.
   * 
   * @param energy Double
   */
  public void setEnergy(Double energy) {
    this.energy = energy;
  }
}
