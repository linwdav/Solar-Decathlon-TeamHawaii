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
  
  //private static final String SYSTEM_NAME = "lighting";
  
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

    System.out.println("Something just happened in Lights.");
  }
}
