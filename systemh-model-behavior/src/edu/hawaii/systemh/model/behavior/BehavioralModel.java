package edu.hawaii.systemh.model.behavior;

import java.util.Calendar; 
import java.util.GregorianCalendar;
 
 

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
  /**
   * Runs the Behavioral Model.
   * 
   * @param args (ignored)
   * @throws Exception 
   */ 
  public static void main(String[] args) throws Exception {
    System.err.println("Behavioral model of the house.");   
    DailyTemperatureHistory temp = new DailyTemperatureHistory(2011,4);  
    Calendar date = new GregorianCalendar ();    

    int month = date.get(Calendar.MONTH ) + 1;  
    for (int i = 0; i <  32; i++) { 
      if (temp.getTemp(month, i) != null) {
      System.out.println("Date = " + month + "/" +  i); 
      System.out.println(temp.getTemp(month, i));
      }
      if (i == 31) {
        month -= 1;
        i = 0;
      }
      
    } 
  }


}
