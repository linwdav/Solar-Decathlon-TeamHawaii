package edu.hawaii.ihale.frontend.page.dashboard;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
//import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.frontend.page.Header;
import edu.hawaii.ihale.frontend.weatherparser.WeatherForecast;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
//import edu.hawaii.ihale.api.SystemStateEntry;
//import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
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

  // conversion for kWh to $ (arbitrary right now)
  private static final double conversion = .2413;

  private static final String DATE_FORMAT = "hh:mm:ss a";
    
  // String constants to replace string literals that gave PMD errors
  private static final String SRC = "src";
//  private static final String ELECTRICAL_CONSUMPTION = "electrical";
//  private static final String EGAUGE_1 = "egauge-1";
//  private static final String EGAUGE_2 = "egauge-2";
//  private static final String PHOTOVOLTAICS = "photovoltaics";
//  private static final String ENERGY = "energy";
  private static final String C_VALUES = "cValues: ";
  private static final String G_VALUES = "gValues: ";
  private static final String Y_AXIS = "8000.0";
  // private static final String negativeYAxis = "-500";

  private static final String classTagName = "class";

  static Label dayUsage = new Label("DayUsage", "");
  static Label dayUsage2 = new Label("DayUsage2", "");
  static Label weekUsage = new Label("WeekUsage", "");
  static Label weekUsage2 = new Label("WeekUsage2", "");
  static Label monthUsage = new Label("MonthUsage", "");
  static Label monthUsage2 = new Label("MonthUsage2", "");
  static Label dayPriceConverter = new Label("DayPriceConverter", "");
  static Label weekPriceConverter = new Label("WeekPriceConverter", "");
  static Label monthPriceConverter = new Label("MonthPriceConverter", "");

  private List<WeatherForecast> weathers;
  
  private static final List<String> cities = Arrays.asList(new String[] {"Honolulu", "Washington"});
  private String selectedCity;
 
  /**
   * The page layout.
   */
  public Dashboard() {
    
    ((SolarDecathlonSession)getSession()).getHeaderSession().setActiveTab(0);
    
    selectedCity = this.getCityName();
    
    // support city selection for weather parsing
    DropDownChoice<String> cityChoice = new DropDownChoice<String>("Cities", 
        new PropertyModel<String>(this, "selectedCity"), cities) {

          /**
           * 
           */
          private static final long serialVersionUID = 1L;
          
          @Override
          protected boolean wantOnSelectionChangedNotifications() {
            return true;
          }
          
          @Override
          protected void onSelectionChanged(String newSelection) {
            
            setCityName(newSelection);
            if ("Honolulu".equals(newSelection)) {
              setTimeZone("US/Hawaii");
            }
            else if ("Washington".equals(newSelection)) {
              setTimeZone("US/Washington");
            }            
            selectedCity = getCityName();
            setResponsePage(Dashboard.class);            
          }
      
    };
    add(cityChoice);

    // create label and model for the current weather condition.
    Model<String> currentWeatherConditionModel = new Model<String>() {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * Override the getObject for dynamic programming and retrieve the
       * current weather condition.
       */
      @Override
      public String getObject() {      
        return currentWeather.getCondition() + "<br />" +  
          currentWeather.getWindCondition() + "<br />" + 
          currentWeather.getHumidity();
      }
    };    
    Label currentWeatherConditionLabel = 
      new Label("CurrentWeatherCondition", currentWeatherConditionModel);
    currentWeatherConditionLabel.setEscapeModelStrings(false);
    add(currentWeatherConditionLabel);

    // below are weather forecast images in localization div.
    // weather at this moment, the largest image.
    Image currentWeatherImage = new Image("CurrentWeatherImage");
    currentWeatherImage.add(new AttributeModifier(SRC, true, new Model<String>
    (currentWeather.getImageURL())));
    currentWeatherImage.setOutputMarkupId(true);
    add(currentWeatherImage);
    
    // list of weather conditions for today and the next three days
    weathers = weatherParser.getWeatherForecast();
    
    // today's weather, which is 1st image below the current weather image
    Image weatherForecastImage1 = new Image("WeatherForecastImage1");
    weatherForecastImage1.add(new AttributeModifier(SRC, true, new Model<String>
    (weathers.get(0).getImageURL())));
    weatherForecastImage1.setOutputMarkupId(true);
    add(weatherForecastImage1);
    
    // tomorrow's weather, which is to the left of today's weather image
    Image weatherForecastImage2 = new Image("WeatherForecastImage2");
    weatherForecastImage2.add(new AttributeModifier(SRC, true, new Model<String>
    (weathers.get(1).getImageURL())));
    weatherForecastImage2.setOutputMarkupId(true);
    add(weatherForecastImage2);
    
    // The day after tomorrow's weather image
    Image weatherForecastImage3 = new Image("WeatherForecastImage3");
    weatherForecastImage3.add(new AttributeModifier(SRC, true, new Model<String>
    (weathers.get(2).getImageURL())));
    weatherForecastImage3.setOutputMarkupId(true);
    add(weatherForecastImage3);
    
    // The last weather image
    Image weatherForecastImage4 = new Image("WeatherForecastImage4");
    weatherForecastImage4.add(new AttributeModifier(SRC, true, new Model<String>
    (weathers.get(3).getImageURL())));
    weatherForecastImage4.setOutputMarkupId(true);
    add(weatherForecastImage4);
    
    // create label and model for today's weather image.
    Model<String> weatherForecastDayModel1 = new Model<String>() {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * Override the getObject for dynamic programming and retrieve today's weather.
       */
      @Override
      public String getObject() {      
        return weathers.get(0).getDayOfWeek();
      }
    };    
    Label weatherForecastDayLabel1 = 
      new Label("weatherForecastDayLabel1", weatherForecastDayModel1);
    
    add(weatherForecastDayLabel1);
    
    //  create label and model for tomorrow's weather image.
    Model<String> weatherForecastDayModel2 = new Model<String>() {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * Override the getObject for dynamic programming and retrieve tomorrow's  
       * weather.
       */
      @Override
      public String getObject() {      
        return weathers.get(1).getDayOfWeek();
      }
    };    
    Label weatherForecastDayLabel2 = 
      new Label("weatherForecastDayLabel2", weatherForecastDayModel2);
    
    add(weatherForecastDayLabel2);

    // create label and model for the third weather image.
    Model<String> weatherForecastDayModel3 = new Model<String>() {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * Override the getObject for dynamic programming and retrieve the 
       * third weather forecast.
       */
      @Override
      public String getObject() {      
        return weathers.get(2).getDayOfWeek();
      }
    };    
    Label weatherForecastDayLabel3 = 
      new Label("weatherForecastDayLabel3", weatherForecastDayModel3);
    
    add(weatherForecastDayLabel3);
    
    // create label and model for today's weather image.
    Model<String> weatherForecastDayModel4 = new Model<String>() {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * Override the getObject for dynamic programming and retrieve the
       * fourth weather forecast.
       */
      @Override
      public String getObject() {      
        return weathers.get(3).getDayOfWeek();
      }
    };    
    Label weatherForecastDayLabel4 = 
      new Label("weatherForecastDayLabel4", weatherForecastDayModel4);
    
    add(weatherForecastDayLabel4);
        
    // Divs for graphs
    WebMarkupContainerWithAssociatedMarkup dayDiv =
        new WebMarkupContainerWithAssociatedMarkup("DayDiv");

    WebMarkupContainerWithAssociatedMarkup weekDiv =
        new WebMarkupContainerWithAssociatedMarkup("WeekDiv");

    WebMarkupContainerWithAssociatedMarkup monthDiv =
        new WebMarkupContainerWithAssociatedMarkup("MonthDiv");

    dayDiv.add(new AbstractBehavior() {

      /** Support serialization. */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "right medium-large dark-purple");
      }
    });

    weekDiv.add(new AbstractBehavior() {

      /** Support serialization. */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "right medium-large dark-purple");
      }
    });

    monthDiv.add(new AbstractBehavior() {

      /** Support serialization. */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "right medium-large dark-purple");
      }
    });

    Model<String> day = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        String url;

        url = setDayGraph();

        return url;
      }
    };
    Label dayLabel = new Label("dayLabel", day);
    dayGraph.add(new AttributeModifier(SRC, true, new Model<String>(dayLabel
        .getDefaultModelObjectAsString())));
    add(dayGraph);

    Model<String> week = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        String url;

        url = setWeekGraph();

        return url;
      }
    };
    Label weekLabel = new Label("weekLabel", week);
    weekGraph.add(new AttributeModifier(SRC, true, new Model<String>(weekLabel
        .getDefaultModelObjectAsString())));
    add(weekGraph);

    Model<String> month = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        String url;

        url = setMonthGraph();

        return url;
      }
    };
    Label monthLabel = new Label("monthLabel", month);
    monthGraph.add(new AttributeModifier(SRC, true, new Model<String>(monthLabel
        .getDefaultModelObjectAsString())));
    add(monthGraph);

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

    // inside and outside temperatures are retrieved from Header.java
    Model<String> insideModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getHvac().getTemp());
      }
    };
    Label insideTemperature = new Label("InsideTemperature", insideModel);

    Model<String> outsideModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(currentWeather.getTempF());
      }
    };
    Label outsideTemperature = new Label("OutsideTemperature", outsideModel);
    
    insideTemperature.setEscapeModelStrings(false);
    outsideTemperature.setEscapeModelStrings(false);

    add(cityChoice);
    add(insideTemperature);
    add(outsideTemperature);

    // updates the ajax clock on the dashboard every second
    Calendar cal = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
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
   * Set the daily graph for production vs consumption. The points on the graph are averages from 1
   * hour periods. So from 24 hours ago to 23 hours ago is one period, 23 hours ago to 22 hours ago
   * is another period, etc, with the current hour being its own period.
   * 
   * In order to reget points for the graphs have to click on dashboard link in tabs to refresh
   * page.
   * 
   * @return The day graph url.
   */
  private String setDayGraph() {
    DecimalFormat df = new DecimalFormat("#.##");
    // Google charts Y_AXIS is always from 0-100 even if y-axis is different
    // so have to create conversion for values determined later on.
    double divisor = Double.valueOf(df.format(Double.valueOf(Y_AXIS) / 100.0));
    long usage = 0;
    Calendar current = Calendar.getInstance();
    int currentHour = current.get(Calendar.HOUR_OF_DAY);

    // Sets x-axis
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 11; i++) {
      if ((currentHour + i * 2) % 12 == 0) {
        xBuf.append(12 + "|");
      }
      else {
        xBuf.append(((currentHour + i * 2) % 12) + "|");
      }
    }
    xBuf.append((currentHour % 12));
    xAxis = xBuf.toString();
    long lastTwentyFour = 24 * 60 * 60 * 1000L;
    long time = (new Date()).getTime();
    List<TimestampIntegerPair> consumptionList = null;
    List<TimestampIntegerPair> generationList = null;
    // Gets all entries for photovoltaics and consumption in the last 24 hours.
    
    consumptionList =
        SolarDecathlonApplication.getRepository().getElectricalEnergySince(time - lastTwentyFour);
          
        //getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - lastTwentyFour), time);
    generationList =
       SolarDecathlonApplication.getRepository().getPhotovoltaicEnergySince(time - lastTwentyFour);
          
        //getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - lastTwentyFour), time);
    
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
          cValue += consumptionList.get(j).getValue();
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mHourBegin) - (twoHours * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mHourBegin) - (twoHours * i)) {
          gValue += generationList.get(j).getValue();
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
      String tempValue = "";
      if (i == 0) {
        tempValue = (long) (cValue * divisor) + "|";
        cPrintBuf.append(tempValue);
        gPrintBuf.append((long) (gValue * divisor));
        tempValue = cValue + "|";
        cBuf.append(tempValue);
        gBuf.append(gValue);
      }
      else {
        tempValue = (long) (cValue * divisor) + ",";
        cPrintBuf.append(tempValue);
        tempValue = (long) (gValue * divisor) + ",";
        gPrintBuf.append(tempValue);
        tempValue = cValue + ",";
        cBuf.append(tempValue);
        tempValue = gValue + ",";
        gBuf.append(tempValue);
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
    System.out
        .println("Dashboard Day Graph:\n\tcValues: " + printC + "\n\t" + "gValues: " + printG);
    String url =
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + Y_AXIS
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    return url;
  }

  /**
   * Sets the weekly graph for production vs consumption. The points on the graph are averages from
   * 1 day periods. So from 7 days ago to 6 days ago is one period, 6 days ago to 5 days ago is
   * another period, etc, with the current day being its own period.
   * 
   * @return The url for the week graph.
   */
  private String setWeekGraph() {
    DecimalFormat df = new DecimalFormat("#.##");
    double divisor = Double.valueOf(df.format(Double.valueOf(Y_AXIS) / 100.0));
    long usage = 0;
    long mInADay = 24 * 3600000;
    Calendar current = Calendar.getInstance();
    int currentDay = current.get(Calendar.DAY_OF_WEEK);
    String[] daysOfWeek = { "Sat", "Sun", "Mon", "Tues", "Wed", "Thurs", "Fri" };
    long mWeek =
        (6 * mInADay) + current.get(Calendar.HOUR_OF_DAY) * 3600000L + current.get(Calendar.MINUTE)
            * 60000L + current.get(Calendar.SECOND) * 1000L + current.get(Calendar.MILLISECOND);
    long mSinceBeginning =
        current.get(Calendar.HOUR_OF_DAY) * 3600000L + current.get(Calendar.MINUTE) * 60000L
            + current.get(Calendar.SECOND) * 1000L + current.get(Calendar.MILLISECOND);

    // x-axis is the past 7 days starting from current day.
    String xAxis = "";
    StringBuffer xBuf = new StringBuffer();
    for (int i = 0; i <= 5; i++) {
      xBuf.append(daysOfWeek[(currentDay + i + 1) % 7] + "|");
    }
    xBuf.append(daysOfWeek[currentDay % 7]);
    xAxis = xBuf.toString();
    long time = (new Date()).getTime();
    List<TimestampIntegerPair> consumptionList = null;
    List<TimestampIntegerPair> generationList = null;
    
    consumptionList =
        SolarDecathlonApplication.getRepository().getElectricalEnergySince(time - mWeek);
          
        //getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - mWeek), time);
    generationList =
       SolarDecathlonApplication.getRepository().getPhotovoltaicEnergySince(time - mWeek);
          
        //getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - mWeek), time);
   

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
          cValue += consumptionList.get(j).getValue();
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mSinceBeginning) - (mInADay * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mSinceBeginning) - (mInADay * i)) {
          gValue += generationList.get(j).getValue();
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
      String tempValue = "";
      if (i == 0) {
        tempValue = (long) (cValue * divisor) + "|";
        cPrintBuf.append(tempValue);
        gPrintBuf.append((long) (gValue * divisor));
        tempValue = cValue + "|";
        cBuf.append(tempValue);
        gBuf.append(gValue);
      }
      else {
        tempValue = (long) (cValue * divisor) + ",";
        cPrintBuf.append(tempValue);
        tempValue = (long) (gValue * divisor) + ",";
        gPrintBuf.append(tempValue);
        tempValue = cValue + ",";
        cBuf.append(tempValue);
        tempValue = gValue + ",";
        gBuf.append(tempValue);
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
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + Y_AXIS
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    return url;
  }

  /**
   * Sets the monthly graph for production vs consumption. The points on the graph are averages 
   * from 5 day periods. So from 30 days ago to 25 days ago is one period, 25 days ago to 20 
   * days ago is another period, etc, with the current day being its own period.
   * 
   * @return The url for the month graph
   */
  private String setMonthGraph() {
    DecimalFormat df = new DecimalFormat("#.##");
    double divisor = Double.valueOf(df.format(Double.valueOf(Y_AXIS) / 100.0));
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
      if ((currentDay - (i * 5)) <= 0) {
        xBuf.append((months[currentMonth - 1] - (i * 5 - currentDay)) + "|");
      }
      else {
        xBuf.append((currentDay - (i * 5)) + "|");
      }
    }
    xBuf.append(currentDay);
    xAxis = xBuf.toString();
    long time = (new Date()).getTime();
    List<TimestampIntegerPair> consumptionList = null;
    List<TimestampIntegerPair> generationList = null;
    long mSinceBeginning =
        ((current.get(Calendar.DAY_OF_MONTH) - 1) * mInADay) + current.get(Calendar.HOUR_OF_DAY)
            * 3600000L + current.get(Calendar.MINUTE) * 60000L + current.get(Calendar.SECOND)
            * 1000L + current.get(Calendar.MILLISECOND);
    
    consumptionList =
      SolarDecathlonApplication.getRepository().getElectricalEnergySince(time - mSinceBeginning);
          
        //getEntries(ELECTRICAL_CONSUMPTION, EGAUGE_2, (time - mSinceBeginning), time);
    generationList =
        SolarDecathlonApplication.getRepository().getPhotovoltaicEnergySince(time
            - mSinceBeginning);
          
        //getEntries(PHOTOVOLTAICS, EGAUGE_1, (time - mSinceBeginning), time);
   

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
          cValue += consumptionList.get(j).getValue();
          cAverage++;
        }

      }
      for (int j = 0; j < generationList.size(); j++) {

        if (generationList.get(j).getTimestamp() < (time - mToday) - (mFive * (i - 1))
            && generationList.get(j).getTimestamp() > (time - mToday) - (mFive * i)) {
          gValue += generationList.get(j).getValue();
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
      String tempValue = "";
      if (i == 0) {
        tempValue = (long) (cValue * divisor) + "|";
        cPrintBuf.append(tempValue);
        gPrintBuf.append((long) (gValue * divisor));
        tempValue = cValue + "|";
        cBuf.append(tempValue);
        gBuf.append(gValue);
      }
      else {
        tempValue = (long) (cValue * divisor) + ",";
        cPrintBuf.append(tempValue);
        tempValue = (long) (gValue * divisor) + ",";
        gPrintBuf.append(tempValue);
        tempValue = cValue + ",";
        cBuf.append(tempValue);
        tempValue = gValue + ",";
        gBuf.append(tempValue);
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
        "http://chart.apis.google.com/chart" + "?chxl=0:|" + xAxis + "&chxr=1,0," + Y_AXIS
            + "&chxt=x,y" + "&chs=525x350" + "&cht=lc" + "&chco=FF0000,008000" + "&chd=t:"
            + cValues + gValues + "&chdl=Energy+Consumption|Energy+Generation" + "&chdlp=t"
            + "&chg=25,50" + "&chls=0.75|2"
            + "&chm=o,008000,1,-1,8|b,3399CC44,0,1,0|d,FF0000,0,-1,8";
    return url;
  }
    
  /**
   * Returns the selected city for the drop down choice.
   * @return THe selected city.
   */
  public String getSelectedCity() {
    return selectedCity;
  }
}
