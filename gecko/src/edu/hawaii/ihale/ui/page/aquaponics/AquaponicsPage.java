package edu.hawaii.ihale.ui.page.aquaponics;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;
import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.page.BasePage;

/**
 * Aquaponics page.
 * 
 * @author Bret K. Ikehara
 * @revised Shoji Bravo
 */
public class AquaponicsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the Aquaponics page.
   */
  public AquaponicsPage() {

    AquaponicsListener listener = new AquaponicsListener();
    this.database.addSystemStateListener(listener);

    Label main = new Label("Main", "main");
    add(main);

    // Initiate the sidebar panel
    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Time Stamp"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "timestamp"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Oxygen"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "oxygen"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Temp"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "temp"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "pH"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "ph"))));

    SidebarPanel sidebar = new SidebarPanel("Status", list);
    sidebar.add(listener.getDatabaseUpdate());
    
    add(sidebar);
  }
}
