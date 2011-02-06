package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the aquaponics
 * system.
 * 
 * @author Team Kohola
 */
public interface AquaponicsApi {

  /**
   * GET the Aquaponics instance that the sensors are reading currently from the aquaponics system.
   * This method should only be used by iHale to GET the current readings from the aquaponics
   * system.
   * 
   * @return The Aquaponics
   */
  public Aquaponics getAquaponics();

  /**
   * Store the passed aquaponics instance in the database. This method should be used when the
   * aquaponics subsystem performs a PUT to iHale
   * 
   * @param aquaponics The Aquaponics instance to store.
   */
  public void putAquaponicsToDB(Aquaponics aquaponics);

  /**
   * POST the passed temperature to the aquaponics subsystem. Assuming that iHale can tell the
   * subsystem to change its water temperature.
   * 
   * @param value The temperature in Celsius.
   */
  public void postTemperatureToSubsystem(long value);

  /**
   * POST the passed volume to the aquaponics subsystem. Assuming that iHale can tell the subsystem
   * to change its water volume.
   * 
   * @param value The temperature in liter.
   */
  public void postVolumeToSubsystem(long value);

}
