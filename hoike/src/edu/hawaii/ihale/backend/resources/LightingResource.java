package edu.hawaii.ihale.backend.resources;

import java.util.Date;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.backend.rest.IHaleDAO;

/**
 * A server resource that will handle requests for the lighting system.
 * Supported operations: GET and PUT.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class LightingResource extends ServerResource {

  private String system = "lighting";
  private String device = "device-05";

  /**
   * Returns the current state of the lighting system.
   * In the actual implementation, an XML representation will be returned instead of a String.
   * @return Current state of the lighting system.
   */
  @Get
  public String getLightingState() {
    return "This is the lighting resource!";
  }

  /**
   * Commands an lighting device to set the brightness to a new value.
   * @param brightness The brightness.
   */
  @Put
  public void setLevel(Double brightness) {
    IHaleDAO dao = new IHaleDAO();
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("Brightness", 100.00);
    dao.putEntry(entry);
  }
}
