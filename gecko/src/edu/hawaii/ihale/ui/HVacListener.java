package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.wicket.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.wicket.model.HVacModel;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @revised Shoji Bravo
 */
public class HVacListener extends SystemStateListener {

  private AjaxDatabaseUpdate databaseUpdate;
  private HVacModel model;

  /**
   * Provide a default constructor that indicates that this listener is for HVac.
   */
  public HVacListener() {
    super("HVAC");
    this.databaseUpdate = new AjaxDatabaseUpdate();
    this.model = new HVacModel();
  }

  /**
   * Invoked whenever a new state entry for HVac is received by the system.
   * 
   * @param entry A SystemStateEntry for the HVac system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in HVac: " + entry);
    model.setTimestamp(entry.getTimestamp());
    model.setTemp(entry.getLongValue("Temp"));

    try {
      databaseUpdate.onRequest();
    }
    catch (Exception e) {
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
   * Gets this HVac model.
   * 
   * @return HVacModel
   */
  public HVacModel getModel() {
    return model;
  }
}
