package edu.hawaii.systemh.housesimulator.hvac;

/**
 * Provides a record of average high and average low temperatures.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TemperatureRecord {

  private int avgHighTemp;
  private int avgLowTemp;
  private int meanTemp;
  
  /**
   * Main constructor.
   * 
   * @param avgHighTemp Average high temperature for the month.
   * @param avgLowTemp Average low temperature for the month.
   */
  public TemperatureRecord(int avgHighTemp, int avgLowTemp) {
    this.avgHighTemp = avgHighTemp;
    this.avgLowTemp = avgLowTemp;
    meanTemp = (avgHighTemp + avgLowTemp) / 2;
  }
  
  /**
   * Returns the average high temperature of the month.
   *
   * @return Average high temperature.
   */
  public int getAvgHighTemp() {
    return this.avgHighTemp;
  }
  
  /**
   * Returns the average low temperature of the month.
   *
   * @return Average low temperature.
   */
  public int getAvgLowTemp() {
    return this.avgLowTemp;
  }
  
  /**
   * Returns the mean temperature determined from the average high and low temperature of the
   * month.
   *
   * @return Mean temperature.
   */
  public int getMeanTemp() {
    return this.meanTemp;
  }
}
