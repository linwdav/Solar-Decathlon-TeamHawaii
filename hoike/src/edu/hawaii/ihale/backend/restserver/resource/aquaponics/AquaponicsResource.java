package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.backend.db.IHaleDB;

/**
 * The aquaponics resource.
 *
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class AquaponicsResource extends ServerResource {
  
  /**
   * Needs to be commented. 
   * @return Needs to be commented. 
   */
  @Get
  public String getYear() {
    return String.valueOf("This is the aquaponics resource!");
  }
  
  /**
   * 
   */
  @Put
  public void storeEntry() {
    long timestamp = 111111111;
    SystemStateEntry entry = new SystemStateEntry("Aquaponics", "Arduino-23", timestamp);
    SystemStateEntryDB db = new IHaleDB();
    db.putEntry(entry);
  }
}
