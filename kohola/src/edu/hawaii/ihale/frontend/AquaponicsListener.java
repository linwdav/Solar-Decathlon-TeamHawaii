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

  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("Aquaponics");
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * 
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);
    long temp;
    Double pH, ec;
    try {
      if ((temp = entry.getLongValue("Temp")) != -1) {
        AquaPonics.setTemp(temp);
      }
      if ((pH = entry.getDoubleValue("pH")) != -1) {
        AquaPonics.setPH(pH);
      }
      if ((ec = entry.getDoubleValue("ec")) != -1) {
        AquaPonics.setEC(ec);
      }

    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println("AquaPonicsListener: entryAdded");
      e.printStackTrace();
    }

  }

}
