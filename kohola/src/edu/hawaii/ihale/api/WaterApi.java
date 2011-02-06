package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the water
 * system.
 * 
 * @author Team Kohola
 */
public interface WaterApi {

  /**
   * GET the water level that the sensors are reading from the water tank. This
   * method should only be used by iHale to GET the readings from the water system.
   * 
   * @return The Hvac instance.
   */
  public Water getWaterVolume();

  /**
   * Store the passed water instance to the database. This method should be used when the
   * water subsystem performs a PUT to iHale
   * 
   * @param water The Water instance.
   */
  public void putWaterVolume(Water water);

}
