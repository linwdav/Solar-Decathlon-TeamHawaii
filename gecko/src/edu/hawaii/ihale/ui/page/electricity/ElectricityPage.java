package edu.hawaii.ihale.ui.page.electricity;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.ElectricalListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;

/**
 * Electricity page.
 * 
 * @author Shoji Bravo
 * @author Bret K. Ikehara
 */
public class ElectricityPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the electricity page.
   */
  public ElectricityPage() {
    add(CSSPackageResource.getHeaderContribution(ElectricityPage.class, "style.css"));

    add(new Label("Main", "main"));
  }

  /**
   * Renders the sidebar with session.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    
    ElectricalListener listener = new ElectricalListener();
    this.database.addSystemStateListener(listener);

    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Power"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "power"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Energy"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "energy"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}
