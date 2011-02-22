package edu.hawaii.ihale.ui.page.hvac;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
/**
 * HVAC page.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class HVACPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the HVAC page.
   */
  public HVACPage() {
    add(new Label("HVACStatus", "status"));
    add(new HVACPageMain("HVACMain", new Model<String>("HVACMain")));
  }
}