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
  static List<SystemStatusMessage> allMessages;
  static List<SystemStatusMessage> aquaponicsMessages;
  static List<SystemStatusMessage> hvacMessages;
  static List<SystemStatusMessage> electricMessages;
  static List<SystemStatusMessage> photovoltaicMessages;
  static List<SystemStatusMessage> lightingMessages;

  /**
   * Creates linked lists that hold messages for each system.
   */
  public Messages () {
	  allMessages = new LinkedList<SystemStatusMessage>();
	  aquaponicsMessages = new LinkedList<SystemStatusMessage>();
	  hvacMessages = new LinkedList<SystemStatusMessage>();
	  electricMessages = new LinkedList<SystemStatusMessage>();
	  photovoltaicMessages = new LinkedList<SystemStatusMessage>();
	  lightingMessages = new LinkedList<SystemStatusMessage>();
  } // End Constructor
  
  /**
   * Returns the corresponding list of messages based on the system
   * being requested.
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
  
  public static String messagesToString(List<SystemStatusMessage> list) {
	  String returnString = "";
	  for (SystemStatusMessage msg : list) {
		  returnString += "System: " + msg.getSystem();
		  returnString += " Time: " + new Date(msg.getTimestamp());
		  returnString += " Message: " + msg.getMessage() + "\n";
	  }
	  returnString += "\n";
	  
	  for (SystemStatusMessage msg : aquaponicsMessages) {
		  returnString += "System: " + msg.getSystem();
		  returnString += " Time: " + new Date(msg.getTimestamp());
		  returnString += " Message: " + msg.getMessage() + "\n";
	  }
	  
	  returnString += "\n";
	  
	  for (SystemStatusMessage msg : hvacMessages) {
		  returnString += "System: " + msg.getSystem();
		  returnString += " Time: " + new Date(msg.getTimestamp());
		  returnString += " Message: " + msg.getMessage() + "\n";
	  }
	  
	  returnString += "\n";
	  
	  for (SystemStatusMessage msg : lightingMessages) {
		  returnString += "System: " + msg.getSystem();
		  returnString += " Time: " + new Date(msg.getTimestamp());
		  returnString += " Message: " + msg.getMessage() + "\n";
	  }

	  
	  return returnString;
  }
  
  
} // End Messages Class
