package edu.hawaii.systemh.model.behavior;

/**
 * A holder object that holds a high and low temp for a day, and a timestamp.
 * @author Greg Burgess
 *
 */
public class TempPair {
  private long timeStamp;
  private int low;
  private int high;
  
  /**
   * Constructor.
   * @param timeStamp long representing the time in ms.
   * @param low the low temperature for the day.
   * @param high the high temperature for the day.
   */
  TempPair(long timeStamp, int low, int high) { 
    this.low = low;
    this.high = high;
    this.timeStamp = timeStamp;
  }
  
  /**
   * Returns the high temp.
   * @return a double representing the high temp.
   */
  public double getHigh() {
    return high;
  }
  
  /**
   * Returns the low temp.
   * @return a double representing the low temp.
   */
  public double getLow() {
    return low;
  }
  
  /**
   * Returns the timestamp.
   * @return a long representing the time in ms.
   */
  public long getTimeStamp() {
    return timeStamp;
  }
  
  /**
   * Returns the average of the high and low temps.
   * @return a double representing the average temp.
   */
  public double getAverage() {
    return (high + low) / 2.0;
  }
  
  /**
   * Returns a string representation of the object.
   * @return a string representing the object.
   */
  @Override
  public String toString() {
    return String.format("%s / %s",high,low);
  }
}
