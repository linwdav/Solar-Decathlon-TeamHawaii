package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

//TODO create panel for status other than ph.

/**
 * Creates the Status Panel for the Aquaponics system so that a user can easily identify problems.
 * 
 * @author Bret K. Ikehara
 */
public class StatusPanel extends Panel {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 9197552736141510649L;

  /**
   * Creates the status panel of 0 - temperature, 1 - ph, and 3 - electricity.  
   * 
   * @param id String
   * @param type int
   */
  public StatusPanel(String id, int type) {
    super(id);
    
    Label phLabel = new Label("phValue");
    phLabel.setOutputMarkupId(true);
    add(phLabel);

    add(new Label("title", ""));
    add(new Label("phMsg", ""));
  }
}
