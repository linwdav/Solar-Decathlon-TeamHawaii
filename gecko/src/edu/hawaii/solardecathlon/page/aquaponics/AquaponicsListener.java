package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.ajax.AjaxEventBehavior;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.listener.AjaxDAOUpdate;

/**
 * A listener that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @revised Bret Ikehara
 */
public class AquaponicsListener extends SystemStateListener {

  private AjaxDAOUpdate ajax;
  private Long timestamp = 0L;
  private Long temp = 0L;
  private Double ph = 0.0;
  private Double oxygen = 0.0;
  
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("aquaponics");
    
    ajax = new AjaxDAOUpdate();
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * 
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);
    
    /*
    timestamp = entry.getTimestamp();
    temp = entry.getLongValue("Temp");
    ph = entry.getDoubleValue("pH");
    oxygen = entry.getDoubleValue("Oxygen");
    */
  }

  /**
   * Gets this time stamp.
   * 
   * @return Long
   */
  public Long getTimestamp() {
    return timestamp;
  }

  /**
   * Gets this temperature.
   * 
   * @return Long
   */
  public Long getTemp() {
    return temp;
  }

  /**
   * Gets this pH.
   * 
   * @return Double
   */
  public Double getPh() {
    return ph;
  }

  /**
   * Gets this oxygen.
   * 
   * @return Double
   */
  public Double getOxygen() {
    return oxygen;
  }
  
  /**
   * Gets this DAO update event.
   * 
   * @return AjaxEventBehavior
   */
  public AjaxEventBehavior getDAOUpdateEvent() {
    return this.ajax;
  }
}
