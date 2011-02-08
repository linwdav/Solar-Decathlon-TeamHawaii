package edu.hawaii.ihale.api;

import java.text.DateFormat;
import java.util.Date;

/**
 * Describes the aquaponics
 * 
 * @author Team Naia
 */
public class Water {

  // The pH reading of the water tank
  private double pH;
  // Tank water temperature
  private double waterTemp;
  // Air temperature of the aquaponics system
  private double airTemp;
  // Water level of the tank
  private double waterLevel;
  // Device used to send and receive data
  private String device;

  // Time the aquaponics reading was taken
  private long timeStamp;

  /**
   * Creates an Aquaponics instance from a given set of inputs
   * 
   * @param pH The pH in the water tank
   * @param waterTemp The temperature of the water
   * @param airTemp The air temperature of the aquaponics system
   * @param waterLevel The water level of the tank
   * @param device The device used to send and receive data from the iHale system
   * @param timeStamp The time that the aquaponics reading was taken
   */
  public Water(double pH, double waterTemp, double airTemp, double waterLevel,
      String device, long timeStamp) {
    this.pH = pH;
    this.waterTemp = waterTemp;
    this.airTemp = airTemp;
    this.waterLevel = waterLevel;
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
  double getPH() {
    return this.pH;
  }
  
  /**
   * Get the water temperature.
   * 
   * @return water temperature.
   */
  double getWaterTemp() {
    return this.waterTemp;
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
   * Get the water level.
   * 
   * @return the tank's water level.
   */
  double getWaterLevel() {
    return this.waterLevel;
  }
  
  /**
   * Gets the device name used for I/O with the iHale aquaponics system
   * @return device name.
   */
  String getDevice() {
    return this.device;
  }
  

  /**
   * Return this Aquaponics object as a formatted string.
   * 
   * @return The data set for the Aquaponics object as a string.
   */
  @Override
  public String toString() {
    
    // Date formatting variables
    Date date = new Date(this.timeStamp);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    
    return String.format("[Time: %s pH: %d Water Temp: %d F Air Temp: %d Water Level: %d L Device: %s]", dateFormat.format(date), 
        this.pH, this.waterTemp,this.airTemp, this.waterLevel, this.device);
        
  }
} // End Aquaponics class
