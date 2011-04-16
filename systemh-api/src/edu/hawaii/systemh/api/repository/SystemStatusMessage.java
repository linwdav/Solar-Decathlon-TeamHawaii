package edu.hawaii.systemh.api.repository;

import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.ApiDictionary.SystemStatusMessageType;

/**
 * Indicates information about the running of the system, supports alerts and warnings and so forth.
 * @author Philip Johnson
 */
public class SystemStatusMessage {
  
  /** The timestamp. */
  private Long timestamp;
  
  /** The system of interest. */
  private SystemHSystem system;
  
  /** The message type. */
  private SystemStatusMessageType type;
  
  /** The message itself. */
  private String message;
  
  /**
   * Creates a new SystemStatusMessage instance with info about the system's status.
   * @param timestamp The timestamp.
   * @param system The system.
   * @param type The type.
   * @param message The message. 
   */
  public SystemStatusMessage(Long timestamp, SystemHSystem system, SystemStatusMessageType type, 
      String message) {
    this.timestamp = timestamp;
    this.system = system;
    this.message = message;
    this.type = type;
  }
  
  /**
   * Returns the timestamp.
   * @return The timestamp.
   */
  public Long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Returns the system.
   * @return The system.
   */
  public SystemHSystem getSystem() {
    return this.system;
  }
  
  /**
   * Returns the message type.
   * @return The message type.
   */
  public SystemStatusMessageType getType() {
    return this.type;
  }
  
  /**
   * Returns the message itself. 
   * @return The message. 
   */
  public String getMessage() {
    return this.message;
  }

}
