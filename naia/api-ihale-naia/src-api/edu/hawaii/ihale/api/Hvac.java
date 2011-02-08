package edu.hawaii.ihale.api;

import java.text.DateFormat;
import java.util.Date;

/**
 * Describes the HVAC system
 * 
 * @author Team Naia
 */
public class Hvac {

  // The power consuption in kWh of the hvac system.
  private double powerConsumption;
  // Air temperature of the hvac system
  private double airTemp;
  // Device used to send and receive data
  private String device;
  

  // Time the hvac reading was taken
  private long timeStamp;

  /**
   * Creates an Aquaponics instance from a given set of inputs
   * 
   * @param powerConsumption The amount of power consumed by the hvac
   * @param airTemp The air temperature of the aquaponics system
   */
  public Hvac(double powerConsumption, double airTemp, String device, long timeStamp) {
    this.powerConsumption = powerConsumption;
    this.airTemp = airTemp;
    this.device = device;
    this.timeStamp = timeStamp;
  }

  /**
   * Get the time that this data set was taken. (Unique ID)
   * 
   * @return The time that the reading was taken.
   */
  long getTimeStamp() {
    return this.timeStamp;
  }
  
  /**
   * Get the pH of the water tank
   *
   * @return pH of tank.
   */
  double getpowerConsumption() {
    return this.powerConsumption;
  }
  
  /**
   * Get the air temperature.
   * 
   * @return air temperature.
   */
  double getAirTemp() {
    return this.airTemp;
  }
  
  /**
   * Gets the device name used for I/O with the iHale aquaponics system
   * @return device name.
   */
  String getDevice() {
    return this.device;
  }
  

  /**
   * Return this Hvac object as a formatted string.
   * 
   * @return The data set for the Hvac object as a string.
   */
  @Override
  public String toString() {
    
    // Date formatting variables
    Date date = new Date(this.timeStamp);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    
    return String.format("[Power Consuption: %d Air Temp: %d Device: %s]", dateFormat.format(date), 
        this.powerConsumption,this.airTemp, this.device);
        
  }
} // End Hvac class
