package edu.hawaii.systemh.frontend.page.energy;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;
import edu.hawaii.systemh.frontend.RepositoryRefresher;
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Energy page.
 * 
 * @author Anthony Kinsey
 */
public class TestEnergy {

  /**
   * Performs JUnit tests on the Energy page.
   */
  @Test
  public void testPage() {
    
    // populate the repository
    IHaleBackend backend = (IHaleBackend) SolarDecathlonApplication.getBackend();
    Repository repository = new Repository();
    RepositoryRefresher refresh = new RepositoryRefresher(backend, repository);
    refresh.start(500);
         
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Energy.class);
    tester.assertRenderedPage(Energy.class);

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
    tester.assertComponent("DayGraph", WebMarkupContainer.class);
    tester.assertComponent("WeekGraph", WebMarkupContainer.class);
    tester.assertComponent("MonthGraph", WebMarkupContainer.class);
    tester.assertComponent("CurrentConsumption", Label.class);
    tester.assertComponent("CurrentGeneration", Label.class);
   
    // The following line is useful for seeing what's on the page.
    //tester.debugComponentTrees();

  }
}
