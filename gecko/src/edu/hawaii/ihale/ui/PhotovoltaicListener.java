package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.wicket.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.wicket.model.ElectricityModel;

/**
 * A listener that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @revised Shoji Bravo
 */
public class PhotovoltaicListener extends SystemStateListener {
    private AjaxDatabaseUpdate databaseUpdate;
    private ElectricityModel model;
  
  /**
   * Provide a default constructor that indicates that this listener is for Photovoltaics.
   */
  public PhotovoltaicListener() {
    super("Photovoltaics");
    this.databaseUpdate = new AjaxDatabaseUpdate();
    this.model = new ElectricityModel();
  }

  /**
   * Invoked whenever a new state entry for PV is received by the system.
   * @param entry A SystemStateEntry for the PV system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in PV: " + entry);

    model.setTimestamp(entry.getTimestamp());
    model.setEnergy(entry.getDoubleValue("power"));
    model.setPower(entry.getDoubleValue("energy"));

    databaseUpdate.onRequest();
    
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
   * Gets this Photovoltaics model.
   * 
   * @return PhotovoltaicssModel
   */
  public ElectricityModel getModel() {
    return model;
  }
}
