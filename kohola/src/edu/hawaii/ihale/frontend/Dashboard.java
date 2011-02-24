package edu.hawaii.ihale.frontend;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDBException;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Dashboard extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer dayGraph = new WebMarkupContainer("consprodD");
  WebMarkupContainer weekGraph = new WebMarkupContainer("consprodW");
  WebMarkupContainer monthGraph = new WebMarkupContainer("consprodM");
  WebMarkupContainer yearGraph = new WebMarkupContainer("consprodY");

  //conversion for kWh to $ (arbitrary right now)
  private static final double conversion = .2413;

  private static final String DATE_FORMAT = "hh:mm:ss a";

  //String constants to replace string literals that gave PMD errors
  private static final String SRC = "src";
  private static final String ELECTRICAL_CONSUMPTION = "ElectricalConsumption";
  private static final String EGAUGE_1 = "eGauge-1";
  private static final String EGAUGE_2 = "eGauge-2";
  private static final String PHOTOVOLTAICS = "Photovoltaics";
  private static final String POWER = "power";
  private static final String C_VALUES = "cValues: ";
  private static final String G_VALUES = "gValues: ";

  private static final String classTagName = "class";

  static Label insideTemperature = new Label("InsideTemperature", "");
  static Label outsideTemperature = new Label("OutsideTemperature", "");
  static Label dayUsage = new Label("DayUsage", "");
  static Label dayUsage2 = new Label("DayUsage2", "");
  static Label weekUsage = new Label("WeekUsage", "");
  static Label weekUsage2 = new Label("WeekUsage2", "");
  static Label monthUsage = new Label("MonthUsage", "");
  static Label monthUsage2 = new Label("MonthUsage2", "");
  static Label dayPriceConverter = new Label("DayPriceConverter", "");
  static Label weekPriceConverter = new Label("WeekPriceConverter", "");
  static Label monthPriceConverter = new Label("MonthPriceConverter", "");

  /**
   * The page layout.
   */
  public Dashboard() {

    // Temporary Images
    add(new Image("weather", new ResourceReference(Header.class, "images/weather.jpg")));
    add(new Image("graph", new ResourceReference(Header.class, "images/graph.jpg")));

//    SystemStateEntryDB db = null;
//    try {
//      db = ((SolarDecathlonApplication) SolarDecathlonApplication.get()).getDB();
//      //Add both photovoltaics and consumption listeners for graphs.
//      db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
//          .getPhotovoltaicListener());
//      db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
//          .getConsumptionListener());
//      new BlackMagic(db);

      WebMarkupContainerWithAssociatedMarkup dayDiv =
          new WebMarkupContainerWithAssociatedMarkup("DayDiv");

      WebMarkupContainerWithAssociatedMarkup weekDiv =
          new WebMarkupContainerWithAssociatedMarkup("WeekDiv");

      WebMarkupContainerWithAssociatedMarkup monthDiv =
          new WebMarkupContainerWithAssociatedMarkup("MonthDiv");

      dayDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, "right medium-large dark-purple");
        }
      });

      weekDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, "right medium-large dark-purple");
        }
      });

      monthDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, "right medium-large dark-purple");
        }
      });
      //need separate labels for dayUsage, weekUsage, and monthUsage as they are already
      //added into a div, different hierarchy.
      add(dayUsage2);
      add(weekUsage2);
      add(monthUsage2);
      add(dayPriceConverter);
      add(weekPriceConverter);
      add(monthPriceConverter);
      dayDiv.add(dayUsage);
      weekDiv.add(weekUsage);
      monthDiv.add(monthUsage);
      add(dayDiv);
      add(weekDiv);
      add(monthDiv);
      setDayGraph(dayGraph, db);
      add(dayGraph);
      setWeekGraph(weekGraph, db);
      add(weekGraph);
      setMonthGraph(monthGraph, db);
      add(monthGraph);
