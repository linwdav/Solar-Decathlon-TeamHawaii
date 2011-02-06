package edu.hawaii.ihale.api;

/**
 * Generic abstract class for implementing the database objects.
 * 
 * @author Team Hawaii
 */
public abstract class GenericSystemADT {
  
  /**
   * Get this System status.
   * 
   * @return int
   */
  public abstract int getStatus();
  
  /**
   * Get this System data's time stamp.
   * 
   * @return long
   */
  public abstract long getTimeStamp();
  
  /**
   * Gets this System's title.
   * 
   * @return String
   */
  public abstract String getTitle();
  /**
   * Gets this System description.
   * 
   * @return String
   */
  public abstract String getDescription();
    
  /**
   * Return a String of this System's data.
   * 
   * @return String
   */
  public abstract String toString();
}
