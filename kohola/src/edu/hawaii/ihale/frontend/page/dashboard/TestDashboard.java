package edu.hawaii.ihale.frontend.page.dashboard;

import static org.junit.Assert.assertEquals;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Dashboard page.
 * 
 * @author Anthony Kinsey
 */
public class TestDashboard {

  /**
   * Performs JUnit tests on the Dashboard page.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Dashboard.class);
    tester.assertRenderedPage(Dashboard.class);

    // Check all components on page
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
    tester.assertComponent("CurrentWeatherCondition", Label.class);
    tester.assertComponent("CurrentWeatherImage", Image.class);
    tester.assertComponent("WeatherForecastImage1", Image.class);
    tester.assertComponent("WeatherForecastImage2", Image.class);
    tester.assertComponent("WeatherForecastImage3", Image.class);
    tester.assertComponent("WeatherForecastImage4", Image.class);
    tester.assertComponent("weatherForecastDayLabel1", Label.class);
    tester.assertComponent("weatherForecastDayLabel2", Label.class);
    tester.assertComponent("weatherForecastDayLabel3", Label.class);
    tester.assertComponent("weatherForecastDayLabel4", Label.class);
    tester.assertComponent("consprodD", WebMarkupContainer.class);
    tester.assertComponent("consprodW", WebMarkupContainer.class);
    tester.assertComponent("consprodM", WebMarkupContainer.class);
    tester.assertComponent("DayUsage2", Label.class);
    tester.assertComponent("WeekUsage2", Label.class);
    tester.assertComponent("MonthUsage2", Label.class);
    tester.assertComponent("DayPriceConverter", Label.class);
    tester.assertComponent("WeekPriceConverter", Label.class);
    tester.assertComponent("MonthPriceConverter", Label.class);
    tester.assertComponent("DayDiv", WebMarkupContainerWithAssociatedMarkup.class);
    tester.assertComponent("DayDiv:DayUsage", Label.class);
    tester.assertComponent("WeekDiv", WebMarkupContainerWithAssociatedMarkup.class);
    tester.assertComponent("WeekDiv:WeekUsage", Label.class);
    tester.assertComponent("MonthDiv", WebMarkupContainerWithAssociatedMarkup.class);
    tester.assertComponent("MonthDiv:MonthUsage", Label.class);
    tester.assertComponent("Cities", DropDownChoice.class);
    tester.assertComponent("InsideTemperature", Label.class);
    tester.assertComponent("OutsideTemperature", Label.class);
    tester.assertComponent("Time", Label.class);
    tester.assertComponent("StatusMessages", MultiLineLabel.class);

    // Check the dropdown box
    DropDownChoice<String> countryDropDownChoice =
        (DropDownChoice<String>) tester.getComponentFromLastRenderedPage("Cities");
    assertEquals("Check that the dropdown has correct amount of selections.", countryDropDownChoice
        .getChoices().size(), 2);

    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
