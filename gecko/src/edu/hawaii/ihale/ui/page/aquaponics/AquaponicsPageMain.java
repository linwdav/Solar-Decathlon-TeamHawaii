package edu.hawaii.ihale.ui.page.aquaponics;

import edu.hawaii.ihale.ui.page.BasePanel;
import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


/**
 * Displays the overall status for the home page.
 * 
 * @author Shoji Bravo
 * @author Bret Ikehara
 */
public class AquaponicsPageMain extends BasePanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3393503073298832456L;

    /**
     * Handles the output for the status screen.
     * 
     * @param name
     *            String
     * @param <T>
     *            type
     * @param model
     *            Model<T>
     */
    public <T> AquaponicsPageMain(String name, IModel<T> model) {
        super(name, model);

        // Set some values for the settings form.
        Map<String, String> settings = new HashMap<String, String>();

        Form<String> form = new Form<String>("form");

        form.add(new TextField<String>("water_t", new PropertyModel<String>(
                settings, "water_t")).setType(String.class));


        // Cancel button to refresh the page without processing the form.
        Button cancel = new Button("cancel") {
            private static final long serialVersionUID = 1L;

            /** Refresh the page */
            @Override
            public void onSubmit() {
                setResponsePage(AquaponicsPage.class);
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

                setResponsePage(AquaponicsPage.class);
            }
        };
        // Disable processing of form.
        save.setDefaultFormProcessing(true);
        form.add(save);

        // Add the form to this page.
        add(form);

    }
    
}
