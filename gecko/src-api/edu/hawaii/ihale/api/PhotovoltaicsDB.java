package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface PhotovoltaicsDB {
  
  /**
   * Get the instance of this Photovoltaics by Primary Key ID.
   *  
   * @param id String
   * @return Photovoltaics
   */
  public Photovoltaics getById(String id);

  /**
   * Get the instance of this Photovoltaics by Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return Photovoltaics
   */
  public Photovoltaics getByTimeStamp(long timestamp);
  
  /**
   * Sets the status of the Photovoltaics System.
   * 
   * @param status Integer
   */
  public void putStatus(int status);
  
  /**
   * Sets the direction of the Photovoltaics System panel.
   * 
   * @param direction double
   */
  public void putDirection(double direction);

  /**
   * Sets the angle of the Photovoltaics System panel.
   * 
   * @param angle double
   */
  public void putAngle(double angle);
}
