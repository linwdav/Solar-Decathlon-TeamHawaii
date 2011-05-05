package edu.hawaii.systemh.frontend.page.aquaponics;

//import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.systemh.frontend.SolarDecathlonSession;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;
import edu.hawaii.systemh.frontend.googlecharts.ChartGenerator;
import edu.hawaii.systemh.frontend.page.Header;

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

    ChartDataType currentGraphDisplay =
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

      /** Upon clicking this link, bring up daily pH graph. **/
      @Override
      public void onClick() {
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().
          setCurrentGraph(ChartDataType.AQUAPONICS_PH);
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
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().
          setCurrentGraph(ChartDataType.AQUAPONICS_TEMP);
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
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().
          setCurrentGraph(ChartDataType.AQUAPONICS_CONDUCTIVITY);
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

    ChartGenerator cgDay = new ChartGenerator(TimeInterval.DAY,
        currentGraphDisplay, dayGraph, 510, 350 );
    ChartGenerator cgWeek = new ChartGenerator(TimeInterval.WEEK,
        currentGraphDisplay, weekGraph, 510, 350 );
    ChartGenerator cgMonth = new ChartGenerator(TimeInterval.MONTH,
        currentGraphDisplay, monthGraph, 510, 350 );
    
    cgDay.addChart();
    cgWeek.addChart();
    cgMonth.addChart();
    
    add(dayGraph);
    add(weekGraph);
    add(monthGraph);
  } // end constructor
  
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
   * @param currentGraphDisplay graph identifier
   */
  private void makeButtonActive(ChartDataType currentGraphDisplay) {
    String classContainer = "class";
    String buttonContainer = "green-button";

    switch (currentGraphDisplay) {

    case AQUAPONICS_PH:
      dayPhGraph
          .add(new AttributeModifier(classContainer, true, new Model<String>(buttonContainer)));
      setGraphLabels(ph);
      break;

    case AQUAPONICS_TEMP:
      dayTemperatureGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      setGraphLabels(temperature);
      break;

    case AQUAPONICS_CONDUCTIVITY:
      dayConductivityGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      setGraphLabels(electricalConductivity);
      break;
      
    default:
      break;
    } // End Switch
  } // End Button Active method

} // End aquaponics stats class
