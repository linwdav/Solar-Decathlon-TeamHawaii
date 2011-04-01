package edu.hawaii.ihale.frontend.page.lighting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Lighting page.
 * 
 * @author Anthony Kinsey
 */
public class TestLighting {

  /**
   * Performs JUnit tests on the Lighting page.
   */
  @Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Lighting.class);
    tester.assertRenderedPage(Lighting.class);

    // Check that all components are loaded
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
    tester.assertComponent("colorchange", HiddenField.class);    
    tester.assertComponent("form", Form.class);
    tester.assertComponent("form:amountBright", TextField.class);
    tester.assertComponent("form:SubmitIntensity", AjaxButton.class);
    tester.assertComponent("form:intensityfeedback", Label.class);
    tester.assertComponent("room", DropDownChoice.class);
    tester.assertComponent("OnButton", Link.class);
    tester.assertComponent("OffButton", Link.class);

    // Check that we can change the light brightness
    FormTester formTester = tester.newFormTester("form");
    assertNotNull("Check that the initial brightness isn't null", "amountBright");
    formTester.setValue("amountBright", "80%");
    formTester.submit("SubmitIntensity");
    assertEquals("Checking new form contents", "80%", formTester
        .getTextComponentValue("amountBright"));
    tester.assertRenderedPage(Lighting.class);
    
    // Check that the color is not null
    // Can't test for initial value since it's random
 
    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
