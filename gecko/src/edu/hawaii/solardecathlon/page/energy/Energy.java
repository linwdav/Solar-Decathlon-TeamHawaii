package edu.hawaii.solardecathlon.page.energy;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Energy extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer dayGraph = new WebMarkupContainer("dayGraphImage");
  WebMarkupContainer weekGraph = new WebMarkupContainer("weekGraphImage");
  WebMarkupContainer monthGraph = new WebMarkupContainer("monthGraphImage");
  WebMarkupContainer yearGraph = new WebMarkupContainer("yearGraphImage");

  /**
   * Buttons for all graphs.
   */
  Link<String> dayConsumptionGraph;
  Link<String> dayBreakdownGraph;
  Link<String> dayHVACGraph;
  Link<String> dayAquaponicsGraph;
  Link<String> dayLightingGraph;

  /**
   * The page layout.
   */
  public Energy() {

    // Create buttons

    dayConsumptionGraph = new Link<String>("dayConsumptionGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        session.setProperty("GraphNum", "0");
        setResponsePage(new Energy());
      }
    };

    dayBreakdownGraph = new Link<String>("dayBreakdownGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        session.setProperty("GraphNum", "1");
        setResponsePage(new Energy());
      }
    };

    dayHVACGraph = new Link<String>("dayHVACGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        session.setProperty("GraphNum", "2");
        setResponsePage(new Energy());
      }
    };

    dayAquaponicsGraph = new Link<String>("dayAquaponicsGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        session.setProperty("GraphNum", "3");
        setResponsePage(new Energy());
      }
    };

    dayLightingGraph = new Link<String>("dayLightingGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        session.setProperty("GraphNum", "4");
        setResponsePage(new Energy());
      }
    };

    add(weekGraph);
    add(monthGraph);
    add(yearGraph);
    add(dayGraph);
    add(dayConsumptionGraph);
    add(dayBreakdownGraph);
    add(dayHVACGraph);
    add(dayAquaponicsGraph);
    add(dayLightingGraph);
    
    int currentGraphDisplay = Integer.valueOf(session.getProperty("GraphNum"));
    makeButtonActive(currentGraphDisplay);
    displayDayGraph(currentGraphDisplay);

  } // End Constructor

  /**
   * Determines which graph to display.
   */
  private void makeButtonActive(int i) {
    String classContainer = "class";
    String buttonContainer = "green-button";

    switch (i) {

    case 0:
      dayConsumptionGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 1:
      dayBreakdownGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 2:
      dayHVACGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 3:
      dayAquaponicsGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          "green-button")));
      break;

    case 4:
      dayLightingGraph.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    default:
      break;

    } // End Switch
  } // End button selector method

  /**
   * Determines which graph to display.
   * 
   * @param i graph identifier
   */
  private void displayDayGraph(int i) {

    switch (i) {

    case 0:
      displayDayGraphCaseZero();
      break;

    case 1:
      displayDayGraphCaseOne();
      break;

    default:
      displayDayGraphCaseDefault();
      break;
    } // End switch
  } // End DisplayDayGraph

  /**
   * Private function to handle consolidating code from displayDayGraph switch statement.
   */
  private void displayDayGraphCaseZero() {
    String srcContainer = "src";
    String graphURL = "";
    String monthURL = "";
    String yearURL = "";
    String weekURL = "";
    add(new Label("dayChartType", "Energy Consumption vs. Production (by Day)"));
    add(new Label("weekChartType", "Energy Consumption vs. Production (by Week)"));
    add(new Label("monthChartType", "Energy Consumption vs. Production (by Month)"));
    add(new Label("yearChartType", "Energy Consumption vs. Production (by Year)"));
    graphURL =
        "http://chart.apis.google.com/chart?chxl=1:|Mon|Tues|Wed|Thurs|Fri|Sat|Sun&chxs=0,"
            + "676767,11.5,0,lt,676767|1,676767,11.5,0,lt,676767&chxtc=1,15&chxt=y,x&chs=500x350"
            + "&cht=lc&chco=DA3B15,008000&chd=s:QMNRVekpnlgfW,somedabelprmnr&chdl=Power+"
            + "Consumption+(kWh)|Power+Production+(kWh)&chdlp=t&chg=16.67,10&chls=3|2"
            + "&chts=676767,16.5";
    dayGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
    monthURL =
        "http://chart.apis.google.com/chart?chxl=1:|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov"
            + "|Dec&chxr=0,500,2000&chxs=0,676767,11.5,0,lt,676767|1,676767,11.5,0,lt,676767&"
            + "chxtc=1,15&chxt=y,x&chs=500x350&cht=lc&chco=DA3B15,008000&chds=500,2000,500,2000"
            + "&chd=t:926.259,759.968,801.055,865.218,1112.252,1000.682,1014.534,794.78,794.213"
            + ",768.188,639.259,691.447,655.782|1039.359,1107.18,1051.859,1148.457,1200.341,"
            + "1413.972,1478.653,1435.088,1389.195,1283.2,1262.331,1333.671,1186.133,976.084"
            + "&chdl=Power+Consumption+(kWh)|Power+Production+(kWh)&chdlp=t&chg=9.091,6.6667"
            + "&chls=3|2&chts=676767,16.5";
    monthGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(monthURL)));
    yearURL =
        "http://chart.apis.google.com/chart?chxl=0:|2010|2011|2012|2013|2014&chxr=1,5,5000&"
            + "chxs=0,00AA00,14,0.5,l,676767&chxt=x,y&chs=500x350&cht=lc&chco=008000,FF0000&"
            + "chd=s:mkliggediiiklgfeffgijjkijgefcc,VVXWWURPOLILKJGMLNPQRRUXZZWVWY&chg=20,25&"
            + "chls=2|2";
    yearGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(yearURL)));
    weekURL =
        "http://chart.apis.google.com/chart?chxl=0:|0|Week+1|Week+2|Week+3|Week+4&chxr=1,5,500&"
            + "chxs=0,00AA00,14,0.5,l,676767&chxt=x,y&chs=500x350&cht=lc&chco=FF0000,008000&"
            + "chd=s:igcgjlryonldXXV,3483ghhasdfsdf&chg=20,25&chls=2|2";
    weekGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(weekURL)));
  }

  /**
   * Private function to handle consolidating code from displayDayGraph switch statement.
   */
  private void displayDayGraphCaseOne() {
    String srcContainer = "src";
    String graphURL = "";

    add(new Label("dayChartType", "Energy Breakdown (by Day)"));
    add(new Label("weekChartType", "Energy Breakdown (by Week)"));
    add(new Label("monthChartType", "Energy Breakdown (by Month)"));
    add(new Label("yearChartType", "Energy Breakdown (by Year)"));
    graphURL =
        "http://chart.apis.google.com/chart?chs=500x350&cht=p3&chco=EA0D0D|FF9900|3EC03E|3267CF"
            + "&chd=t:45.634,50.337,28.189,25.964&chdl=Aquaponics|HVAC|Appliances|Lighting&"
            + "chdlp=t&chts=676767,16.5";
    dayGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
    monthGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
    yearGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
    weekGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
  }

  /**
   * Private function to handle consolidating code from displayDayGraph switch statement.
   */
  private void displayDayGraphCaseDefault() {
    String srcContainer = "src";
    String s1Container = "Please see Consumption charts for detailed examples";
    String graphURL = "";
    String monthURL = "";
    String yearURL = "";
    String weekURL = "";

    add(new Label("dayChartType", s1Container).setEscapeModelStrings(false));
    add(new Label("weekChartType", s1Container).setEscapeModelStrings(false));
    add(new Label("monthChartType", s1Container).setEscapeModelStrings(false));
    add(new Label("yearChartType", s1Container).setEscapeModelStrings(false));
    graphURL =
        "http://chart.apis.google.com/chart?chxl=0:|Jan|Feb|Mar|Jun|Jul|Aug|1:|100|50|0&chxt=x,y"
            + "&chs=500x350&cht=lc&chd=s:AA,ASms297wzuowqytmbSKA&chg=25,50&chls=0.75,-1,-1|2,4,1"
            + "&chm=o,FF9900,1,-2,8|b,3399CC44,0,1,0&chtt=Generic+Chart&chts=676767,14.5";
    monthURL = graphURL;
    yearURL = graphURL;
    weekURL = graphURL;
    dayGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(graphURL)));
    monthGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(monthURL)));
    yearGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(yearURL)));
    weekGraph.add(new AttributeModifier(srcContainer, true, new Model<String>(weekURL)));
  }
}
