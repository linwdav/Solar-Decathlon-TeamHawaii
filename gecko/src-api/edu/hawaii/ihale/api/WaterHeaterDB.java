package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface WaterHeaterDB {

  /**
   * Get the instance of this Water Heater by Primary Key ID.
   * 
   * @param id String
   * @return Water Heater
   */
  public WaterHeater getById(String id);

  /**
   * Get the instance of this Water Heater by Secondary Key TimeStamp.
   * 
   * @param timestamp long
   * @return Water Heater
   */
  public WaterHeater getByTimeStamp(long timestamp);

  /**
   * Sets the status of the Water Heater System.
   * 
   * @param status Integer
   */
  public void putStatus(int status);

  /**
   * Sets the water temperature of the Water Heater System.
   * 
   * @param temperature double
   */
  public void putTemperature(double temperature);
}
