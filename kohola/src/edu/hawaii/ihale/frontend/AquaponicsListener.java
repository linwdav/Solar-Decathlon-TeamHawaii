package edu.hawaii.ihale.frontend;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener for aquaponics that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsListener extends SystemStateListener {

  private long temp = -1;
  private Double pH = -1.0, oxygen = -1.0;
  
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("aquaponics");
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * 
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);
    try {
      if (entry.getLongValue("Temp") != -1) {        
        temp = entry.getLongValue("Temp"); 
      }
      if (entry.getDoubleValue("pH") != -1.0) {
        pH = entry.getDoubleValue("pH"); 
      }
      if (entry.getDoubleValue("Oxygen") != -1.0) {
        oxygen = entry.getDoubleValue("Oxygen"); 
      }

    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println("AquaPonicsListener: entryAdded");
      e.printStackTrace();
    }

  }
  
  /**
   * The temp.
   * @return The temp
   */
  public long getTemp() {
    return temp;
  }

  /**
   * The ph.
   * @return The ph
   */
  public double getPH() {
    return pH;
  }
  
  /**
   * The oxygen.
   * @return The oxygen
   */
  public double getOxygen() {
    return oxygen;
  }
  
}
