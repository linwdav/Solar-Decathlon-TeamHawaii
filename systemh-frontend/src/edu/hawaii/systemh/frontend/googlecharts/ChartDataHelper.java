package edu.hawaii.systemh.frontend.googlecharts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import edu.hawaii.ihale.api.repository.IHaleRepository;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;

/**
 * Organizes data from the System-H repository to integrate with the Wicket-GoogleCharts API. This
 * class makes the following assumption: that data will be looked at going backwards from a given
 * date. E.g., a month before today, a week before today, 24 hours before today.
 * 
 * @author Kevin Leong
 */
public class ChartDataHelper {

  // Get an instance of the current repository
  static IHaleRepository repository = SolarDecathlonApplication.getRepository();

  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;

  // Basic conversions for time intervals
  static final int HOURS_IN_A_DAY = 24;
  static final int DAYS_IN_A_WEEK = 7;
  static final int WEEKS_IN_A_MONTH = 4;

  // Specifies what kind of chart to generate data and axis for
  TimeInterval chartTimeInterval;
  ChartDataType chartDataType;
  Calendar chartStartDate;

  // String array holding the axis labels
  String[] axis;

  // Array to hold the data values
  double[][] dataArray;

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

  /** The type of data in the chart (e.g., HVAC temperature, pH, etc.). **/
  public enum ChartDataType {
    /** The inside temperature of the house. **/
    HVAC_TEMP,

    /** The pH of the aquaponics system. **/
    AQUAPONICS_PH,

    /** The temperature of the aquaponics system. **/
    AQUAPONICS_TEMP,

    /** The conductivity of the aquaponics system. **/
    AQUAPONICS_CONDUCTIVITY,

    /** The energy generated vs energy consumed graph. **/
    GENERATION_CONSUMPTION
  }

  /**
   * Specifies what type of chart to display.
   * 
   * @param chartTimeInterval The time interval (Day, Week, or Month).
   * @param chartDataType The chart type to display.
   * @param chartStartDate The chart start date. Usually current time.
   */
  public ChartDataHelper(TimeInterval chartTimeInterval, ChartDataType chartDataType,
      Calendar chartStartDate) {
    this.chartTimeInterval = chartTimeInterval;
    this.chartDataType = chartDataType;
    this.chartStartDate = chartStartDate;
  } // End constructor

  /**
   * Populate the chart (axis and data).
   */
  public void populateChart() {
    // Set the axis
    setAxis(chartStartDate);
    
    // Set the data
    setChartData(chartTimeInterval, chartDataType);  }
  
