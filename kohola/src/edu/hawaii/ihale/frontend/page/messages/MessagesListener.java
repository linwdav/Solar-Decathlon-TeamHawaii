package edu.hawaii.ihale.frontend.page.messages;

import java.util.List;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.repository.SystemStatusMessageListener;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * Listens for message events.
 * 
 * @author FrontEnd
 * 
 */
public class MessagesListener extends SystemStatusMessageListener {

  @Override
  public void messageAdded(Long timestamp, IHaleSystem system, SystemStatusMessageType messageType,
      String msg) {

    // Convert into a status message object
    SerializableMessage statusMessage =
        new SerializableMessage(timestamp, system, messageType, msg);

    // All messages
    List<SerializableMessage> allMsgs = SolarDecathlonApplication.getMessages().getAllMessages();

    // Messages only pertaining to this system
    List<SerializableMessage> thisSystemMsgs =
        SolarDecathlonApplication.getMessages().getAllMessages();

    // If this is the first message, then remove the "No Messages" default message
    if (allMsgs.size() == 1) {
      allMsgs.remove(0);
    }
    if (thisSystemMsgs.size() == 1) {
      thisSystemMsgs.remove(0);
    }

    // Append it to the front of the list that holds all messages (for the dashboard)
    allMsgs.add(0, statusMessage);

    // Adds the message to the correct system page (latest goes to the front of list)
    thisSystemMsgs.add(0, statusMessage);

  } // End message added method

} // End Messages Listener Class
