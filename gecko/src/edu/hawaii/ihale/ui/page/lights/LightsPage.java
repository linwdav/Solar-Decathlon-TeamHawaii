package edu.hawaii.ihale.ui.page.lights;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;

/**
 * Lights page.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class LightsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the lights page.
   */
  public LightsPage() {
    add(new Label("LightsStatus"));
    add(new LightsPageMain("LightsMain", new Model<String>("LightsMain")));
  }
}