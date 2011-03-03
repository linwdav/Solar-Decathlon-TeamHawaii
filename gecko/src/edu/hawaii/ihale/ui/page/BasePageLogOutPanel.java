package edu.hawaii.ihale.ui.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.SolarDecathlonSession;
import edu.hawaii.ihale.ui.BlackMagic;
import edu.hawaii.ihale.ui.page.service.LogOutPage;

/**
 * Creates the log out panel.
 * 
 * @author Bret K. Ikehara
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

    add(new Label("MenuBarUserName", getSessionProperty("UserName")));
    add(logOutLink);

    if (session.getProperties().get("ConfigType").equalsIgnoreCase("development")) {
      add(new AjaxLink<String>("MenuBarBlackMagic") {

        /**
         * Serial ID.
         */
        private static final long serialVersionUID = -5577357049183345374L;

        /**
         * Calls Black Magic.
         * 
         * @param target AjaxRequestTarget
         */
        @Override
        public void onClick(AjaxRequestTarget target) {
          try {
            new BlackMagic(((BasePage)getPage()).getDatabase());
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    else {
      add(new Label("MenuBarBlackMagic"));
    }
  }

  /**
   * Only visible on authenticated.
   * 
   * @return boolean
   */
  @Override
  public boolean isVisible() {

    if (session == null) {
      session = (SolarDecathlonSession) getSession();
    }

    return session.isAuthenticated();
  }
}