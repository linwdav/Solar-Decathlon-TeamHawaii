package edu.hawaii.ihale.frontend.page.lighting;

/**
 * Used to keep track of lighting state in each room.
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 *
 */
public class Room {

  private String name;
  private boolean lightingEnabled;
  private int lightingLevel;
  private String lightingColor;

  /**
   * Constructor.
   * @param name The room name.
   * @param lightingEnabled Whether the light is on.
   * @param lightingLevel The light intensity.
   * @param lightingColor The light color.
   */
  public Room(String name, boolean lightingEnabled, int lightingLevel, String lightingColor) {
    this.name = name;
    this.lightingEnabled = lightingEnabled;
    this.lightingColor = lightingColor;
    this.lightingLevel = lightingLevel;
  }
  
  /**
   * Returns the name of this room.
   * @return The room name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Returns whether the light in this room is on.
   * @return Whether the light is enabled.
   */
  public boolean isLightingEnabled() {
    return lightingEnabled;
  }
  
  /**
   * Sets the lighting state.
   * @param enabled true or false.
   */
  public void setLightingEnabled(boolean enabled) {
    this.lightingEnabled = enabled;
  }
  
  /**
   * Returns the lighting level of this room.
   * @return The lighting level.
   */
  public int getLightingLevel() {
    return lightingLevel;
  }
  
  /**
   * Sets the lighting level.
   * @param level The lighting level.
   */
  public void setLightingLevel(int level) {
    this.lightingLevel = level;
  }
  
  /**
   * Returns the lighting color of this room.
   * @return The lighting color.
   */
  public String getLightingColor() {
    return lightingColor;
  }
  
  /**
   * Sets the lighting color of this room.
   * @param color The color.
   */
  public void setLightingColor(String color) {
    this.lightingColor = color;
  }
}
