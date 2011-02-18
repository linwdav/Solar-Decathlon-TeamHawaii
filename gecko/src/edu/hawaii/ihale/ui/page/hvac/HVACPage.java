package edu.hawaii.ihale.ui.page.hvac;

import java.util.ArrayList;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.electricity.ElectricityPage;
import edu.hawaii.ihale.ui.page.home.HomePage;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * HVAC page.
 * 
 * @author Michael Cera
 */
public class HVACPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the HVAC page.
   */
  public HVACPage() {

    add(CSSPackageResource.getHeaderContribution(HomePage.class, "homePage.css"));

    // Creates the OverAllStatusPanel
    ArrayList<OverAllStatus> list = new ArrayList<OverAllStatus>();
    list.add(new OverAllStatus("Current Temperature", "65° F"));
    list.add(new OverAllStatus("Temperature of Stored Air", "70° F"));
    list.add(new OverAllStatus("Energy Consumption per hour", "1 kWh", ElectricityPage.class));

    add(new OverAllStatusPanel("HVACStatus", new Model<String>("HVACStatus"), list));
    add(new HVACPageMain("HVACMain", new Model<String>("HVACMain")));
  }
}