package edu.hawaii.systemh.model.behavior;

import java.util.Calendar; 
import java.util.GregorianCalendar; 
import java.util.TimeZone;

/**
 * A class which provides time-based values for solar intensity, 
 * time, and outdoor temperature.  
 * @author Greg Burgess
 *
 */
public class Simulator { 
  /** The String value of the area of interest, 
   * given by WeatherUndergound's API.**/
  private final static String location = "Washington, District of Columbia";
  /** The weather station nearest DC that we will pull data from. **/
  private final static String station = "Eckington Pl, NE, Washington";
  
  private static WeatherReport weatherReport;
  
  
  public Simulator() throws Exception {
    init();
  }
  
  public static final void init() throws Exception {
    weatherReport = new WeatherReport(location,station);
  }
  /**
   * Generate a double between -12 (AM) and 12 (PM) representing the distance from noon.
   * @return a double representing the distance from noon.
   */
  public static double getTime() {  
                                                 //DC time zone is "EDT"
                                              //Hawaii timezone is "HST"
    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("HST"));  
    double rough = calendar.get(Calendar.HOUR_OF_DAY) - 12.0;  
    int multiplier = 1;  
 
    //Create a double representing time with 
    //hour as the whole number,
    //minutes as the first two decimal places,
    //seconds as the third and fourth decimal places, and
    //milliseconds as the fifth and sixth decimal places
    //all times expressed as percentages of their max value
    double fine = calendar.get(Calendar.MINUTE) / 60.0;
    fine += calendar.get(Calendar.SECOND) / 6000.0;
    fine += calendar.get(Calendar.MILLISECOND) / 10000000.0;
    
    //essentially, were taking the distance from noon, so
    //fine should be negative in the morning (when rough
    //is negative).
    if (rough < 0) {
      multiplier = -1;
    } 
    //plug the double into a parabolic function to get a 
    //value between 0 and 100.
    return rough + fine * multiplier; 
  }
  
  /**
   * Returns the temp based on time.
   * @return double temp.
   */
  public static double getTemp() {
    return getTemp(getTime());
    }
  
  /**
   * Returns the temp based on time.
   * @param time - a double representing the time given by getTime().
   * @return a double representing the temperature in F.
   */
  public static double getTemp(double time) {
    //Using the basic formula   y = 12.5*sin((x/4)-7)
    //average temperature in DC is between 50 and 75 F, see link below...
    //http://www.wunderground.com/history/airport/KDCA/2011/10/21/MonthlyHistory.
//html?req_city=NA&req_state=NA&req_statename=NA
    double seed = time / 4.0 - 5.1; 
    return 12.5 * Math.sin(seed) + 62.5; 
  }
  
  
  public static double getSolarIntensity() {
    return getSolarIntensity(getTime());
  }
  
  /**
   * Returns a double between 0 and 100 representing the solar intensity
   * at the current hour of the day; returns 0 between the hours of 6PM
   * and 6AM.  This function is inaccurate by a small amount due to the 
   * use of doubles.
   * @return a double between 0 and 100 representing the solar intensity.
   */  
  //using the formula:
  // h = hrs of daylight/2; 
  // x = current time given by getTime();
  // y = -(100/((h/2)^2)) (x + (sunrise time-h/2)^2 + 100
  public static double getSolarIntensity(double time) { 
    try {
      weatherReport.refresh();
    }
    catch (Exception e) {
      e.printStackTrace();
    } 
    //make sure its daytime or return 0
    if (time >= weatherReport.sunSet || time * -1.0 >= weatherReport.sunRise * -1.0) {
      return 0;
    }
    double hoursOfDaylight = distanceBetween(weatherReport.sunSet,weatherReport.sunRise);
    double halfHOD = hoursOfDaylight/2.0; 
    //affects the zeroes of the sin function, as well as the max height of 100
    double multiplier = (-100.0 / Math.pow(halfHOD,2.0));
    //crazy maths...
    double sun = Math.pow((time + subtractTimes(weatherReport.sunRise,-halfHOD)),2.0); 
    sun = sun * multiplier + 100.0;
    //definately dont return negatives...
    if (sun < 0) {
      return 0;
    }
    //return the sunshine but correct for cloud cover...
    return sun * weatherReport.cloudCover;
  }
  

  private static double subtractTimes(double early, double late) {
    int earlyHour = (int) (early / 1) + 12;
    int lateHour = (int) (late / 1) + 12;
    double earlyMin = Math.abs(early % 1.0);
    double lateMin = Math.abs(late % 1.0);
    double earlyTime = earlyHour + earlyMin;
    double lateTime = lateHour + lateMin;
    return lateTime - earlyTime;
  }
  
  private static double distanceBetween(double early, double late) {
    return Math.abs(subtractTimes(early,late));
  }
  
  /**
   * Main method.
   * @param args some args.
   * @throws Exception 
   */
  public static void main (String[] args) throws Exception {
    init(); 
    System.out.println(subtractTimes(weatherReport.sunSet,weatherReport.sunRise));
     
    System.out.println("Current Data:");
    System.out.println("Time = " + getTime());
    System.out.println("Solar Intensity = " +  getSolarIntensity());
    System.out.println("Temp = " +  getTemp()); 
    System.out.println("Sunrise/set = " + weatherReport.sunRise + " / " + weatherReport.sunSet);
    System.out.println("Cloud cover = " + ( (1 - weatherReport.cloudCover) * 100.0) + "%");
    System.out.println("**************************************");
    System.out.println("Data over time");
    for (double i = -12; i <= 12; ++i) {
      System.out.println();
      System.out.println("Hour = " + i);
      System.out.println("Temp = " + getTemp(i)); 
      System.out.println("Sun = " + getSolarIntensity(i)); 
      //System.out.print(getSolarIntensity(i) +",");
    }  
  }


  
}
