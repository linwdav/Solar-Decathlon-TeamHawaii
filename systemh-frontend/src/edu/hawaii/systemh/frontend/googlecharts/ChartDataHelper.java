package edu.hawaii.systemh.frontend.googlecharts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Organizes data from the System-H repository to integrate with the Wicket-GoogleCharts API.
 * This class makes the following assumption: that data will be looked at going backwards
 * from a given date.  E.g., a month before today, a week before today, 24 hours before today.
 * 
 * @author Kevin Leong
 */
public class ChartDataHelper {

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;
  
  // Basic conversions for time intervals
  static final int HOURS_IN_A_DAY = 24;
  static final int DAYS_IN_A_WEEK = 7;
  static final int WEEKS_IN_A_MONTH = 4;

  // String array holding the axis labels
  String[] axis;

  // String array holding list of days for week chart
  String[] weekList = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
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
   * Sets the axis labels given a specified time interval and
   * current date.
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
      String[] actualDayList = new String[HOURS_IN_A_DAY];

      // Loop through to populate day list.
      for (int i = (HOURS_IN_A_DAY - 1); i >= 0; i--) {
        actualDayList[i] = dayList[startingHour--];
        if (startingHour < 0) {
          startingHour = (HOURS_IN_A_DAY - 1);
        }
      } // End for loop

      // Set axis label to day axis
      this.axis = actualDayList;
      break;

    case WEEK:
      // Get the day for the date given 1 = Sunday, 7 = Saturday
      // Subtract 1 since week starts at 1.
      int startingDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      
      // Create list to hold axis labels
      String[] actualWeekList = new String[DAYS_IN_A_WEEK];
      
      // Loop through to populate week list.
      for (int i = (DAYS_IN_A_WEEK - 1); i >= 0; i--) {
        actualWeekList[i] = weekList[startingDay--];
        if (startingDay < 0) {
          startingDay = (DAYS_IN_A_WEEK - 1);
        }
      } // End for loop
      
      // Set axis label to week axis
      this.axis = actualWeekList;
      break;
      
    case MONTH:
      // Create list for 8-weeks worth of axis labels
      String[] actualMonthList = new String[WEEKS_IN_A_MONTH * 2];
      
      // Set Format for date.  e.g., Apr 19
      String dateFormat = "MMM dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
      Calendar tempCalendar = calendar;
      
      // Back off by 2 months.  This is to make loop code below more readable.
      tempCalendar.add(Calendar.WEEK_OF_YEAR, (-1 * WEEKS_IN_A_MONTH * 2));
      
      // Loop through and populate array with 8-weeks of dates.
      for (int i = 0; i < (WEEKS_IN_A_MONTH * 2); i++) {
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        actualMonthList[i] = formatter.format(tempCalendar.getTime());
      }
      // Set axis label to month axis.
      this.axis = actualMonthList;
      break;
      
    default:
      break;
    } // End Switch
  } // End set axis

  /**
   * Returns the contents of the axis array.
   * 
   * @param size The size of the axis array.
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
