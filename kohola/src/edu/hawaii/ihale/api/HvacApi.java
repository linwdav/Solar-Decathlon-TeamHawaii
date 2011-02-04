package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the Hvac
 * system.
 * 
 * @author Team Kohola
 */
public interface HvacApi {

  /**
   * Returns the Hvac instance that the sensors are reading currently from the Hvac system. This
   * method should only be used by iHale to GET the current readings from the Hvac system.
   * 
   * @return The Hvac instance.
   */
  public Hvac getHvac();

  /**
   * Create or modify the passed Hvac instance in the database. This method should be used when the
   * Hvac subsystem performs a POST to iHale
   * 
   * @param hvac The Hvac instance.
   */
  public void postHvacToDB(Hvac hvac);

  /**
   * Put the passed temperature to the Hvac subsystem. Assuming that iHale can tell the
   * subsystem to change the room temperature.
   * 
   * @param value The temperature in Celsius.
   */
  public void putDesiredTemperature(long value);
}
