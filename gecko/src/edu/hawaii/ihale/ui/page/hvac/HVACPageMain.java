package edu.hawaii.ihale.ui.page.hvac;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Extends the base page class to create the HVAC page.
 * 
 * @author Michael Cera
 * @author Bret Ikehara
 * 
 */
public class HVACPageMain extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -3795013580495267776L;

  /**
   * Handles the content for main portion of the HVAC page.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> HVACPageMain(String name, IModel<T> model) {
    super(name, model);

    // Set some values for the settings form.
    Map<String, String> settings = new HashMap<String, String>();
    // HVAC System Status
    settings.put("HVACCurrentTemp", "65° F");
    settings.put("HVACStoredTemp", "70° F");
    settings.put("HVACPowerConsumption", "1 kWh");

    // HVAC System Settings
    settings.put("HVACPower", "true");
    settings.put("HVACDesiredTemp", "100");
    settings.put("HVACPowerLevel", "3");

    // Create the form that shows HVAC settings
    Form<String> form = new Form<String>("form");
    form.add(new CheckBox("HVACPower", new PropertyModel<Boolean>(settings, "HVACPower")));
    form.add(new TextField<String>("HVACDesiredTemp", new PropertyModel<String>(settings,
        "HVACDesiredTemp")).setType(String.class));
    form.add(new TextField<String>("HVACPowerLevel", new PropertyModel<String>(settings,
        "HVACPowerLevel")).setType(String.class));

    // Cancel button to refresh the page without processing the form.
    Button cancel = new Button("cancel") {
      private static final long serialVersionUID = 1L;

      /** Refresh the page */
      @Override
      public void onSubmit() {
        setResponsePage(HVACPage.class);
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
        setResponsePage(HVACPage.class);
      }
    };
    // Disable processing of form.
    save.setDefaultFormProcessing(false);
    form.add(save);

    // Add the form to this page.
    add(form);

  }
}