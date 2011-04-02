package edu.hawaii.ihale.frontend.page.messages;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * Tests the Messages and MessageListener Class.
 * 
 * @author Frontend
 * 
 */
public class TestMessages {
  static MessagesListener msgListener;
  static SolarDecathlonApplication sda;

  /**
   * Setup for Testing.
   * @throws Exception e.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    sda = new SolarDecathlonApplication();
    msgListener = SolarDecathlonApplication.getMessagesListener();

    // Get Current Time and set backoff variables.
    long currentTime = new Date().getTime();
    long min = 60 * 1000;
    long hour = min * 60;
    long day = hour * 24;

    // Test Message
    String msg = "Test Message";

    // Populate lists
    msgListener
        .messageAdded(currentTime, IHaleSystem.AQUAPONICS, SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - min, IHaleSystem.AQUAPONICS,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - hour, IHaleSystem.AQUAPONICS,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - day, IHaleSystem.AQUAPONICS,
        SystemStatusMessageType.INFO, msg);

    msgListener.messageAdded(currentTime, IHaleSystem.HVAC, SystemStatusMessageType.INFO, msg);
    msgListener
        .messageAdded(currentTime - min, IHaleSystem.HVAC, SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - hour, IHaleSystem.HVAC, SystemStatusMessageType.INFO,
        msg);
    msgListener
        .messageAdded(currentTime - day, IHaleSystem.HVAC, SystemStatusMessageType.INFO, msg);
    
    msgListener
    .messageAdded(currentTime, IHaleSystem.LIGHTING, SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - min, IHaleSystem.LIGHTING,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - hour, IHaleSystem.LIGHTING,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - day, IHaleSystem.LIGHTING,
        SystemStatusMessageType.INFO, msg);
    
    msgListener
    .messageAdded(currentTime, IHaleSystem.ELECTRIC, SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - min, IHaleSystem.ELECTRIC,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - hour, IHaleSystem.ELECTRIC,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - day, IHaleSystem.ELECTRIC,
        SystemStatusMessageType.INFO, msg);
    
    msgListener
    .messageAdded(currentTime, IHaleSystem.PHOTOVOLTAIC, SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - min, IHaleSystem.PHOTOVOLTAIC,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - hour, IHaleSystem.PHOTOVOLTAIC,
        SystemStatusMessageType.INFO, msg);
    msgListener.messageAdded(currentTime - day, IHaleSystem.PHOTOVOLTAIC,
        SystemStatusMessageType.INFO, msg);
  } // Test Setup

  /**
   * Tests the getMessages method.
   */
  @Test
  public void testGetMessages() {
    assertEquals("Check AQUAPONICS List", 4,
        SolarDecathlonApplication.getMessages().getMessages(IHaleSystem.AQUAPONICS).size() - 1);
    assertEquals("Check HVAC List", 4,
        SolarDecathlonApplication.getMessages().getMessages(IHaleSystem.HVAC).size() - 1);
    assertEquals("Check LIGHTING List", 4,
        SolarDecathlonApplication.getMessages().getMessages(IHaleSystem.LIGHTING).size() - 1);
  }

  /**
   * Tests the getAllMessages method.
   */
  @Test
  public void testGetAllMessages() {
    assertEquals("Check Dashboard List", 20,
        SolarDecathlonApplication.getMessages().getAllMessages().size() - 1);
  }

  /**
   * Tests the getElectricalMessages method.
   */
  @Test
  public void testGetElectricalMessages() {
    assertEquals("Check Energy List", 8,
        SolarDecathlonApplication.getMessages().getElectricalMessages().size() - 1);  
  }
} // End test class.
