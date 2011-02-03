package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the Hvac
 * system.
 * 
 * @author Team Kohola
 */
public interface Hvac {

  /**
   * To set the desired room temperature in the house.
   * 
   * @param value The temperature in Celsius.
   */
  public void setDesiredTemperature(long value);

  /**
   * To retrieve the desired room temperature in the house.
   * 
   * @return The temperature in Celsius.
   */
  public long getDesiredTemperature();
}
