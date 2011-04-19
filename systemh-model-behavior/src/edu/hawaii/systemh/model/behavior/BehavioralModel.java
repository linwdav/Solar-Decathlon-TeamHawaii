package edu.hawaii.systemh.model.behavior;

/**
 * Improve the realism of the house simulation by creating a 
 * more sophisticated "behavioral model" of house.
 * This should take into account weather, time of day, 
 * and expected occupant behaviors to do things like:
 * Change inside/outside temperatures.
 * Turn on or off various aquaponics systems.
 * Maybe indicate that fish should be fed or whatever.
 * If outside temperature is hot, then water temperature should increase, etc.
 * HVAC use and/or thermostat setting should result in automated on or off of system. 
 * PV generation should depend upon time of day and outside weather (cloudy, sunny). 
 * 
 * @author Gregory Burgess
 * @author Tony Gaskell
 * @author Kurt Teichman
 */
public class BehavioralModel {
  private static final String location = "Washington, District of Columbia";
  
  /**
   * Sets up the REST server to communicate with the IHaleBackend object.
   * 
   * @param args (ignored)
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
    System.err.println("Behavioral model of the house.");
    WeatherReport weather = new WeatherReport(location);
    
    System.out.println(weather.cloudCover);
    System.out.println(weather.fTemp);
    System.out.println(weather.timestamp);
  }
}
