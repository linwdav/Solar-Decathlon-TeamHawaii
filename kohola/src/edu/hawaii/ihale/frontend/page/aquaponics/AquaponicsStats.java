package edu.hawaii.ihale.frontend.page.aquaponics;

//import java.util.Map;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
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
  static final String waterLevel = "Water Level";

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

    // Water Level Graph (by Day)
    waterLevelGraph = new Link<String>("dayWaterLevelGraph") {
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

    // Hvac Graph (by Day)
    dayTemperatureGraph = new Link<String>("dayTemperatureGraph") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, bring up daily pH graph. */
      @Override
      public void onClick() {
        // currentGraphDisplay = 2;
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(2);
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
        // currentGraphDisplay = 3;
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(3);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Power Graph (by Day)
    dayPowerGraph = new Link<String>("dayPowerGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        // currentGraphDisplay = 4;
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(4);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Water Quality Graph (by Day)
    dayWaterGraph = new Link<String>("dayWaterGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        // currentGraphDisplay = 5;
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(5);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // Add items

    add(waterLevelGraph);
    add(dayPhGraph);
    add(dayTemperatureGraph);
    add(dayConductivityGraph);
    add(dayPowerGraph);
    add(dayWaterGraph);
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
    displayWeekGraph(currentGraphDisplay, dayGraph);
    displayWeekGraph(currentGraphDisplay, weekGraph);
    displayWeekGraph(currentGraphDisplay, monthGraph);

    // Add Chart to page.
    add(dayGraph);
    add(weekGraph);
    add(monthGraph);

  } // end constructor

  /**
   * Displays the corresponding week graph.
   * 
   * @param currentGraph The current chart to show (e.g., pH, water level)
   * @param wmc Container holding graph.
   */
  private void displayWeekGraph(int currentGraph, WebMarkupContainer wmc) {
    // Get the current date
    Calendar current = Calendar.getInstance();

    // Week array
    String[] days =
        { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", 
          "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

    // index for day
    int day = current.get(Calendar.DAY_OF_WEEK);

    // Generate the week axis based on today's date
    String weekAxis = "";
    int weekLength = 7;

    for (int i = 1; i <= weekLength; i++) {
      weekAxis += "|" + days[day++];
    }

    // Base string for graph URI
    String graphURI = "http://chart.googleapis.com/chart?";

    // Common Attributes
    String chartType = "cht=lc"; // lc = line chart
    String chartAxis = "&chxt=x,y"; // Show X & Y axis
    String chartSize = "&chs=470x350"; // 470 x 350
    String lineColor = "&chco=0000FF"; // Blue
    String lineWeight = "&chls=3"; // line weight = 3
    String tickMarks = "&chxtc=0,10|1,10"; // Tick line length for X and Y Axis = 10px
    
    // Diamond, Red Markers, 10pixels each
    String shapeMarkers = "&chm=d,FF0000,0,-1,10";

    // Show days of week
    String chartAxisLabels = "&chxl=0:" + weekAxis;

    // Get current time and length of one day in milliseconds
    long oneWeekInMillis = 1000 * 60 * 60 * 24 * 7;
    long oneDayInMillis = 1000 * 60 * 60 * 24;
    long currentTime = current.getTimeInMillis();
    long startTime = currentTime - oneWeekInMillis;

    // Get information
    List<TimestampDoublePair> phList =
        SolarDecathlonApplication.getRepository().getAquaponicsPhCommmandSince(startTime);

    long nextInterval = startTime + oneDayInMillis;

    // Tracks how many points we've counted in total
    int totalNumPoints = 0;
    // Tracks number of points this interval
    int numPoints = 0;
    // Total value for all points this interval
    double sumPoints = 0;

    // List to store all averages for each interval
    List<Double> avgList = new LinkedList<Double>();

    // Loop through entire list of values
    for (TimestampDoublePair tdp : phList) {
      totalNumPoints++;

      // As long as the timestamp for the item
      // is more than the interval, then advance the
      // interval.
      while (tdp.getTimestamp() > nextInterval) {

        // Add 0 to list for that particular day
        if (numPoints == 0) {
          avgList.add(0.0);
          nextInterval += oneDayInMillis;
        }
        else {
          avgList.add(sumPoints / (double) numPoints);
          sumPoints = 0;
          numPoints = 0;
          nextInterval += oneDayInMillis;
        }
      } // End While

      // Add number to current interval
      numPoints++;
      sumPoints += tdp.getValue();

      if (totalNumPoints == phList.size()) {
        avgList.add(sumPoints / (double) numPoints);
      }

    } // End for each loop

    // pH Specific scaling
    double scale = 14.0;
    double scaleFactor = 100.0 / scale;

    // String to hold data values
    String dataString = "";

    // Keep track of where we we are in the list
    int index = 1;

    // Scale and Combine data points into string for chart.
    for (Double i : avgList) {
      dataString += (i * scaleFactor);

      // Only add a comma if we're not at the end of the list
      if (index < avgList.size()) {
        dataString += ",";
      }
      index++;
    } // End For each loop

    String yAxis = "&chxr=1,0," + scale;
    String dataPoints = "&chd=t:" + dataString;

    // Construct Graph's URI
    graphURI +=
        chartType + chartAxis + chartSize + lineColor + lineWeight + tickMarks + chartAxisLabels
            + shapeMarkers + dataPoints + yAxis;

    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURI)));

  } // End display week graph

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
      waterLevelGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      dayLabel.setDefaultModelObject("Water level");
      setGraphLabels(waterLevel);
      break;

    case 2:
      dayTemperatureGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 3:
      dayConductivityGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 4:
      dayPowerGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 5:
      dayWaterGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    default:
      break;
    } // End Switch
  } // End Button Active method

} // End aquaponics class
