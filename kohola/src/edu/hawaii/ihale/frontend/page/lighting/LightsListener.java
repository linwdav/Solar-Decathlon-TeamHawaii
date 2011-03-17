package edu.hawaii.ihale.frontend.page.lighting;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener for lighting that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class LightsListener extends SystemStateListener {
  
  private String color = "000000";
  //private static final String SYSTEM_NAME = "lighting";
  Room living = new Room("living", false, 0, color);
  Room dining = new Room("dining", false, 0, color);
  Room kitchen = new Room("kitchen", false, 0, color);
  Room bathroom = new Room("bathroom", false, 0, color);
  
  /**
   * Provide a default constructor that indicates that this listener is for lighting.
   */
  public LightsListener() {
    super(IHaleSystem.LIGHTING);
  }

  /**
   * Runs when the Lighting state changes. 
   * @param state One of the Lighting state values. 
   * @param room Always null for the Lighting system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    switch(room) {
    case LIVING:
      // do swtich statement
      switch(state) {
      case LIGHTING_ENABLED:
        living.setLightingEnabled((Boolean)value);
        break;
      
      case LIGHTING_LEVEL:
        living.setLightingLevel((Integer)value);
        break;
        
      case LIGHTING_COLOR:
        living.setLightingColor((String)value);
        break;
        
      default:
        break;
      }
      
      break;
      
    case DINING:
      
      switch(state) {
      case LIGHTING_ENABLED:
        dining.setLightingEnabled((Boolean)value);
        break;
      
      case LIGHTING_LEVEL:
        dining.setLightingLevel((Integer)value);
        break;
        
      case LIGHTING_COLOR:
        dining.setLightingColor((String)value);
        break;
        
      default:
        break;
      }
      break;
      
    case KITCHEN:
      
      switch(state) {
      case LIGHTING_ENABLED:
        kitchen.setLightingEnabled((Boolean)value);
        break;
      
      case LIGHTING_LEVEL:
        kitchen.setLightingLevel((Integer)value);
        break;
        
      case LIGHTING_COLOR:
        kitchen.setLightingColor((String)value);
        break;
        
      default:
        break;
      }
      
      break;
      
    case BATHROOM:
      
      switch(state) {
      case LIGHTING_ENABLED:
        bathroom.setLightingEnabled((Boolean)value);
        break;
      
      case LIGHTING_LEVEL:
        bathroom.setLightingLevel((Integer)value);
        break;
        
      case LIGHTING_COLOR:
        bathroom.setLightingColor((String)value);
        break;
        
      default:
        break;
      }
      
      break;
      
    default:
      break;
    } 
  }
  
  /**
   * Returns the state of the living room.
   * @return living - the state of the living room
   */
  public Room getLivingRoom() {
    return living;
  }
  
  /**
   * Returns the state of the dining room.
   * @return dining - the state of the dining room
   */
  public Room getDiningRoom() {
    return dining;
  }
  
  /**
   * Returns the state of the kitchen room.
   * @return kitchen - the state of the kitchen room
   */
  public Room getKitchenRoom() {
    return kitchen;
  }
  
  /**
   * Returns the state of the bathroom.
   * @return bathroom - the state of the bathroom
   */
  public Room getBathroom() {
    return bathroom;
  }
}
