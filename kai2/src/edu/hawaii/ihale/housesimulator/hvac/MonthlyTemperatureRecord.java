package edu.hawaii.ihale.housesimulator.hvac;

/**
 * Provides a record of average high and average low temperatures for a given month.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class MonthlyTemperatureRecord {

  private String month;
  private int avgHighTemp;
  private int avgLowTemp;
  private int meanTemp;
  
  /**
   * Main constructor.
   * 
   * @param month The month in String format.
   * @param avgHighTemp Average high temperature for the month.
   * @param avgLowTemp Average low temperature for the month.
   */
  public MonthlyTemperatureRecord(String month, int avgHighTemp, int avgLowTemp) {
    this.month = month;
    this.avgHighTemp = avgHighTemp;
    this.avgLowTemp = avgLowTemp;
    meanTemp = (avgHighTemp + avgLowTemp) / 2;
  }
  
  /**
   * Returns the month.
   *
   * @return The month.
   */
  public String getMonth() {
    return this.month;
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
  public int getAvgLowHighTemp() {
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
