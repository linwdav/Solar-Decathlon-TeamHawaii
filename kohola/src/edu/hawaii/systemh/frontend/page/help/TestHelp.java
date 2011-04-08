package edu.hawaii.systemh.frontend.page.help;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Help page.
 * 
 * @author Anthony Kinsey
 */
public class TestHelp {

  /**
   * Performs JUnit tests on the Help page.
   */
  @Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Help.class);
    tester.assertRenderedPage(Help.class);

    // click on all buttons to check all components.
    tester.clickLink("overviewButton");
    assertPageComponents(tester);
    tester.clickLink("dashboardButton");
    assertPageComponents(tester);
    tester.clickLink("energyButton");
    assertPageComponents(tester);
    tester.clickLink("aquaponicsButton");
    assertPageComponents(tester);
    tester.clickLink("lightingButton");
    assertPageComponents(tester);
    tester.clickLink("hvacButton");

    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();
  }
  
  /**
   * Check all components on page.
   * @param tester WicketTester.
   */
  private void assertPageComponents(WicketTester tester) {
    tester.assertComponent("logo", Image.class);
    tester.assertComponent("printer", Image.class);
    tester.assertComponent("help", Image.class);
    tester.assertComponent("refresh", Image.class);
    tester.assertComponent("TableViewImage", Image.class);
    tester.assertComponent("TableEditImage", Image.class);
    tester.assertComponent("TableDeleteImage", Image.class);
    tester.assertComponent("title", Label.class);
    tester.assertComponent("DashboardItem", WebMarkupContainer.class);
    tester.assertComponent("DashboardItem:DashboardPageLinkTab", Link.class);
    tester.assertComponent("EnergyItem", WebMarkupContainer.class);
    tester.assertComponent("EnergyItem:EnergyPageLinkTab", Link.class);
    tester.assertComponent("AquaponicsItem", WebMarkupContainer.class);
    tester.assertComponent("AquaponicsItem:AquaponicsPageLinkTab", Link.class);
    tester.assertComponent("LightingItem", WebMarkupContainer.class);
    tester.assertComponent("LightingItem:LightingPageLinkTab", Link.class);
    tester.assertComponent("HvacItem", WebMarkupContainer.class);
    tester.assertComponent("HvacItem:HvacPageLinkTab", Link.class);
    tester.assertComponent("HelpItem", WebMarkupContainer.class);
    tester.assertComponent("HelpItem:HelpPageLinkTab", Link.class);
    tester.assertComponent("DashboardPageLink", Link.class);
    tester.assertComponent("EnergyPageLink", Link.class);
    tester.assertComponent("AquaponicsPageLink", Link.class);
    tester.assertComponent("LightingPageLink", Link.class);
    tester.assertComponent("HvacPageLink", Link.class);
    tester.assertComponent("HelpPageLink", Link.class);
    tester.assertComponent("modalwindow", SelectModalWindow.class);
    tester.assertComponent("Calendar", Label.class);
    tester.assertComponent("CurrentWeatherHeader", Label.class);
    tester.assertComponent("OutsideTemperatureHeader", Label.class);
    tester.assertComponent("InsideTemperatureHeader", Label.class);
    tester.assertComponent("titleHelp", Label.class);
    tester.assertComponent("infoHelp", Label.class);
    tester.assertComponent("overviewButton", Link.class);
    tester.assertComponent("dashboardButton", Link.class);
    tester.assertComponent("energyButton", Link.class);
    tester.assertComponent("aquaponicsButton", Link.class);
    tester.assertComponent("lightingButton", Link.class);
    tester.assertComponent("hvacButton", Link.class);
  }
}
