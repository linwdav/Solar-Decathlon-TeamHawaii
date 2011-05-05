package edu.hawaii.systemh.frontend.googlecharts;

import java.util.Calendar;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;

/**
 * Generates google charts within wicket.
 * 
 * @author Kevin Leong
 * 
 */
public class ChartGenerator {

  // Data helper to supply axis and data
  ChartDataHelper dataHelper;
  
  // Specifies what type of chart to display
  TimeInterval timeInterval;
  ChartDataType chartDataType;
  
  // Chart height and width;
  int height;
  int width;
  
  // Container to add chart to
  WebMarkupContainer wmc;

  /**
   * Constructor that creates a data helper object and populates it with
   * the necessary data.
   * 
   * @param timeInterval The time interval (Day, Week, or Month).
   * @param chartDataType The chart type (pH, Oxygen, temp).
   * @param wmc The web markup container to add this to.
   * @param width The width of the chart.
   * @param height The height of the chart.
   */
  public ChartGenerator(TimeInterval timeInterval, ChartDataType chartDataType,
      WebMarkupContainer wmc, int width, int height) {

    this.timeInterval = timeInterval;
    this.chartDataType = chartDataType;
    this.width = width;
    this.height = height;
    this.wmc = wmc;
    
  } // End constructor

  /**
   * Adds the chart to the web markup container (instance variable).
   */
  public void addChart() {    
    // Get current time
    Calendar currentTime = Calendar.getInstance();
    
    // Get axis and data points
    dataHelper = new ChartDataHelper(timeInterval, chartDataType, currentTime);
    
    // Populate Data
    dataHelper.populateChart();
    
    wmc.add(new AttributeModifier("src", true, 
        new Model<String>(constructChartURI(width, height))));
  }
  
  /**
   * Generates the chart's URI.
   * 
   * @param width The width of the chart.
   * @param height The height of the chart.
   * @return The chart URI
   */
  String constructChartURI(int width, int height) {
    
    // End range.  The range of the y-axis goes from 0 - endrange.
    int endRange = 0;
    int stepCount = 0;
    
    // Gives the end ranges for a particular type of
    // graph.
    switch (chartDataType) {
    
    // PH graph
    case AQUAPONICS_PH:
      endRange = 14;
      stepCount = 1;
      break;
    
    // Temperature
    case AQUAPONICS_TEMP:
      endRange = 50;
      stepCount = 5;
      break;
    
    // Conductivity
    case AQUAPONICS_CONDUCTIVITY:
      endRange = 20;
      stepCount = 2;
      break;
      
    // HVAC Temp
    case HVAC_TEMP:
      endRange = 50;
      stepCount = 5;
      break;
      
    // Energy consumption vs. generation
    case GENERATION_CONSUMPTION:
      endRange = 2400;
      stepCount = 250;
      break;
      
    default:
        break;
    }
    
    // Scaling the y-axis
    double scale = (double)endRange;
    double scaleFactor = 100.0 / scale;
    
    // Base string for graph URI
    String baseURI = "http://chart.googleapis.com/chart?";
    
    // Common Attributes
    String chartType = "cht=lc"; // lc = line chart
    String chartAxis = "&chxt=x,y"; // Show X & Y axis
    String chartSize = "&chs=" +  width + "x" + height; // default width 510 x height 350
    String lineColor = "&chco=0000FF"; // Blue line
    String lineWeight = "&chls=2"; // line weight = 2
    String tickMarks = "&chxtc=0,10|1,10"; // Tick line length for X and Y Axis = 10px
    
    // Diamond, Red Markers, 10pixels each
    String shapeMarkers = "&chm=d,FF0000,0,-1,10";
    
    // Set Y-Axis
    String yAxis = "&chxr=1,0," + endRange + "," + stepCount;

    // Get Axis
    String chartAxisLabels = "&chxl=0:" + generateXAxis();

    String dataString = generateDataString(scaleFactor);

    String dataPoints = "&chd=t:" + dataString;

    StringBuffer resultString = new StringBuffer();
    
    // Construct URI
    resultString.append(baseURI);
    resultString.append(chartType);
    resultString.append(chartAxis);
    resultString.append(chartSize);
    resultString.append(lineColor); 
    resultString.append(lineWeight);
    resultString.append(tickMarks);
    resultString.append(chartAxisLabels);
    resultString.append(shapeMarkers);
    resultString.append(dataPoints);
    resultString.append(yAxis);

    return resultString.toString();
  } // End construct URI
  
  /**
   * Returns the axis in the format required by the Google Chart URI.
   * 
   * @return The x-axis string for google charts.
   */
  String generateXAxis() {
    
    StringBuffer returnString = new StringBuffer(50);

    // Grab axis information from the chart data helper
    String[]  axisArray = dataHelper.getAxisArray();
    
    // Append entire array to axis string
    for (int i = 0; i < axisArray.length; i++) {
      String appendString = "|" + axisArray[i];
      returnString.append(appendString);
    }
    return returnString.toString();
  } // End generate X Axis
  
  /**
   * Generates the data points as a google-chart uri-ready string.
   * 
   * @param scaleFactor The amount to scale the data by.
   * @return The data points as a string.
   */
  String generateDataString(double scaleFactor) {
    StringBuffer returnString = new StringBuffer(50);
    
    double[][] dataArray = dataHelper.getDataArray();
    
    // If this is the energy chart, then a multidimensional array
    // holds the data
    if (chartDataType == ChartDataType.GENERATION_CONSUMPTION) {
      String space = " ";
      returnString.append(space);
      // TO DO
    }
    // Otherwise, we have only a single dimension
    else {
      
      // Go through array and append
      for (int i = 0; i < dataArray[0].length; i++) {
        
        // Scale the values
        returnString.append(ChartDataHelper.roundTwoDecimals
            (dataArray[0][i] * scaleFactor));
        
        if (i != (dataArray[0].length - 1)) {
          String comma = ",";
          returnString.append(comma);
        }
      }
    } // End else
  
    return returnString.toString();
  } // End generateDataString method

} // End ChartGenerator class
