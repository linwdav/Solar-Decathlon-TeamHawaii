package edu.hawaii.ihale.frontend.page.aquaponics;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import com.codecommit.wicket.Chart;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.page.SelectModalWindow;

/**
 * JUnit testing for AquaponicsStats page.
 * 
 * @author Anthony Kinsey
 */
public class TestAquaponicsStats {

  /**
   * Performs JUnit tests on the AquaponicsStats page.
   */
  @Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(AquaponicsStats.class);
    tester.assertRenderedPage(AquaponicsStats.class);

    // Check that all components
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
    tester.assertComponent("dayWaterLevelGraph", Link.class);
    tester.assertComponent("dayPhGraph", Link.class);
    tester.assertComponent("dayTemperatureGraph", Link.class);
    tester.assertComponent("dayConductivityGraph", Link.class);
    tester.assertComponent("dayPowerGraph", Link.class);
    tester.assertComponent("dayWaterGraph", Link.class);
    tester.assertComponent("mainButton", Link.class);
    tester.assertComponent("dayChartType", Label.class);
    tester.assertComponent("weekChartType", Label.class);
    tester.assertComponent("monthChartType", Label.class);
    tester.assertComponent("dayGraphImage", Chart.class);
    tester.assertComponent("weekGraphImage", Chart.class);
    tester.assertComponent("monthGraphImage", Chart.class);

    // The following line is useful for seeing what's on the page.
    // tester.debugComponentTrees();

  }
}
