package edu.hawaii.ihale.frontend;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * The header page. This is a parent class to all pages.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Header extends WebPage {

  // create the db instance.
  static final SystemStateEntryDB db = ((SolarDecathlonApplication) SolarDecathlonApplication
      .get()).getDB();
  // Date format for the time displayed at the top right corner.
  private static final String DATE_FORMAT = "MMMM d, yyyy  hh:mm a";
  private static final String PAGE_DISPLAY = "ActivePage";

  // Variables to allow the active tab to change.
  protected int activePage = 0;
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

  // Session properties
  Map<String, Integer> properties = ((SolarDecathlonSession) getSession()).getProperties();

  // labels for the header, aka basepage.
  static Label insideTemperatureHeader = new Label("InsideTemperatureHeader", "0");
  static Label outsideTemperatureHeader = new Label("OutsideTemperatureHeader", "0");

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The header page. This is a parent class to all pages.
   */
  public Header() {

    // This is totally bogus!!
    // Right now there's no outside temp in the dictionary so we just use a random number
    Long rand = (long) (Math.random() * 100);
    outsideTemperatureHeader.setDefaultModelObject(String.valueOf(rand));

    // add hvac listener for the temperature display.
    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
        .getAquaponicsListener());
    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
        .getHvacListener());
    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
        .getLightsListener());
    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
        .getPhotovoltaicListener());
    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
        .getConsumptionListener());

    // for testing purpose, may remove after the integration with backend system.
