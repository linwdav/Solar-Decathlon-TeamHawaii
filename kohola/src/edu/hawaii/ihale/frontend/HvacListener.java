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
  
  private static final String SYSTEM_NAME = "hvac";
  private static final String TEMP_KEY = "temp";
  
  private long temp = -1;
  
  /**
   * Provide a default constructor that indicates that this listener is for Hvac.
   */
  public HvacListener() {
    super(SYSTEM_NAME);
  }

  /**
   * Invoked whenever a new state entry for Hvac is received by the system.
   * @param entry A SystemStateEntry for the Hvac system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Hvac: " + entry);
    if (entry.getLongValue(TEMP_KEY) != -1) {  
      temp = entry.getLongValue(TEMP_KEY);
    }    
  }
  
  /**
   * The house temperature.
   * @return The temperature in Fahrenheit.
   */
  public long getTemp() {
    return temp;
  }
}
