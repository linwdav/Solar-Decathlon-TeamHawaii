package edu.hawaii.ihale.ui.page.settings;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;

/**
 * Settings page.
 * 
 * @author Bret Ikehara
 */
public class SettingsPage extends BasePage {

  /**
   * Support serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the settings page.
   */
  public SettingsPage() {

    // TODO Create the styles for the page.
    // TODO Finish the settings criteria. I.E. Settings, Privileges, Notifications, User Management,
    // etc.
    // TODO Implement the content for the criteria.

    List<ITab> tabview = new ArrayList<ITab>();
    tabview.add(new AbstractTab(new Model<String>("User Settings")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Returns the User Settings Panel.
       */
      @Override
      public Panel getPanel(String id) {
        return new SettingsUserSettingsPanel(id);
      }

    });

    tabview.add(new AbstractTab(new Model<String>("User Privileges")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Returns the User Settings Panel.
       */
      @Override
      public Panel getPanel(String id) {
        return new SettingsUserPrivilegesPanel(id);
      }

    });

    add(new AjaxTabbedPanel("SettingsMain", tabview));
  }
}
