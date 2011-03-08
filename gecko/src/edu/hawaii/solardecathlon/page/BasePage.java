package edu.hawaii.solardecathlon.page;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.solardecathlon.BlackMagic;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.SolarDecathlonSession;
import edu.hawaii.solardecathlon.page.admin.Administrator;
import edu.hawaii.solardecathlon.page.admin.Settings;
import edu.hawaii.solardecathlon.page.aquaponics.AquaponicsPage;
import edu.hawaii.solardecathlon.page.dashboard.Dashboard;
import edu.hawaii.solardecathlon.page.energy.EnergyPage;
import edu.hawaii.solardecathlon.page.lighting.LightingPage;
import edu.hawaii.solardecathlon.page.reports.Reports;
import edu.hawaii.solardecathlon.page.security.Security;
import edu.hawaii.solardecathlon.page.services.Help;
import edu.hawaii.solardecathlon.page.services.Login;
import edu.hawaii.solardecathlon.page.temperature.TemperaturePage;

/**
 * The header page. This is a parent class to all pages.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class BasePage extends WebPage {

  protected static final String TAB_NUM = "TabNum";
  protected static final String GRAPH_NUM = "GraphNum";
  protected static final String CSS_SCREEN = "screen";

  protected static final Pattern CLASS_PAT_BTN = Pattern.compile("(green|grey)-button");
  protected static final String CLASS_BTN_GREEN = "green-button";
  protected static final String CLASS_BTN_GRAY = "gray-button";

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private List<Item<Class<? extends Page>>> pageLinks;

  protected Class<? extends BasePage> currentPage;
  protected SolarDecathlonSession session;
  protected transient SystemStateEntryDB dao;

  /**
   * Default constructor.
   */
  public BasePage() {

    // Set reference to session.
    this.session = (SolarDecathlonSession) getSession();
    this.dao = SolarDecathlonApplication.getDAO();

    // Links to add to the header and footer.
    pageLinks = new ArrayList<Item<Class<? extends Page>>>();
    pageLinks.add(new Item<Class<? extends Page>>("Dashboard", 0,
        new Model<Class<? extends Page>>(Dashboard.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Energy", 1, new Model<Class<? extends Page>>(
        EnergyPage.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Aquaponics", 2,
        new Model<Class<? extends Page>>(AquaponicsPage.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Lighting", 3,
        new Model<Class<? extends Page>>(LightingPage.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Temperature", 4,
        new Model<Class<? extends Page>>(TemperaturePage.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Security", 5,
        new Model<Class<? extends Page>>(Security.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Reports", 6, new Model<Class<? extends Page>>(
        Reports.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Settings", 7,
        new Model<Class<? extends Page>>(Settings.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Administration", 8,
        new Model<Class<? extends Page>>(Administrator.class)));
    pageLinks.add(new Item<Class<? extends Page>>("Help", 9, new Model<Class<? extends Page>>(
        Help.class)));
  }

  /**
   * Selects the tab.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    // Sets the page in order to hightlight tab in linkReference method.
    currentPage = this.getClass();

    // add all resources
    resourceReference();
    linkReference();
  }

  /**
   * References all the styles and javascript resources inside the default constructor.
   */
  private void resourceReference() {

    // Add CSS definitions for use in all pages
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/style.css", CSS_SCREEN));

    // Add Javascript for use in all pages
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.min.js"));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery-ui.min.js"));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.effects.core.js"));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.core.js"));

    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.widget.js"));
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/jqueryUI.css", CSS_SCREEN));

    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/main.js"));

    // For Time Picker and Color chooser
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.icolor.js"));
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/timePicker.css", CSS_SCREEN));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.timePicker.js"));

    // For Date Picker
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/datePicker.css", CSS_SCREEN));
    add(JavascriptPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/javascript/jquery.ui.datepicker.js"));

    add(new Image("logo", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/logo.png")));

    // Print Image
    add(new Image("printer", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/printer.png")));

    // Help Image
    add(new Image("help", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/help.png")));

    // Refresh Image
    add(new Image("refresh", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/arrow_rotate_clockwise.png")));

    // Other images used throughout system
    add(new Image("TableViewImage", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/magnifier.png")));
    add(new Image("TableEditImage", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/pencil.png")));
    add(new Image("TableDeleteImage", new ResourceReference(SolarDecathlonApplication.class,
        "page/images/icons/cancel.png")));
    add(new Label("title", "Home Management System"));
  }

  /**
   * Sets the link references in onBeforeRender method.
   */
  private void linkReference() {

    // Add the header links to the page.
    add(new ListView<Item<Class<? extends Page>>>("headerLinkList", pageLinks) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 3992102347955899398L;

      /**
       * Populates the links.
       */
      @Override
      protected void populateItem(ListItem<Item<Class<? extends Page>>> item) {
        final Item<Class<? extends Page>> modelObj = item.getModelObject();

        BookmarkablePageLink<String> link =
            new BookmarkablePageLink<String>("link", modelObj.getModelObject());
        link.add(new Label("name", modelObj.getId()));

        WebMarkupContainer wmc = new WebMarkupContainer("class");
        wmc.add(link);

        // highlight tab
        if (currentPage.equals(modelObj.getModelObject())) {
          wmc.add(new AttributeModifier("class", true, new Model<String>("active")));
        }

        item.add(wmc);
      }
    });

    // Adds the link for the footer.
    add(new ListView<Item<Class<? extends Page>>>("footerLinkList", pageLinks) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 3992102347955899398L;

      /**
       * Populates the links.
       */
      @Override
      protected void populateItem(ListItem<Item<Class<? extends Page>>> item) {
        final Item<Class<? extends Page>> modelObj = item.getModelObject();

        BookmarkablePageLink<String> link =
            new BookmarkablePageLink<String>("link", modelObj.getModelObject());
        link.add(new Label("name", modelObj.getId()));

        item.add(link);

        // show divider.
        Label divider =
            new Label("divider", (item.getIndex() < pageLinks.size() - 1) ? " | " : null);
        item.add(divider);
      }
    });

    add(new Link<String>("LoggedInAsLink") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to FormPage. */
      @Override
      public void onClick() {
        session.putProperty(TAB_NUM, "7");
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

    // TODO remove after integration.
    if (session.getProperty("ConfigType").equalsIgnoreCase(Application.DEVELOPMENT)) {
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
          new BlackMagic(SolarDecathlonApplication.getDAO());
        }
      });
    }
    else {
      add(new WebMarkupContainer("blackmagic"));
    }
  }
}