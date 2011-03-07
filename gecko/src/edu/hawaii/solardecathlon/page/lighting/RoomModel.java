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
  private Long level;

  /**
   * Default Constructor.
   * 
   * @param name String
   */
  public RoomModel(String name) {
    this.name = name;
    this.level = 0L;
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
}
