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
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;

/**
 * JUnit Test Class for ChartDataHelper.
 * @author Kevin Leong
 */
public class ChartDataHelperTest {
  /*
   * @BeforeClass public static void setUpBeforeClass() throws Exception { }
   * 
   * @AfterClass public static void tearDownAfterClass() throws Exception { }
   * 
   * @Before public void setUp() throws Exception { }
   * 
   * @After public void tearDown() throws Exception { }
   * 
   * @Test public void testChartDataHelper() { // fail("Not yet implemented"); }
   * 
   * @Test public void testGetAxis() { // fail("Not yet implemented"); }
   */

  /**
   * Tests the setAxis method.
   */
  @Test
  public void testSetAxis() {
    // Supply an initial date and the format it's in.
    String date01 = "19-Apr-11 01:34:54";
    DateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss", Locale.US);

    // Instantiate an object of the ChartDataHelper class for testing.
    ChartDataHelper cdh = new ChartDataHelper();

    try {
      // Convert date to a calendar object
      Date date = formatter.parse(date01);
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);

      // Set the axis for the interval DAY using the given date.
      cdh.setAxis(TimeInterval.DAY, cal);

      // Test to make sure axis matches what is expected.
      String actualAxis = cdh.stringArrayToString(24);
      String expectedAxis = "2  4  6  8  10  12PM  2  4  6  8  10  12AM  ";
      assertEquals("Day Test", actualAxis, expectedAxis);
    }
    catch (ParseException e) {
      System.out.println("Problem parsing date.");
      e.printStackTrace();
    }
  } // End testSetAxis

} // End ChartDataHelperTest class