package edu.hawaii.ihale.ui.page.lights;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Extends the base page class to create the Lights page.
 * 
 * @author Shoji Bravo
 * @author Bret K. Ikehara
 */
public class LightsPageMain extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -3795013580495267776L;

  /**
   * Handles the content for main portion of the Lights page.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> LightsPageMain(String name, IModel<T> model) {
    super(name, model);
    // Set some values for the settings form.
    Map<String, String> settings = new HashMap<String, String>();

    Form<String> form = new Form<String>("form");

    form.add(new TextField<String>("LivingRoomLevel", new PropertyModel<String>(
            settings, "LivingRoomLevel")).setType(String.class));
    form.add(new TextField<String>("DiningRoomLevel", new PropertyModel<String>(
            settings, "DiningRoomLevel")).setType(String.class));
    form.add(new TextField<String>("KitchenRoomLevel", new PropertyModel<String>(
            settings, "KitchenRoomLevel")).setType(String.class));
    form.add(new TextField<String>("BathroomLevel", new PropertyModel<String>(
            settings, "BathroomLevel")).setType(String.class));


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

        /**
         * Display the page again, now with the updated values of field1 and
         * field2.
         */
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
