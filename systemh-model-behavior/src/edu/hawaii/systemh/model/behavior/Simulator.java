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
  //private  static final String location = "Washington%2c%20District%20of%20Columbia";
  ///** The weather station nearest DC that we will pull data from. **/
  //private static final String station = "Eckington%20Pl%2c%20NE%2c%20Washington";
  ///** The singleton instance.*/
  private static Simulator instance;
  
  static {
    try {
        instance = new Simulator();
    } 
    catch (Exception e) {
        throw new ExceptionInInitializerError(e);
    }
  }
  
  private static WeatherReport weatherReport; 
  /**
   * Default Constrctor.
   * @throws Exception If WeatherUnderground is unreachable.
   */
  private Simulator() throws Exception {
    init();
  }
  
  /**
   * Returns the singleton instance of the class.
   * @return instance of Simulator.
   */
  public static synchronized Simulator getInstance() {
    return instance;
  }
  /**
   * Initializes the data.
   * @throws Exception If WeatherUndergound cannot be reached.
   */
  private static final void init() throws Exception {
    weatherReport = WeatherReport.getInstance(); 
  }
  /**
   * Generate a double between -12 (AM) and 12 (PM) representing the time.
   * @return a double representing the distance from noon.
   */
  public double getTime() {  
                                                 //DC time zone is "EDT"
                                              //Hawaii timezone is "HST"
    Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("HST"));  
    double rough = calendar.get(Calendar.HOUR_OF_DAY);  
    //Create a double representing time with 
    //hour as the whole number (24 hr clock),
    //minutes as the first two decimal places,
    //seconds as the third and fourth decimal places, and
    //milliseconds as the fifth and sixth decimal places
    //all times expressed as percentages of their max value
    //finally subtract 12.
    double fine = calendar.get(Calendar.MINUTE) / 60.0;
    fine += calendar.get(Calendar.SECOND) / 6000.0;
    fine += calendar.get(Calendar.MILLISECOND) / 10000000.0;
    
    
    //plug the double into a parabolic function to get a 
    //value between 0 and 100.
    return (rough + fine) - 12.0; 
  }
  
  /**
   * Converts a long into a Simulator compatible value.
   * @param timestamp long representing the date.
   * @return a double representing a simulator timestamp.
   */
  public double getSystemTime(long timestamp) {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(timestamp); 
    double hours = cal.get(Calendar.HOUR_OF_DAY);
    double fine = cal.get(Calendar.MINUTE) / 59.0;
    fine += cal.get(Calendar.SECOND) / 6000.0;
    fine += cal.get(Calendar.MILLISECOND) / 10000000.0;
    return hours + fine - 12.0;
  }
  /**
   * Returns the current outside temp in F.
   * @return double temp.
   */
  public double getOutsideTemp() {
    return getOutsideTemp(getTime());
    }
  
  
  /**
   * Returns the outside temperature for a time during today's
   * 24 hour period based on time.
   * @param time the time of day to get the temp for.
   * @return the temp in F at the given time.
   */
  public double getOutsideTemp(double time) {
    return getOutsideTemp(time, weatherReport.getLowTemp(),
                    weatherReport.getHighTemp());
  }
  /**
   * Returns the outside temp based on a time from a certain day, and the
   * highest and lowest temperatures of that day.  
   * Do not use for generating historical data.
   * @param time - a double representing the time given by getTime().
   * @param lowTemp - the lowest temp of the day.
   * @param highTemp - the highest temp of the day.
   * @return a double representing the temperature in F.
   */
  public double getOutsideTemp(double time, double lowTemp, double highTemp) {
    //Using the basic formula   y = k*sin((x/4)-p) 
    //where k is half the difference in high and low temp.
      //representing the fluxuation above and below 0 for the basic sin function.
    //p represents some offset to make the wave fit the daylight hours.

    double seed = time / 4.0 - 5.0;  
    double high = highTemp;
    double low = lowTemp;
    return ((high - low) / 2.0) * Math.sin(seed) + ((high + low) / 2.0); 
  }
  
 
  /**
   * Returns a value between 0 and 100 representing solar intensity.
   * @return a double between 0 and 100.
   */
  public double getSolarIntensity() {
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
  public double getSolarIntensity(double time) { 
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
    if ((time >= weatherReport.getSunSet()) || 
        time < weatherReport.getSunRise()) {
      System.out.println("Dark");
      return 0;
    } 
    //affects the zeroes of the sin function, as well as the max height of 100
    double multiplier = (-100.0 / Math.pow(halfHOD,2.0));
    //crazy maths...
    double sun = Math.pow((time + subtractTimes(weatherReport.getSunRise(),-(halfHOD))),2.0); 
    sun = sun * multiplier + 100.0;
    //Definitely don't return negatives...
    if (sun < 0) { 
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
    return late - early;
  }
  
  /**
   * Returns the current WeatherReport object.
   * @return WeatherReport object containing meteorological data.
   */
  public WeatherReport getWeatherReport() {
    return weatherReport;
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
    Simulator sim = new Simulator();
    System.out.println(subtractTimes(weatherReport.getSunSet(),weatherReport.getSunRise()));
     weatherReport.refresh();
    System.out.println("Current Data:");
    System.out.println("Time = " + sim.getTime());
    System.out.println("Solar Intensity = " +  sim.getSolarIntensity());
    System.out.println("Temp = " +  sim.getOutsideTemp()); 
    System.out.println("High/low = " + weatherReport.getHighTemp() +
                        "/" + weatherReport.getLowTemp());
    System.out.println("Sunrise/set = " + weatherReport.getSunRise() + 
                        " / " + weatherReport.getSunSet());
    System.out.println("Cloud cover = " + ( (1 - weatherReport.getCloudCover()) * 100.0) + "%");
    System.out.println("**************************************");
    System.out.println("Data over time");
    for (double i = -12; i <= 12; i += 1) { 
      System.out.println();
      System.out.println("Hour = " + i);
      System.out.println("Temp = " + sim.getOutsideTemp(i)); 
      System.out.println("Sun = " + sim.getSolarIntensity(i)); 
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
