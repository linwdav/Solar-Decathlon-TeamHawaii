package edu.hawaii.ihale.ui.page.lights;

import java.util.ArrayList;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.electricity.ElectricityPage;
import edu.hawaii.ihale.ui.page.home.HomePage;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * Lights page.
 * 
 * @author Michael Cera
 */
public class LightsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the lights page.
   */
  public LightsPage() {

    add(CSSPackageResource.getHeaderContribution(HomePage.class, "homePage.css"));

    // Creates the OverAllStatusPanel
    ArrayList<OverAllStatus> list = new ArrayList<OverAllStatus>();
    list.add(new OverAllStatus("Living Room", "On"));

    list.add(new OverAllStatus("Dining Room", "On"));

    list.add(new OverAllStatus("Bedroom", "Off"));

    list.add(new OverAllStatus("Kitchen", "Off"));

    list.add(new OverAllStatus("Bathroom", "Off"));

    list.add(new OverAllStatus("Energy Consumption per hour", "1 kWh", ElectricityPage.class));

    add(new OverAllStatusPanel("LightsStatus", new Model<String>("LightsStatus"), list));

    add(new LightsPageMain("LightsMain", new Model<String>("LightsMain")));
  }
}