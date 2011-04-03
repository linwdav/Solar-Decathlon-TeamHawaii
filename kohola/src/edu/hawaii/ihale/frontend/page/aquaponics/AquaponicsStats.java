package edu.hawaii.ihale.frontend.page.aquaponics;

//import java.util.Map;
import java.awt.Dimension;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import com.codecommit.wicket.AbstractChartData;
import com.codecommit.wicket.Chart;
import com.codecommit.wicket.ChartProvider;
import com.codecommit.wicket.ChartType;
import com.codecommit.wicket.IChartData;
import edu.hawaii.ihale.frontend.page.Header;
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
   * MarkupContainer for day graph.
   */
  WebMarkupContainer dayGraph = new WebMarkupContainer("dayGraphImage");

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
  static final String ph = "pH";
  static final String waterLevel = "Water Level";
  
  
  /**
   * Layouts of page.
   * 
   * @throws Exception The exception.
   */
  public AquaponicsStats() throws Exception {
    
    ((SolarDecathlonSession)getSession()).getHeaderSession().setActiveTab(2);
       
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
    
    /**************testing google chart API********************/
    IChartData data = new AbstractChartData() {
      private static final long serialVersionUID = 1L;
      public double[][] getData() {
        return new double[][] {{34, 22}};          
      }
    };
    
    ChartProvider provider = new ChartProvider(new Dimension(250, 100), ChartType.PIE_3D, data);
    provider.setPieLabels(new String[] {"Hello", "World"});
    
    add(new Chart("dayGraphImage", provider)); 
    add(new Chart("weekGraphImage", provider));      
    add(new Chart("monthGraphImage", provider));      

   /*********************************/
  }

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
