package edu.hawaii.ihale.ui.page.home;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.aquaponics.AquaponicsPage;
import edu.hawaii.ihale.ui.page.electricity.ElectricityPage;
import edu.hawaii.ihale.ui.page.hvac.HVACPage;
import edu.hawaii.ihale.ui.page.lights.LightsPage;
import edu.hawaii.ihale.ui.page.settings.SettingsPage;
import edu.hawaii.ihale.ui.page.waterHeater.WaterHeaterPage;

/**
 * The application home page. This page illustrates links.
 * 
 * @author Bret K. Ikehara
 */
public class HomePage extends BasePage {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The home page for this application. In this example, all of the material for the home page is
   * provided in the BasePage class, the BasePage.html file, and the HomePage.html file.
   * 
   */
  public HomePage() {

    add(CSSPackageResource.getHeaderContribution(HomePage.class, "style.css"));

    add(new Label("Status"));
    
    add(new Link<String>("settingsPage") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 2754547573809868748L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(SettingsPage.class);
      }
    });

    add(new Link<String>("HomeButtonLink1") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(AquaponicsPage.class);
      }
    });
    
    add(new Link<String>("HomeButtonLink2") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(ElectricityPage.class);
      }
    });
    
    add(new Link<String>("HomeButtonLink3") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(HVACPage.class);
      }
    });
    
    add(new Link<String>("HomeButtonLink4") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(LightsPage.class);
      }
    });
    
    add(new Link<String>("HomeButtonLink5") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(WaterHeaterPage.class);
      }
    });
    
    add(new Link<String>("HomeButtonLink6") {      
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Go to the settings page.
       */
      @Override
      public void onClick() {
        setResponsePage(SettingsPage.class);
      }
    });      
  }
}
