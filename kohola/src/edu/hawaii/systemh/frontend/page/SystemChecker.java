package edu.hawaii.systemh.frontend.page;

import java.io.Serializable;

import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.page.aquaponics.AquaponicsListener;
import edu.hawaii.systemh.frontend.page.hvac.HvacListener;

/**
 * SystemChecker is a class that will check current values of
 * the system listeners to see if they are with in desired ranges.
 * @author kurtteichman
 */
public class SystemChecker implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;
  /** boolean to describe if aquaponics is malfunctioning. */
  public boolean aquaponicsError = false;
  /** boolean to describe if hvac is malfunctioning. */
  public boolean hvacError = false;
  
  /**
   * Method to check to see if indeed an error was found.
   * @return boolean that describes if an error was found
   */
  public boolean foundError () {
    // alludes to how we will implement the other error catching
    if (aquaponicsError()) {
      return aquaponicsError;
    }
    else if (hvacError()) {
      return hvacError;
    }
    
    return false;
  }
  
  /**
   * Method to get the name of an erroneous system. Will eventually
   * create a list of erroneous systems.
   * @return A string representing the erroneous system.
   */
  public String getErroneousSystem() {
    if (aquaponicsError) {
      return "Aquaponics";
    }
    else if (hvacError) {
      return "Hvac";
    }
    
    return null;
  }
  
  /**
   * Method to check whether the aquaponics system is malfunctioning.
   * @return boolean describing whether there is an error associated
   * with the aquaponics system.
   */
  private synchronized boolean aquaponicsError() {
    AquaponicsListener aquaponicsListener =
      SolarDecathlonApplication.getAquaponics();
    int temp = aquaponicsListener.getTemp();
    //int deadFish = aquaponicsListener.getDeadFish();
    double pH = aquaponicsListener.getPH();
    //double circulation = aquaponicsListener.getCirculation();
    //double nutrients = aquaponicsListener.getNutrients();
    //double turbidity = aquaponicsListener.getTurbidity();
    //double conductivity = aquaponicsListener.getConductivity();
    double oxygen = aquaponicsListener.getOxygen();
    //int waterLevel = aquaponicsListener.getWaterLevel();
    
    if (temp < 82 || temp > 86) {
      aquaponicsError = true;
    }
    else if (pH < 6.8 || pH > 8.0) {
      aquaponicsError = true;
    }
    else if (oxygen < 4.5 || oxygen > 5.5) {
      aquaponicsError = true;
    }
    /*
    else if (deadFish > 0) {
      aquaponicsError = true;
    }
    */
    else {
      aquaponicsError = false;
    }
    
    return aquaponicsError;
  }
  
  //private synchronized static boolean checkAquaponics() {
    //return aquaponicsError;
  //}

  /**
   * Method to check whether the hvac system is malfunctioning.
   * @return boolean describing whether there is an error associated
   * with the hvac system.
   */
  private synchronized boolean hvacError() {
    HvacListener hvacListener =
      SolarDecathlonApplication.getHvac();
    int temp = hvacListener.getTemp();
    
    if (temp > 80 || temp < 60) {
      hvacError = true;
    }
    else {
      hvacError = false;
    }
    
    return hvacError;
  }
  
  /*
  private boolean energyError() {
    ElectricalListener electricalListener =
      SolarDecathlonApplication.getElectrical();
    long energy = electricalListener.getEnergy();
    long power = electricalListener.getPower();
    return false;
  }
  */
}
