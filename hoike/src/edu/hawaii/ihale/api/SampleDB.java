package edu.hawaii.ihale.api;


/**
 * Provides a specification of the operations that should be implemented by every ContactDB.
 * @author Nathan Dorman
 */
public interface SampleDB {
  
  /**
   * Returns the Sample instance associated with uniqueTime, or null if not found.
   * @param uniqueTime The uniqueTime.
   * @return The sample, or null
   */
  public Sample getContact(long uniqueTime);
  
  /**
   * Store the passed Sample in the database.
   * @param sample The sample to store. 
   */
  public void putContact(Sample sample); 
  
  /**
   * Removes the Sample instance with the specified time and system ID.
   * @param uniqueTime The unique time for the instance to be deleted.
   * @param systemID The system ID for the instance to be deleted.
   */
  public void deleteContact(long uniqueTime, String systemID);

}
