package edu.hawaii.ihale.ui.page.lights;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Extends the base page class to create the lights page.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class LightsPageMain extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -3795013580495267776L;

  /**
   * List of rooms.
   */
  private static final List<String> rooms = Arrays.asList(new String[] { "Living Room",
      "Dining Room", "Bedroom", "Kitchen", "Bathroom" });

  /**
   * Handles the content for main portion of the lights page.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> LightsPageMain(String name, IModel<T> model) {
    super(name, model);

    // Set some values for the settings form.
    Map<String, String> settings = new HashMap<String, String>();
    settings.put("LightsPower", "ON");
    settings.put("LightsColor", "White");
    settings.put("LightsIntensity", "3");
    settings.put("LightsDynamic", "true");
    settings.put("LightsSensitivity", "2");
    settings.put("LightsTimer", "10");

    // Create the form that shows lights settings
    Form<String> form = new Form<String>("form");
    form.add(new DropDownChoice<String>("RoomDropDown", rooms));
    form.add(new TextField<String>("LightsColor",
        new PropertyModel<String>(settings, "LightsColor")).setType(String.class));
    form.add(new TextField<String>("LightsIntensity", new PropertyModel<String>(settings,
        "LightsIntensity")).setType(String.class));
    form.add(new CheckBox("LightsDynamic", new PropertyModel<Boolean>(settings, "LightsDynamic")));
    form.add(new TextField<String>("LightsSensitivity", new PropertyModel<String>(settings,
        "LightsSensitivity")).setType(String.class));
    form.add(new TextField<String>("LightsTimer",
        new PropertyModel<String>(settings, "LightsTimer")).setType(String.class));
    form.add(new CheckBox("LightsParty"));

    // Cancel button to refresh the page without processing the form.
    Button cancel = new Button("cancel") {
      private static final long serialVersionUID = 1L;

      /** Refresh the page */
      @Override
      public void onSubmit() {
        setResponsePage(LightsPage.class);
      }
    };
    // Disable processing of form.
    cancel.setDefaultFormProcessing(false);
    form.add(cancel);

    // Save button, currently does not process the form.
    Button save = new Button("save") {
      private static final long serialVersionUID = 1L;

      /** Display the page again, now with the updated values of field1 and field2. */
      @Override
      public void onSubmit() {
        setResponsePage(LightsPage.class);
      }
    };
    // Disable processing of form.
    save.setDefaultFormProcessing(false);
    form.add(save);

    // Add the form to this page.
    add(form);
  }
}