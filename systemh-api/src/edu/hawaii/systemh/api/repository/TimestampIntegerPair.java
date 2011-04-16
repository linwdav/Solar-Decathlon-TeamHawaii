package edu.hawaii.systemh.api.repository;

/**
 * Provides a pair of values: a timestamp and a Integer.
 * @author Philip Johnson
 */
public class TimestampIntegerPair {
  /** The timestamp. */
  private Long timestamp;
  /** The value associated with this timestamp. */
  private Integer value;

  /**
   * Creates a new pair.
   * @param timestamp The timestamp.
   * @param value The value.
   */
  public TimestampIntegerPair(Long timestamp, Integer value) {
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
  public Integer getValue() {
    return this.value;
  }

}