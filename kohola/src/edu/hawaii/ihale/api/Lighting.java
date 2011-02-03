package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the lighting
 * system.
 * 
 * @author Team Kohola
 */
public interface Lighting {

  /**
   * To turn on or off the lights in the given room.
   * 
   * @param room The name of a room.
   */
  public void toggleLightsSwitch(String room);

  /**
   * To retrieve the current state of the lighting in the given room.
   * 
   * @param room The name of a room
   * @return The state of the lights ("ON" or "OFF")
   */
  public String getCurrentState(String room);

  /**
   * To set the color of lighting in the given room.
   * 
   * @param room The name of a room.
   * @param colorCode The color given in RGB hexidecimal format.
   */
  public void setLightsColorOf(String room, String colorCode);

  /**
   * To get the current color of the lighting in the given room.
   * 
   * @param room The name of a room.
   * @return The color given in RGB hexidecimal format.
   */
  public String getLightsColorOf(String room);

  /**
   * To get the current mode of the lighting in the given room.
   * 
   * @param room The name of a room.
   * @return "LOCK", "FOLLOW", or "ENTERTAINMENT"
   */
  public String getLightsModeOf(String room);

  /**
   * To set the current mode of the lighting in the given room.
   * 
   * @param room The name of a room.
   * @param mode The lighting mode ("LOCK", "FOLLOW", or "ENTERTAINMENT")
   */
  public void setLightsModeOf(String room, String mode);

}
