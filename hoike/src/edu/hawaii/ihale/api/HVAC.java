package edu.hawaii.ihale.api;

/**
 * ADT to store information about the HVAC system.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class HVAC {

  /** Fields for the system. */
  private String roomName;
  private float humidity;
  private float temperature;
  private long timestamp;
  
  /**
   * Default constructor.
   * 
   * @param roomName The room name within the home (i.e., naturalSpaceRoom).
   * @param humidity The room's relative humidity.
   * @param temperature The temperature within the room.
   * @param timestamp The timestamp of when these measurements were obtained by the sensors.
   */
  public HVAC(String roomName, float humidity, float temperature, long timestamp) {
    this.roomName = roomName;
    this.humidity = humidity;
    this.temperature = temperature;
    this.timestamp = timestamp;
  }
  
  /**
   * Returns the room's name.
   * 
   * @return Room name.
   */
  public String getRoomName() {
    return this.roomName;
  }
  
  /**
   * Returns the room's relative humidity.
   * 
   * @return The relative humidity.
   */
  public float getHumidity() {
    return this.humidity;
  }
  
  /**
   * Return the temperature of the room.
   * 
   * @return The temperature.
   */
  public float getTemperature() {
    return this.temperature;
  }
  
  /**
   * Returns the timestamp of when the measurements were obtained for this room.
   * 
   * @return The timestamp.
   */
  public long getTimestamp() {
    return this.timestamp;
  }
}
