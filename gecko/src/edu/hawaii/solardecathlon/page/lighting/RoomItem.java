package edu.hawaii.solardecathlon.page.lighting;

/**
 * Creates the class to specify the room options.
 * 
 * @author Bret K. Ikehara
 */
public class RoomItem {
  
  private String name;
  private String value;
  
  /**
   * Default Constructor.
   * 
   * @param name String
   * @param value String 
   */
  public RoomItem(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Gets this name.
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Gets this value.
   * 
   * @return String
   */
  public String getValue() {
    return value;
  }
}
