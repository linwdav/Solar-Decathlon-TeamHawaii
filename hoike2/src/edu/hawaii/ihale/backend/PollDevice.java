package edu.hawaii.ihale.backend;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;


/**
 * Class for each polling device between the System H simulator and backend.
 * @author Tony Gaskell
 */
public class PollDevice extends Thread {
  
  private IHaleSystem system;
  private IHaleRoom room;
  private ClientResource resource;
  
  /**
   * Constructor method.
   * @author Tony Gaskell
   * @param system the system of the device.
   * @param room the room of the device, null if none.
   * @param domain the URI domain.
   */
  public PollDevice(IHaleSystem system, IHaleRoom room, String domain) {
    this.system = system;
    this.room = room;
    if (system.equals(IHaleSystem.AQUAPONICS)
        || system.equals(IHaleSystem.HVAC) 
        || system.equals(IHaleSystem.LIGHTING)) {
      this.resource = new ClientResource(domain + system.toString().toLowerCase() + "/state");
    }
    else if (system.equals(IHaleSystem.ELECTRIC) || system.equals(IHaleSystem.PHOTOVOLTAIC)) {
      this.resource = new ClientResource(domain + "cgi-bin/egauge?tot");
    }
  }

  /**
   * Polls a device given it's system (and room).
   * @author Tony Gaskell
   */
  @Override
  public void run() {
    XmlHandler handler = new XmlHandler();

      // Check which system is being polled to ensure the correct parser is called.
      if (system.equals(IHaleSystem.AQUAPONICS)
          || system.equals(IHaleSystem.HVAC)
          || system.equals(IHaleSystem.LIGHTING)) {
        try {
          handler.xml2StateEntry(resource.get());
        }
        catch (XPathExpressionException e) {
          System.err.println("There was an error parsing the XML file.");
          e.printStackTrace();
        }
        catch (ResourceException e) {
          System.err.println("There retrieving " + system.toString() + " data.");
          e.printStackTrace();
        }
        catch (IOException e) {
          System.err.println("IO exception.");
          e.printStackTrace();
        }
      }
      else if (system.equals(IHaleSystem.ELECTRIC)
          || system.equals(IHaleSystem.PHOTOVOLTAIC)) {
        try {
          handler.eGauge2StateEntry(resource.get());
        }
        catch (XPathExpressionException e) {
          System.err.println("There was an error parsing the XML file.");
          e.printStackTrace();
        }
        catch (ResourceException e) {
          System.err.println("There retrieving " + system.toString() + " data.");
          e.printStackTrace();
        }
        catch (IOException e) {
          System.err.println("IO exception.");
          e.printStackTrace();
        }
      }

      System.err.print("Error polling: " + system.toString());
      if (room != null ) {
        System.err.print(" " + room.toString());
      }
      System.out.println();

  }
}

