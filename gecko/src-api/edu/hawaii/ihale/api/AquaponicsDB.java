package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface AquaponicsDB {
  
  /**
   * Get the instance of this Aquaponics by Primary Key ID.
   *  
   * @param id String
   * @return Aquaponics
   */
  public Aquaponics getById(String id);

  /**
   * Get the instance of this Aquaponics by Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return Aquaponics
   */
  public Aquaponics getByTimeStamp(long timestamp);
  
  /**
   * Sets the status of the Aquaponics System.
   * 
   * @param status Integer
   */
  public void putStatus(int status);
  
  /**
   * Sets the water temperature of the Aquaponics System.
   * 
   * @param temperature double
   */
  public void putStatus(double temperature);
}
