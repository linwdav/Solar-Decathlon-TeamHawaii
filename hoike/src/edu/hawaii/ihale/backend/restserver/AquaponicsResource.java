package edu.hawaii.ihale.backend.restserver;

import java.util.Date;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * A server resource that will handle requests for the aquaponics system.
 * Supported operations: GET and PUT.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class AquaponicsResource extends ServerResource {
  
  private String system = "aquaponics";
  private String device = "device-01";
  
  /**
   * Returns the current state of the aquaponics system. 
   * @return Current state of the aquaponics system. 
   */
  @Get
  public String getAquaponicsState() {
    
    return "This is the aquaponics resource!";
  }
  
  /**
   * Commands an aquaponics device to set the temperature to a new value.
   * @param temperature The temperature.
   */
  @Put
  public void setTemp(Long temperature) {
    IHaleDAO dao = new IHaleDAO();
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putLongValue("Temp", 25);
    dao.putEntry(entry);
  }
}
