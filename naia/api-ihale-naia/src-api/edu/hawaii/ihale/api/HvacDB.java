package edu.hawaii.ihale.api;

import java.util.ArrayList;

/**
 * Provides a specification of the operations that should be implemented by every HvacActionDB.
 * This database contains a list of all 
 * 
 * @author Team Naia
 */
public interface HvacDB {

  /**
   * Gets a list of Hvac readings from a specified time range.
   * 
   * @param startTime Start of the time range.
   * @param endTime End of the time range.
   * @return a list of all readings within the specified range.
   */
  public ArrayList<Hvac> getHvac(long startTime, long endTime);

  /**
   * Store the passed Hvac data object in the database.
   * 
   * @param havc The Hvac object to store in the DB.
   */
  public void putHvac(Hvac hvac);

  /**
   * Removes the HVAC instance with the specified timestamp.
   * 
   * @param timeStamp Removes the Hvac object with the given timestamp.
   */
  public void deleteHvac(long timeStamp);

}
