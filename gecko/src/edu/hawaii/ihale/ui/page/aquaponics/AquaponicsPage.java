package edu.hawaii.ihale.ui.page.aquaponics;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.ajax.AjaxDatabaseUpdate;
import edu.hawaii.ihale.ui.model.AquaponicsModel;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.home.HomePage;

/**
 * Aquaponics page.
 * 
 * @author Bret Ikehara
 * @revised Shoji Bravo
 */
public class AquaponicsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates the Aquaponics page.
   */
  public AquaponicsPage() {
    super();
    add(CSSPackageResource.getHeaderContribution(HomePage.class, "homePage.css"));

    AjaxDatabaseUpdate databaseUpdate = new AjaxDatabaseUpdate();
    AquaponicsModel model = new AquaponicsModel();
    this.database.addSystemStateListener(new AquaponicsListener(databaseUpdate, model));

    Label main = new Label("Main", new PropertyModel<Long>(model, "timestamp"));
    main.setOutputMarkupId(true);
    main.add(databaseUpdate);
    add(main);
    
    add(new Label("Status"));
  }
}
