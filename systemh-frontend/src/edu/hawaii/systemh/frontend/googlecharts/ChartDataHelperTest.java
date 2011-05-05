package edu.hawaii.systemh.frontend.googlecharts;

import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;

/**
 * JUnit Test Class for ChartDataHelper.
 * @author Kevin Leong
 */
public class ChartDataHelperTest {

  List <TimestampDoublePair> doubleList = new LinkedList<TimestampDoublePair>();
  List <TimestampIntegerPair> intList = new LinkedList<TimestampIntegerPair>();
  Calendar testCalendar;
  
  /**
   * Setup actions.
   * @throws Exception e
   */
  @Before
  public void setUp() throws Exception {
  
    // Supply an initial date and the format it's in.
    String date01 = "19-Apr-11 01:34:54"; // Long time = 1303212894000
    DateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss", Locale.US);

    try {
      // Convert date to a calendar object
      Date date = formatter.parse(date01);
      testCalendar = Calendar.getInstance();
      testCalendar.setTime(date);
    } // End try block
    
    catch (ParseException e) {
      System.out.println("Problem parsing date.");
      e.printStackTrace();
    } // End catch block
    
    doubleList.add(new TimestampDoublePair(1303212894000L, 30.0));
    doubleList.add(new TimestampDoublePair(1303212894001L, 31.5));
    doubleList.add(new TimestampDoublePair(1303212894002L, 28.5));

    intList.add(new TimestampIntegerPair(1303212894005L, 5));
    intList.add(new TimestampIntegerPair(1303212894003L, 6));
    intList.add(new TimestampIntegerPair(1303212894004L, 7));    
  } // End setup
  
  /**
   * Tests the averageTimestampInteger method.
   */
  @Test
  public void testAverageInteger() {
    assertEquals(ChartDataHelper.averageTimestampInteger(intList), 6.0, 0.0);
  }
  
  /**
   * Tests the averageTimestampDouble method.
   */
  @Test
  public void testAverageDouble() {
    assertEquals(ChartDataHelper.averageTimestampDouble(doubleList), 30.0, 0.0);
  }
  
  /**
   * Tests the StepBackwardsInTime method.
   */
  @Test
  public void testStepBackwardsInTime() {
    
    long oneHourInMillis = 60L * 60L * 1000L;
    
    Calendar tempTestCalendar = Calendar.getInstance();
    
    // Set to 19-Apr-11 01:34:54 - 
    tempTestCalendar.setTimeInMillis(testCalendar.getTimeInMillis());
    
    // Test to ensure original time is returned: 1303212894000L
    assertEquals("Original time", 
        ChartDataHelper.stepBackwardInTime(TimeInterval.DAY, tempTestCalendar, 23), 1303212894000L);
    
    // Test that new time is 34 minutes ahead
    assertEquals("New step back time", 
        1303212894000L - tempTestCalendar.getTimeInMillis(), (oneHourInMillis * 34 / 60));
    
    long tempTimestamp = ChartDataHelper.stepBackwardInTime(TimeInterval.DAY, tempTestCalendar, 3);
    
    // all subsequent iterations should be one hour different
    assertEquals("Step back again", 
        tempTimestamp - tempTestCalendar.getTimeInMillis(), oneHourInMillis);
  }
  
  /**
   * Tests the setAxis method.
   */
  @Test
  public void testSetAxis() {
      
      // Instantiate an object of the ChartDataHelper class for testing.
      ChartDataHelper cdhDay = new ChartDataHelper
        (TimeInterval.DAY, ChartDataType.AQUAPONICS_PH, testCalendar);
      
      ChartDataHelper cdhWeek = new ChartDataHelper
        (TimeInterval.WEEK, ChartDataType.AQUAPONICS_PH, testCalendar);
      
      ChartDataHelper cdhMonth = new ChartDataHelper
        (TimeInterval.MONTH, ChartDataType.AQUAPONICS_PH, testCalendar);
      
      // Set the axis for the interval DAY using the given date.
      cdhDay.setAxis(testCalendar);

      // Test to make sure axis matches what is expected.
      String actualAxis = cdhDay.axisArrayToString(24);
      
      // Should start at 2AM
      String expectedAxis = "2  4  6  8  10  12PM  2  4  6  8  10  12AM  ";
      assertEquals("Day Test", actualAxis, expectedAxis);
      
      // Set the axis for the interval WEEK using the given date.
      cdhWeek.setAxis(testCalendar);

      // Test to make sure axis matches what is expected.
      // Should start at Wed
      actualAxis = cdhWeek.axisArrayToString(7);
      expectedAxis = "Wed Thu Fri Sat Sun Mon Tue ";      
      assertEquals("Week Test", actualAxis, expectedAxis);
      
      // Set the axis for the interval WEEK using the given date.
      cdhMonth.setAxis(testCalendar);

      // Test to make sure axis matches what is expected.
      // Should start at Wed
      actualAxis = cdhMonth.axisArrayToString(8);
      expectedAxis = "Mar 01 Mar 08 Mar 15 Mar 22 Mar 29 Apr 05 Apr 12 Apr 19 ";      
      assertEquals("Month Test", actualAxis, expectedAxis);
  } // End testSetAxis
  
  /**
   * Tests the setData method.
   */
  @Test
  public void testSetData() {
    // Convert date to a calendar object
    Calendar calendar = Calendar.getInstance();
    
    // Instantiate an object of the ChartDataHelper class for testing.
    ChartDataHelper cdhDay = new ChartDataHelper
      (TimeInterval.DAY, ChartDataType.AQUAPONICS_CONDUCTIVITY, calendar);
    
    cdhDay.setChartData(TimeInterval.DAY, ChartDataType.AQUAPONICS_CONDUCTIVITY);
    double[][] dataArray = cdhDay.getDataArray();
    
    // Print out data array
    for (int i = 0; i < dataArray[0].length; i++) {
      System.out.println("DataArray - HOUR[" + i + "]: " + dataArray[0][i]);
    }
  } // End testSetData method
  
  /**
   * Tests the round two decimals method.
   */
  @Test
  public void testRoundTwoDecimals() {
    assertEquals(
        52.53, ChartDataHelper.roundTwoDecimals(52.531), 0.0);
    assertEquals(
        52.53, ChartDataHelper.roundTwoDecimals(52.529), 0.0);
  }
  
} // End ChartDataHelperTest class
