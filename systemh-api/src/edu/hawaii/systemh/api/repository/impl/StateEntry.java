package edu.hawaii.systemh.api.repository.impl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * The single persistent class in this BerkeleyDB-based repository implementation.
 * Stores a timestamp and either a Double, String, Long, or Boolean value.
 * Note that a separate entity store is created for each valid (system, state) or (room, state)
 * combination, so there's no need to store this information in this class. 
 * @author Philip Johnson
 *
 */
@Entity
class StateEntry {
  
  @PrimaryKey
  /** The timestamp.  Always available and serves as the primary key. */
  private Long timestamp;
  /** The double, if stored. */
  private Double doubleValue;
  /** The string, if stored. */
  private String stringValue;
  /** The long, if stored. */
  private Long longValue;
  /** The boolean, if stored. */
  private Boolean booleanValue;

  /**
   * The default constructor, required by BerkeleyDB. Not used in practice.
   */
  public StateEntry() {
    //no body
  }
  
  /**
   * Create a persistent (timestamp, double) pair.
   * @param timestamp The timestamp.
   * @param doubleValue The double value.
   */
  public StateEntry(Long timestamp, Double doubleValue) {
    this.timestamp = timestamp;
    this.doubleValue = doubleValue;
  }
  
  /**
   * Create a persistent (timestamp, string) pair.
   * @param timestamp The timestamp.
   * @param stringValue The string value.
   */
  public StateEntry(Long timestamp, String stringValue) {
    this.timestamp = timestamp;
    this.stringValue = stringValue;
  }
  
  /**
   * Create a persistent (timestamp, long) pair.
   * @param timestamp The timestamp.
   * @param longValue The long value.
   */
  public StateEntry(Long timestamp, Long longValue) {
    this.timestamp = timestamp;
    this.longValue = longValue;
  }
  
  /**
   * Create a persistent (timestamp, boolean) pair.
   * @param timestamp The timestamp.
   * @param booleanValue The boolean value.
   */
  public StateEntry(Long timestamp, Boolean booleanValue) {
    this.timestamp = timestamp;
    this.booleanValue = booleanValue;
  }
  
  /**
   * Gets the timestamp.
   * @return The timestamp.
   */
  public Long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Gets the double value.
   * @return The double value.
   */
  public Double getDoubleValue() {
    return this.doubleValue;
  }

  /**
   * Gets the string value. 
   * @return The string value.
   */
  public String getStringValue() {
    return this.stringValue;
  }
  
  /**
   * Gets the long value.
   * @return The long value.
   */
  public Long getLongValue() {
    return this.longValue;
  }
  
  /**
   * Gets the boolean value.
   * @return The boolean value.
   */
  public Boolean getBooleanValue() {
    return this.booleanValue;
  }
}
