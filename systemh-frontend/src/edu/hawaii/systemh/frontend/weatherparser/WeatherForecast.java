package edu.hawaii.systemh.frontend.weatherparser;

import java.io.Serializable;

/**
 * A class instance for weather forecasts info.
 * 
 * @author Chuan Lun Hung
 * 
 */
public class WeatherForecast  implements Serializable {
  
  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private String dayOfWeek;
  private int lowTemp;
  private int highTemp;
  private String imageURL;
  private String condition;

  /**
   * The constructor to read in the weather forecast.
   * 
   * @param dayOfWeek Day of the week.
   * @param lowTemp Lowest temperature of this day in F.
   * @param highTemp Highest temperature of this day in F.
   * @param imageURL The image URL.
   * @param condition The weather condition.
   */
  public WeatherForecast(String dayOfWeek, int lowTemp, int highTemp, String imageURL,
      String condition) {
    this.dayOfWeek = dayOfWeek;
    this.lowTemp = lowTemp;
    this.highTemp = highTemp;
    this.imageURL = imageURL;
    this.condition = condition;
  }

  /**
   * Returns the day of the week.
   * 
   * @return The day of the week.
   */
  public String getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * Returns the lowest temperature of this day in F.
   * 
   * @return The temperature in F.
   */
  public int getLowTemp() {
    return lowTemp;
  }

  /**
   * Returns the highest temperature of this day in F.
   * 
   * @return The temperature in F.
   */
  public int getHighTemp() {
    return highTemp;
  }

  /**
   * Returns the image icon url.
   * 
   * @return The image URL.
   */
  public String getImageURL() {
    return imageURL;
  }

  /**
   * Returns the weather condition of this day.
   * 
   * @return The weather condition.
   */
  public String getCondition() {
    return condition;
  }

  /**
   * Returns a String that contains all weather info for this day.
   * 
   * @return The weather forecast info as String.
   */
  public String toString() {
    return "Day of week: " + dayOfWeek + "\nLowTemp: " + lowTemp + "\nHighTemp: " + highTemp
        + "\nImage Source: " + imageURL + "\nCondition: " + condition + "\n\n";
  }
}
