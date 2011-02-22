package edu.hawaii.ihale.ui.model;

import java.io.Serializable;

/**
 * Implements an abstract System Model.
 * 
 * @author Bret K. Ikehara
 */
public abstract class SystemModel implements Serializable {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -3063681993461244149L;
  protected Long timestamp = 0L;
  
  /**
   * Gets the System Model time stamp.
   * 
   * @return Long
   */
  public abstract Long getTimestamp();
  
  /**
   * Sets the System Model time stamp.
   * @param timestamp Long
   */
  public abstract void setTimestamp(Long timestamp);  
}
