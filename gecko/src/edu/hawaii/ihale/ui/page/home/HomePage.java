package edu.hawaii.ihale.ui.page.home;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.aquaponics.AquaponicsPage;
import edu.hawaii.ihale.ui.page.electricity.ElectricityPage;
import edu.hawaii.ihale.ui.page.hvac.HVACPage;
import edu.hawaii.ihale.ui.page.lights.LightsPage;
import edu.hawaii.ihale.ui.page.settings.SettingsPage;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;
import edu.hawaii.ihale.ui.page.waterHeater.WaterHeaterPage;

/**
 * The application home page. This page illustrates links.
 * 
 * @author Bret I. Ikehara
 */
public class HomePage extends BasePage {
  //TODO : implement a model for the buttons.
  
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

    add(CSSPackageResource.getHeaderContribution(HomePage.class, "homePage.css"));

    // Creates the OverAllStatusPanel
    List<AbstractBehavior> aquaponicsAttr = new ArrayList<AbstractBehavior>();
    aquaponicsAttr.add(new SimpleAttributeModifier("title", "this is a test."));

    ArrayList<OverAllStatus> list = new ArrayList<OverAllStatus>();
    list.add(new OverAllStatus("Aquaponics", new ResourceReference(OverAllStatus.class,
        OverAllStatus.STATUSGOOD), AquaponicsPage.class, aquaponicsAttr));

    list.add(new OverAllStatus("Electricity", new ResourceReference(OverAllStatus.class,
        OverAllStatus.STATUSGOOD), ElectricityPage.class));

    list.add(new OverAllStatus("HVAC", new ResourceReference(OverAllStatus.class,
        OverAllStatus.STATUSGOOD), HVACPage.class));

    list.add(new OverAllStatus("Water Heater", new ResourceReference(OverAllStatus.class,
        OverAllStatus.STATUSGOOD), WaterHeaterPage.class));

    add(new OverAllStatusPanel("HomeStatus", new Model<String>("HomeStatus"), list));
    
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
