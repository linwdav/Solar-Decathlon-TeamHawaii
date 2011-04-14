package edu.hawaii.systemh.frontend.googlecharts;

/**
 * Organizes data from the System-H repository to integrate with the Wicket-GoogleCharts API.
 * 
 * @author Kevin Leong
 */
public class DataOrganizer {

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;

  /** The time interval that data is being examined over. */
  public enum TimeInterval {
    /** Day interval. */
    DAY, 
    /** Week interval. */
    WEEK,
    /** Month interval. */
    MONTH 
  } // End enum TimeInterval

  /**
   * Empty Constructor (for now).
   */
  public DataOrganizer() {
    super();
  } // End constructor

} // End DataOrganizer Class
