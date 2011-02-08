package edu.hawaii.ihale.api;

import java.text.DateFormat;
import java.util.Date;

/**
 * Describes a water readings object.
 * 
 * @author Team Naia
 */
public class Water {

  // The temperature of the hot water tank
  private double hotWaterTemp;

  // The temperature of the cold water tank
  private double coldWaterTemp;

  // Water level of the hot water tank
  private double hotWaterLevel;

  // Water level of the cold water tank
  private double coldWaterLevel;

  // Device used to send and receive data
  private String device;

  // Time the Water reading was taken
  private long timeStamp;

  /**
   * Creates a Water object instance from a given set of inputs
   * 
   * @param hotWaterTemp The temperature of the hot water tank
   * @param waterTemp The temperature of the cold water tank
   * @param hotWaterLevel The water level of the hot water tank
   * @param coldWaterLevel The water level of the cold water tank
   * @param device The device used to send and receive data from the iHale system
   * @param timeStamp The time that the Water reading was taken
   */
  public Water(double hotWaterTemp, double coldWaterTemp, double hotWaterLevel,
      double coldWaterLevel, String device, long timeStamp) {

    this.hotWaterTemp = hotWaterTemp;
    this.coldWaterTemp = coldWaterTemp;
    this.hotWaterLevel = hotWaterLevel;
    this.coldWaterLevel = coldWaterLevel;
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
   * Get the hot water temperature.
   * 
   * @return hot water temperature.
   */
  double getHotWaterTemp() {
    return this.hotWaterTemp;
  }

  /**
   * Get the cold water temperature.
   * 
   * @return cold water temperature.
   */
  double getColdWaterTemp() {
    return this.coldWaterTemp;
  }

  /**
   * Get the hot water tank water level.
   * 
   * @return the hot water tank's water level.
   */
  double getHotWaterLevel() {
    return this.hotWaterLevel;
  }

  /**
   * Get the cold water tank's water level.
   * 
   * @return the cold water tank's water level.
   */
  double getColdWaterLevel() {
    return this.coldWaterLevel;
  }

  /**
   * Gets the device name used for I/O with the iHale aquaponics system
   * 
   * @return device name.
   */
  String getDevice() {
    return this.device;
  }

  /**
   * Return this Water object as a formatted string.
   * 
   * @return The data set for the Water object as a string.
   */
  @Override
  public String toString() {

    // Date formatting variables
    Date date = new Date(this.timeStamp);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

    return String
        .format(
            "[Time: %s Hot Water Temp: %d F Cold Water Temp: %d F Hot Water Level: %d L Cold Water Level: %d L Device: %s]",
            dateFormat.format(date), this.hotWaterTemp, this.coldWaterTemp, this.hotWaterLevel,
            this.coldWaterLevel, this.device);

  }
} // End Water class
