package edu.hawaii.systemh.api.repository.impl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.systemh.api.repository.SystemStatusMessage;

/**
 * The persistent version of the SystemStatusMessage. Annotated with BerkeleyDB stuff.
 * @author Philip Johnson
 */
@Entity
class SystemStatusMessageEntry {
  
  @PrimaryKey
  /** The timestamp.  Always available and serves as the primary key. */
  private Long timestamp;
  
  /** The system of interest. */
  private SystemHSystem system;
  
  /** The message type. */
  private SystemStatusMessageType type;
  
  /** The message itself. */
  private String message;
  
  /**
   * The default constructor, required by BerkeleyDB. Not used in practice.
   */
  public SystemStatusMessageEntry() {
    //no body
  }
  
  /**
   * Creates a new SystemStatusMessage instance which stores info about a status message.
   * @param timestamp The timestamp.
   * @param system The system.
   * @param type The type.
   * @param message The message. 
   */
  public SystemStatusMessageEntry(Long timestamp, SystemHSystem system, 
      SystemStatusMessageType type, String message) {
    this.timestamp = timestamp;
    this.system = system;
    this.message = message;
    this.type = type;
  }
  
  /**
   * Creates a SystemStatusMessageEntry from the passed SystemStatusMessage.
   * This is basically a way to convert the public status message representation into our 
   * internal, persistent representation.
   * @param message The SystemStatus Message. 
   */
  public SystemStatusMessageEntry(SystemStatusMessage message) {
    this(message.getTimestamp(), message.getSystem(), message.getType(), message.getMessage());
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
