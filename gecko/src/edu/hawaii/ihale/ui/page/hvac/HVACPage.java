package edu.hawaii.ihale.ui.page.hvac;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.HVacListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;


/**
 * HVAC page.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class HVACPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private transient HVacListener listener;
  
  /**
   * Creates the HVAC page.
   */
  public HVACPage() {
    
    listener = (HVacListener) this.getSession().getSystemStateListener("hvac");

    final FeedbackPanel feedback = new FeedbackPanel("feedback");
    add(feedback);
    
    Form<String> form = new Form<String>("form");
    form.add(new TextField<Long>("temp", new PropertyModel<Long>(listener.getModel(), "temp")));

    // Save button, currently does not process the form.
    AjaxSubmitLink submit = new AjaxSubmitLink("save") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -6391281604352212246L;

      /**
       * Updates the hvac.
       * 
       * @param target AjaxRequestTarget
       * @param form Form<?>
       */
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        // Send command to the backend
        String temp = listener.getModel().getTemp().toString();
        List<String> args = new ArrayList<String>();
        args.add(temp);

        database.doCommand("Hvac", "Arduino-2", "setTemp", args);

        // Update the side panel
        listener.getDatabaseUpdate().onRequest();

        // Should show feedback, but nothing is showing.
        target.addComponent(feedback, "feedback");
        info("Update Value to " + temp);
      }

      /**
       * Displays error.
       */
      @Override
      protected void onError(AjaxRequestTarget target, Form<?> form) {
        super.onError(target, form);
        target.addComponent(feedback);
      }
    };
    
    form.add(submit);

    // Add the form to this page.
    add(form);
  }
  
  /**
   * Renders the sidebar with session variables.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    
    this.database.addSystemStateListener(listener);
    
    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Temperature"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "Temp"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}