package edu.hawaii.systemh.frontend.weatherparser;

//import java.util.ArrayList;
//import java.util.List;

/**
 * A class for testing the WeatherParser.
 * @author Chuan Lun Hung
 *
 */
public class WeatherTester {
  
  /**
   * Main method.
   * @param args The command line arguments.
   */
  public static void main(String [] args) {
    
    WeatherParser parser = new WeatherParser("Honolulu");
    System.out.println(parser.getCurrentWeather().toString());
    //List<WeatherForecast> weathers = parser.getWeatherForecast();
    //System.out.println(weathers);
  }
}
