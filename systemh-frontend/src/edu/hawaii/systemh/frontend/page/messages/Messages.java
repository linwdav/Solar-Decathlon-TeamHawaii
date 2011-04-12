package edu.hawaii.systemh.frontend.page.messages;

import java.util.LinkedList;
import java.util.List;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;

/**
 * Implements a message class to handle all system log functions.
 * 
 * @author Frontend
 * 
 */
public class Messages {

  // Different set of lists to store messages for each page/system
  List<SystemStatusMessage> allMessages;
  List<SystemStatusMessage> aquaponicsMessages;
  List<SystemStatusMessage> hvacMessages;
  
  // Electrical messags combines photovoltaic and electrical systems
  List<SystemStatusMessage> electricalMessages;
  List<SystemStatusMessage> lightingMessages;

  /**
   * Creates linked lists that hold messages for each system.
   */
  public Messages() {
    // Empty Message to allow default message to appear
    SystemStatusMessage empty = new SystemStatusMessage((long) 0, null, null, null);

    allMessages = new LinkedList<SystemStatusMessage>();
    aquaponicsMessages = new LinkedList<SystemStatusMessage>();
    hvacMessages = new LinkedList<SystemStatusMessage>();
    electricalMessages = new LinkedList<SystemStatusMessage>();
    lightingMessages = new LinkedList<SystemStatusMessage>();

    // Add initial empty message. Allows for displaying of default text
    allMessages.add(empty);
    aquaponicsMessages.add(empty);
    hvacMessages.add(empty);
    electricalMessages.add(empty);
    lightingMessages.add(empty);

  } // End Constructor

  /**
   * Returns the corresponding list of messages based on the system being requested.
   * 
   * @param system The system requested.
   * @return List of all messages for the system requested.
   */
  public List<SystemStatusMessage> getMessages(IHaleSystem system) {
    switch (system) {
    case AQUAPONICS:
      return aquaponicsMessages;
    case HVAC:
      return hvacMessages;
    case LIGHTING:
      return lightingMessages;
    default:
      return null;
    }
  } // End GetMessages

  /**
   * Accessor method to the list containing all messages.
   * 
   * @return List of all messages.
   */
  public List<SystemStatusMessage> getAllMessages() {
    return allMessages;
  }
  
  /**
   * Accessor method for the electrical messages.  This is necessary
   * due to photovoltaic and electrical systems being on the same 
   * page.
   * 
   * @return The messages for both photovoltaic and electrical systems.
   */
  public List<SystemStatusMessage> getElectricalMessages() {
    return electricalMessages;
  }
} // End Messages Class
