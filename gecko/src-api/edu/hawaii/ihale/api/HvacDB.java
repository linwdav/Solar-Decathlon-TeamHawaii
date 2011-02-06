package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface HvacDB {
  
  /**
   * Get the instance of this Hvac by Primary Key ID.
   *  
   * @param id String
   * @return Hvac
   */
  public Hvac getById(String id);

  /**
   * Get the instance of this Hvac by Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return Hvac
   */
  public Hvac getByTimeStamp(long timestamp);
  
  /**
   * Sets the status of the Hvac System.
   * 
   * @param status Integer
   */
  public void putStatus(int status);
  
  /**
   * Sets the water temperature of the Hvac System.
   * 
   * @param temperature double
   */
  public void putTemperature(double temperature);
}
