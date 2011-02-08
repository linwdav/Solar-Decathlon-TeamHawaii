package edu.hawaii.ihale.api;

import java.util.ArrayList;

/**
 * Provides a specification of the operations that should be implemented by every AquaponicsActionDB.
 * This database contains a list of all 
 * 
 * @author Team Naia
 */
public interface WaterDB {

  /**
   * Gets a list of Aquaponics readings from a specified time range.
   * 
   * @param startTime Start of the time range.
   * @param endTime End of the time range.
   * @return a list of all readings within the specified range.
   */
  public ArrayList<Aquaponics> getAquaponics(long startTime, long endTime);

  /**
   * Store the passed Aquaponics data object in the database.
   * 
   * @param aquaponics The aquaponics object to store in the DB.
   */
  public void putAquaponics(Aquaponics aquaponics);

  /**
   * Removes the Aquaponics instance with the specified timestamp.
   * 
   * @param timeStamp Removes the Aquaponics object with the given timestamp.
   */
  public void deleteAquaponics(long timeStamp);

}
