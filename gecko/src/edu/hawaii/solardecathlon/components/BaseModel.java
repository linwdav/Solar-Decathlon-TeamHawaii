package edu.hawaii.solardecathlon.components;

import java.io.Serializable;

/**
 * Implements an abstract System Model. This is used to populate 
 * 
 * @author Bret K. Ikehara
 */
public abstract class BaseModel implements Serializable {

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
  public Long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Sets the System Model time stamp.
   * @param timestamp Long
   */
  public void setTimestamp(Long timestamp) {  
    this.timestamp = timestamp;
  }
}
