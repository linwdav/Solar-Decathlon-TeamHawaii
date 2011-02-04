package edu.hawaii.ihale.api;

/**
 * Provides information about the lighting in a room.
 * 
 * @author Team Kohola
 */
public class Lighting {

  private String room;
  private boolean lightOn;
  private boolean normalMode;
  private String colorCode;
  private int percentOn;

  /**
   * Creates a Lighting instance given its field values.
   * 
   * @param room The name of a room.
   * @param lightOn Whether the light is on.
   * @param normalMode Whether it's in normal mode or entertainment mode.
   * @param colorCode The RGB hexidecimal value.
   * @param percentOn The percentage of the lighting.
   */
  public Lighting(String room, boolean lightOn, 
      boolean normalMode, String colorCode, int percentOn) {
    this.room = room;
    this.lightOn = lightOn;
    this.normalMode = normalMode;
    this.colorCode = colorCode;
    this.percentOn = percentOn;
  }

  /**
   * Returns the name of the room.
   * 
   * @return The name of the room.
   */
  public String getRoom() {
    return this.room;
  }

  /**
   * Toggles the lights switch on or off.
   */
  public void toggleLightsSwitch() {
    lightOn = !lightOn;
  }

  /**
   * Returns the lighting state.
   * 
   * @return Whether the lights are on or off.
   */
  public boolean isOn() {
    return this.lightOn;
  }

  /**
   * Change between normal mode and entertainment mode.
   */
  public void changeMode() {
    normalMode = !normalMode;
  }

  /**
   * Returns the lighting mode.
   * 
   * @return Whether the lights are in normal mode or entertainment mode.
   */
  public boolean getMode() {
    return this.normalMode;
  }

  /**
   * Set the color of the lights in this room.
   * 
   * @param colorCode The RGB hexidecimal value.
   */
  public void setColor(String colorCode) {
    this.colorCode = colorCode;
  }

  /**
   * Returns the color of the lights in this room.
   * 
   * @return The RGB hexidecimal value.
   */
  public String getColor() {
    return this.colorCode;
  }

  /**
   * Set the light intensity in this room.
   * 
   * @param percentage The light intensity range from 0 to 100.
   */
  public void setPercentOn(int percentage) {
    this.percentOn = percentage;
  }

  /**
   * Returns the light intensity in this room.
   * 
   * @return The light intensity range from 0 to 100.
   */
  public int getPercentOn() {
    return this.percentOn;
  }
}
