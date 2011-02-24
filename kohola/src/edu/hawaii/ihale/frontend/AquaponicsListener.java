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
    Long airTemp, waterTemp;
    Double pH, ec;
    try {
      if ((airTemp = entry.getLongValue("airTemp")) != null) {
        AquaPonics.setAirTemp(airTemp);
      }
      if ((waterTemp = entry.getLongValue("waterTemp")) != null) {
        AquaPonics.setWaterTemp(waterTemp);
      }
      if ((pH = entry.getDoubleValue("pH")) != null) {
        AquaPonics.setPH(pH);
      }
      if ((ec = entry.getDoubleValue("ec")) != null) {
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
