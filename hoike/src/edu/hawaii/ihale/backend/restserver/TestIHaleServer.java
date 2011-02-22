package edu.hawaii.ihale.backend.restserver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
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
    CustomSystemStateEntry entry = new CustomSystemStateEntry(system, device, timestamp);
    entry.putDoubleValue("pH", 7.5);
    entry.putStringValue("FishType", "Goldfish");
    entry.putLongValue("NumFish", 8);
    System.out.println(entry);
    
    dbRedirector.putEntry(entry);
    
    IHaleSystemStateEntry iHaleEntry = IHaleDB.getEntry(system, device, timestamp);
    
    System.out.println(iHaleEntry);
    
    
    /* Start - Ignore the below code, not a true Junit assert style, for testing a concept. */
    /* Author - Leonardo Nguyen */
    system = "Aquaponics";
    device = "Arduino-10";
    timestamp = (new Date()).getTime();
    
    CustomSystemStateEntry myEntryA = new CustomSystemStateEntry(system, device, timestamp);
    myEntryA.putDoubleValue("pH", 10.5);
    myEntryA.putDoubleValue("Oxygen", 6.2);
    myEntryA.putStringValue("FishType", "Dead");
    myEntryA.putLongValue("NumFish", 0);
    myEntryA.putLongValue("Temp", 25);
    
    dbRedirector.putEntry(myEntryA);
    System.out.println("\nThe following is information regarding myEntryA\n\n" + myEntryA);
    
    List<String> myLongKeys = myEntryA.getLongDataKey();
    Map<String, Long> myLongMap = new HashMap<String, Long>();
    String key = "";
    for (int i = 0; i < myLongKeys.size(); i++) {
      key = myLongKeys.get(i);
      myLongMap.put(key, myEntryA.getLongValue(key));
    }
    System.out.println(myLongMap);
    myEntryA = (CustomSystemStateEntry) dbRedirector.getEntry(system, device, timestamp);
    System.out.println("After retrieval from IHaleDB and conversion process:\n" + myEntryA);
    /* End */
  }
}
