package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.wicket.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.wicket.model.ElectricityModel;

/**
 * A listener that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 */
public class ElectricalListener extends SystemStateListener {

  private AjaxDatabaseUpdate databaseUpdate;
  private ElectricityModel model;

  /**
   * Provide a default constructor that indicates that this listener is for Water.
   */
  public ElectricalListener() {
    super("Electrical");
    this.databaseUpdate = new AjaxDatabaseUpdate();
    this.model = new ElectricityModel();
  }

  /**
   * Invoked whenever a new state entry for Water is received by the system.
   * @param entry A SystemStateEntry for the Water system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Electricity: " + entry);
    
    this.model.setEnergy(entry.getLongValue("energy"));
    this.model.setPower(entry.getLongValue("power"));

    try {
      databaseUpdate.onRequest();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets this AjaxDatabaseUpdate event.
   * 
   * @return AjaxDatabaseUpdate
   */
  public AjaxDatabaseUpdate getDatabaseUpdate() {
    return databaseUpdate;
  }

  /**
   * Gets this Electricity Model.
   * 
   * @return ElectricityModel
   */
  public ElectricityModel getModel() {
    return model;
  }
}
