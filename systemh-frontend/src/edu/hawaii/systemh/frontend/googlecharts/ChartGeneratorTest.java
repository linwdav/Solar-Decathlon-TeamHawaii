package edu.hawaii.systemh.frontend.googlecharts;

import static org.junit.Assert.assertEquals;
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
  
  ChartGenerator testGenerator;
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
    
    // Setup instances for testing
    testGenerator = new ChartGenerator(TimeInterval.WEEK, 
        ChartDataType.AQUAPONICS_PH, null, 500, 300);
    testGenerator.addChart();
    testHelper = testGenerator.getHelper();    
  } // end Setup

  /**
   * Tests URI construction.
   */
  @Test
  public void testConstructChartURI() {
    testHelper.setAxis(testCalendar);
    testHelper.setDataArray(dataArray);
    
    String expectedAxis = "|Wed|Thu|Fri|Sat|Sun|Mon|Tue";
    String expectedData = "52.14,37.86,66.43,59.29,73.57,45.0,16.43";
    
    String partOne = "http://chart.googleapis.com/chart?cht=lc&chxt=x,y&chs=500x300";
    String partTwo = "&chco=0000FF&chls=2&chxtc=0,10|1,10&chxl=0:";
    String partThree = "&chm=d,FF0000,0,-1,10&chd=t:";
    String partFour = "&chxr=1,0,14,1";
    
    StringBuffer expected = new StringBuffer(partOne);
    expected.append(partTwo);
    expected.append(expectedAxis);
    expected.append(partThree);
    expected.append(expectedData);
    expected.append(partFour);

    // Tests to ensure the chart matches what we expect.
    assertEquals("Chart URI", expected.toString(), testGenerator.constructChartURI(500, 300));
  } // End test Construct Chart URI

  /**
   * Test Axis String Generation.
   */
  @Test
  public void testGenerateXAxis() {
    testHelper.setAxis(testCalendar);
    
    // Since Apr 19 is a Tuesday, we expect the axis to end on a Tuesday
    String expected = "|Wed|Thu|Fri|Sat|Sun|Mon|Tue";
    
    assertEquals("Axis Test", testGenerator.generateXAxis(), expected);
  }

  /**
   * Tests Data String Generation.
   */
  @Test
  public void testGenerateDataString() {
    testHelper.setDataArray(dataArray);
    StringBuffer expected = new StringBuffer();
    double scaleFactor = 100 / (double) 14;
    
    String comma = ",";
    
    for (int i = 0; i < dataArray[0].length; i++) {
      expected.append(ChartDataHelper.roundTwoDecimals(dataArray[0][i] * scaleFactor));
      if (i != dataArray[0].length - 1) {
        expected.append(comma);
      }
    }

    assertEquals("Data Array", testGenerator.generateDataString(scaleFactor), expected.toString());
  }

} // End Chart Generator Test Class
