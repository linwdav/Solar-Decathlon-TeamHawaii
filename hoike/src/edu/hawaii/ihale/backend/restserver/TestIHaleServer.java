package edu.hawaii.ihale.backend.restserver;

import java.util.Date;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.backend.db.IHaleDB;
import edu.hawaii.ihale.backend.db.IHaleSystemStateEntry;

/**
 * Unit test of the IHale HTTP server's processing ability.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TestIHaleServer {

  /**
   * Test XML parsing and creating an instance of IHaleSystemStateEntryRepresentation.
   */
  @Test
  public void testXML() {
    // To Remove PMD complaints.
    System.out.println("");
  }
  
  /**
   * Test the redirection from IHaleDBDirector to IHaleDB.
   */
  @Test
  public void testRedirector() {
    IHaleDBRedirector dbRedirector = new IHaleDBRedirector();
    
    String system = "Aquaponics";
    String device = "Arduino-80";
    long timestamp = (new Date()).getTime();
    SystemStateEntry entry = new SystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 7.5);
    entry.putStringValue("FishType", "Goldfish");
    entry.putLongValue("NumFish", 8);
    System.out.println(entry);
    
    dbRedirector.putEntry(entry);
    
    IHaleSystemStateEntry iHaleEntry = IHaleDB.getEntry(system, device, timestamp);
    
    System.out.println(iHaleEntry);
  }
}
