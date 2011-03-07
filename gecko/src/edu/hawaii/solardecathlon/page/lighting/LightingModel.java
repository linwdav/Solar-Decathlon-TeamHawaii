package edu.hawaii.solardecathlon.page.lighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.hawaii.solardecathlon.components.BaseModel;

/**
 * Property Model for the Lights System.
 * 
 * @author Bret K. Ikehara
 */
public class LightingModel extends BaseModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Map<String, RoomModel> rooms;

  /**
   * Default Constructor.
   */
  public LightingModel() {
    rooms = new HashMap<String, RoomModel>();
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
   * @return RoomModel
   */
  public RoomModel putRoom(RoomModel room) {
    return this.rooms.put(room.getName(), room);
  }
  
  /**
   * Gets this rooms as a list.
   * 
   * @return List<RoomModel>
   */
  public List<RoomModel> getRoomList() {
    List<RoomModel> list = new ArrayList<RoomModel>();
    
    for (String room : this.rooms.keySet()) {
     list.add(this.rooms.get(room)); 
    }
    
    return list; 
  }
}