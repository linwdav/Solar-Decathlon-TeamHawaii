package edu.hawaii.systemh.api.repository;

import edu.hawaii.systemh.api.ApiDictionary.SystemHRoom;
import edu.hawaii.systemh.api.ApiDictionary.SystemHState;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;

/**
 * Defines the structure of a SystemStateListener class.  This class must implement an entryAdded
 * method which will be invoked whenever an entry is added to the database.
 * Clients must extend this class and override the entryAdded method. 
 * @author Philip Johnson
 */
public abstract class SystemStateListener {
  
  /** The system of interest. */
  private SystemHSystem system;
  
  /**
   * Creates a new listener which responds to additions of entries for the given system.
   * @param system The system.
   */
  public SystemStateListener(SystemHSystem system) {
    this.system = system;
  }
  
  /**
   * Returns the system.
   * @return The system.
   */
  public SystemHSystem getSystem() {
    return this.system;
    
  }
  /**
   * A method that is invoked whenever the state of any system changes.
   * @param state The state variable that changed.
   * @param room If the system is "Lighting", then room will indicate the room. 
   * @param timestamp The time associated with this state change.
   * @param value The value of the state (Boolean, String, Double, Long, Integer).
   */
  public abstract void entryAdded(SystemHState state, SystemHRoom room, 
      Long timestamp, Object value);
}
