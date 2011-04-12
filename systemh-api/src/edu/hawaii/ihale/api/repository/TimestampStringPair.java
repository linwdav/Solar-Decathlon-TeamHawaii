package edu.hawaii.ihale.api.repository;

/**
 * Provides a pair of values: a timestamp and a String.
 * @author Philip Johnson
 */
public class TimestampStringPair {
  /** The timestamp. */
  private Long timestamp;
  /** The value associated with this timestamp. */
  private String value;

  /**
   * Creates a new pair.
   * @param timestamp The timestamp.
   * @param value The value.
   */
  public TimestampStringPair(Long timestamp, String value) {
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
  public String getValue() {
    return this.value;
  }

}