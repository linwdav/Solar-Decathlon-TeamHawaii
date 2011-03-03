package edu.hawaii.ihale.ui.page.aquaponics;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;
import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.wicket.url.ExternalImageUrl;

/**
 * Aquaponics page.
 * 
 * @author Bret K. Ikehara
 * @revised Shoji Bravo
 */
public class AquaponicsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private transient AquaponicsListener listener;

  /**
   * Creates the Aquaponics page.
   */
  public AquaponicsPage() {

    listener = (AquaponicsListener) this.getSession().getSystemStateListener("aquaponics");

    final FeedbackPanel feedback = new FeedbackPanel("feedback", IFeedbackMessageFilter.ALL);
    feedback.setOutputMarkupId(true);
    add(feedback);

    AjaxSubmitLink submit = new AjaxSubmitLink("submit") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1206546233742642460L;

      /**
       * Submits the data to the database.
       */
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        super.onSubmit();

        // Send command to the backend
        String temp = listener.getModel().getTemp().toString();
        List<String> args = new ArrayList<String>();
        args.add(temp);

        database.doCommand("Aquaponics", "Arduino-2", "setTemp", args);

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
    submit.add(new SimpleAttributeModifier("value", "Update"));

    String tempID = "Temp";

    Form<String> form = new Form<String>("form");
    form.add(new Label("tempLabel", "Temperature:").setMarkupId(tempID));
    form.add(new TextField<Long>("temp", new PropertyModel<Long>(listener.getModel(), "temp"))
        .setMarkupId(tempID));
    form.add(submit);
    add(form);

    String url =
        "http://chart.apis.google.com/chart?"
            + "chxs=1,676767,11.5,1,l,676767&chxt=x,y&chs=600x400&cht=lxy&chco=76A4FB"
            + "&chd=s:_,llllllx_________________&chls=1&chm=B,C6D9FD,0,0,0,-1|D,3399CC,0,0.1,2,1"
            + "&chtt=Aquaponics+Energy+Consumption&chts=676767,12.5";

    add(new ExternalImageUrl("chart", url, "600", "400"));
  }

  /**
   * Renders the sidebar.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    // Initiate the sidebar panel
    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Time Stamp"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "timestamp"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Oxygen"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "oxygen"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Temperature"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "temp"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "pH"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "ph"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.setOutputMarkupId(true);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}
