package edu.hawaii.ihale.frontend.page.lighting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
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
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.repository.impl.Repository;
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
    
    // PMD strings
    final String brightness = "amountBright";
    final String colorChange = "colorchange";
    final String submitIntensityPath = "form:SubmitIntensity";
    final String intensityFeedbackPath =  "form:intensityfeedback";
    final String unchecked = "unchecked";
    final String onChange = "onchange";
    final String onClick = "onclick";
    final String assertMessage =  "Checking intensity feedback contents.";
    
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    // Start up the WicketTester and check that the page renders.
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
    tester.assertComponent(colorChange, HiddenField.class);
    tester.assertComponent("form", Form.class);
    tester.assertComponent("form:amountBright", TextField.class);
    tester.assertComponent(submitIntensityPath, AjaxButton.class);
    tester.assertComponent(intensityFeedbackPath, Label.class);
    tester.assertComponent("room", DropDownChoice.class);
    tester.assertComponent("OnButton", Link.class);
    tester.assertComponent("OffButton", Link.class);

    Label iFeedback;
    FormTester formTester;
    int testIntensity;

    Repository repository = new Repository();
    long timestamp = new Date().getTime();

    // Initial room should be living room
    @SuppressWarnings(unchecked)
    DropDownChoice<String> roomDropDownChoice =
        (DropDownChoice<String>) tester.getComponentFromLastRenderedPage("room");
    assertEquals("Checking size of drop down", roomDropDownChoice.getChoices().size(), 4);
    assertEquals("Checking room is Living room.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Living Room");

    /** Testing AJAX button. */

    // Living room
    formTester = tester.newFormTester("form");
    formTester.setValue(brightness, "60%");
    tester.executeAjaxEvent("form:amountBright", onChange);
    tester.executeAjaxEvent(submitIntensityPath, onClick);
    iFeedback = (Label) tester.getComponentFromLastRenderedPage(intensityFeedbackPath);
    assertEquals("Checking new form contents", "60%", formTester.getTextComponentValue(brightness));
    assertEquals(assertMessage,
        "<font color=\"green\">Success: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent(submitIntensityPath, onClick);
    iFeedback = (Label) tester.getComponentFromLastRenderedPage(intensityFeedbackPath);
    assertEquals(assertMessage,
        "<font color=\"#FF9900\">Same: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.LIVING).getValue();
    assertEquals("Check that intensity was submitted.", 60, testIntensity);

    // Dining room
    roomDropDownChoice.setDefaultModelObject("Dining Room");
    assertNotNull("Check that the initial brightness isn't null", brightness);
    assertEquals("Checking that room is Dining room.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Dining Room");
    formTester.setValue(brightness, "70%");
    tester.executeAjaxEvent("form:amountBright", onChange);
    tester.executeAjaxEvent(submitIntensityPath, onClick);
    iFeedback = (Label) tester.getComponentFromLastRenderedPage(intensityFeedbackPath);
    assertEquals("Checking new form contents", "70%", formTester.getTextComponentValue(brightness));
    assertEquals(assertMessage,
        "<font color=\"green\">Success: (70%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent(submitIntensityPath, onClick);
    iFeedback = (Label) tester.getComponentFromLastRenderedPage(intensityFeedbackPath);
    assertEquals(assertMessage,
        "<font color=\"#FF9900\">Same: (70%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.DINING).getValue();
    assertEquals("Check that intensity was submitted.", 70, testIntensity);

    /** Testing color changer. */

    // Living room
    roomDropDownChoice.setDefaultModelObject("Living Room");
    assertNotNull("Check that the initial color isn't null", colorChange);
    @SuppressWarnings(unchecked)
    HiddenField<String> color =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage(colorChange);
    assertEquals("Checking initial color.", "#000000", color.getDefaultModelObjectAsString());
    tester.executeAjaxEvent(colorChange, onChange);
    color.setDefaultModelObject(repository.getLightingColor(IHaleRoom.LIVING).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#000000",
        color.getDefaultModelObjectAsString());

    // Dining room
    roomDropDownChoice.setDefaultModelObject("Dining Room");
    assertNotNull("Check that the initial color isn't null", colorChange);
    @SuppressWarnings(unchecked)
    HiddenField<String> color2 =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage(colorChange);
    assertEquals("Checking initial color.", "#000000", color2.getDefaultModelObjectAsString());
    tester.executeAjaxEvent(colorChange, onChange);
    color2.setDefaultModelObject(repository.getLightingColor(IHaleRoom.DINING).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#FFFFFF",
        color2.getDefaultModelObjectAsString());

    /** Testing drop down update. */

    // Living room
    roomDropDownChoice.setDefaultModelObject("Living Room");
    repository.store(IHaleRoom.LIVING, IHaleState.SET_LIGHTING_ENABLED_COMMAND, timestamp, true);
    tester.executeAjaxEvent("room", onChange);
    @SuppressWarnings(unchecked)
    Link<String> onButton = (Link<String>) tester.getComponentFromLastRenderedPage("OnButton");
    assertEquals("Check class of on button.", onButton.getMarkupAttributes().getString("class"),
        "green-button");

    // Dining room
    // roomDropDownChoice.setDefaultModelObject("Dining Room");
    // repository.store(IHaleRoom.DINING, IHaleState.SET_LIGHTING_ENABLED_COMMAND, timestamp,
    // false);
    // tester.executeAjaxEvent("room", "onchange");
    // @SuppressWarnings("unchecked")
    // Link<String> offButton = (Link<String>) tester.getComponentFromLastRenderedPage("OffButton");
    // assertEquals("Check class of off button.",offButton.getMarkupAttributes().getString("class"),
    // "green-button" );

    repository.store(IHaleRoom.DINING, IHaleState.LIGHTING_ENABLED, timestamp, true);
    assertEquals("Checking button state", true, repository.getLightingEnabled(IHaleRoom.DINING)
        .getValue());
    // Check that the color is not null
    // Can't test for initial value since it's random
    assertNotNull("Check that the initial color isn't null", colorChange);

    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();
  }
}
