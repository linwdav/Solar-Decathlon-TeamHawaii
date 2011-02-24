package edu.hawaii.ihale.ui.page.lights;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import edu.hawaii.ihale.ui.LightingListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.Sidebar;
import edu.hawaii.ihale.ui.page.SidebarPanel;



/**
 * Lights page.
 * 
 * @author Michael Cera
 * @author Bret K. Ikehara
 */
public class LightsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  private transient LightingListener listener;

  /**
   * Creates the lights page.
   */
  public LightsPage() {
      
      listener = new LightingListener();
//      final FeedbackPanel feedback = new FeedbackPanel("feedback");
//      add(feedback);
      Form<String> form = new Form<String>("form");
      form.add(new TextField<Long>("LivingRoomLevel", new PropertyModel<Long>(
              listener.getModel(), "livingRoom")));
      form.add(new TextField<Long>("DiningRoomLevel", new PropertyModel<Long>(
              listener.getModel(), "diningRoom")));
      form.add(new TextField<Long>("KitchenRoomLevel", new PropertyModel<Long>(
              listener.getModel(), "kitchenRoom")));
      form.add(new TextField<Long>("BathroomLevel", new PropertyModel<Long>(
              listener.getModel(), "bathroom")));
      
      // Save button, currently does not process the form.
      AjaxSubmitLink submit;
      
      submit = new AjaxSubmitLink("saveLivingRoom") {
        /**
         * Serial ID.
         */
        private static final long serialVersionUID = -6391281604352212246L;

        /**
         * Updates the Lights.
         * 
         * @param target AjaxRequestTarget
         * @param form Form<?>
         */
        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
          // Send command to the backend
          String temp1 = listener.getModel().getLivingRoom().toString();
          List<String> args = new ArrayList<String>();
          args.add(temp1);

          database.doCommand("Lighting-1", "Arduino-5", "setLivingRoom", args);

          // Update the side panel
          listener.getDatabaseUpdate().onRequest();

          // Should show feedback, but nothing is showing.
  //        target.addComponent(feedback, "feedback");
  //        info("Update Value to " + temp1);
        }

        /**
         * Displays error.
         */
        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
          super.onError(target, form);
//          target.addComponent(feedback);
        }
      };
      form.add(submit);
      submit = new AjaxSubmitLink("saveDiningRoom") {
          /**
           * Serial ID.
           */
          private static final long serialVersionUID = -6391281604352212246L;

          /**
           * Updates the Lights.
           * 
           * @param target AjaxRequestTarget
           * @param form Form<?>
           */
          @Override
          protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            // Send command to the backend
            String temp1 = listener.getModel().getDiningRoom().toString();
            List<String> args = new ArrayList<String>();
            args.add(temp1);

            database.doCommand("Lighting-2", "Arduino-6", "setDiningRoom", args);

            // Update the side panel
            listener.getDatabaseUpdate().onRequest();

            // Should show feedback, but nothing is showing.
 //           target.addComponent(feedback, "feedback");
 //           info("Update Value to " + temp1);
          }

          /**
           * Displays error.
           */
          @Override
          protected void onError(AjaxRequestTarget target, Form<?> form) {
            super.onError(target, form);
   //         target.addComponent(feedback);
          }
        };
        form.add(submit);
        
        submit = new AjaxSubmitLink("saveKitchenRoom") {
            /**
             * Serial ID.
             */
            private static final long serialVersionUID = -6391281604352212246L;

            /**
             * Updates the Lights.
             * 
             * @param target AjaxRequestTarget
             * @param form Form<?>
             */
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
              // Send command to the backend
              String temp1 = listener.getModel().getKitchenRoom().toString();
              List<String> args = new ArrayList<String>();
              args.add(temp1);

              database.doCommand("Lighting-3", "Arduino-7", "setKitchenRoom", args);

              // Update the side panel
              listener.getDatabaseUpdate().onRequest();

              // Should show feedback, but nothing is showing.
//              target.addComponent(feedback, "feedback");
 //             info("Update Value to " + temp1);
            }

            /**
             * Displays error.
             */
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
              super.onError(target, form);
  //            target.addComponent(feedback);
            }
          };
          form.add(submit);
          
          submit = new AjaxSubmitLink("saveBathroom") {
              /**
               * Serial ID.
               */
              private static final long serialVersionUID = -6391281604352212246L;

              /**
               * Updates the Lights.
               * 
               * @param target AjaxRequestTarget
               * @param form Form<?>
               */
              @Override
              protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                // Send command to the backend
                String temp1 = listener.getModel().getBathroom().toString();
                List<String> args = new ArrayList<String>();
                args.add(temp1);

                database.doCommand("Lighting-4", "Arduino-8", "setBathroom", args);

                // Update the side panel
                listener.getDatabaseUpdate().onRequest();

                // Should show feedback, but nothing is showing.
 //               target.addComponent(feedback, "feedback");
 //               info("Update Value to " + temp1);
              }

              /**
               * Displays error.
               */
              @Override
              protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
  //              target.addComponent(feedback);
              }
            };
            form.add(submit);
      // Add the form to this page.
      add(form);
  }

  /**
   * Renders more components with access to session.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();


    List<Sidebar> list = new ArrayList<Sidebar>();
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Living Room"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "livingRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Dining Room"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "diningRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Kitchen"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "kitchenRoom"))));
    list.add(new Sidebar(new Label(SidebarPanel.LEFT, "Bathroom"), new Label(SidebarPanel.RIGHT,
        new PropertyModel<Long>(listener.getModel(), "bathroom"))));

    SidebarPanel sidebar = new SidebarPanel("Sidebar", list);
    sidebar.add(listener.getDatabaseUpdate());
    add(sidebar);
  }
}