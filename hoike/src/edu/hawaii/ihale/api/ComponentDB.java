package edu.hawaii.ihale.api;


/**
 * Provides a specification of the operations that should be implemented by every ComponentDB.
 * @author Team Hoike
 */
public interface ComponentDB {
  
  /**
   * Returns the Component instance associated with its ID, or null if not found.
   * @param componentID The component's ID.
   * @return The component, or null
   */
  public Component getContact(long componentID);
  
  /**
   * Store the passed Component into the database.
   * @param component The component to store. 
   */
  public void putContact(Component component); 
  
  /**
   * Removes the Component instance with the specified time and system ID.
   * @param componentID The component's ID for the instance to be deleted.
   * @param systemID The system ID for the instance to be deleted.
   */
  public void deleteContact(long componentID, String systemID);

}
