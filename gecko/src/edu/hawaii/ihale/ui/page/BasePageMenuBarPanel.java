package edu.hawaii.ihale.ui.page;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.SolarDecathlonSession;
import edu.hawaii.ihale.ui.page.aquaponics.AquaponicsPage;
import edu.hawaii.ihale.ui.page.electricity.ElectricalPage;
import edu.hawaii.ihale.ui.page.home.HomePage;
import edu.hawaii.ihale.ui.page.hvac.HVACPage;
import edu.hawaii.ihale.ui.page.lights.LightsPage;
import edu.hawaii.ihale.ui.page.photovoltaics.PhotovoltaicsPage;
import edu.hawaii.ihale.ui.page.settings.SettingsPage;
import edu.hawaii.ihale.ui.page.settings.SettingsUserPrivilegesPanel;
import edu.hawaii.ihale.ui.page.settings.SettingsUserSettingsPanel;

/**
 * Creates the log out panel.
 * 
 * @author Bret K. Ikehara
 * 
 */
public class BasePageMenuBarPanel extends BasePanel {

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
   * @param <C> class
   * @param page Class<C>
   */
  public <T, C> BasePageMenuBarPanel(String name, IModel<T> model, Class<C> page) {
    super(name, model);

    Link<String> homePageLink = new Link<String>("homePageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(HomePage.class);
      }
    };

    Link<String> aquaponicsPageLink = new Link<String>("aquaponicsPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(AquaponicsPage.class);
      }
    };

    Link<String> electricityPageLink = new Link<String>("electricityPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(ElectricalPage.class);
      }
    };

    Link<String> hvacPageLink = new Link<String>("hvacPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(HVACPage.class);
      }
    };

    Link<String> lightsPageLink = new Link<String>("lightsPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(LightsPage.class);
      }
    };

    Link<String> waterHeaterPageLink = new Link<String>("waterHeaterPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(PhotovoltaicsPage.class);
      }
    };

    Link<String> settingsPageLink = new Link<String>("settingsPageLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(SettingsPage.class);
      }
    };

    AttributeAppender aaSelected =
        new AttributeAppender("class", new Model<String>("selected"), " ");

    if (page == HomePage.class) {
      homePageLink.add(aaSelected);
    }
    else if (page == AquaponicsPage.class) {
      aquaponicsPageLink.add(aaSelected);
    }
    else if (page == ElectricalPage.class) {
      electricityPageLink.add(aaSelected);
    }
    else if (page == HVACPage.class) {
      hvacPageLink.add(aaSelected);
    }
    else if (page == LightsPage.class) {
      lightsPageLink.add(aaSelected);
    }
    else if (page == PhotovoltaicsPage.class) {
      waterHeaterPageLink.add(aaSelected);
    }
    else if (page == SettingsPage.class || page == SettingsUserPrivilegesPanel.class
        || page == SettingsUserSettingsPanel.class) {
      settingsPageLink.add(aaSelected);
    }

    add(homePageLink);
    add(aquaponicsPageLink);
    add(electricityPageLink);
    add(hvacPageLink);
    add(lightsPageLink);
    add(waterHeaterPageLink);
    add(settingsPageLink);
  }
  
  /**
   * Only visible when authenticated.
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