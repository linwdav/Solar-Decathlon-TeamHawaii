package edu.hawaii.solardecathlon.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.page.lighting.RoomModel;

/**
 * Creates the Lighting listener.
 * 
 * @author Bret K. Ikehara
 */
public class LightingListener extends SystemStateListener {

  private Map<String, RoomModel> rooms;

  /**
   * Default Constructor.
   * 
   * @param systemName String
   */
  public LightingListener(String systemName) {
    super(systemName);
    rooms = new HashMap<String, RoomModel>();
  }

  /**
   * Handles the a new entry. This is called by the backend api to update the values for the user
   * interface.
   * 
   * @param entry SystemStateEntry
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println(entry);

    // Set entry
    for (String name : this.rooms.keySet()) {
      RoomModel room = this.rooms.get(name);
      if (room.getDevice().equalsIgnoreCase(entry.getDeviceName())) {
        room.setLevel(entry.getLongValue("level"));
        break;
      }
    }
  }

  /**
   * Gets this rooms base upon name.
   * 
   * @param room String
   * @return RoomModel
   */
  public RoomModel getRoom(String room) {
    return this.rooms.get(room);
  }

  /**
   * Puts this rooms into the HashMap based upon room name.
   * 
   * @param room RoomModel
   */
  public void putRoom(RoomModel room) {
    this.rooms.put(room.getName(), room);
  }

  /**
   * Gets this rooms as a list.
   * 
   * @return List<RoomModel>
   */
  public List<RoomModel> getRoomList() {
    List<RoomModel> list = new ArrayList<RoomModel>();

    if (!this.rooms.isEmpty()) {
      for (String room : this.rooms.keySet()) {
        list.add(this.rooms.get(room));
      }
    }
    return list;
  }
}
