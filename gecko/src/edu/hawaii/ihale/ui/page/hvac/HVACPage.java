package edu.hawaii.ihale.ui.page.hvac;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.HVacListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;

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
    add(new Label("Main", "status"));
  }
  
  /**
   * Renders the sidebar with session variables.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    
    HVacListener listener = new HVacListener();
    this.database.addSystemStateListener(listener);
    
    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Temperature"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "Temp"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}