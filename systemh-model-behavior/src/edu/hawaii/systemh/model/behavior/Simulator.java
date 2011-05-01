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
  private  static final String location = "Washington, District of Columbia";
  /** The weather station nearest DC that we will pull data from. **/
  private static final String station = "Eckington Pl, NE, Washington";
  
  private static WeatherReport weatherReport;
  
  /**
   * Default Constrctor.
   * @throws Exception If WeatherUnderground is unreachable.
   */
  public Simulator() throws Exception {
    init();
  }
  
  /**
   * Initalizes the data.
   * @throws Exception If WeatherUndergound cannot be reached.
   */
  public static final void init() throws Exception {
    weatherReport = new WeatherReport(location,station);
  }
  /**
   * Generate a double between -12 (AM) and 12 (PM) representing the time.
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
  public static double getOutsideTemp() {
    return getOutsideTemp(getTime());
    }
  
  /**
   * Returns the outside temp based on time.
   * @param time - a double representing the time given by getTime().
   * @return a double representing the temperature in F.
   */
  public static double getOutsideTemp(double time) {
    //Using the basic formula   y = 12.5*sin((x/4)-7)
    //average temperature in DC is between 50 and 75 F, see link below...
    //http://www.wunderground.com/history/airport/KDCA/2011/10/21/MonthlyHistory.
//html?req_city=NA&req_state=NA&req_statename=NA
    double seed = time / 4.0 - 1.1;  
    double high = weatherReport.getHighTemp();
    double low = weatherReport.getLowTemp();
    return ((high - low) / 2.0) * Math.sin(seed) + ((high + low) / 2.0); 
  }
  
  /**
   * Returns a value between 0 and 100 representing solar intensity.
   * @return a double between 0 and 100.
   */
  public static double getSolarIntensity() {
    return getSolarIntensity(getTime());
  }
  
  /**
   * Returns a double between 0 and 100 representing the solar intensity
   * at the current hour of the day; returns 0 between the hours of 6PM
   * and 6AM.  This function is inaccurate by a small amount due to the 
   * use of doubles.
   * @param time current time in ms as a long.
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

    double hoursOfDaylight = distanceBetween(weatherReport.getSunSet()
        ,weatherReport.getSunRise());
    double halfHOD = hoursOfDaylight / 2.0; 
    
    //make sure its daytime or return 0
    if (((time > 0) && (time >= weatherReport.getSunSet())) || (time < 0) && 
        Math.abs(time) < Math.abs(weatherReport.getSunRise())) {
      System.out.println("Dark");
      return 0;
    } 
    //affects the zeroes of the sin function, as well as the max height of 100
    double multiplier = (-100.0 / Math.pow(halfHOD,2.0));
    //crazy maths...
    double sun = Math.pow((time + subtractTimes(weatherReport.getSunRise(),-(halfHOD + .63))),2.0); 
    sun = sun * multiplier + 100.0;
    //Definitely don't return negatives...
    if (sun < 0) {
      System.out.println("Was negative");
      return 0;
    }
    //return the sunshine but correct for cloud cover...
    return sun * weatherReport.getCloudCover();
  }
  
/**
 * Returns a signed double representing the time between
 * two times.
 * @param early the first time.
 * @param late the second time.
 * @return a signed double.
 */
  private static double subtractTimes(double early, double late) {
    int earlyHour = (int) (early / 1) + 12;
    int lateHour = (int) (late / 1) + 12;
    double earlyMin = Math.abs(early % 1.0);
    double lateMin = Math.abs(late % 1.0);
    double earlyTime = earlyHour + earlyMin;
    double lateTime = lateHour + lateMin;
    return lateTime - earlyTime;
  }
  
  /**
   * Returns an unsigned double representing the absolute distance
   * between two times.
   * @param early the first time.
   * @param late the second time.
   * @return the distance between the two times.
   */
  private static double distanceBetween(double early, double late) {
    return Math.abs(subtractTimes(early,late));
  }
  
  /**
   * Main method, prints current data.
   * @param args some args.
   * @throws Exception 
   */
  public static void main (String[] args) throws Exception {
    init(); 
    System.out.println(subtractTimes(weatherReport.getSunSet(),weatherReport.getSunRise()));
     
    System.out.println("Current Data:");
    System.out.println("Time = " + getTime());
    System.out.println("Solar Intensity = " +  getSolarIntensity());
    System.out.println("Temp = " +  getOutsideTemp()); 
    System.out.println("High/low = " + weatherReport.getHighTemp() +
                        "/" + weatherReport.getLowTemp());
    System.out.println("Sunrise/set = " + weatherReport.getSunRise() + 
                        " / " + weatherReport.getSunSet());
    System.out.println("Cloud cover = " + ( (1 - weatherReport.getCloudCover()) * 100.0) + "%");
    System.out.println("**************************************");
    System.out.println("Data over time");
    for (double i = -7; i <= 8.5; i += .1) {
      if (i == -5) {
        i += 12;
      }
      System.out.println();
      System.out.println("Hour = " + i);
      //System.out.println("Temp = " + getTemp(i)); 
      System.out.println("Sun = " + getSolarIntensity(i)); 
      //System.out.print(getSolarIntensity(i) +",");
    }  
  }

  /**
   * Converts from degrees F to C.
   * @param temp temp in F.
   * @return temp in C.
   */
  public double fToC(double temp) {
    return (temp - 32) * (5 / 9.0);
  }
  
  /**
   * Converts from degrees C to F.
   * @param temp temp in C.
   * @return temp in F.
   */
  public double cToF(double temp) {
    return (temp + 32) * (9 / 5.0);
  }
  
}
