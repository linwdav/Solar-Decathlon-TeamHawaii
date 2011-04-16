package edu.hawaii.systemh.api.repository;

/**
 * Provides a pair of values: a timestamp and a boolean.
 * @author Philip Johnson
 */
public class TimestampBooleanPair {
  /** The timestamp. */
  private Long timestamp;
  /** The value associated with this timestamp. */
  private Boolean value;

  /**
   * Creates a new pair.
   * @param timestamp The timestamp.
   * @param value The value.
   */
  public TimestampBooleanPair(Long timestamp, Boolean value) {
    this.timestamp = timestamp;
    this.value = value;
  }
  /**
   * Returns the timestamp. 
   * @return The timestamp.
   */
  public Long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Returns the value. 
   * @return The value. 
   */
  public Boolean getValue() {
    return this.value;
  }

}
