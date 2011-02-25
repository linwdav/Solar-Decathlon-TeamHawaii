package edu.hawaii.ihale.backend.resources;

import java.util.Date;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.backend.rest.IHaleDAO;

/**
 * A server resource that will handle requests for the HVAC system.
 * Supported operations: GET and PUT.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class HvacResource extends ServerResource {

  private String system = "hvac";
  private String device = "device-03";

  /**
   * Returns the current state of the hvac system.
   * In the actual implementation, an XML representation will be returned instead of a String.
   * @return Current state of the hvac system.
   */
  @Get
  public String getHvacState() {
    return "This is the HVAC resource!";
  }

  /**
   * Commands an HVAC device to set the temperature to a new value.
   * @param temperature The temperature.
   */
  @Put
  public void setTemp(Long temperature) {
    IHaleDAO dao = new IHaleDAO();
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putLongValue("Temp", 70);
    dao.putEntry(entry);
  }
}
