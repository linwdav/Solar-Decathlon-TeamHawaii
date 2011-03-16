package edu.hawaii.ihale.frontend.page.aquaponics;

//import java.util.Map;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
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
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer dayGraph = new WebMarkupContainer("dayGraphImage");

  /**
   * Various graph links.
   */
  Link<String> dayPhGraph;
  Link<String> dayReservesGraph;
  Link<String> dayTemperatureGraph;
  Link<String> dayConductivityGraph;
  Link<String> dayPowerGraph;
  Link<String> dayWaterGraph;

  /**
   * Graph to display.
   */
  //private static final String GRAPH_NAME = "AquaponicsStatsGraph";

  // private int currentGraphDisplay = 0;

  // Map<String, Integer> properties;

  /**
   * Layouts of page.
   * 
   * @throws Exception The exception.
   */
  public AquaponicsStats() throws Exception {
    
    ((SolarDecathlonSession)getSession()).getHeaderSession().setActiveTab(2);

    // properties = ((SolarDecathlonSession) getSession()).getProperties();
    // int currentGraphDisplay = properties.get(GRAPH_NAME);
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
        // currentGraphDisplay = 0;
        ((SolarDecathlonSession) getSession()).getAquaponicsStatsSession().setCurrentGraph(0);
        try {
          setResponsePage(AquaponicsStats.class);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    // pH Graph (by Day)
    dayReservesGraph = new Link<String>("dayReservesGraph") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, bring up daily pH graph. */
      @Override
      public void onClick() {
        // currentGraphDisplay = 1;
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

    add(dayReservesGraph);
    add(dayPhGraph);
    add(dayTemperatureGraph);
    add(dayConductivityGraph);
    add(dayPowerGraph);
    add(dayWaterGraph);
    add(mainButton);

    makeButtonActive(currentGraphDisplay);
    displayDayGraph(currentGraphDisplay, dayGraph);
    add(dayGraph);
  }

  /**
   * Determines which graph to display.
   * 
   * @param i graph identifier
   * @param wmc web component to display graph in
   */
  private void displayDayGraph(int i, WebMarkupContainer wmc) {
    String graphURL;
    switch (i) {

    // pH Graph
    case 0:
      add(new Label("dayChartType", "pH Readings (By Day)"));
      add(new Label("detailTitle", "pH"));
      add(new Label(
          "detailsText",
          "<h5>What is pH?</h5><p>In chemistry, <strong>pH is a measure of the acidity or basicity "
              + "of a solution.</strong> Pure water is said to be neutral, with a pH close to 7.0 "
              + "at 25&deg;C (77&deg;F). Solutions with a pH less than 7 are said to be acidic and "
              + "solutions with a pH greater than 7 are said to be basic or alkaline.</p>"
              + "<h5>How do you adjust pH levels?</h5><p>Check the pH of your water using litmus "
              + "paper, a pH test kit or pH meter. Limtmus paper and inexpensive pH test kits are "
              + "avilable in most hardware pool supply stores. The ideal pH is 7.0 for an aquaponic"
              + " system.</p>").setEscapeModelStrings(false));
      graphURL =
          "http://chart.apis.google.com/chart?chxl=0:|5|5.5|6|6.5|7|7.5|8|8.5|9|9.5|10|1:|Mon|"
              + "Tues|Wed|Thurs|Fri|Sat|Sun&chxs=1,676767,11.5,0,lt,676767&chxtc=1,15&chxt=y,x"
              + "&chs=470x350&cht=lc&chco=FF0000,3072F3,FF9900&chds=0,100,-5,100&chd=t:30,30,30,30"
              + ",30|42.043,45.165,44.785,37.934,38.18,25.102,21.844,44.405,51.957,52.305,52.412,"
              + "50.339,55.195,72.881|48,48&chdl=Min+Recommended|pH Level|Max+Recommended&chdlp=t"
              + "&chg=16.667,10&chls=2,5,5|3|2,5,5&chts=676767,16.5";
      wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));
      break;

    case 1:
      add(new Label("dayChartType", "Water Reserves (Current Status)"));
      add(new Label("detailTitle", "Water Reserves"));
      add(new Label("detailsText",
          "<h5>Do I need to Replace the Water?</h5><p>Water in hydroponic systems needs to be"
              + " discharged periodically, as the salts and chemicals build up in the water which"
              + " becomes toxic to the plants. This is both inconvenient and problematic as the "
              + "disposal location of this waste water needs to be carefully considered. In "
              + " aquaponics you NEVER replace your water; you only top it off as it evaporates."
              + "</p>"
              + "<h5>Remember this:</h5><p>The water level in the tank will slowly decrease as "
              + "some water is absorbed by the plants and some evaporates. Every few days you "
              + "should refill the tank to the top. About once a month a 10 - 15% of the tank "
              + "water should be siphoned out and replaced with fresh water.</p>")
          .setEscapeModelStrings(false));
      graphURL =
          "http://chart.apis.google.com/chart?chs=470x350&cht=p3&chco=E93434|80C65A"
              + "&chd=t:65,35&chdl=Used|Remaining&chdlp=t&chts=676767,16.5";
      wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));
      break;

    default:
      add(new Label("dayChartType",
          "Please see &#34;H<sub>2</sub>O Reserves&#34; or &#34;pH&#34; for detailed examples")
          .setEscapeModelStrings(false));
      add(new Label("detailTitle", "Title"));
      add(new Label("detailsText",
          "Please click on either &#34;H<sub>2</sub>O Reserves&#34; or &#34;pH&#34; "
              + "for detailed examples.").setEscapeModelStrings(false));
      break;
    }

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
      break;

    case 1:
      dayReservesGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
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
    }

  }

}
