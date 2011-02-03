package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale.
 * @author Team Kohola
 */
public interface Aquaponics  {

  /**
   * To store the current oxygen concentration in the fish tank to the database.
   * @param value The concentration value in mg/L.
   * @param timestamp The current time.
   */
  public void storeWaterOxygen(long value, String timestamp);
  
  /**
   * To retrieve the oxygen concentration associated with the given time.
   * @param timestamp The time when the oxygen data was collected.
   * @return The oxygen concentration mg/L from the given time.
   */
  public long getWaterOxygen(String timestamp);

  /**
   * To store the current temperature in the fish tank to the database.
   * @param value The temperature in Celsius.
   * @param timestamp The current time.
   */
  public void storeWaterTemperature(long value, String timestamp);
  
  /**
   * To retrieve the temperature associated with the given time.
   * @param timestamp The time when the temperature data was collected.
   * @return The temperature in Celsius from the given time.
   */
  public long getWaterTemperature(String timestamp);
  
  /**
   * To store the current ph value in the fish tank to the database.
   * @param value The ph value.
   * @param timestamp The current time.
   */
  public void storeWaterPh(long value, String timestamp);
  
  /**
   * To retrieve the ph value associated with the given time.
   * @param timestamp The time when the ph data was collected.
   * @return The ph value from the given time.
   */
  public long getWaterPh(String timestamp);
  
  /**
   * To store the current water volume in the fish tank to the database.
   * @param value The water volume in liter.
   * @param timestamp The current time.
   */
  public void storeWaterVolume(long value, String timestamp);
  
  /**
   * To retrieve the water volume associated with the given time.
   * @param timestamp The time when the water volume data was collected.
   * @return The water volume in liter from the given time.
   */
  public long getWaterVolume(String timestamp);
}
