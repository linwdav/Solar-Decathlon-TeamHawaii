package edu.hawaii.solardecathlon.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.BlackMagic;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.SolarDecathlonSession;
import edu.hawaii.solardecathlon.page.admin.Administrator;
import edu.hawaii.solardecathlon.page.admin.Settings;
import edu.hawaii.solardecathlon.page.aquaponics.Aquaponics;
import edu.hawaii.solardecathlon.page.dashboard.Dashboard;
import edu.hawaii.solardecathlon.page.energy.Energy;
import edu.hawaii.solardecathlon.page.lighting.Lighting;
import edu.hawaii.solardecathlon.page.reports.Reports;
import edu.hawaii.solardecathlon.page.security.Security;
import edu.hawaii.solardecathlon.page.security.SecurityCam;
import edu.hawaii.solardecathlon.page.security.SecurityRec;
import edu.hawaii.solardecathlon.page.services.Help;
import edu.hawaii.solardecathlon.page.services.Login;
import edu.hawaii.solardecathlon.page.temperature.Temperature;

/**
 * The header page. This is a parent class to all pages.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class BasePage extends WebPage {

  /**
   * Variables to allow the active tab to change.
   */
  WebMarkupContainer dashboardItem;
  WebMarkupContainer energyItem;
  WebMarkupContainer aquaponicsItem;
  WebMarkupContainer lightingItem;
  WebMarkupContainer temperatureItem;
  WebMarkupContainer securityItem;
  WebMarkupContainer settingsItem;
  WebMarkupContainer reportsItem;
  WebMarkupContainer administratorItem;
  WebMarkupContainer helpItem;

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  protected SolarDecathlonSession session;

  /**
   * Layout of page.
   */
  public BasePage() {
    String screenContainer = "screen";
    
    this.session = (SolarDecathlonSession) getSession();
    
    add(new AjaxLink<String>("blackmagic") {

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
        new BlackMagic(session.listAquaponics);
      }
    });
    
    // Add CSS definitions for use in all pages
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/style.css", screenContainer));

    // Add Javascript for use in all pages
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.min.js"));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery-ui.min.js"));
    /*
     * add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
     * "javascripts/jquery-ui-1.8.7.custom.js"));
     */
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.effects.core.js"));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.core.js"));

    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.widget.js"));
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/jqueryUI.css", screenContainer));

    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/main.js"));

    // For Time Picker and Color chooser
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.icolor.js"));
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/timePicker.css", screenContainer));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.timePicker.js"));

    // For Date Picker
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/datePicker.css", screenContainer));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.datepicker.js"));

    // Logo Image
    // dashboardLink.add(new Image("logo", new ResourceReference(SolarDecathlonApplication.class, "page/images/logo.png")));
    add(new Image("logo", new ResourceReference(SolarDecathlonApplication.class, "page/images/logo.png")));

    // Print Image
    add(new Image("printer", new ResourceReference(SolarDecathlonApplication.class, "page/images/icons/printer.png")));

    // Help Image
    add(new Image("help", new ResourceReference(SolarDecathlonApplication.class, "page/images/icons/help.png")));

    // Refresh Image
    add(new Image("refresh", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/arrow_rotate_clockwise.png")));

    // Other images used throughout system
    add(new Image("TableViewImage", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/magnifier.png")));
    add(new Image("TableEditImage",
        new ResourceReference(SolarDecathlonApplication.class, "page/images/icons/pencil.png")));
    add(new Image("TableDeleteImage", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/cancel.png")));
    add(new Label("title", "Home Management System"));

    // Add Dashboard Link to page (tabs)
    dashboardItem = new WebMarkupContainer("DashboardItem");
    add(dashboardItem);
    dashboardItem.add(new Link<String>("DashboardPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "0");
        setResponsePage(Dashboard.class);
      }
    });

    // Add Energy Link to page (tabs)
    energyItem = new WebMarkupContainer("EnergyItem");
    add(energyItem);
    energyItem.add(new Link<String>("EnergyPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "1");
        setResponsePage(Energy.class);

      }
    });

    // Add Aquaponics Link to page (tabs)
    aquaponicsItem = new WebMarkupContainer("AquaponicsItem");
    add(aquaponicsItem);
    aquaponicsItem.add(new Link<String>("AquaponicsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "2");
        setResponsePage(Aquaponics.class);
      }
    });

    // Add Lighting Link to page (tabs)
    lightingItem = new WebMarkupContainer("LightingItem");
    add(lightingItem);
    lightingItem.add(new Link<String>("LightingPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "3");
        setResponsePage(Lighting.class);

      }
    });

    // Add Temperature Link to page (tabs)
    temperatureItem = new WebMarkupContainer("TemperatureItem");
    add(temperatureItem);
    temperatureItem.add(new Link<String>("TemperaturePageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "4");
        setResponsePage(Temperature.class);

      }
    });

    // Add Security Link to page (tabs)
    securityItem = new WebMarkupContainer("SecurityItem");
    add(securityItem);
    securityItem.add(new Link<String>("SecurityPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "5");
        setResponsePage(Security.class);

      }
    });

    // Add Reports Link to page (tabs)
    reportsItem = new WebMarkupContainer("ReportsItem");
    add(reportsItem);
    reportsItem.add(new Link<String>("ReportsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "6");
        setResponsePage(Reports.class);
      }
    });

    // Add Settings Link to page (tabs)
    settingsItem = new WebMarkupContainer("SettingsItem");
    add(settingsItem);
    settingsItem.add(new Link<String>("SettingsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "7");
        setResponsePage(Settings.class);

      }
    });

    // Add Administrator Link to page (tabs)
    administratorItem = new WebMarkupContainer("AdministratorItem");
    add(administratorItem);
    administratorItem.add(new Link<String>("AdministratorPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "8");
        setResponsePage(Administrator.class);

      }
    });

    // Add Help Link to page (tabs)
    helpItem = new WebMarkupContainer("HelpItem");
    add(helpItem);
    helpItem.add(new Link<String>("HelpPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "9");
        setResponsePage(new Help());

      }
    });

    // Footer Links

    add(new Link<String>("DashboardPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "0");
        setResponsePage(new Dashboard());
      }
    });

    add(new Link<String>("EnergyPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "1");
        setResponsePage(new Energy());
      }
    });

    add(new Link<String>("AquaponicsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "2");
        setResponsePage(new Aquaponics());
      }
    });
    add(new Link<String>("LightingPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "3");
        setResponsePage(new Lighting());
      }
    });
    add(new Link<String>("TemperaturePageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "4");
        setResponsePage(new Temperature());
      }
    });
    add(new Link<String>("SecurityPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "5");
        setResponsePage(new Security());
      }
    });
    add(new Link<String>("SecurityCamPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "5");
        setResponsePage(new SecurityCam());
      }
    });
    add(new Link<String>("SecurityRecPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "5");
        setResponsePage(new SecurityRec());
      }
    });
    add(new Link<String>("ReportsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "6");
        setResponsePage(new Reports());
      }
    });
    add(new Link<String>("SettingsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "7");
        setResponsePage(new Settings());
      }
    });
    add(new Link<String>("AdministratorPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "8");
        setResponsePage(new Administrator());
      }
    });
    add(new Link<String>("HelpPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "9");
        setResponsePage(new Help());
      }
    });

    add(new Link<String>("LoggedInAsLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.setProperty("TabNum", "7");
        setResponsePage(new Settings());
      }
    });

    add(new Link<String>("LoginPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        setResponsePage(new Login());
      }
    });

    // Make the current tab active
    makeTabActive();
  }

  /**
   * Highlights the active tab.
   * 
   * @param i - this is a flag to tell the application which tab should be active
   */
  private void makeTabActive() {
    String classContainer = "class";
    String activeContainer = "active";
    
    int i = Integer.valueOf(session.getProperty("TabNum"));
    
    switch (i) {

    case 1:
      energyItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 2:
      aquaponicsItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 3:
      lightingItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 4:
      temperatureItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 5:
      securityItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 6:
      reportsItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 7:
      settingsItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 8:
      administratorItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 9:
      helpItem
          .add(new AttributeModifier(classContainer, true, new Model<String>(activeContainer)));
      break;
    case 0: // pass-through
    default:
      dashboardItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    }
  }
}