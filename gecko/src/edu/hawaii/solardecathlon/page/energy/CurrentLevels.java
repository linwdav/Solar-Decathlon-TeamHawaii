package edu.hawaii.solardecathlon.page.energy;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.panel.Panel;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;

/**
 * Creates the panel to display the current level values in the energy system.
 * 
 * @author Bret K. Ikehara
 */
public class CurrentLevels extends Panel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 8372485740417516335L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public CurrentLevels(String id) {
    super(id);
    
    add(new AjaxSelfUpdatingTimerBehavior(SolarDecathlonApplication.getUpdateInterval()));
  }

}
