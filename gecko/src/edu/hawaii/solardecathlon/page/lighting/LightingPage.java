package edu.hawaii.solardecathlon.page.lighting;

import java.util.List;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.components.AttributeModifier;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The lighting page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Bret K. Ikehara
 */
public class LightingPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private LightingModel propModel;
  private DropDownChoice<RoomModel> dropDown;
  private AjaxFallbackLink<String> onButton;
  private AjaxFallbackLink<String> offButton;
  private TextField<String> roomLevel;

  /**
   * Layout of page.
   */
  public LightingPage() {
    // Set model for easy reference.
    propModel = (LightingModel) session.getModel("lighting");

    resourceRef();
    formRef();
  }

  /**
   * References all javascript, css, and images.
   */
  private void resourceRef() {

    // Add image
    add(new Image("lighting-graph", new ResourceReference(BasePage.class,
        "images/lighting-graph.png")));

    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/lighting.css", CSS_SCREEN));
  }

  /**
   * References the form object.
   */
  private void formRef() {
    // add the form to control the lighting
    Form<String> form = new Form<String>("roomForm");
    form.setOutputMarkupId(true);
    add(form);

    // Set slider bar reference.
    roomLevel = new TextField<String>("roomLevel", new Model<String>("1%"));
    roomLevel.setMarkupId(roomLevel.getId());
    roomLevel.add(new AjaxFormComponentUpdatingBehavior("onchange") {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 9032711839250500028L;

      /**
       * Update the buttons.
       */
      protected void onUpdate(AjaxRequestTarget target) {
        RoomModel roomModel = dropDown.getModelObject();

        String value = roomLevel.getValue();
        value = value.substring(0, value.length() - 1);
        roomModel.setLevel(Long.valueOf(value));
      }
    });
    form.add(roomLevel);

    List<RoomModel> roomlist = propModel.getRoomList();

    // Create drop down
    dropDown =
        new DropDownChoice<RoomModel>("roomChoice", new Model<RoomModel>(roomlist.get(0)),
            roomlist);
    dropDown.setMarkupId("roomChoice");
    dropDown.setChoiceRenderer(new ChoiceRenderer<RoomModel>("name"));
    dropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 9032711839250500028L;

      /**
       * Update the buttons.
       */
      protected void onUpdate(AjaxRequestTarget target) {
        RoomModel roomModel = dropDown.getModelObject();

        updateStatusButtonStyle();

        // Reflect changes dynamically on the page.
        target.addComponent(onButton);
        target.addComponent(offButton);

        // Reflect new value on the slider bar.
        target.prependJavascript("$( '#sliderBright' ).slider('value', "
            + roomModel.getLevel().intValue() + ");");
      }

    });
    form.add(dropDown);

    // On and Off butons
    onButton = new AjaxFallbackLink<String>("roomStatusOn") {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 4991339741881286535L;

      /**
       * Turn the lights on through lighting session property.
       */
      @Override
      public void onClick(AjaxRequestTarget target) {
        /*
         * RoomModel roomModel = dropDown.getModelObject(); roomModel.setLevel(100L);
         */
        updateStatusButtonStyle();

        // Reflect changes dynamically on the page.
        target.addComponent(onButton);
        target.addComponent(offButton);
      }
    };
    form.add(onButton);

    offButton = new AjaxFallbackLink<String>("roomStatusOff") {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 4991339741881286535L;

      /**
       * Turn the lights off through lighting session property.
       */
      @Override
      public void onClick(AjaxRequestTarget target) {
        /*
         * RoomModel roomModel = dropDown.getModelObject(); roomModel.setLevel(0L);
         */

        updateStatusButtonStyle();

        // Reflect changes dynamically on the page.
        target.addComponent(onButton);
        target.addComponent(offButton);
      }
    };
    form.add(offButton);

    // Update the style of the on/off button.
    updateStatusButtonStyle();
  }

  /**
   * Updates the on and off button.
   */
  private void updateStatusButtonStyle() {

    if (dropDown == null) {
      System.out.println("test");
    }

    //TODO update status with model.
    //RoomModel room = dropDown.getModelObject();

    boolean status = false;
    String onClass = (status) ? CLASS_BTN_GREEN : CLASS_BTN_GRAY;
    String offClass = (status) ? CLASS_BTN_GRAY : CLASS_BTN_GREEN;

    onButton.add(new AttributeModifier("class", true, new Model<String>(onClass),
        CLASS_PAT_BTN));

    offButton.add(new AttributeModifier("class", true, new Model<String>(offClass),
        CLASS_PAT_BTN));
  }
}