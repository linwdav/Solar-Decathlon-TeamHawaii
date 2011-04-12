package edu.hawaii.ihale.api.repository;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * Defines the structure of a SystemStateListener class.  This class must implement an entryAdded
 * method which will be invoked whenever an entry is added to the database.
 * Clients must extend this class and override the entryAdded method. 
 * @author Philip Johnson
 */
public abstract class SystemStateListener {
  
  /** The system of interest. */
  private IHaleSystem system;
  
  /**
   * Creates a new listener which responds to additions of entries for the given system.
   * @param system The system.
   */
  public SystemStateListener(IHaleSystem system) {
    this.system = system;
  }
  
  /**
   * Returns the system.
   * @return The system.
   */
  public IHaleSystem getSystem() {
    return this.system;
    
  }
  /**
   * A method that is invoked whenever the state of any system changes.
   * @param state The state variable that changed.
   * @param room If the system is "Lighting", then room will indicate the room. 
   * @param timestamp The time associated with this state change.
   * @param value The value of the state (Boolean, String, Double, Long, Integer).
   */
  public abstract void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value);
}
