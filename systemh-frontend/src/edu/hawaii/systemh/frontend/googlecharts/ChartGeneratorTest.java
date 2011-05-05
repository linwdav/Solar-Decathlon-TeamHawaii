package edu.hawaii.systemh.frontend.googlecharts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;

/**
 * Tests the Chart Generator class.
 * 
 * @author Kevin Leong
 */
public class ChartGeneratorTest {
  
  ChartDataHelper testHelper;
  double[][] dataArray = {{7.3, 5.3, 9.3, 8.3, 10.3, 6.3, 2.3}};
  Calendar testCalendar;
 
  /**
   * Setup prior to running tests.
   * @throws Exception Setup exception.
   */
  @Before
  public void setUp() throws Exception {
   
    // Supply an initial date and the format it's in.
    String date01 = "19-Apr-11 01:34:54"; // Long time = 1303212894000L
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
    
    testHelper = new ChartDataHelper(TimeInterval.WEEK, ChartDataType.AQUAPONICS_PH, testCalendar);
    
    dataArray[0][3] = 0.35;
    testHelper.setAxis(testCalendar);
    
  } // end Setup

  /**
   * Tests URI construction.
   */
  @Test
  public void testConstructChartURI() {
    // TO DO
  }

  /**
   * Test Axis Generation.
   */
  @Test
  public void testGenerateXAxis() {
    // TO DO
  }

  /**
   * Tests Data Generation.
   */
  @Test
  public void testGenerateDataString() {
    // TO DO
  }

} // End Chart Generator Test Class
