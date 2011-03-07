package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.markup.html.basic.Label;
import edu.hawaii.solardecathlon.components.StatusPanel;

/**
 * Creates the panel for the electrical conductivity reference so the user knows the target
 * conductivity.
 * 
 * @author Bret K. Ikehara
 */
public class ElecRefPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -6076914788246070907L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public ElecRefPanel(String id) {
    super(id);

    Label title = new Label("title", "<h5>Recommended EC</h5>");
    title.setEscapeModelStrings(false);
    add(title);

    // This value shouldn't change too often since it is a reference, not the current EC.
    Label value = new Label("value", "1.75 - 2.25");
    add(value);
  }

}
