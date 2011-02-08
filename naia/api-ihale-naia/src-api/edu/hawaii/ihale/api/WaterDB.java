package edu.hawaii.ihale.api;

import java.util.ArrayList;

/**
 * Provides a specification of the operations that should be implemented by every WaterDB. This
 * database contains a list of all Water readings.
 * 
 * @author Team Naia
 */
public interface WaterDB {

  /**
   * Gets a list of Water readings from a specified time range.
   * 
   * @param startTime Start of the time range.
   * @param endTime End of the time range.
   * @return a list of all readings within the specified range.
   */
  public ArrayList<Water> getWater(long startTime, long endTime);

  /**
   * Store the passed Water data object in the database.
   * 
   * @param aquaponics The water object to store in the DB.
   */
  public void putAquaponics(Aquaponics aquaponics);

  /**
   * Removes the Water instance with the specified timestamp.
   * 
   * @param timeStamp Removes the Water object with the given timestamp.
   */
  public void deleteWater(long timeStamp);

}
