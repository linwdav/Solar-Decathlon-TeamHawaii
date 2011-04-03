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
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.SelectModalWindow;

/**
 * JUnit testing for Lighting page.
 * 
 * @author Anthony Kinsey
 */
public class TestLighting {

  // PMD strings
  private String brightness = "amountBright";

  /**
   * Performs JUnit tests on the Lighting page.
   */
  @Test
  public void testPage() {
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
    tester.assertComponent("colorchange", HiddenField.class);
    tester.assertComponent("form", Form.class);
    tester.assertComponent("form:amountBright", TextField.class);
    tester.assertComponent("form:SubmitIntensity", AjaxButton.class);
    tester.assertComponent("form:intensityfeedback", Label.class);
    tester.assertComponent("testForm:room", DropDownChoice.class);
    tester.assertComponent("OnButton", Link.class);
    tester.assertComponent("OffButton", Link.class);

    Label iFeedback;
    FormTester formTester;
    int testIntensity;

    Repository repository = new Repository();
    long timestamp = new Date().getTime();

    // Initial room should be living room
    @SuppressWarnings("unchecked")
    DropDownChoice<String> roomDropDownChoice =
        (DropDownChoice<String>) tester.getComponentFromLastRenderedPage("testForm:room");
    assertEquals("Checking size of drop down", roomDropDownChoice.getChoices().size(), 4);
    assertEquals("Checking room is Living room.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Living Room");

    /** Testing AJAX button. */

    // Living room
    formTester = tester.newFormTester("form");
    formTester.setValue(brightness, "60%");
    tester.executeAjaxEvent("form:amountBright", "onchange");
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking new form contents", "60%", formTester.getTextComponentValue(brightness));
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"green\">Success: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"#FF9900\">Same: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.LIVING).getValue();
    assertEquals("Check that intensity was submitted.", 60, testIntensity);

    // Dining room
    roomDropDownChoice.setDefaultModelObject("Dining Room");
    assertNotNull("Check that the initial brightness isn't null", brightness);
    assertEquals("Checking that room is Dining room.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Dining Room");
    formTester.setValue(brightness, "70%");
    tester.executeAjaxEvent("form:amountBright", "onchange");
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking new form contents", "70%", formTester.getTextComponentValue(brightness));
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"green\">Success: (70%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"#FF9900\">Same: (70%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.DINING).getValue();
    assertEquals("Check that intensity was submitted.", 70, testIntensity);

    // Kitchen
    roomDropDownChoice.setDefaultModelObject("Kitchen");
    assertNotNull("Check that the initial brightness isn't null", brightness);
    assertEquals("Checking that room is Kitchen.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Kitchen");
    formTester.setValue(brightness, "90%");
    tester.executeAjaxEvent("form:amountBright", "onchange");
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking new form contents", "90%", formTester.getTextComponentValue(brightness));
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"green\">Success: (90%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"#FF9900\">Same: (90%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.KITCHEN).getValue();
    assertEquals("Check that intensity was submitted.", 90, testIntensity);

    // Bathroom
    roomDropDownChoice.setDefaultModelObject("Bathroom");
    assertNotNull("Check that the initial brightness isn't null", brightness);
    assertEquals("Checking that room is Bathroom.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Bathroom");
    formTester.setValue(brightness, "60%");
    tester.executeAjaxEvent("form:amountBright", "onchange");
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking new form contents", "60%", formTester.getTextComponentValue(brightness));
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"green\">Success: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    tester.executeAjaxEvent("form:SubmitIntensity", "onclick");
    iFeedback = (Label) tester.getComponentFromLastRenderedPage("form:intensityfeedback");
    assertEquals("Checking intensity feedback contents.",
        "<font color=\"#FF9900\">Same: (60%)</font>", iFeedback.getDefaultModelObjectAsString());
    testIntensity = repository.getLightingLevelCommand(IHaleRoom.BATHROOM).getValue();
    assertEquals("Check that intensity was submitted.", 60, testIntensity);

    /** Testing color changer. */

    // Living room
    roomDropDownChoice.setDefaultModelObject("Living Room");
    assertNotNull("Check that the initial color isn't null", "colorchange");
    @SuppressWarnings("unchecked")
    HiddenField<String> color =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage("colorchange");
    tester.executeAjaxEvent("colorchange", "onchange");
    SolarDecathlonApplication.getBackend().doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
        IHaleCommandType.SET_LIGHTING_COLOR, "#FFFFFF");
    color.setDefaultModelObject(repository.getLightingColorCommand(IHaleRoom.LIVING).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#FFFFFF",
        color.getDefaultModelObjectAsString());

    // Dining room
    roomDropDownChoice.setDefaultModelObject("Dining Room");
    assertNotNull("Check that the initial color isn't null", "colorchange");
    @SuppressWarnings("unchecked")
    HiddenField<String> color2 =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage("colorchange");
    tester.executeAjaxEvent("colorchange", "onchange");
    SolarDecathlonApplication.getBackend().doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
        IHaleCommandType.SET_LIGHTING_COLOR, "#000000");
    color2.setDefaultModelObject(repository.getLightingColorCommand(IHaleRoom.DINING).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#000000",
        color.getDefaultModelObjectAsString());

    // Kitchen
    roomDropDownChoice.setDefaultModelObject("Kitchen");
    assertNotNull("Check that the initial color isn't null", "colorchange");
    @SuppressWarnings("unchecked")
    HiddenField<String> color3 =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage("colorchange");
    tester.executeAjaxEvent("colorchange", "onchange");
    SolarDecathlonApplication.getBackend().doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
        IHaleCommandType.SET_LIGHTING_COLOR, "#0000AA");
    color2.setDefaultModelObject(repository.getLightingColorCommand(IHaleRoom.KITCHEN).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#0000AA",
        color.getDefaultModelObjectAsString());

    // Bathroom
    roomDropDownChoice.setDefaultModelObject("Bathroom");
    assertNotNull("Check that the initial color isn't null", "colorchange");
    @SuppressWarnings("unchecked")
    HiddenField<String> color4 =
        (HiddenField<String>) tester.getComponentFromLastRenderedPage("colorchange");
    tester.executeAjaxEvent("colorchange", "onchange");
    SolarDecathlonApplication.getBackend().doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
        IHaleCommandType.SET_LIGHTING_COLOR, "#00CC00");
    color2.setDefaultModelObject(repository.getLightingColorCommand(IHaleRoom.BATHROOM).getValue());
    assertEquals("Checking onChange's insertion into repository.", "#00CC00",
        color.getDefaultModelObjectAsString());

    /** Testing drop down update. */

    // Living room
    roomDropDownChoice.setDefaultModelObject("Living Room");
    FormTester test = tester.newFormTester("testForm");
    tester.executeAjaxEvent("testForm:room", "onchange");
    assertEquals("Check that living room was set.",
        roomDropDownChoice.getDefaultModelObjectAsString(), "Living Room");

    repository.store(IHaleRoom.DINING, IHaleState.LIGHTING_ENABLED, timestamp, true);
    assertEquals("Checking button state", true, repository.getLightingEnabled(IHaleRoom.DINING)
        .getValue());
    
    /** Testing on/off buttons. */
    tester.clickLink("OnButton");
    assertEquals("Check that button is on.", true,
        repository.getLightingEnabledCommand(IHaleRoom.LIVING).getValue());
    
    tester.clickLink("OffButton");
    assertEquals("Check that button is off.", false,
        repository.getLightingEnabledCommand(IHaleRoom.LIVING).getValue());

    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();
  }
}