  /**
   * Sets the axis labels given a specified time interval and current date.
   * 
   * @param calendar The date to start at. E.g., current date.
   */
  void setAxis(Calendar calendar) {
    // Do different operations depending on the time interval given.
    switch (chartTimeInterval) {

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

      // Set Format for date. e.g., Apr 19
      String dateFormat = "MMM dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
      Calendar tempCalendar = calendar;

      // Back off by 2 months. This is to make loop code below more readable.
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
   * Populate the chart data array, depending on what time interval is needed (day, week, or month)
   * and the chart type (pH, Oxygen, conductivity, temperature, etc.).
   * 
   * @param timeInterval Day, week, or month.
   * @param dataType The type of data requested.
   */
  void setChartData(TimeInterval timeInterval, ChartDataType dataType) {

    // Current index in the array being populated.
    int index = 0;

    // Initialize the data array.
    double[][] actualData = initializeDataArray(timeInterval, dataType);

    // Used to back off from the given calendar date. Make a copy.
    Calendar tempCalendar = Calendar.getInstance();
    tempCalendar.setTime(chartStartDate.getTime());

    long tempEndTimestamp;

    // Temp lists to hold repository data fetches
    List<TimestampDoublePair> doubleList;
    List<TimestampIntegerPair> intList;

    if (dataType == ChartDataType.AQUAPONICS_CONDUCTIVITY) {
      
      // handle different time intervals
      switch (timeInterval) {

      case DAY:
        for (int i = (HOURS_IN_A_DAY - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.DAY, tempCalendar, i);

          // Get the range of data
          doubleList =
              repository.getAquaponicsElectricalConductivityDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        }
        break;

      case WEEK:
        for (int i = (DAYS_IN_A_WEEK - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.WEEK, tempCalendar, i);

          // Get the range of data
          doubleList =
              repository.getAquaponicsElectricalConductivityDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        }
        break;
      
      case MONTH:
        for (int i = (WEEKS_IN_A_MONTH * 2 - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.MONTH, tempCalendar, i);
          
          // Get the range of data
          doubleList =
              repository.getAquaponicsElectricalConductivityDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        } // End For loop
        break;
      default:
        break;
      }
    }
      
    else if (dataType == ChartDataType.AQUAPONICS_PH) {
      // handle different time intervals
      switch (timeInterval) {

      case DAY:
        for (int i = (HOURS_IN_A_DAY - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.DAY, tempCalendar, i);

          // Get the range of data
          doubleList =
              repository.getAquaponicsPhDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);
          
          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        }
        break;

      case WEEK:
        for (int i = (DAYS_IN_A_WEEK - 1); i >= 0; i--) {

          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.WEEK, tempCalendar, i);

          // Get the range of data
          doubleList =
              repository.getAquaponicsPhDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        }
        break;
      
      case MONTH:
        for (int i = (WEEKS_IN_A_MONTH * 2 - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.MONTH, tempCalendar, i);
          
          // Get the range of data
          doubleList =
              repository.getAquaponicsPhDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (doubleList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampDouble(doubleList));
          }
          // decrement index
          index--;
        } // End For loop
        break;
      default:
          break;
      } // End Switch
    }
    else if (dataType == ChartDataType.AQUAPONICS_TEMP) {
      // handle different time intervals
      switch (timeInterval) {

      case DAY:
        for (int i = (HOURS_IN_A_DAY - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.DAY, tempCalendar, i);

          // Get the range of data
          intList =
            repository.getAquaponicsTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        }
        break;

      case WEEK:
        for (int i = (DAYS_IN_A_WEEK - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.WEEK, tempCalendar, i);

          // Get the range of data
          intList =
            repository.getAquaponicsTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

       // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        }
        break;
      
      case MONTH:
        for (int i = (WEEKS_IN_A_MONTH * 2 - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.MONTH, tempCalendar, i);
          
          // Get the range of data
          intList =
            repository.getAquaponicsTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        } // End For loop
        break;
      default:
        break;
      } // End Switch
    }      
    
    else if (dataType == ChartDataType.AQUAPONICS_TEMP) {
      // handle different time intervals
      switch (timeInterval) {

      case DAY:
        for (int i = (HOURS_IN_A_DAY - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.DAY, tempCalendar, i);

          // Get the range of data
          intList =
            repository.getHvacTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        }
        break;

      case WEEK:
        for (int i = (DAYS_IN_A_WEEK - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.WEEK, tempCalendar, i);

          // Get the range of data
          intList =
            repository.getHvacTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        }
        break;
      
      case MONTH:
        for (int i = (WEEKS_IN_A_MONTH * 2 - 1); i >= 0; i--) {
          // Set end time
          tempEndTimestamp = stepBackwardInTime(TimeInterval.MONTH, tempCalendar, i);
          
          // Get the range of data
          intList =
              repository.getHvacTemperatureDuringInterval
                  (tempCalendar.getTimeInMillis(), tempEndTimestamp);

          // Check to ensure that the list is not empty.
          if (intList.isEmpty()) {
            actualData[0][i] = 0;
          }
          else {
            // Save average in data array and round to two decimals
            actualData[0][i] = roundTwoDecimals(averageTimestampInteger(intList));
          }
          // decrement index
          index--;
        } // End For loop
        break;
      default:
        break;
      } // End Switch
    }    

    this.dataArray = actualData;    
  } // End setChartData
  
