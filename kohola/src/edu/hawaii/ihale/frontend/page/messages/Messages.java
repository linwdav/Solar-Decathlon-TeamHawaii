package edu.hawaii.ihale.frontend.page.messages;

import java.util.Date;
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
  static List<SystemStatusMessage> allMessages = new LinkedList<SystemStatusMessage>();
  static List<SystemStatusMessage> aquaponicsMessages = new LinkedList<SystemStatusMessage>();;
  static List<SystemStatusMessage> hvacMessages = new LinkedList<SystemStatusMessage>();;
  static List<SystemStatusMessage> electricMessages = new LinkedList<SystemStatusMessage>();;
  static List<SystemStatusMessage> photovoltaicMessages = new LinkedList<SystemStatusMessage>();;
  static List<SystemStatusMessage> lightingMessages = new LinkedList<SystemStatusMessage>();;

  /**
   * Creates linked lists that hold messages for each system.
   */
  public Messages() {
//    allMessages = new LinkedList<SystemStatusMessage>();
//    aquaponicsMessages = new LinkedList<SystemStatusMessage>();
//    hvacMessages = new LinkedList<SystemStatusMessage>();
//    electricMessages = new LinkedList<SystemStatusMessage>();
//    photovoltaicMessages = new LinkedList<SystemStatusMessage>();
//    lightingMessages = new LinkedList<SystemStatusMessage>();
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
  public List<SystemStatusMessage> getAllMessages() {
    return allMessages;
  }

  /**
   * Returns the list as a String.
   * 
   * @param list The list of messages.
   * @return The messages.
   */
  public static String messagesToString(List<SystemStatusMessage> list) {
    
    String system = "System: ";
    String time = " Time: ";
    String message = " Message: ";
    String newLine = "\n";
    StringBuffer buf = new StringBuffer();
    
    for (SystemStatusMessage msg : list) {
      buf.append(system);
      buf.append(msg.getSystem());
      buf.append(time);
      buf.append(new Date(msg.getTimestamp()));
      buf.append(message);
      buf.append(msg.getMessage());
      buf.append(newLine);
    }
    buf.append(newLine);

    for (SystemStatusMessage msg : aquaponicsMessages) {
      buf.append(system);
      buf.append(msg.getSystem());
      buf.append(time);
      buf.append(new Date(msg.getTimestamp()));
      buf.append(message);
      buf.append(msg.getMessage());
      buf.append(newLine);
    }
    buf.append(newLine);

    for (SystemStatusMessage msg : hvacMessages) {
      buf.append(system);
      buf.append(msg.getSystem());
      buf.append(time);
      buf.append(new Date(msg.getTimestamp()));
      buf.append(message);
      buf.append(msg.getMessage());
      buf.append(newLine);
    }    
    buf.append(newLine);

    for (SystemStatusMessage msg : lightingMessages) {
      buf.append(system);
      buf.append(msg.getSystem());
      buf.append(time);
      buf.append(new Date(msg.getTimestamp()));
      buf.append(message);
      buf.append(msg.getMessage());
      buf.append(newLine);
    }    
    buf.append(newLine);
    return buf.toString();
  }

} // End Messages Class