//    try {
//      new BlackMagic(db);
//    }
//    catch (Exception e1) {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//    }

    // figure out the active page from session properties.
    int activePage = properties.get(PAGE_DISPLAY);

    insideTemperatureHeader.setEscapeModelStrings(false);
    outsideTemperatureHeader.setEscapeModelStrings(false);

    String screenContainer = "screen";

    // Add CSS definitions for use in all pages
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.frontend.Header.class,
        "style.css", screenContainer));

    // Add Javascript for use in all pages
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.min.js"));
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery-ui.min.js"));
    /*
     * add(JavascriptPackageResource.getHeaderContribution(edu.hawaii.solardecathlon
     * .frontend.Header.* class, "javascripts/jquery-ui-1.8.7.custom.js"));
     */
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.effects.core.js"));
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.ui.core.js"));

    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.ui.widget.js"));
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.frontend.Header.class,
        "jqueryUI.css", screenContainer));

    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/main.js"));

    // For Time Picker and Color chooser
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.icolor.js"));
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.frontend.Header.class,
        "timePicker.css", screenContainer));
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.timePicker.js"));

    // For Date Picker
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.frontend.Header.class,
        "datePicker.css", screenContainer));
    add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.ihale.frontend.Header.class, "javascripts/jquery.ui.datepicker.js"));

    // Logo Image
    add(new Image("logo", new ResourceReference(Header.class, "images/logo.png")));

    // Print Image
    add(new Image("printer", new ResourceReference(Header.class, "images/icons/printer.png")));

    // Help Image
    add(new Image("help", new ResourceReference(Header.class, "images/icons/help.png")));

    // Refresh Image
    add(new Image("refresh", new ResourceReference(Header.class,
        "images/icons/arrow_rotate_clockwise.png")));

    // Other images used throughout system
    add(new Image("TableViewImage", new ResourceReference(Header.class,
        "images/icons/magnifier.png")));
    add(new Image("TableEditImage",
        new ResourceReference(Header.class, "images/icons/pencil.png")));
    add(new Image("TableDeleteImage", new ResourceReference(Header.class,
        "images/icons/cancel.png")));
    add(new Label("title", "Home Management System"));

    // Add Dashboard Link to page (tabs)
    dashboardItem = new WebMarkupContainer("DashboardItem");
    add(dashboardItem);
    dashboardItem.add(new Link<String>("DashboardPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the dashboard. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 0);
        try {
          setResponsePage(new Dashboard());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });

    // Add Energy Link to page (tabs)
    energyItem = new WebMarkupContainer("EnergyItem");
    add(energyItem);
    energyItem.add(new Link<String>("EnergyPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the energy page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 1);
        try {
          setResponsePage(new Energy());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });

    // Add Aquaponics Link to page (tabs)
    aquaponicsItem = new WebMarkupContainer("AquaponicsItem");
    add(aquaponicsItem);
    aquaponicsItem.add(new Link<String>("AquaponicsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the aquaponics page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 2);
        try {
          setResponsePage(new AquaPonics());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }
    });

    // Add Lighting Link to page (tabs)
    lightingItem = new WebMarkupContainer("LightingItem");
    add(lightingItem);
    lightingItem.add(new Link<String>("LightingPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to lighting page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 3);
        setResponsePage(new Lighting());

      }
    });

    // Add Temperature Link to page (tabs)
    temperatureItem = new WebMarkupContainer("TemperatureItem");
    add(temperatureItem);
    temperatureItem.add(new Link<String>("TemperaturePageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the temperature page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 4);
        try {
          setResponsePage(new Temperature());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    // Add Security Link to page (tabs)
    securityItem = new WebMarkupContainer("SecurityItem");
    add(securityItem);
    securityItem.add(new Link<String>("SecurityPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the security page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 5);
        setResponsePage(new Security());

      }
    });

    // Add Reports Link to page (tabs)
    reportsItem = new WebMarkupContainer("ReportsItem");
    add(reportsItem);
    reportsItem.add(new Link<String>("ReportsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to reports page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 6);
        setResponsePage(new Reports());

      }
    });

    // Add Settings Link to page (tabs)
    settingsItem = new WebMarkupContainer("SettingsItem");
    add(settingsItem);
    settingsItem.add(new Link<String>("SettingsPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to settings page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 7);
        setResponsePage(new Settings());

      }
    });

    // Add Administrator Link to page (tabs)
    administratorItem = new WebMarkupContainer("AdministratorItem");
    add(administratorItem);
    administratorItem.add(new Link<String>("AdministratorPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to admin page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 8);
        setResponsePage(new Administrator());

      }
    });

    // Add Help Link to page (tabs)
    helpItem = new WebMarkupContainer("HelpItem");
    add(helpItem);
    helpItem.add(new Link<String>("HelpPageLinkTab") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to help page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 9);
        setResponsePage(new Help());

      }
    });

    // Footer Links
    add(new Link<String>("DashboardPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to the dashboard. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 0);
        try {
          setResponsePage(new Dashboard());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    add(new Link<String>("EnergyPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to energy page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 1);
        try {
          setResponsePage(new Energy());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    add(new Link<String>("AquaponicsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to aquaponics page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 2);
        try {
          setResponsePage(new AquaPonics());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    add(new Link<String>("LightingPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to lighting page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 3);
        setResponsePage(new Lighting());
      }
    });
    add(new Link<String>("TemperaturePageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to temperature page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 4);
        try {
          setResponsePage(new Temperature());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    add(new Link<String>("SecurityPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to security page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 5);
        setResponsePage(new Security());
      }
    });
    add(new Link<String>("SecurityCamPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to security page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 5);
        setResponsePage(new SecurityCam());
      }
    });
    add(new Link<String>("SecurityRecPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to security page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 5);
        setResponsePage(new SecurityRec());
      }
    });
    add(new Link<String>("ReportsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to reports page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 6);
        setResponsePage(new Reports());
      }
    });
    add(new Link<String>("SettingsPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to settings page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 7);
        setResponsePage(new Settings());
      }
    });
    add(new Link<String>("AdministratorPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to admin page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 8);
        setResponsePage(new Administrator());
      }
    });
    add(new Link<String>("HelpPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to help page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 9);
        setResponsePage(new Help());
      }
    });

    add(new Link<String>("LoggedInAsLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to settings page. */
      @Override
      public void onClick() {
        properties.put(PAGE_DISPLAY, 7);
        setResponsePage(new Settings());
      }
    });

    add(new Link<String>("LoginPageLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to login page. */
      @Override
      public void onClick() {
        setResponsePage(new Login());
      }
    });

    // Make the current tab active
    makeTabActive(activePage);

    // the info on top right of the page
    Calendar cal = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    String currentTime = dateFormat.format(cal.getTime());

    final Label time = new Label("Calendar", currentTime);
    // update the time every second
    time.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onPostProcessTarget(AjaxRequestTarget target) {
        Calendar newCal = Calendar.getInstance();
        // set new time to the label
        time.setDefaultModelObject(String.valueOf(dateFormat.format(newCal.getTime())));

      }
    });

    add(time);

    add(outsideTemperatureHeader);
    add(insideTemperatureHeader);
  }

  /**
   * Highlights the active tab.
   * 
   * @param i - this is a flag to tell the application which tab should be active
   */
  private void makeTabActive(int i) {
    String classContainer = "class";
    String activeContainer = "active";

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

  /**
   * Returns session properties for graphs.
   * 
   * @return the graph to display.
   */
  public Map<String, Integer> getSessionGraphProperties() {
    return ((SolarDecathlonSession) getSession()).getProperties();

  }

  /**
   * Set the inside temperature label on header page.
   * 
   * @param value The inside temperature.
   */
  public static void setInsideTemp(Long value) {
    // DecimalFormat df = new DecimalFormat("#.##");
    insideTemperatureHeader.setDefaultModelObject(String.valueOf(value));
  }

  /**
   * Set the outside temperature label on header page.
   * 
   * @param value The inside temperature.
   */
  public static void setOutsideTemp(Long value) {
    // DecimalFormat df = new DecimalFormat("#.##");
    outsideTemperatureHeader.setDefaultModelObject(String.valueOf(value));
  }

}
