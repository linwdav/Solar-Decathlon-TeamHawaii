package edu.hawaii.ihale.api;

import java.util.ArrayList;

/**
 * Provides a specification of the operations that should be implemented by every AquaponicsActionDB.
 * This database contains a list of all 
 * 
 * @author Team Naia
 */
public interface PhotovoltaicDB {

  /**
   * Gets a list of Energy (PV) readings from a specified time range.
   * 
   * @param startTime Start of the time range.
   * @param endTime End of the time range.
   * @return a list of all readings within the specified range.
   */
  public ArrayList<Photovoltaic> getPhotovoltaic(long startTime, long endTime);

  /**
   * Store the passed Photovoltaic data object in the database.
   * 
   * @param aquaponics The phtovoltaic object to store in the DB.
   */
  public void putPhotovoltaic(Photovoltaic pv);

  /**
   * Removes the Photovoltaic instance with the specified timestamp.
   * 
   * @param timestamp Removes the Photovoltaic object with the given timestamp.
   */
  public void deleteAquaponics(long timeStamp);

}