  /**
   * Steps back on a given calendar by the given time interval.
   * @param timeInterval Day, Week, or Month.
   * @param calendar The original date.
   * @param index The index (for loop).
   * @return The original timestamp.
   */
  static long stepBackwardInTime(TimeInterval timeInterval,
      Calendar calendar, int index) {
    
    // Store the original time
    long originalTime = calendar.getTimeInMillis();
    
    switch(timeInterval) {
      case DAY:
          // If this is the first iteration
          if (index == (HOURS_IN_A_DAY - 1)) {
            // Subtract the fraction of the hour
            calendar.add(Calendar.MINUTE, (-1) * calendar.get(Calendar.MINUTE));
          }
    
          else {
            // Should be on the top of the hour, so back off one hour.
            calendar.add(Calendar.HOUR_OF_DAY, -1);
          }
        break;
    
      case WEEK:
          // If this is the first iteration
          if (index == (DAYS_IN_A_WEEK - 1)) {
            // Subtract the fraction of the Day
            calendar.add(Calendar.HOUR_OF_DAY, (-1) * calendar.get(Calendar.HOUR_OF_DAY));
          }
    
          else {
            // Should be on the top of the day, so back off one day.
            calendar.add(Calendar.DAY_OF_WEEK, -1);
          }
        break;
      
      case MONTH:
          // If this is the first iteration
          if (index == (WEEKS_IN_A_MONTH * 2 - 1)) {
            // Subtract the fraction of the week
            calendar.add(Calendar.DAY_OF_WEEK, (-1) * calendar.get(Calendar.DAY_OF_WEEK));
          }
          else {
            // Should be on the top of the day, so back off one week.
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
          }
        break;
      default:
        break;
      } // End Switch
    
    return originalTime;
  }
    
  /**
   * Averages all values in the given list. This handles a list of TimestampDoublePairs.
   * 
   * @param list List of entries.
   * @return Average value of all entries in list.
   */
  static double averageTimestampDouble(List<TimestampDoublePair> list) {
    double sum = 0;
    int count = 0;

    // Loop through list and sum all entries.
    for (TimestampDoublePair entry : list) {
      count++;
      sum += entry.getValue();
    }

    // return the average
    return sum / (double) count;
  } // End averageTimestampInteger

  /**
   * Averages all values in the given list. This handles a list of TimestampIntegerPairs.
   * 
   * @param list List of entries.
   * @return Average value of all entries in list.
   */
  static double averageTimestampInteger(List<TimestampIntegerPair> list) {
    int sum = 0;
    int count = 0;

    // Loop through list and sum all entries.
    for (TimestampIntegerPair entry : list) {
      count++;
      sum += entry.getValue();
    }
    // return the average
    return (double) sum / (double) count;
  } // End averageTimestampInteger

  /**
   * Initialize the data array (no data yet).
   * 
   * @param timeInterval The time interval.
   * @param dataType The chart data type.
   * @return A blank data array.
   */
  double[][] initializeDataArray(TimeInterval timeInterval, ChartDataType dataType) {

    // Rows and columns for multidimensional data array
    int rows;
    int columns;

    // For generation consumption graph, two sets of data are needed, hence
    // two rows are needed.
    if (dataType == ChartDataType.GENERATION_CONSUMPTION) {
      rows = 2;
    }
    // The default is just one set of data (one dimensional array)
    else {  
      rows = 1;
    } // End Switch

    // Determine number of columns needed based on the time interval.
    switch (timeInterval) {
    case DAY:
      columns = HOURS_IN_A_DAY;
      break;
    case WEEK:
      columns = DAYS_IN_A_WEEK;
      break;
    case MONTH:
      // 8 weeks worth
      columns = (WEEKS_IN_A_MONTH) * 2;
      break;
    default:
      columns = 0;
      break;
    } // End switch

    return new double[rows][columns];

  } // End initialize data array

  /**
   * Rounds a double to two decimal places.
   * 
   * @param d Number to round.
   * @return Rounded number.
   */
  static double roundTwoDecimals(double d) {
    DecimalFormat twoDecimals = new DecimalFormat("#.##");
    return Double.valueOf(twoDecimals.format(d));
  }

  /**
   * Returns the contents of the axis array.
   * 
   * @param size The size of the axis array.
   * @return The string containing the contents.
   */
  public String axisArrayToString(int size) {
    StringBuffer resultString = new StringBuffer();

    for (int i = 0; i < size; i++) {
      resultString.append(axis[i]);
      String space = " ";
      resultString.append(space);
    }
    return resultString.toString();
  } // End String Array To String

  /**
   * Returns the data array.
   * 
   * @return The data array.
   */
  double[][] getDataArray() {
    return this.dataArray;
  }
  
  /**
   * Returns the axis array.
   * 
   * @return The axis array.
   */
  String[] getAxisArray() {
    return this.axis;
  }

  /**
   * Sets the data array.
   * 
   * @param dataArray The data array to set.
   */
  void setDataArray(double[][] dataArray) {
    this.dataArray = dataArray;
  }
  
  /**
   * Sets the axis array.
   * 
   * @param axisArray The axis array to set.
   */
  void setAxisArray(String[] axisArray) {
    this.axis = axisArray;
  }
  
} // End ChartDataHelper Class
