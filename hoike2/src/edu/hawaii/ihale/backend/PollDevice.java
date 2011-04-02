package edu.hawaii.ihale.backend;

import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * A Class to handle the polling of data from the HSim.
 * @author Tony Gaskell, Greg Burgess
 *
 */
public class PollDevice implements Runnable {
  
  private IHaleSystem system;
  private IHaleRoom room;
  private ClientResource resource;
  
  /**
   * Constructor.
   * @param system The System which this object will poll data from.
   * @param room The room (if applicable) this object will poll data from.
   * @param domain The root URI of the device.
   */
  public PollDevice(IHaleSystem system, IHaleRoom room, String domain) {
    this.system = system;
    this.room = room;
    String state = "/state";
    String uriString = "";
    if (system == IHaleSystem.ELECTRIC) {
      uriString = domain + "electrical" + state;
    }
    else if (system == IHaleSystem.PHOTOVOLTAIC) {
      uriString = domain + "photovoltaics" + state;
    }
    else {
      uriString = domain + system.toString().toLowerCase() + state;
      if (domain == null) {
        System.out.println(system.toString() + " is null");
      }
    }
    this.resource = new ClientResource(uriString); 
  }

  /**
   * Activates the thread.
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
        Representation rep = resource.get();
        if (rep == null) {
          System.out.println(system.toString() + " is null"); 
        }
        handler.eGauge2StateEntry(rep);
      }
    }
    catch (XPathExpressionException e) {
      System.err.print("Error polling: " + system.toString());
      if (room != null ) {
        System.err.print(" " + room.toString());
      }
      System.out.println();
    }
    catch (IOException e) {
      System.err.print("Error polling: " + system.toString());
      if (room != null ) {
        System.err.print(" " + room.toString());
      }
      System.out.println();
    }
  }
}

