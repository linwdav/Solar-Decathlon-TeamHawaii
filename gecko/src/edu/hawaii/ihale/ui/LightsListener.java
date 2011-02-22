package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.wicket.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.wicket.model.LightsModel;


/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @revised Shoji Bravo
 * @revised Bret Ikehara
 */
public class LightsListener extends SystemStateListener {

    private AjaxDatabaseUpdate databaseUpdate;
    private LightsModel model;
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public LightsListener() {
    super("Lights");
    this.databaseUpdate = new AjaxDatabaseUpdate();
    this.model = new LightsModel();
  }

  /**
   * Invoked whenever a new state entry for Lights is received by the system.
   * @param entry A SystemStateEntry for the Lights system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Lights: " + entry);
    
    model.setLivingRoom(entry.getLongValue("LivingRoom"));
    model.setDiningRoom(entry.getLongValue("DiningRoom"));
    model.setKitchenRoom(entry.getLongValue("KitchenRoom"));
    model.setBathroom(entry.getLongValue("Bathroom"));
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
   * Gets this Lights model.
   * 
   * @return LightsModel
   */
  public LightsModel getModel() {
    return model;
  }
}
