package edu.hawaii.ihale.api;

import java.util.ArrayList;

/**
 * Provides a specification of the operations that should be implemented by every HvacActionDB. This
 * database contains a list of all
 * 
 * @author Team Naia
 */
public interface LightingDB {

  /**
   * Gets a list of lighting readings from a specified time range.
   * 
   * @param startTime Start of the time range.
   * @param endTime End of the time range.
   * @return a list of all readings within the specified range.
   */
  public ArrayList<Lighting> getLighting(long startTime, long endTime);

  /**
   * Store the passed Lighting data object in the database.
   * 
   * @param havc The Lighting object to store in the DB.
   */
  public void putLighting(Lighting lighting);

  /**
   * Removes the Lighting instance with the specified timestamp.
   * 
   * @param timeStamp Removes the Lighting object with the given timestamp.
   */
  public void deleteLighting(long timeStamp);

}
