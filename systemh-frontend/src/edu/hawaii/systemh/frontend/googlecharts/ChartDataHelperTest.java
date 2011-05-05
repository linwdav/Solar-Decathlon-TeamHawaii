package edu.hawaii.systemh.frontend.googlecharts;

import static org.junit.Assert.assertEquals;
/*
 import org.junit.After;
 import org.junit.AfterClass;
 import org.junit.Before;
 import org.junit.BeforeClass;
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.junit.Test;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;

/**
 * JUnit Test Class for ChartDataHelper.
 * @author Kevin Leong
 */
public class ChartDataHelperTest {

  /**
   * Tests the setAxis method.
   */
  @Test
  public void testSetAxis() {
    // Supply an initial date and the format it's in.
    String date01 = "19-Apr-11 01:34:54";
    DateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss", Locale.US);

    try {
      // Convert date to a calendar object
      Date date = formatter.parse(date01);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      
      // Instantiate an object of the ChartDataHelper class for testing.
      ChartDataHelper cdhDay = new ChartDataHelper
        (TimeInterval.DAY, ChartDataType.AQUAPONICS_PH, calendar);
      
      ChartDataHelper cdhWeek = new ChartDataHelper
        (TimeInterval.WEEK, ChartDataType.AQUAPONICS_PH, calendar);
      
      ChartDataHelper cdhMonth = new ChartDataHelper
        (TimeInterval.MONTH, ChartDataType.AQUAPONICS_PH, calendar);
      
      // Set the axis for the interval DAY using the given date.
      cdhDay.setAxis(calendar);

      // Test to make sure axis matches what is expected.
      String actualAxis = cdhDay.axisArrayToString(24);
      
      // Should start at 2AM
      String expectedAxis = "2  4  6  8  10  12PM  2  4  6  8  10  12AM  ";
      assertEquals("Day Test", actualAxis, expectedAxis);
      
      // Set the axis for the interval WEEK using the given date.
      cdhWeek.setAxis(calendar);

      // Test to make sure axis matches what is expected.
      // Should start at Wed
      actualAxis = cdhWeek.axisArrayToString(7);
      expectedAxis = "Wed Thu Fri Sat Sun Mon Tue ";      
      assertEquals("Week Test", actualAxis, expectedAxis);
      
      // Set the axis for the interval WEEK using the given date.
      cdhMonth.setAxis(calendar);

      // Test to make sure axis matches what is expected.
      // Should start at Wed
      actualAxis = cdhMonth.axisArrayToString(8);
      expectedAxis = "Mar 01 Mar 08 Mar 15 Mar 22 Mar 29 Apr 05 Apr 12 Apr 19 ";      
      assertEquals("Month Test", actualAxis, expectedAxis);

    } // End try block
    catch (ParseException e) {
      System.out.println("Problem parsing date.");
      e.printStackTrace();
    } // End catch block
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
      System.out.println("DataArray index[" + i + "]: " + dataArray[0][i]);
    }
  
  } // End testSetData method
  
} // End ChartDataHelperTest class
