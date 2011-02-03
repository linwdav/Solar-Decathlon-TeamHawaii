package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the water tank
 * system.
 * 
 * @author Team Kohola
 */
public interface Water {

  /**
   * To set the water tank volume.
   * @param value The value in liter.
   */
  public void setTankVolume(long value);
  
  /**
   * To retrieve the water tank volume.
   * @return The value in liter.
   */
  public long getTankVolume();
  
  /**
   * To set the water tank temperature.
   * @param value The value in Celsius.
   */
  public void setTankTemperature(long value);
  
  /**
   * To retrieve the water tank temperature.
   * @return value The value in Celsius.
   */
  public long getTankTemperature();

}
