package edu.hawaii.ihale.ui.page.hvac;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Extends the base page class to create the HVAC page.
 * 
 * @author Shoji Bravo
 * @author Bret K. Ikehara
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

    Form<String> form = new Form<String>("form");

    form.add(new TextField<String>("hvac_t", new PropertyModel<String>(
            settings, "hvac_t")).setType(String.class));


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

        /**
         * Display the page again, now with the updated values of field1 and
         * field2.
         */
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
