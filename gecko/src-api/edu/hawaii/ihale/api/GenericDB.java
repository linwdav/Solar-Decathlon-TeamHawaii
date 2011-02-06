package edu.hawaii.ihale.api;

/**
 * Generic abstract interface for implementing the Database connection interface.
 * 
 * @author Team Hawaii
 */
public abstract interface GenericDB {

  /**
   * Get the instance of the database object by Primary Key ID.
   *  
   * @param id String
   * @return GenericSystem
   */
  public abstract GenericSystemADT getById(String id);

  /**
   * Get the instance of database object by  Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return GenericSystem
   */
  public abstract GenericSystemADT getByTimeStamp(long timestamp);
  
  /**
   * Sets the status of the database object.
   * 
   * @param status int
   */
  public abstract void putStatus(int status);
}
