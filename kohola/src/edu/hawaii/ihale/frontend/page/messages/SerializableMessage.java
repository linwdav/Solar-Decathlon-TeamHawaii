package edu.hawaii.ihale.frontend.page.messages;

import java.io.Serializable;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;

/**
 * Serializes the System Status Messages from the IHale API.
 * 
 * @author FrontEnd Team
 * 
 */
public class SerializableMessage implements Serializable {

  private static final long serialVersionUID = 1L;

  private long timestamp;
  private IHaleSystem system;
  private SystemStatusMessageType type;
  private String message;

  /**
   * Converts a non-serial System Status Message to a serializable
   * message.
   * 
   * @param msg SystemStatusMessage to convert to serializable message
   */
  public SerializableMessage(SystemStatusMessage msg) {
    this.timestamp = msg.getTimestamp();
    this.system = msg.getSystem();
    this.type = msg.getType();
    this.message = msg.getMessage();
  } // End constructor

  /**
   * Creates a serializable message from the given parameters.
   * 
   * @param system The system name.
   * @param type The type of message.
   * @param timestamp The timestamp of the message.
   * @param message The message content.
   */
  public SerializableMessage(long timestamp, IHaleSystem system, SystemStatusMessageType type,
      String message) {
    this.timestamp = timestamp;
    this.system = system;
    this.type = type;
    this.message = message;
  } // End Constructor

  /**
   * Returns the timestamp.
   * 
   * @return The timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Returns the system.
   * 
   * @return The system.
   */
  public IHaleSystem getSystem() {
    return system;
  }

  /**
   * Returns the message type.
   * 
   * @return The message type.
   */
  public SystemStatusMessageType getType() {
    return type;
  }

  /**
   * Returns the message itself.
   * 
   * @return The message.
   */
  public String getMessage() {
    return message;
  }

} // End Serializable Message class
