package edu.hawaii.systemh.frontend.googlecharts;

import java.util.Calendar;

/**
 * Organizes data from the System-H repository to integrate with the Wicket-GoogleCharts API.
 * 
 * @author Kevin Leong
 */
public class ChartDataHelper {

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;

  // String array holding the axis labels
  String[] axis;

  // String array holding list of days for week chart
  // String[] weekList = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
  String[] dayList = { "12AM", "", "2", "", "4", "", "6", "", "8", "", "10", "", "12PM", "", "2",
      "", "4", "", "6", "", "8", "", "10", "" };

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
  public ChartDataHelper() {
    super();
  } // End constructor

  /**
   * Sets the axis labels.
   * 
   * @param interval The time interval. E.g., Day, Week, Month.
   * @param calendar The date to start at. E.g., current date.
   */
  public void setAxis(TimeInterval interval, Calendar calendar) {
    // Do different operations depending on the time interval given.
    switch (interval) {

    case DAY:
      // Get the hour for the date given
      int startingHour = calendar.get(Calendar.HOUR_OF_DAY);

      // Create a list to hold the axis labels
      String[] actualDayList = new String[24];

      // Loop through to popoulate day list.
      for (int i = 23; i >= 0; i--) {
        actualDayList[i] = dayList[startingHour--];
        if (startingHour < 0) {
          startingHour = 23;
        }
      } // End for loop

      // Set axis label to day axis
      this.axis = actualDayList;
      break;

    case WEEK:
      // Get the day for the date given 1 = Sunday, 7 = Saturday
      // int startingDay = calendar.get(Calendar.DAY_OF_WEEK);

    case MONTH:
    default:
      break;
    } // End Switch
  } // End set axis

  /**
   * Returns the contents of a string array.
   * 
   * @param size The size of the array.
   * @return The string containing the contents.
   */
  public String stringArrayToString(int size) {
    StringBuffer resultString = new StringBuffer();
    for (int i = 0; i < size; i++) {
      resultString.append(axis[i]);
      String space = " ";
      resultString.append(space);
    }
    return resultString.toString();
  } // End String Array To String

} // End DataOrganizer Class
