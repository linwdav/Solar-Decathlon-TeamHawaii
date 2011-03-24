package edu.hawaii.ihale.frontend.page.lighting;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The lighting sessions page.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class LightingSession implements Serializable {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private String room = "Living Room";

  private List<String> rooms = Arrays.asList(new String[] { "Living Room", "Dining Room",
      "Kitchen", "Bathroom" });

  /**
   * empty for now.
   */
  public LightingSession() {

    // empty
  }

  /**
   * Returns the room associated with the user's session.
   * 
   * @return The room the user is viewing in lighting page.
   */
  public String getRoom() {
    return room;
  }

  /**
   * Sets the room to the room the user is currently viewing.
   * 
   * @param room - the room the user is looking at in the application.
   */
  public void setRoom(String room) {
    this.room = room;
  }
  
  /**
   * Returns a list of the rooms in the house.
   * 
   * @return The names of the rooms in a list.
   */
  public List<String> getRooms() {
    return Collections.unmodifiableList(rooms);
  }
}
