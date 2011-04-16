package edu.hawaii.systemh.api.repository;

import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.ApiDictionary.SystemStatusMessageType;

/**
 * Defines the structure of a SystemStatusMessageListener class.  
 * This class must implement an messageAdded method which is invoked anytime the back-end
 * adds any SystemStatusMessage instances to the repository.
 * Clients must extend this class and override the messageAdded method. 
 * @author Philip Johnson
 */
public abstract class SystemStatusMessageListener {

  /**
   * A method that is invoked whenever a SystemStatusMessage is generated.
   * @param timestamp The timestamp when the message is generated. 
   * @param system The system of interest. 
   * @param type The type of message (Info, Warning, etc.) 
   * @param message The actual message. 
   */
  public abstract void messageAdded(Long timestamp, SystemHSystem system, 
      SystemStatusMessageType type, String message);
}
