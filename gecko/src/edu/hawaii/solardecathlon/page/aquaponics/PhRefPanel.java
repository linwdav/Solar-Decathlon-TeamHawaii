package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.markup.html.basic.Label;
import edu.hawaii.solardecathlon.components.StatusPanel;

/**
 * Creates the pH reference panel.
 * 
 * @author Bret K. Ikehara
 */
public class PhRefPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -6076914788246070907L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public PhRefPanel(String id) {
    super(id);

    Label title = new Label("title", "<h5>Current pH</h5>");
    title.setEscapeModelStrings(false);
    add(title);

    Label value = new Label("value", "6.0 - 8.0");
    add(value);
  }
}
