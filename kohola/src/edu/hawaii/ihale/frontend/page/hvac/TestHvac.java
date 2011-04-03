package edu.hawaii.ihale.frontend.page.hvac;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;
//import edu.hawaii.ihale.frontend.RepositoryRefresher;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import com.codecommit.wicket.Chart;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Hvac page.
 * 
 * @author Anthony Kinsey
 */
public class TestHvac {

  /**
   * Performs JUnit tests on the Hvac page.
   */
  @Test
  public void testPage() {

    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Hvac.class);
    tester.assertRenderedPage(Hvac.class);

    // Check all components on page.
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
    tester.assertComponent("button", Link.class);
    tester.assertComponent("button:buttonLabel", Label.class);
    tester.assertComponent("hvacState", Label.class);
    tester.assertComponent("InsideTemperature", Label.class);
    tester.assertComponent("OutsideTemperature", Label.class);
    tester.assertComponent("form", Form.class);
    tester.assertComponent("form:airTemperature", TextField.class);
    tester.assertComponent("form:SubmitTemp", AjaxButton.class);
    tester.assertComponent("form:Feedback", Label.class);
    tester.assertComponent("tempM", Chart.class);
    tester.assertComponent("tempW", Chart.class);
    tester.assertComponent("tempD", Chart.class);

    tester.clickLink("button");
    tester.clickLink("button");

    FormTester formTester = tester.newFormTester("form");
    
    // new temperature change
    formTester.setValue("airTemperature", "60");
    tester.executeAjaxEvent("form:airTemperature", "onchange");
    tester.executeAjaxEvent("form:SubmitTemp", "onclick");    
    assertEquals("Checking new form contents", "60",
        formTester.getTextComponentValue("airTemperature"));    
    Label feedback = (Label) tester.getComponentFromLastRenderedPage("form:Feedback");
    assertEquals("Check feedback", "<font color=\"green\">Success: (60&deg;F)</font>",
        feedback.getDefaultModelObjectAsString());
    
    // same temperature change 
    tester.executeAjaxEvent("form:airTemperature", "onchange");
    tester.executeAjaxEvent("form:SubmitTemp", "onclick");
    assertEquals("Check feedback", "<font color=\"#FF9900\">Same: (60&deg;F)</font>",
        feedback.getDefaultModelObjectAsString());
    
    // trigger the Hvac listener
    IHaleBackend backend = new IHaleBackend();
    Repository repository = new Repository();
    long timestamp = new Date().getTime();
    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, 70);
    repository.store(IHaleSystem.HVAC, IHaleState.TEMPERATURE, timestamp, 70);
    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, 50);
    repository.store(IHaleSystem.HVAC, IHaleState.TEMPERATURE, timestamp, 50);
        
    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
