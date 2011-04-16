package edu.hawaii.systemh.api.repository;

/**
 * Provides a pair of values: a timestamp and a Double.
 * @author Philip Johnson
 */
public class TimestampDoublePair {
  /** The timestamp. */
  private Long timestamp;
  /** The value associated with this timestamp. */
  private Double value;

  /**
   * Creates a new pair.
   * @param timestamp The timestamp.
   * @param value The value.
   */
  public TimestampDoublePair(Long timestamp, Double value) {
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
  public Double getValue() {
    return this.value;
  }

}