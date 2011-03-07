package edu.hawaii.solardecathlon.page.temperature;

import edu.hawaii.solardecathlon.components.BaseModel;

/**
 * Property Model for the HVac System.
 *  
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class TemperatureModel extends BaseModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Long temp;
  
  /**
   * Default Constructor.
   */
  public TemperatureModel() {
    this.temp = 50L;
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