//    }
//    catch (InstantiationException e1) {
//      e1.printStackTrace();
//    }
//    catch (IllegalAccessException e1) {
//      e1.printStackTrace();
//    }
//    catch (ClassNotFoundException e1) {
//      e1.printStackTrace();
//    }
//    catch (Exception e1) {
//      e1.printStackTrace();
//    }

    //inside and outside temperatures are retrieved from Header.java
    String insideTemp = (String) insideTemperatureHeader.getDefaultModelObject();
    insideTemperature.setDefaultModelObject(insideTemp);

    String outsideTemp = (String) outsideTemperatureHeader.getDefaultModelObject();
    outsideTemperature.setDefaultModelObject(outsideTemp);


    insideTemperature.setEscapeModelStrings(false);

    outsideTemperature.setEscapeModelStrings(false);

    add(insideTemperature);
    add(outsideTemperature);

    //updates the ajax clock on the dashboard every second
    Calendar cal = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    String currentTime = dateFormat.format(cal.getTime());

    final Label time = new Label("Time", currentTime);
    time.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onPostProcessTarget(AjaxRequestTarget target) {
        Calendar newCal = Calendar.getInstance();
        time.setDefaultModelObject(String.valueOf(dateFormat.format(newCal.getTime())));

      }
    });

    add(time);

  }

  /**
   * Set the daily graph for production vs consumption.  The points on the graph are averages
   * from 1 hour periods.  So from 24 hours ago to 23 hours ago is one period, 23 hours ago to
   * 22 hours ago is another period, etc, with the current hour being its own period.
   * 
   * In order to reget points for the graphs have to click on dashboard link in tabs to refresh
   * page.
   * 
   * @param wmc The container.
   * @param db The database instance.
   */
  private void setDayGraph(WebMarkupContainer wmc, SystemStateEntryDB db) {
    DecimalFormat df = new DecimalFormat("#.##");
    String yAxis = "50.0";
    //Google charts yAxis is always from 0-100 even if y-axis is different
    //so have to create conversion for values determined later on.
    double divisor = Double.valueOf(df.format(Double.valueOf(yAxis) / 100.0));
    long usage = 0;
    Calendar current = Calendar.getInstance();
    int currentHour = current.get(Calendar.HOUR_OF_DAY);
    
    //Sets x-axis
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 11; i++) {
      if ((currentHour + i * 2) % 12 == 0) {
        xBuf.append(12 + "|"); //NOPMD
      }
      else {
        xBuf.append(((currentHour + i * 2) % 12) + "|"); //NOPMDs
      }
    }
    xBuf.append((currentHour % 12));
    xAxis = xBuf.toString();
    long lastTwentyFour = 24 * 60 * 60 * 1000L;
    long time = (new Date()).getTime();
    List<SystemStateEntry> consumptionList = null, generationList = null;
    //Gets all entries for photovoltaics and consumption in the last 24 hours.
    try {
      consumptionList =
          db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - lastTwentyFour), time);
      generationList = db.getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - lastTwentyFour), time);
    }
    catch (SystemStateEntryDBException e) {
      System.out.println("Creating a list of entries in day dashboard.");
    }
    //milliseconds since beginning of hour
    long mHourBegin =
        current.get(Calendar.MINUTE) * 60000 + current.get(Calendar.SECOND) * 1000
            + current.get(Calendar.MILLISECOND);
    long twoHours = 2 * 60 * 60 * 1000L;
    long cValue = 0, gValue = 0;
    String cValues = "", gValues = "";
    String printC = "", printG = "";
    int cAverage = 0, gAverage = 0;
    StringBuffer cBuf = new StringBuffer();
    StringBuffer gBuf = new StringBuffer();
    StringBuffer cPrintBuf = new StringBuffer();
    StringBuffer gPrintBuf = new StringBuffer();
    //x-axis is every two hours so get entries power average over 2 hour periods
    //Ex. Entries from 2-4 is averaged into hour 4, 3-5 averaged into hour 5
    for (int i = 12; i >= 0; i--) {
      for (int j = 0; j < consumptionList.size(); j++) {

        if ((consumptionList.get(j).getTimestamp() < ((time - mHourBegin) - (twoHours * (i - 1))))
            && (consumptionList.get(j).getTimestamp() > ((time - mHourBegin) - (twoHours * i)))) {
          cValue += consumptionList.get(j).getLongValue(POWER);
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mHourBegin) - (twoHours * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mHourBegin) - (twoHours * i)) {
          gValue += generationList.get(j).getLongValue(POWER);
          gAverage++;
        }

      }
      if (cAverage != 0) {
        usage += cValue;
        cValue = (long) ((cValue / (double)cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double)gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); //NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); //NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); //NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); //NOPMD
        cBuf.append(cValue + ","); //NOPMD
        gBuf.append(gValue + ","); //NOPMD
      }
      cValue = 0;
      gValue = 0;
      cAverage = 0;
      gAverage = 0;
    }
    cValues = cBuf.toString();
    gValues = gBuf.toString();
    printC = cPrintBuf.toString();
    printG = gPrintBuf.toString();
    dayUsage.setDefaultModelObject(usage + " kWh/day");
    dayUsage2.setDefaultModelObject(dayUsage.getDefaultModelObject());
    dayPriceConverter.setDefaultModelObject("$" + df.format(usage * conversion) + "/day");
    System.out.println("Dashboard Day Graph:\n\tcValues: " + printC + "\n\t" + "gValues: "
        + printG);
    String url =
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + yAxis
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    wmc.add(new AttributeModifier(SRC, true, new Model<String>(url)));
  }

  /**
   * Sets the weekly graph for production vs consumption.  The points on the graph are averages
   * from 1 day periods.  So from 7 days ago to 6 days ago is one period, 6 days ago to 5 days
   * ago is another period, etc, with the current day being its own period.
   * 
   * @param wmc The container.
   * @param db The database instance.
   */
  private void setWeekGraph(WebMarkupContainer wmc, SystemStateEntryDB db) {
    DecimalFormat df = new DecimalFormat("#.##");
    String yAxis = "100.0";
    double divisor = Double.valueOf(df.format(Double.valueOf(yAxis) / 100.0));
    long usage = 0;
    long mInADay = 24 * 3600000;
    Calendar current = Calendar.getInstance();
    int currentDay = current.get(Calendar.DAY_OF_WEEK);
    String[] daysOfWeek = { "Sat", "Sun", "Mon", "Tues", "Wed", "Thurs", "Fri" };
    long mWeek =
        (6 * mInADay) + current.get(Calendar.HOUR_OF_DAY) * 3600000L
            + current.get(Calendar.MINUTE) * 60000L + current.get(Calendar.SECOND) * 1000L
            + current.get(Calendar.MILLISECOND);
    long mSinceBeginning =
        current.get(Calendar.HOUR_OF_DAY) * 3600000L + current.get(Calendar.MINUTE) * 60000L
            + current.get(Calendar.SECOND) * 1000L + current.get(Calendar.MILLISECOND);
    
    //x-axis is the past 7 days starting from current day.
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 5; i++) {
      xBuf.append(daysOfWeek[(currentDay + i + 1) % 7] + "|"); //NOPMD
    }
    xBuf.append(daysOfWeek[currentDay]);
    xAxis = xBuf.toString();
    long time = (new Date()).getTime();
    List<SystemStateEntry> consumptionList = null, generationList = null;
    try {
      consumptionList = db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - mWeek), time);
      generationList = db.getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - mWeek), time);
    }
    catch (SystemStateEntryDBException e) {
      System.out.println("Creating a list of entries in week dashboard.");
    }

    long cValue = 0, gValue = 0;
    String cValues = "", gValues = "", printC = "", printG = "";
    int cAverage = 0, gAverage = 0;
    StringBuffer cBuf = new StringBuffer();
    StringBuffer gBuf = new StringBuffer();
    StringBuffer cPrintBuf = new StringBuffer();
    StringBuffer gPrintBuf = new StringBuffer();
    for (int i = 6; i >= 0; i--) {
      for (int j = 0; j < consumptionList.size(); j++) {

        if ((consumptionList.get(j).getTimestamp() < 
            ((time - mSinceBeginning) - (mInADay * (i - 1))))
            && (consumptionList.get(j).getTimestamp() > 
            ((time - mSinceBeginning) - (mInADay * i)))) {
          cValue += consumptionList.get(j).getLongValue(POWER);
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mSinceBeginning) - (mInADay * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mSinceBeginning) - (mInADay * i)) {
          gValue += generationList.get(j).getLongValue(POWER);
          gAverage++;
        }

      }
      if (cAverage != 0) {
        usage += cValue;
        cValue = (long) ((cValue / (double)cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double)gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); //NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); //NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); //NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); //NOPMD
        cBuf.append(cValue + ","); //NOPMD
        gBuf.append(gValue + ","); //NOPMD
      }
      cValue = 0;
      gValue = 0;
      cAverage = 0;
      gAverage = 0;
    }
    cValues = cBuf.toString();
    gValues = gBuf.toString();
    printC = cPrintBuf.toString();
    printG = gPrintBuf.toString();
    weekUsage.setDefaultModelObject(usage + " kWh/week");
    weekUsage2.setDefaultModelObject(weekUsage.getDefaultModelObject());
    weekPriceConverter.setDefaultModelObject("$" + df.format(usage * conversion) + "/week");
    System.out.println("Dashboard Week Graph:\n\tcValues: " + printC + "\n" + "\tgValues: "
        + printG);
    String url =
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + yAxis
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    wmc.add(new AttributeModifier(SRC, true, new Model<String>(url)));
  }

  /**
   * Sets the monthly graph for production vs consumption.  The points on the graph are averages
   * from 5 day periods.  So from 30 days ago to 25 days ago is one period, 25 days ago to 20 days
   * ago is another period, etc, with the current day being its own period.
   * 
   * @param wmc The container.
   * @param db The database instance.
   */
  private void setMonthGraph(WebMarkupContainer wmc, SystemStateEntryDB db) {
    DecimalFormat df = new DecimalFormat("#.##");
    String yAxis = "100.0";
    double divisor = Double.valueOf(df.format(Double.valueOf(yAxis) / 100.0));
    long usage = 0;
    long mInADay = 24 * 3600000;
    Calendar current = Calendar.getInstance();
    int currentDay = current.get(Calendar.DAY_OF_MONTH);
    int currentMonth = current.get(Calendar.MONTH);
    int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    
    //xAxis is the past 30 days in increments of 5 days.
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 6; i >= 1; i--) {
      if ((currentDay - (i * 5)) < 0) {
        xBuf.append((months[currentMonth - 1] - (i * 5 - currentDay)) + "|"); //NOPMD
      }
      else {
        xBuf.append((currentDay - (i * 5)) + "|"); //NOPMD
      }
    }
    xBuf.append(currentDay);
    xAxis = xBuf.toString();
    long time = (new Date()).getTime();
    List<SystemStateEntry> consumptionList = null, generationList = null;
    long mSinceBeginning =
        ((current.get(Calendar.DAY_OF_MONTH) - 1) * mInADay) + current.get(Calendar.HOUR_OF_DAY)
            * 3600000L + current.get(Calendar.MINUTE) * 60000L + current.get(Calendar.SECOND)
            * 1000L + current.get(Calendar.MILLISECOND);
    try {
      consumptionList =
          db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - mSinceBeginning), time);
      generationList = db.getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - mSinceBeginning), time);
    }
    catch (SystemStateEntryDBException e) {
      System.out.println("Creating a list of entries in month dashboard.");
    }

    long cValue = 0, gValue = 0;
    String cValues = "", gValues = "", printC = "", printG = "";
    int cAverage = 0, gAverage = 0;
    long mFive = 5 * 24 * 60 * 60 * 1000L;
    long mToday =
        current.get(Calendar.HOUR_OF_DAY) * 3600000L + current.get(Calendar.MINUTE) * 60000L
            + current.get(Calendar.SECOND) * 1000L + current.get(Calendar.MILLISECOND);
    StringBuffer cBuf = new StringBuffer();
    StringBuffer gBuf = new StringBuffer();
    StringBuffer cPrintBuf = new StringBuffer();
    StringBuffer gPrintBuf = new StringBuffer();
    for (int i = 6; i >= 0; i--) {
      for (int j = 0; j < consumptionList.size(); j++) {

        if ((consumptionList.get(j).getTimestamp() < ((time - mToday) - (mFive * (i - 1))))
            && (consumptionList.get(j).getTimestamp() > ((time - mToday) - (mFive * i)))) {
          cValue += consumptionList.get(j).getLongValue(POWER);
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mToday) - (mFive * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mToday) - (mFive * i)) {
          gValue += generationList.get(j).getLongValue(POWER);
          gAverage++;
        }

      }
      if (cAverage != 0) {
        usage += cValue;
        cValue = (long) ((cValue / (double)cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double)gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); //NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); //NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); //NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); //NOPMD
        cBuf.append(cValue + ","); //NOPMD
        gBuf.append(gValue + ","); //NOPMD
      }
      cValue = 0;
      gValue = 0;
      cAverage = 0;
      gAverage = 0;
    }
    cValues = cBuf.toString();
    gValues = gBuf.toString();
    printC = cPrintBuf.toString();
    printG = gPrintBuf.toString();
    monthUsage.setDefaultModelObject(usage + " kWh/month");
    monthUsage2.setDefaultModelObject(monthUsage.getDefaultModelObject());
    monthPriceConverter.setDefaultModelObject("$" + df.format(usage * conversion) + "/month");
    System.out.println("Dashboard Month Graph: \n\t" + C_VALUES + printC + "\n\t" + G_VALUES
        + printG);
    String url =
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + yAxis
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    wmc.add(new AttributeModifier(SRC, true, new Model<String>(url)));
  }
}