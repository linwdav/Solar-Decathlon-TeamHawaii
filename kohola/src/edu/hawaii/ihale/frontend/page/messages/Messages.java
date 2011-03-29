package edu.hawaii.ihale.frontend.page.messages;

import java.util.LinkedList;
import java.util.List;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * Implements a message class to handle all system log functions.
 * 
 * @author Frontend
 * 
 */
public class Messages {

  // Different set of lists to store messages for each page/system
  List<SerializableMessage> allMessages;
  List<SerializableMessage> aquaponicsMessages;
  List<SerializableMessage> hvacMessages;
  List<SerializableMessage> electricMessages;
  List<SerializableMessage> photovoltaicMessages;
  List<SerializableMessage> lightingMessages;

  /**
   * Creates linked lists that hold messages for each system.
   */
  public Messages() {
    // Empty Message to allow default message to appear
    SerializableMessage empty = new SerializableMessage(0, null, null, null);

    allMessages = new LinkedList<SerializableMessage>();
    aquaponicsMessages = new LinkedList<SerializableMessage>();
    hvacMessages = new LinkedList<SerializableMessage>();
    electricMessages = new LinkedList<SerializableMessage>();
    photovoltaicMessages = new LinkedList<SerializableMessage>();
    lightingMessages = new LinkedList<SerializableMessage>();

    // Add initial empty message. Allows for displaying of default text
    allMessages.add(empty);
    aquaponicsMessages.add(empty);
    hvacMessages.add(empty);
    electricMessages.add(empty);
    photovoltaicMessages.add(empty);
    lightingMessages.add(empty);

  } // End Constructor

  /**
   * Returns the corresponding list of messages based on the system being requested.
   * 
   * @param system The system requested.
   * @return List of all messages for the system requested.
   */
  public List<SerializableMessage> getMessages(IHaleSystem system) {
    switch (system) {
    case AQUAPONICS:
      return aquaponicsMessages;
    case HVAC:
      return hvacMessages;
    case PHOTOVOLTAIC:
      return photovoltaicMessages;
    case ELECTRIC:
      return electricMessages;
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
  public List<SerializableMessage> getAllMessages() {
    return allMessages;
  }
} // End Messages Class
