package edu.hawaii.ihale.api.db;

import org.w3c.dom.Document;

/**
 * Interface for the DataBase that stores Data for the System.
 * @author Team Maka
 */
public interface DataBase {
  
  /**
   * Gets a particular entry given a key.
   * @param id A String containing the key.
   * @return An XML Document Object that has the requested data.
   */
  public Document getItem(String id);
  
  /**
   * Stores a DBResource object into the database.
   * @param resource The Resource to store.
   */
  public void putDBResource(DBResource resource);
  
  /**
   * Returns an XML Document that contains properties representing data
   * between the start and end times.
   * @param id The ID of the sensor to retrieve data from.
   * @param start The start date in miliseconds from Date.getTime().
   * @param end The end date in miliseconds from Date.getTime().
   * @return An XML Document that contains properties representing the 
   * data over time.
   */
  public Document getRange(String id,Long start, Long end);
  
  
}
