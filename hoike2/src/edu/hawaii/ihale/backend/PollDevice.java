package edu.hawaii.ihale.backend;

import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

public class PollDevice extends Thread {
  
  private IHaleSystem system;
  private IHaleRoom room;
  private ClientResource resource;
  
  /**
   * Constructor method.
   * @author Tony Gaskell
   */
  public PollDevice(IHaleSystem system, IHaleRoom room, String domain) {
    this.system = system;
    this.room = room;
    this.resource = new ClientResource(domain + "/" + system.toString().toLowerCase() + "/state");
  }

  /**
   * Polls a device given it's system (and room).
   * @author Tony Gaskell
   * @param IHaleSystem the system of the device.
   * @param IHaleRoom the room of the device, null if none.
   * @return boolean representing if poll was successful or not.
   */
  @Override
  public void run() {
    XmlHandler handler = new XmlHandler();
    try {
      // Check which system is being polled to ensure the correct parser is called.
      if (system.equals(IHaleSystem.AQUAPONICS)
          || system.equals(IHaleSystem.HVAC)
          || system.equals(IHaleSystem.LIGHTING)) {
        handler.xml2StateEntry(resource.get());
      }
      else if (system.equals(IHaleSystem.ELECTRIC)
          || system.equals(IHaleSystem.PHOTOVOLTAIC)) {
        handler.eGauge2StateEntry(resource.get());
      }
    }
    catch (Exception e) {
      System.err.print("Error polling: " + system.toString());
      if (room != null ) {
        System.err.print(" " + room.toString());
      }
      System.out.println();
    }
  }
}

