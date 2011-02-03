package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every ContactDB.
 * @author Philip Johnson
 */
public interface ContactDB {
  
  /**
   * Returns the Contact instance associated with uniqueID, or null if not found.
   * @param uniqueID The uniqueID.
   * @return The contact, or null
   */
  public Contact getContact(String uniqueID);
  
  /**
   * Store the passed Contact in the database.
   * @param contact The contact to store. 
   */
  public void putContact(Contact contact); 
  
  /**
   * Removes the Contact instance with the specified ID.
   * @param uniqueID The unique ID for the instance to be deleted.
   */
  public void deleteContact(String uniqueID);

}
