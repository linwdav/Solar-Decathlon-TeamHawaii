package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every AquaponicsAPI.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface AquaponicsAPI {

  /**
   * GET information from the aquaponics system sensors.
   * 
   * @return the Aquaponics
   */
  public Aquaponics getAquaponics();

  /**
   * POST information from the aquaponics system sensors into the database.
   * 
   * @param aquaponics The aquaponics system information.
   */
  public void putAquaponics(Aquaponics aquaponics);

}
