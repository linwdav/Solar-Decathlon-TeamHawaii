package edu.hawaii.ihale.backend;

import java.io.IOException;
import java.util.logging.Level;
import javax.xml.xpath.XPathExpressionException;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * This class defines each IHale system device in order to parse its XML state correctly. The state
 * is then processed by the XMLHandler.
 * 
 * @author Tony Gaskell
 */
public class PollDevice {

  private IHaleSystem system;
  private ClientResource resource;

  /**
   * Constructor method. This links the IHaleSystem device to its URL in order to correctly parse
   * that System's XML state.
   * 
   * @param system the system of the device.
   * @param domain the URI domain.
   */
  public PollDevice(IHaleSystem system, String domain) {
    this.system = system;
    if (system.equals(IHaleSystem.AQUAPONICS) || system.equals(IHaleSystem.HVAC)
        || system.equals(IHaleSystem.LIGHTING)) {
      this.resource = new ClientResource(domain + system.toString().toLowerCase() + "/state");
    }
    else if (system.equals(IHaleSystem.ELECTRIC) || system.equals(IHaleSystem.PHOTOVOLTAIC)) {
      this.resource = new ClientResource(domain + "cgi-bin/egauge?tot");
    }
    resource.getLogger().setLevel(Level.OFF);
  }

  /**
   * Polls the IHale system's device using the XmlHandler's xml2StateEntry or eGauge2StateEntry.
   */
  public void poll() {
    XmlHandler handler = new XmlHandler();

    // Check which system is being polled to ensure the correct parser is called.
    try {
      if (system.equals(IHaleSystem.AQUAPONICS) || system.equals(IHaleSystem.HVAC)
          || system.equals(IHaleSystem.LIGHTING)) {
        handler.parseIhaleXml2StateEntry(resource.get());
      }
      else if (system.equals(IHaleSystem.ELECTRIC) || system.equals(IHaleSystem.PHOTOVOLTAIC)) {
        handler.parseEGaugeXmlToStateEntry(resource.get());
      }
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
}
