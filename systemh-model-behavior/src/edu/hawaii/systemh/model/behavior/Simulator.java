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
   * @param time - a double representing the time given by getTime().
   * @return a double representing the temperature in F.
   */
  public static double getTemp() {
    //Using the basic formula   y = 12.5*sin((x/4)-7)
    //average temperature in DC is between 50 and 75 F, see link below...
    //http://www.wunderground.com/history/airport/KDCA/2011/10/21/MonthlyHistory.
    //html?req_city=NA&req_state=NA&req_statename=NA
    
    double seed = getTime() / 4.0 - 5.1; 
    return 12.5 * Math.sin(seed) + 62.5; 
  }
  /**
   * Returns a double between 0 and 100 representing the solar intensity
   * at the current hour of the day; returns 0 between the hours of 6PM
   * and 6AM.
   * @return a double between 0 and 100 representing the solar intensity.
   */
  public static double getSolarIntensity() { 
    //Using the basic formula y = -(100/36)x^2+100
    double time = getTime();  
    //make sure its daytime or return 0
    if (time >= 6.0 || time <= -6.0) {
      return 0;
    }
    return -2.777 * (Math.pow(time, 2)) + 100;
  }
  

  /**
   * Main method.
   * @param args some args.
   */
  public static void main (String[] args) {
    System.out.println("Time = " + getTime());
    System.out.println("Solar Intensity = " +  getSolarIntensity());
    System.out.println("Temp = " +  getTemp()); 
//    for (double i = -12; i <= 12; ++i) { System.out.println(i + " " + getTemp()); }
  }
}
