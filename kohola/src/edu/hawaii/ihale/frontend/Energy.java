package edu.hawaii.ihale.frontend;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.SystemStateEntry;
//import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;

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

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  // String constants to replace string literals that gave PMD errors
  private static final String SRC = "src";
  private static final String ELECTRICAL_CONSUMPTION = "electrical";
  private static final String EGAUGE_1 = "eGauge-1";
  private static final String EGAUGE_2 = "eGauge-2";
  private static final String PHOTOVOLTAICS = "photovoltaics";
  private static final String POWER = "Power";
  private static final String C_VALUES = "cValues: ";
  private static final String G_VALUES = "gValues: ";

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer dayGraph = new WebMarkupContainer("DayGraph");
  WebMarkupContainer weekGraph = new WebMarkupContainer("WeekGraph");
  WebMarkupContainer monthGraph = new WebMarkupContainer("MonthGraph");

  /**
   * Buttons for all graphs.
   */
  Link<String> dayConsumptionGraph;

  /**
   * Links to other pages.
   */
  Link<String> hvacSetting;
  Link<String> aquaponicsSetting;
  Link<String> lightingSetting;

  Label currentConsumption = new Label("CurrentConsumption", "0");
  Label currentGeneration = new Label("CurrentGeneration", "0");

  /**
   * The layout for energy page.
   * 
   * @throws Exception The exception.
   */
  public Energy() throws Exception {

    // Add listeners to the system. Listeners are the way the UI learns that new state has
    // been received from some system in the house.
    // db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
    // .getPhotovoltaicListener());
    //
    // new BlackMagic(db);

    // Create button
    // Kept this button in case later on need other buttons
    dayConsumptionGraph = new Link<String>("dayConsumptionGraph") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        try {
          setResponsePage(new Energy());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    add(dayConsumptionGraph);
    setDayGraph(dayGraph);
    add(dayGraph);
    setWeekGraph(weekGraph);
    add(weekGraph);
    setMonthGraph(monthGraph);
    add(monthGraph);

    // enables recognition of html code within the string
    currentConsumption.setEscapeModelStrings(false);
    currentGeneration.setEscapeModelStrings(false);

    setCurrentPower(currentConsumption, SolarDecathlonApplication.getElectrical().getPower());
    setCurrentPower(currentGeneration, SolarDecathlonApplication.getPhotovoltaic().getPower());
    add(currentConsumption);
    add(currentGeneration);

    hvacSetting = new Link<String>("HvacSetting") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        try {
          setResponsePage(new Temperature());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    aquaponicsSetting = new Link<String>("AquaponicsSetting") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        try {
          setResponsePage(new AquaPonics());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    lightingSetting = new Link<String>("LightingSetting") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        try {
          setResponsePage(new Lighting());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    add(hvacSetting);
    add(aquaponicsSetting);
    add(lightingSetting);

  } // End Constructor

  /**
   * Set the label for remaining power on this page.
   * 
   * @param current Label to be set.
   * @param power value to be set too.
   */
  public static void setCurrentPower(Label current, long power) {
    String fontOpenTag;
    String fontCloseTag;
    if (power < 1000) {
      fontOpenTag = "<font color=\"red\">";
      fontCloseTag = "</font>";
    }
    else if (power >= 1000 && power <= 1200) {
      fontOpenTag = "<font color=\"#FF9900\">";
      fontCloseTag = "</font>";
    }
    else {
      fontOpenTag = "<font color=\"green\">";
      fontCloseTag = "</font>";
    }

    String labelValue = fontOpenTag + power + " kWh" + fontCloseTag;
    current.setDefaultModelObject(labelValue);

  }

  /**
   * Set the daily graph for production vs consumption. The points on the graph are averages from 1
   * hour periods. So from 24 hours ago to 23 hours ago is one period, 23 hours ago to 22 hours ago
   * is another period, etc, with the current hour being its own period.
   * 
   * In order to reget points for the graphs have to click on dashboard link in tabs to refresh
   * page.
   * 
   * @param wmc The container.
   */
  private void setDayGraph(WebMarkupContainer wmc) {
    DecimalFormat df = new DecimalFormat("#.##");
    String yAxis = "50.0";
    // Google charts yAxis is always from 0-100 even if y-axis is different
    // so have to create conversion for values determined later on.
    double divisor = Double.valueOf(df.format(Double.valueOf(yAxis) / 100.0));
    long usage = 0;
    Calendar current = Calendar.getInstance();
    int currentHour = current.get(Calendar.HOUR_OF_DAY);

    // Sets x-axis
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 11; i++) {
      if ((currentHour + i * 2) % 12 == 0) {
        xBuf.append(12 + "|"); // NOPMD
      }
      else {
        xBuf.append(((currentHour + i * 2) % 12) + "|"); // NOPMDs
      }
    }
    xBuf.append((currentHour % 12));
    xAxis = xBuf.toString();
    long lastTwentyFour = 24 * 60 * 60 * 1000L;
    long time = (new Date()).getTime();
    List<SystemStateEntry> consumptionList = null, generationList = null;
    // Gets all entries for photovoltaics and consumption in the last 24 hours.
    try {
      consumptionList =
          SolarDecathlonApplication.db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2,
              (time - lastTwentyFour), time);
      generationList =
          SolarDecathlonApplication.db.getEntries(PHOTOVOLTAICS, EGAUGE_1,
              (time - lastTwentyFour), time);
    }
    catch (SystemStateEntryDBException e) {
      System.out.println("Creating a list of entries in day dashboard.");
    }
    // milliseconds since beginning of hour
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
    // x-axis is every two hours so get entries power average over 2 hour periods
    // Ex. Entries from 2-4 is averaged into hour 4, 3-5 averaged into hour 5
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
        cValue = (long) ((cValue / (double) cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double) gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); // NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); // NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); // NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); // NOPMD
        cBuf.append(cValue + ","); // NOPMD
        gBuf.append(gValue + ","); // NOPMD
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
   * Sets the weekly graph for production vs consumption. The points on the graph are averages from
   * 1 day periods. So from 7 days ago to 6 days ago is one period, 6 days ago to 5 days ago is
   * another period, etc, with the current day being its own period.
   * 
   * @param wmc The container.
   */
  private void setWeekGraph(WebMarkupContainer wmc) {
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

    // x-axis is the past 7 days starting from current day.
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 5; i++) {
      xBuf.append(daysOfWeek[(currentDay + i + 1) % 7] + "|"); // NOPMD
    }
    xBuf.append(daysOfWeek[currentDay % 7]);
    xAxis = xBuf.toString();
    long time = (new Date()).getTime();
    List<SystemStateEntry> consumptionList = null, generationList = null;
    try {
      consumptionList =
          SolarDecathlonApplication.db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2,
              (time - mWeek), time);
      generationList =
          SolarDecathlonApplication.db.getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - mWeek), time);
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
        cValue = (long) ((cValue / (double) cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double) gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); // NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); // NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); // NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); // NOPMD
        cBuf.append(cValue + ","); // NOPMD
        gBuf.append(gValue + ","); // NOPMD
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
   * Sets the monthly graph for production vs consumption. The points on the graph are averages from
   * 5 day periods. So from 30 days ago to 25 days ago is one period, 25 days ago to 20 days ago is
   * another period, etc, with the current day being its own period.
   * 
   * @param wmc The container.
   */
  private void setMonthGraph(WebMarkupContainer wmc) {
    DecimalFormat df = new DecimalFormat("#.##");
    String yAxis = "100.0";
    double divisor = Double.valueOf(df.format(Double.valueOf(yAxis) / 100.0));
    long usage = 0;
    long mInADay = 24 * 3600000;
    Calendar current = Calendar.getInstance();
    int currentDay = current.get(Calendar.DAY_OF_MONTH);
    int currentMonth = current.get(Calendar.MONTH);
    int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    // xAxis is the past 30 days in increments of 5 days.
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 6; i >= 1; i--) {
      if ((currentDay - (i * 5)) < 0) {
        xBuf.append((months[currentMonth - 1] - (i * 5 - currentDay)) + "|"); // NOPMD
      }
      else {
        xBuf.append((currentDay - (i * 5)) + "|"); // NOPMD
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
          SolarDecathlonApplication.db.getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2,
              (time - mSinceBeginning), time);
      generationList =
          SolarDecathlonApplication.db.getEntries(PHOTOVOLTAICS, EGAUGE_1,
              (time - mSinceBeginning), time);
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
        cValue = (long) ((cValue / (double) cAverage) / divisor);
      }
      if (gAverage != 0) {
        gValue = (long) ((gValue / (double) gAverage) / divisor);
      }
      if (i == 0) {
        cPrintBuf.append((long) (cValue * divisor) + "|"); // NOPMD
        gPrintBuf.append((long) (gValue * divisor));
        cBuf.append(cValue + "|"); // NOPMD
        gBuf.append(gValue);
      }
      else {
        cPrintBuf.append((long) (cValue * divisor) + ","); // NOPMD
        gPrintBuf.append((long) (gValue * divisor) + ","); // NOPMD
        cBuf.append(cValue + ","); // NOPMD
        gBuf.append(gValue + ","); // NOPMD
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
