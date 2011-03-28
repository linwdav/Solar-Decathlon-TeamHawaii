package edu.hawaii.ihale.frontend.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
//import org.apache.wicket.ajax.markup.html.form.AjaxButton;
//import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import edu.hawaii.ihale.frontend.page.aquaponics.AquaPonics;
//import edu.hawaii.ihale.frontend.page.energy.Energy;

/**
 * SelectContentPanel class that defines what will be shown inside of
 * modal window.
 * 
 * @author kurtteichman
 */
public abstract class SelectContentPanel extends Panel {
  /** */
  private static final long serialVersionUID = 1L;

    /**
     * Creates the content panel for the error modal window.
     * @param id to define the content panel.
     * @param systemChecker object that allows to see which systems 
     * are erroneous.
     */
    public SelectContentPanel(String id, final SystemChecker systemChecker) {
        super(id);
        // Add some example 'selection' methods, to show as example
        // You can also use a full fledged form, I left that as an
        // exercise for the reader :-)
        super.add(new AjaxLink<Object>("selectionLink") {
          /** */
          private static final long serialVersionUID = 1L;
            /**
             * @param target can set the target to be dynamically
             * changed.
             */
            public void onClick(AjaxRequestTarget target) {
                //onSelect(target, 
                //new String("Selected something using the link."));
              if (systemChecker.aquaponicsError) {  
                  setResponsePage(AquaPonics.class);
              }
            }
        });

        /*
        form.add(new AjaxButton("selectionButton"){
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                onSelect(target, new String("Selected something using the button."));
            }
        });
        */

        /*
        form.add(new AjaxButton("close") {
            public void onSubmit(AjaxRequestTarget target, Form form) {
                onCancel(target);
            }
        });
        */
    }
}