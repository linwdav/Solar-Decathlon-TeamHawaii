package edu.hawaii.solardecathlon.page.lighting;

import java.io.Serializable;

/**
 * Creates the model for each room.
 * 
 * @author Bret K. Ikehara
 */
public class RoomModel implements Serializable {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -8639610150223419386L;
  private String name;
  private String device;
  private Long level;

  /**
   * Creates a room with a name and an associated Arduino device.
   * 
   * @param name String
   * @param device String
   */
  public RoomModel(String name, String device) {
    this.name = name;
    this.device = device;
    this.level = 0L;
  }
  
  /**
   * Gets the device associated with this room.
   * 
   * @return String
   */
  public String getDevice() {
    return this.device;
  }

  /**
   * Gets this room's name.
   * 
   * @return String
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets this room's lighting level.
   * 
   * @return level
   */
  public Long getLevel() {
    return level;
  }

  /**
   * Sets this room's lighting level.
   * 
   * @param level Long
   */
  public void setLevel(Long level) {
    this.level = level;
  }

  /**
   * Calculate hash code based upon room name.
   * 
   * @return int
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  /**
   * Check if rooms are equal by their name. The state of the room doesn't matter.
   * 
   * @param obj Object
   * @return boolean
   */
  @Override
  public boolean equals(Object obj) {

    if (obj != null && this.getClass() == obj.getClass()
        && this.name == ((RoomModel) obj).getName()) {
      return true;
    }

    return false;
  }
  
  /**
   * Return the room's name.
   * 
   * @return String
   */
  @Override
  public String toString() {
    return "Room: " + this.name;
  }
}
