package edu.hawaii.ihale.ui.page.lights;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.LightingListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;


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
      add(new LightsPageMain("LightsPageMain",
              new Model<String>("LightsPageMain")));
  }

  /**
   * Renders more components with access to session.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    LightingListener listener = new LightingListener();

    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Living Room"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "livingRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Dining Room"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "diningRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Kitchen"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "kitchenRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Bathroom"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "bathroom"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}