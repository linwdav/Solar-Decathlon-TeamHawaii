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
public class LightingListener extends SystemStateListener {

    private AjaxDatabaseUpdate databaseUpdate;
    private LightsModel model;
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public LightingListener() {
    super("Lighting");
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
    
    if ("Arduino-5".equals(entry.getDeviceName())) {
      model.setLivingRoom(entry.getLongValue("Living Room"));      
    }
    else if ("Arduino-6".equals(entry.getDeviceName())) {
      model.setDiningRoom(entry.getLongValue("Dining Room"));
    }
    else if ("Arduino-7".equals(entry.getDeviceName())) {
      model.setKitchenRoom(entry.getLongValue("Kitchen Room"));
    }
    else if ("Arduino-8".equals(entry.getDeviceName())) {
      model.setBathroom(entry.getLongValue("Bathroom"));      
    }
    
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
   * Gets this Lights model.
   * 
   * @return LightsModel
   */
  public LightsModel getModel() {
    return model;
  }
}
