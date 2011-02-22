package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.wicket.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.wicket.model.AquaponicsModel;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @revised Shoji Bravo
 * @revised Bret Ikehara
 */
public class AquaponicsListener extends SystemStateListener {

  private AjaxDatabaseUpdate databaseUpdate;
  private AquaponicsModel model;

  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("Aquaponics");
    this.databaseUpdate = new AjaxDatabaseUpdate();
    this.model = new AquaponicsModel();
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * 
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);

    model.setTimestamp(entry.getTimestamp());
    model.setPh(entry.getDoubleValue("pH"));
    model.setTemp(entry.getLongValue("Temp"));
    model.setOxygen(entry.getDoubleValue("Oxygen"));

    try {
      databaseUpdate.onRequest();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets this AjaxDatabaseUpdate ajax object.
   * 
   * @return AjaxDatabaseUpdate
   */
  public AjaxDatabaseUpdate getDatabaseUpdate() {
    return databaseUpdate;
  }

  /**
   * Gets this Aquaponics model.
   * 
   * @return AquaponicsModel
   */
  public AquaponicsModel getModel() {
    return model;
  }
}
