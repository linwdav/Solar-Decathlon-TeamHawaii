package edu.hawaii.ihale.frontend;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener for Hvac that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @author Chuan Lun Hung
 * @author Kylan Hughes
 */
public class HvacListener extends SystemStateListener {
  
  private long temp = -1;
  
  /**
   * Provide a default constructor that indicates that this listener is for Hvac.
   */
  public HvacListener() {
    super("hvac");
  }

  /**
   * Invoked whenever a new state entry for Hvac is received by the system.
   * @param entry A SystemStateEntry for the Hvac system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Hvac: " + entry);
    if (entry.getLongValue("Temp") != -1) {
      //Header.setInsideTemp(entry.getLongValue("Temp"));
      temp = entry.getLongValue("Temp");
    }    
  }
  
  /**
   * The temp.
   * @return The temp
   */
  public long getTemp() {
    return temp;
  }
}
