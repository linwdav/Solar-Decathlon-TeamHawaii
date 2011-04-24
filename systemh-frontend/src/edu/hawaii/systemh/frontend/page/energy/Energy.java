package edu.hawaii.systemh.frontend.page.energy;

import java.util.Date;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import com.codecommit.wicket.ChartProvider;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.ChartDisplayType;
import edu.hawaii.systemh.energymodel.chartgenerator.EnergyChartData;
import edu.hawaii.systemh.energymodel.chartinterface.EnergyManagementChartInterface;
import edu.hawaii.systemh.energymodel.chartinterface.SampleChartInterface;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.SolarDecathlonSession;
import edu.hawaii.systemh.frontend.page.Header;
import edu.hawaii.systemh.frontend.page.help.Help;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Energy extends Header {

  //private boolean DEBUG = false;

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  // String constants to replace string literals that gave PMD errors
  private static final String SRC = "src";

  EnergyChartData chartData;
  EnergyManagementChartInterface chartInterface;

  ChartProvider provider;

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer consumptionDayGraph = new WebMarkupContainer("DayConsumptionGraph");
  WebMarkupContainer consumptionWeekGraph = new WebMarkupContainer("WeekConsumptionGraph");
  WebMarkupContainer consumptionMonthGraph = new WebMarkupContainer("MonthConsumptionGraph");
  WebMarkupContainer dayDeviceGraph = new WebMarkupContainer("DayDevicesGraph");
  WebMarkupContainer weekDeviceGraph = new WebMarkupContainer("WeekDevicesGraph");
  WebMarkupContainer monthDeviceGraph = new WebMarkupContainer("MonthDevicesGraph");
  WebMarkupContainer daySystemGraph = new WebMarkupContainer("DaySystemGraph");
  WebMarkupContainer weekSystemGraph = new WebMarkupContainer("WeekSystemGraph");
  WebMarkupContainer monthSystemGraph = new WebMarkupContainer("MonthSystemGraph");

  /**
   * Buttons for all graphs.
   */
  // Link<String> dayConsumptionGraph;

  /**
   * Links to other pages.
   */
  Link<String> hvacSetting;
  Link<String> aquaponicsSetting;
  Link<String> lightingSetting;

  // Label currentConsumption = new Label("CurrentConsumption", "0");
  // Label currentGeneration = new Label("CurrentGeneration", "0");

  /**
   * The layout for energy page.
   * 
   * @throws Exception The exception.
   */
  public Energy() throws Exception {

    chartInterface = new SampleChartInterface();
    chartData = new EnergyChartData(chartInterface);

    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(1);

    // Messages
    // Add messages as a list view to each page

    // Get all messages applicable to this page
    List<SystemStatusMessage> msgs =
        SolarDecathlonApplication.getMessages().getElectricalMessages();

    // Create wrapper container for pageable list view
    WebMarkupContainer systemLog = new WebMarkupContainer("EnergySystemLogContainer");
    systemLog.setOutputMarkupId(true);

    // Help button link
    Link<String> helpLink = new Link<String>("helpLink") {
      private static final long serialVersionUID = 1L;

      public void onClick() {
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(2);
        setResponsePage(Help.class);
      }
    };

    // Help Image
    helpLink.add(new Image("helpEnergy", new ResourceReference(Header.class,
        "images/icons/help.png")));

    add(helpLink);

    // Create Listview
    PageableListView<SystemStatusMessage> listView =
        new PageableListView<SystemStatusMessage>("EnergyStatusMessages", msgs, 10) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<SystemStatusMessage> item) {

            SystemStatusMessage msg = item.getModelObject();

            // If only the empty message is in the list, then
            // display "No Messages"
            if (msg.getType() == null) {
              item.add(new Label("EnergySystemName", "-"));
              item.add(new Label("EnergyMessageType", "-"));
              item.add(new Label("EnergyTimestamp", "-"));
              item.add(new Label("EnergyMessageContent", "No Messages"));
            }
            // Populate data
            else {
              item.add(new Label("EnergySystemName", msg.getSystem().toString()));
              item.add(new Label("EnergyTimestamp", new Date(msg.getTimestamp()).toString()));
              item.add(new Label("EnergyMessageType", msg.getType().toString()));
              item.add(new Label("EnergyMessageContent", msg.getMessage()));
            }
          }
        };

    systemLog.add(listView);
    systemLog.add(new AjaxPagingNavigator("paginatorEnergy", listView));
    // Update log every 5 seconds.
    systemLog.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });
    systemLog.setVersioned(false);
    add(systemLog);

    // End messages section

    String url = "";

    url = generateURL(ChartDisplayType.CONSUMPTION_DAY);
    consumptionDayGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(consumptionDayGraph);

    url = generateURL(ChartDisplayType.CONSUMPTION_WEEK);
    consumptionWeekGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(consumptionWeekGraph);

    url = generateURL(ChartDisplayType.CONSUMPTION_MONTH);
    consumptionMonthGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(consumptionMonthGraph);

    url = generateURL(ChartDisplayType.DEVICES_LOAD_DAY);
    dayDeviceGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(dayDeviceGraph);

    url = generateURL(ChartDisplayType.DEVICES_LOAD_WEEK);
    weekDeviceGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(weekDeviceGraph);

    url = generateURL(ChartDisplayType.DEVICES_LOAD_MONTH);
    monthDeviceGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(monthDeviceGraph);
    
    url = generateURL(ChartDisplayType.SYSTEM_LOAD_DAY);
    daySystemGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(daySystemGraph);
    
    url = generateURL(ChartDisplayType.SYSTEM_LOAD_WEEK);
    weekSystemGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(weekSystemGraph);
    
    url = generateURL(ChartDisplayType.SYSTEM_LOAD_MONTH);
    monthSystemGraph.add(new AttributeModifier(SRC, true, new Model<String>(url)));
    add(monthSystemGraph);
  } // End Constructor

  /**
   * Generates the URL for the graphs.
   * @param display - The graph to generate URL for.
   * @return - The URL
   */
  private String generateURL(ChartDisplayType display) {
    chartData.populateChartDataMap(display);
    chartData.populateFromDataMap();
    
    String[] labels = chartData.getLabelString();
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < labels.length; i++) {
      if (i == labels.length - 1) {
        buf.append(labels[i]);
      }
      else {
        buf.append(labels[i] + "|");
      }
    }
    String labelString = buf.toString();
    double[][] values = chartData.getDataArray();
    buf = new StringBuffer();
    for (int i = 0; i < values[0].length; i++) {
      if (i == values[0].length - 1) {
        buf.append(values[0][i]);
      }
      else {
        buf.append(values[0][i] + ",");
      }
    }
    String valueString = buf.toString();
    buf = new StringBuffer();
    for (int i = 0; i < values[0].length; i++) {
      if (i == values[0].length - 1) {
        buf.append(values[0][i]);
      }
      else {
        buf.append(values[0][i] + "|");
      }
    }
    String valueLabels = buf.toString();
    String colors = "";
    String title = "";
    switch (display) {
    case CONSUMPTION_DAY:
      title = "Consumption Covered Past Day";
      colors = "00AF00|FF0000";
      break;
    case CONSUMPTION_WEEK:
      title = "Consumption Covered Past Week";
      colors = "00AF00|FF0000";
      break;
    case CONSUMPTION_MONTH:
      title = "Consumption Covered Past Month";
      colors = "00AF00|FF0000";
      break;
    case DEVICES_LOAD_DAY:
      title = "Device Consumption Past Day";
      colors = "0000FF|000000";
      break;
    case DEVICES_LOAD_WEEK:
      title = "Device Consumption Past Week";
      colors = "0000FF|000000";
      break;
    case DEVICES_LOAD_MONTH:
      title = "Device Consumption Past Month";
      colors = "0000FF|000000";
      break;
    case SYSTEM_LOAD_DAY:
      title = "System Consumption Past Day";
      colors = "0000FF|000000";
      break;
    case SYSTEM_LOAD_WEEK:
      title = "System Consumption Past Week";
      colors = "0000FF|000000";
      break;
    case SYSTEM_LOAD_MONTH:
      title = "System Consumption Past Month";
      colors = "0000FF|000000";
      break;
    default:
      colors = "0000FF|000000";
    }
    return "http://chart.apis.google.com/chart" 
        + "?chs=500x300" + "&cht=p" 
        + "&chco=" + colors
        + "&chd=t:" + valueString 
        + "&chdl=" + labelString 
        + "&chl=" + valueLabels
        + "&chtt=" + title;
  }
}
