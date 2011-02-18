package edu.hawaii.ihale.ui.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.ui.page.service.LogOutPage;

/**
 * Creates the log out panel.
 * 
 * @author Bret I. Ikehara
 *
 */
public class BasePageLogOutPanel extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates the Log Out panel.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> BasePageLogOutPanel(String name, IModel<T> model) {
    super(name, model);
  }

  /**
   * Render the log out link.
   */
  public void onBeforeRender() {
    super.onBeforeRender();
      
    Link<String> logOutLink = new Link<String>("MenuBarLogOut") {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      public void onClick() {
        setResponsePage(LogOutPage.class);
      }
    };

    add(new Label("MenuBarUserName", getSessionProperty("user.name")));
    add(logOutLink); 
  }
}