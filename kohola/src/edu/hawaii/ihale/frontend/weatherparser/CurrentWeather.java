package edu.hawaii.ihale.frontend.weatherparser;

import java.io.Serializable;

/**
 * A class for current weather info.
 * 
 * @author Chuan Lun Hung
 * 
 */
public class CurrentWeather implements Serializable {
  
  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private String condition;
  private int tempF;
  private int tempC;
  private String humidity;
  private String imageURL;
  private String windCondition;

  /**
   * The constructor that reads in the current weather data.
   * 
   * @param condition The weather condition.
   * @param tempF The temperature in F.
   * @param tempC The temperature in C.
   * @param humidity The humidity.
   * @param imageURL The image URL.
   * @param windCondition The wind condition.
   */
  public CurrentWeather(String condition, int tempF, int tempC, String humidity, String imageURL,
      String windCondition) {
    this.condition = condition;
    this.tempF = tempF;
    this.tempC = tempC;
    this.humidity = humidity;
    this.imageURL = imageURL;
    this.windCondition = windCondition;

  }

  /**
   * Returns the current weather condition.
   * 
   * @return The weather condition.
   */
  public String getCondition() {
    return condition;
  }

  /**
   * Returns the current temperature in F.
   * 
   * @return The temperature in F.
   */
  public int getTempF() {
    return tempF;
  }

  /**
   * Returns the current temperature in C.
   * 
   * @return The temperature in C.
   */
  public int getTempC() {
    return tempC;
  }

  /**
   * Returns the current humidity.
   * 
   * @return The humidity.
   */
  public String getHumidity() {
    return humidity;
  }

  /**
   * Returns the image url for the weather.
   * 
   * @return The image URL.
   */
  public String getImageURL() {
    return imageURL;
  }

  /**
   * Returns the current wind condition.
   * 
   * @return The wind condition.
   */
  public String getWindCondition() {
    return windCondition;
  }

  /**
   * Returns a String that contains all the current weather info.
   * 
   * @return The current weather info as String.
   */
  public String toString() {
    return "Condition:" + condition + "\nFahrenheit:" + tempF + "\nCelsius:" + tempC + "\n"
        + humidity + "\nImage source:" + imageURL + "\nWind Condition:" + windCondition;
  }

}
