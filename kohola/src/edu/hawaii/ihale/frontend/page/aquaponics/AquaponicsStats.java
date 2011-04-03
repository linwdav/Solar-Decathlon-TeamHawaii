package edu.hawaii.ihale.frontend.page.aquaponics;

//import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.ihale.frontend.page.Header;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;

/**
 * The Aquaponics Statistics page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsStats extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Various graph links.
   */
  Link<String> dayPhGraph;
  Link<String> waterLevelGraph;
  Link<String> dayTemperatureGraph;
  Link<String> dayConductivityGraph;
  Link<String> dayPowerGraph;
  Link<String> dayWaterGraph;

  // Labels for graphs
  Label dayLabel;
  Label weekLabel;
  Label monthLabel;

  // Pre-generated Strings
  static final String dayStats = " - Statistics (Day)";
  static final String weekStats = " - Statistics (Week)";
  static final String monthStats = " - Statistics (Month)";

  // Chart types
  static final String ph = "pH";
  static final String electricalConductivity = "Electrical Conductivity";
  static final String temperature = "Water Temperature";
  
  // Time Durations in Milliseconds
  static final long oneWeekInMillis = 1000L * 60L * 60L * 24L * 7L;
  static final long oneDayInMillis = 1000L * 60L * 60L * 24L;
  static final long oneMonthInMillis = 1000L * 60L * 60L * 24L * 28L;
  static final long oneHourInMillis = 1000L * 60L * 60L;

  /**
   * Layouts of page.
   * 
   * @throws Exception The exception.
   */
  public AquaponicsStats() throws Exception {

    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(2);

    int currentGraphDisplay =
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().getCurrentGraph();

    // Button at top of page
    Link<String> mainButton = new Link<String>("mainButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        try {
          setResponsePage(AquaPonics.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Graph Buttons

    // pH Graph (by Day)
    dayPhGraph = new Link<String>("dayPhGraph") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, bring up daily pH graph. */
      @Override
      public void onClick() {
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(0);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Temperature Graph (by Day)
    dayTemperatureGraph = new Link<String>("dayTemperatureGraph") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, bring up daily pH graph. */
      @Override
      public void onClick() {
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(1);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Conductivity Graph (by Day)
    dayConductivityGraph = new Link<String>("dayConductivityGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(2);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Add items
    add(dayPhGraph);
    add(dayTemperatureGraph);
    add(dayConductivityGraph);
    add(mainButton);

    // Labels for each chart
    dayLabel = new Label("dayChartType", new Model<String>(""));
    weekLabel = new Label("weekChartType", new Model<String>(""));
    monthLabel = new Label("monthChartType", new Model<String>(""));

    add(dayLabel);
    add(weekLabel);
    add(monthLabel);

    makeButtonActive(currentGraphDisplay);

    // MarkupContainers for graphs.
    WebMarkupContainer dayGraph = new WebMarkupContainer("dayGraphImage");
    WebMarkupContainer weekGraph = new WebMarkupContainer("weekGraphImage");
    WebMarkupContainer monthGraph = new WebMarkupContainer("monthGraphImage");

    // Set graph
    displayGoogleLineChart(0, currentGraphDisplay, dayGraph);
    displayGoogleLineChart(1, currentGraphDisplay, weekGraph);
    displayGoogleLineChart(2, currentGraphDisplay, monthGraph);

    // Add Chart to page.
    add(dayGraph);
    add(weekGraph);
    add(monthGraph);

  } // end constructor

  /**
   * Displays the corresponding graph.
   * 
   * @param interval This is day (0), week (1), or month (2)
   * @param currentGraphDisplay The current chart to show (e.g., pH, Temp, EC)
   * @param wmc Container holding graph.
   */
  private void displayGoogleLineChart (int interval,
      int currentGraphDisplay, WebMarkupContainer wmc) {

    // Range of values for y-axis
    int endRange = 0;
    
    // Generate the X-axis string to be used by google chart URL
    String stringXAxis = generateXAxis(interval);
    
    // Base string for graph URI
    String graphURI = "http://chart.googleapis.com/chart?";
    
    // Gives the start and end ranges for a particular type of
    // graph.
    switch (currentGraphDisplay) {
    // PH graph
    case 0:
      endRange = 14;
      break;
    // Temperature
    case 1:
      endRange = 100;
      break;
    // Conductivity
    case 2:
      endRange = 20;
      break;
    default:
        break;
    }
    // Scaling
    double scale = (double)endRange;
    double scaleFactor = 100.0 / scale;
    
    // Common Attributes
    String chartType = "cht=lc"; // lc = line chart
    String chartAxis = "&chxt=x,y"; // Show X & Y axis
    String chartSize = "&chs=510x350"; // width 510 x height 350
    String lineColor = "&chco=0000FF"; // Blue line
    String lineWeight = "&chls=3"; // line weight = 3
    String tickMarks = "&chxtc=0,10|1,10"; // Tick line length for X and Y Axis = 10px
    // Diamond, Red Markers, 10pixels each
    String shapeMarkers = "&chm=d,FF0000,0,-1,10";
    // Set Y-Axis
    String yAxis = "&chxr=1,0," + endRange;

    // Show days of week
    String chartAxisLabels = "&chxl=0:" + stringXAxis;

    String dataString = generateDataString(interval, currentGraphDisplay, scaleFactor);

    String dataPoints = "&chd=t:" + dataString;

    // Construct Graph's URI
    graphURI +=
        chartType + chartAxis + chartSize + lineColor + lineWeight + tickMarks + chartAxisLabels
            + shapeMarkers + dataPoints + yAxis;

    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURI)));

  } // End display week graph

  /**
   * Generates a string comprising of the data.  String is prepared for google
   * charts.
   * 
   * @param interval The interval; day(0), week(1), month(2)
   * @param currentGraphDisplay The current graph being displayed: PH, EC, Temp, etc.
   * @param scaleFactor The scale.
   * @return Data string for google charts.
   */
  private String generateDataString(int interval, int currentGraphDisplay, double scaleFactor) {
    // Get current time
    long currentTime = new Date().getTime();
    
    // This is the interval in milliseconds
    long intervalInMillis = 0;
    
    // String to hold data values
    String dataString = "";
    
    // This is the step size in milliseconds.  
    // We increment the start time by this amount.
    // E.g., for a week, we increment by days. 
    // I.e., this is the next smallest interval.
    long stepSizeInMillis = 0;
    
    // Choose our interval for data (day, week, month)
    switch (interval) {
    // Day
    case 0:
      intervalInMillis = oneDayInMillis;
      stepSizeInMillis = oneHourInMillis;
      break;
    // Week
    case 1:
      intervalInMillis = oneWeekInMillis;
      stepSizeInMillis = oneDayInMillis;
      break;
    // Month
    case 2:
      intervalInMillis = oneMonthInMillis;
      stepSizeInMillis = oneWeekInMillis;
      break;
    default:
      break;
    }

    // This is the time to get data from or 'since'
    long startTime = currentTime - intervalInMillis;
    
    // The time we are collecting data until.  We average all data
    // up to this point and then go to the next interval/step.
    long nextInterval = startTime + stepSizeInMillis;
    
    
    // Construct data string based on the graph type being displayed.
    switch (currentGraphDisplay) {
    
    // pH
    case 0:
      // Get information
      List<TimestampDoublePair> phList =
          SolarDecathlonApplication.getRepository()
          .getAquaponicsPhSince(startTime);    
   
      // Generate data points as google charts string
      dataString = generateDataStringForDouble(phList, nextInterval, 
          stepSizeInMillis, scaleFactor);
      break;
      
    // Temperature
    case 1:
      // Get information
      List<TimestampIntegerPair> tempList =
          SolarDecathlonApplication.getRepository()
          .getAquaponicsTemperatureSince(startTime);    
   
      // Generate data points as google charts string
      dataString = generateDataStringForInteger(tempList, nextInterval, 
          stepSizeInMillis, scaleFactor);
      break;
      
    // Conductivity
    case 2:
      // Get information
      List<TimestampDoublePair> ecList =
          SolarDecathlonApplication.getRepository()
          .getAquaponicsElectricalConductivitySince(startTime);    
   
      // Generate data points as google charts string
      dataString = generateDataStringForDouble(ecList, nextInterval, 
          stepSizeInMillis, scaleFactor);
      break;
      
    default:
      break;
    } // End switch
    
    return dataString;    
  } // End Generate Data String function
  
  /**
   * Generates a data string given a list of items.  This string is
   * ready for insertion into a google chart URI.
   * 
   * @param list The list of data.  Either double or integer objects.
   * @param nextIntervalIn The next time to gather data to average from.
   * @param stepSizeInMillis The size of the intervals we are averaging over.
   * @param scaleFactor The scale of the chart.
   * @return The google-chart compatible data string.
   */
  private String generateDataStringForDouble(List<TimestampDoublePair> list, 
      long nextIntervalIn, long stepSizeInMillis, double scaleFactor) {
    
    long nextInterval = nextIntervalIn;
    
    StringBuffer dataString = new StringBuffer();
    
    // Tracks how many points we've counted in total
    int totalNumPoints = 0;
    // Tracks number of points this interval
    int numPoints = 0;
    // Total value for all points this interval
    double sumPoints = 0;
    // List to store all averages for each interval
    List<Double> avgList = new LinkedList<Double>();

    // Loop through entire list of values
    for (TimestampDoublePair element : list) {
      totalNumPoints++;

      // As long as the timestamp for the item
      // is more than the interval, then advance the
      // interval.
      while (element.getTimestamp() > nextInterval) {
        
        // Add 0 to list for that particular day
        if (numPoints == 0) {
          avgList.add(0.0);
          nextInterval += stepSizeInMillis;
        }
        else {
          avgList.add(sumPoints / (double) numPoints);
          sumPoints = 0;
          numPoints = 0;
          nextInterval += stepSizeInMillis;
        }
      } // End While

      // Add number to current interval
      numPoints++;
      sumPoints += element.getValue();

      if (totalNumPoints == list.size()) {
        avgList.add(sumPoints / (double) numPoints);
      }
    } // End for each loop

    // Keep track of where we we are in the list
    int index = 1;

    // Scale and Combine data points into string for chart.
    for (Double i : avgList) {
      dataString.append(i * scaleFactor);

      // Only add a comma if we're not at the end of the list
      if (index < avgList.size()) {
        String comma = ",";
        dataString.append(comma);
      }
      index++;
    } // End For each loop
    
    return dataString.toString();
  } // End Generate Data String for Double
  
  /**
   * Generates a data string given a list of items.  This string is
   * ready for insertion into a google chart URI.
   * 
   * @param list The list of data.  Either double or integer objects.
   * @param nextIntervalIn The next time to gather data to average from.
   * @param stepSizeInMillis The size of the intervals we are averaging over.
   * @param scaleFactor The scale of the chart.
   * @return The google-chart compatible data string.
   */
  private String generateDataStringForInteger(List<TimestampIntegerPair> list, 
      long nextIntervalIn, long stepSizeInMillis, double scaleFactor) {
    
    long nextInterval = nextIntervalIn;
    
    StringBuffer dataString = new StringBuffer();
    
    // Tracks how many points we've counted in total
    int totalNumPoints = 0;
    // Tracks number of points this interval
    int numPoints = 0;
    // Total value for all points this interval
    double sumPoints = 0;
    // List to store all averages for each interval
    List<Double> avgList = new LinkedList<Double>();

    // Loop through entire list of values
    for (TimestampIntegerPair element : list) {
      totalNumPoints++;

      // As long as the timestamp for the item
      // is more than the interval, then advance the
      // interval.
      while (element.getTimestamp() > nextInterval) {

        // Add 0 to list for that particular interval
        if (numPoints == 0) {
          avgList.add(0.0);
          nextInterval += stepSizeInMillis;
        }
        else {
          avgList.add(sumPoints / (double) numPoints);
          sumPoints = 0;
          numPoints = 0;
          nextInterval += stepSizeInMillis;
        }
      } // End While

      // Add number to current interval
      numPoints++;
      sumPoints += element.getValue();

      if (totalNumPoints == list.size()) {
        avgList.add(sumPoints / (double) numPoints);
      }
    } // End for each loop

    // Keep track of where we we are in the list
    int index = 1;

    // Scale and Combine data points into string for chart.
    for (Double i : avgList) {
      dataString.append (i * scaleFactor);

      // Only add a comma if we're not at the end of the list
      if (index < avgList.size()) {
        String comma = ",";
        dataString.append(comma);
      }
      index++;
    } // End For each loop
    
    return dataString.toString();
  } // End Generate DataString for Integer
  
  /**
   * This function generate the axis string for a Google Chart.
   * 
   * @param interval The time interval.  Day (0), Week (1), Month (2)
   * @return String to generate the axis.
   */
  private String generateXAxis(int interval) {
    StringBuffer returnString = new StringBuffer(50);
    // Get the current date
    Calendar current = Calendar.getInstance();
    
    switch (interval) {
    
    // Day Chart
    case 0:
     String[] hours = { "12AM", " ", "2", " ", "4", " ",
         "6", " ", "8", " ", "10", " ",
         "12PM", " ", "2", " ", "4", " ",
         "6", " ", "8", " ", "10", " ",
         "12AM", " ", "2", " ", "4", " ",
         "6", " ", "8", " ", "10", " ",
         "12PM", " ", "2", " ", "4", " ",
         "6", " ", "8", " ", "10", " ",};
     
     // index for hour
     int hour = current.get(Calendar.HOUR_OF_DAY);
     
     // Generate day axis based on time now
     int dayLength = 24;
     
     for (int i = 1; i <= dayLength; i++) {
       String appendString = "|" + hours[hour++];
       returnString.append(appendString);
     }
     
     break;
     
    // Week Chart
    case 1:
      // Week array
      String[] days =
          { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", 
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

      // index for day
      int day = current.get(Calendar.DAY_OF_WEEK);

      // Generate the week axis based on today's date
      int weekLength = 7;

      // Construct string for axis
      for (int i = 1; i <= weekLength; i++) {
        String appendString = "|" + days[day++];
        returnString.append( appendString );
      }
      break;
      
    // Month
    case 2:
      String appendString = "|3 Weeks Ago|2 Weeks Ago|1 Week Ago|This Week";
      returnString.append(appendString);
      break;
    default:
      break;
    }
    return returnString.toString();
  } // End Generate Graph Function
  
  /**
   * Changes the label accordingly.
   * 
   * @param label The System.
   */
  private void setGraphLabels(String label) {
    dayLabel.setDefaultModelObject(label + dayStats);
    weekLabel.setDefaultModelObject(label + weekStats);
    monthLabel.setDefaultModelObject(label + monthStats);
  }

  /**
   * Determines which graph to display.
   * 
   * @param i graph identifier
   */
  private void makeButtonActive(int i) {
    String classContainer = "class";
    String buttonContainer = "green-button";

    switch (i) {

    case 0:
      dayPhGraph
          .add(new AttributeModifier(classContainer, true, new Model<String>(buttonContainer)));
      setGraphLabels(ph);
      break;

    case 1:
      dayTemperatureGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      setGraphLabels(temperature);
      break;

    case 2:
      dayConductivityGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      setGraphLabels(electricalConductivity);
      break;
      
    default:
      break;
    } // End Switch
  } // End Button Active method

} // End aquaponics class
