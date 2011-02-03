package edu.hawaii.ihale.api;



/**
 * Provides a specification of the operations that should be implemented by every SampleDB.
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface SampleDB {
  
  /**
   * Returns the Sample instance associated with uniqueTime and systemID, 
   * or null if not found.
   * 
   * @param uniqueTime The uniqueTime.
   * @param systemID The system associated with the time.
   * @return The sample, or null.
   */
  public Sample getSample(long uniqueTime, String systemID);
  
  /**
   * Store the passed Sample in the database.
   * @param sample The sample to store. 
   */
  public void putSample(Sample sample);
  
  /**
   * Removes the Sample instance with the specified time and system ID.
   * @param uniqueTime The unique time for the instance to be deleted.
   * @param systemID The system ID for the instance to be deleted.
   */
  public void deleteSample(long uniqueTime, String systemID);

}
