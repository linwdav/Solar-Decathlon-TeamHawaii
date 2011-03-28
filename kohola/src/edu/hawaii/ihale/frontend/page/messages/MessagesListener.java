package edu.hawaii.ihale.frontend.page.messages;

import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.SystemStatusMessageListener;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

public class MessagesListener extends SystemStatusMessageListener {

	@Override
	public void messageAdded(Long timestamp, IHaleSystem system,
			SystemStatusMessageType messageType, String msg) {

		// Convert into a status message object
		SystemStatusMessage statusMessage = new SystemStatusMessage(timestamp, 
				system, messageType, msg);
		
		// Append it to the list that holds all messages (for the dashboard)
		SolarDecathlonApplication.getMessages().getAllMessages()
				.add(statusMessage);
				
		// Adds the message to the correct system page
		SolarDecathlonApplication.getMessages().getMessages(system)
				.add(statusMessage);
		
		String message = String.format("Received this %s message: %s", system, msg);
		System.out.println(message);
	}

} // End Messages Listener Class
