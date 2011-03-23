package edu.hawaii.ihale.frontend.page.lighting;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.Header;

/**
 * The lighting page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Lighting extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private String greenTag = "<font color=\"green\">";
  private String fontTag = "</font>";
  private String successTag = "Success:<br />Desired room color is now ";

  private String selectedRoom = ((SolarDecathlonSession) getSession()).getLightingSession()
      .getRoom();
  private boolean livingState = false;
  private boolean diningState = false;
  private boolean kitchenState = false;
  private boolean bathroomState = false;

  private String currentRoom = "";
  private String buttonOn = "green-button";
  private String buttonOff = "gray-button";

  private HiddenField<String> colorChange;
  private Label feedback;

  private String setColor = "#FFFFFF";
  private String desiredColor = "#FFFFFF";

  private DropDownChoice<String> roomChoices;

  /**
   * Layout of page.
   */
  public Lighting() {
    // More to come
    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(3);

    // setLivingState();
    // setDiningState();
    // setKitchenState();
    // setBathroomState();

    feedback = new Label("feedback", "");
    colorChange = new HiddenField<String>("colorchange", new Model<String>(setColor));
    // Added for jquery control.
    colorChange.setMarkupId(colorChange.getId());
    colorChange.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        setColor = colorChange.getValue();
        if (desiredColor.equals(setColor)) {
          feedback.setDefaultModelObject("<font color=\"#FF9900\">Unnecessary Change:<br />"
              + "Same as the original desired color (" + desiredColor + ")</font>");
        }
        else {
          desiredColor = setColor;

          IHaleSystem system = IHaleSystem.LIGHTING;
          IHaleCommandType command = IHaleCommandType.SET_LIGHTING_COLOR;
          IHaleRoom room;
          if ("Living Room".equals(currentRoom)) {
            room = IHaleRoom.LIVING;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for living room lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
            System.out.println(feedback.getDefaultModelObject());
          }
          else if ("Dining Room".equals(currentRoom)) {
            room = IHaleRoom.DINING;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for dining room lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
          }
          else if ("Kitchen".equals(currentRoom)) {
            room = IHaleRoom.KITCHEN;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for kitchen lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
          }
          else if ("Bathroom".equals(currentRoom)) {
            room = IHaleRoom.BATHROOM;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for bathroom lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
          }
          else {
            feedback.setDefaultModelObject("<font color = \"red\">"
                + "<b> Need to select a room. </b>");
          }
        }
      }
    });

    add(colorChange);

    feedback.setEscapeModelStrings(false);
    feedback.setOutputMarkupId(true);
    add(feedback);

    Form<String> form = new Form<String>("form");
    WebMarkupContainer wmcRoom = new WebMarkupContainer("room-wmc");
    form.add(wmcRoom);
    wmcRoom.setVisible(true);
    roomChoices =
        new DropDownChoice<String>("room", new PropertyModel<String>(
            ((SolarDecathlonSession) getSession()).getLightingSession(), "room"),
            ((SolarDecathlonSession) getSession()).getLightingSession().getRooms());

    roomChoices.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      private static final long serialVersionUID = 1L;

      /**
       * For when user chooses new room.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        String newSelection = roomChoices.getDefaultModelObjectAsString();
        System.out.println("new room selection: " + newSelection);
        if ("Living Room".equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom("Living Room");
          currentRoom = ((SolarDecathlonSession) getSession()).getLightingSession().getRoom();
          // set button to living state
          // set intensity to living state
          // set color to living state
        }
        else if ("Dining Room".equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom("Dining Room");
          currentRoom = ((SolarDecathlonSession) getSession()).getLightingSession().getRoom();
          // set button to dining state
          // set intensity to dining state
          // set color to dining state
        }
        else if ("Kitchen".equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom("Kitchen");
          currentRoom = ((SolarDecathlonSession) getSession()).getLightingSession().getRoom();
          // set button to kitchen state
          // set intensity to kitchen state
          // set color to kitchen state
        }
        else if ("Bathroom".equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom("Bathroom");
          currentRoom = ((SolarDecathlonSession) getSession()).getLightingSession().getRoom();
          // set button to bathroom state
          // set intensity to bathroom state
          // set color to bathroom state
        }
        // for "Choose One" option
        else {
          System.out.println("choose one selection");
          // make it just living or disable options?
        }
      }
    });
    wmcRoom.add(roomChoices.setRequired(true));

    // the on button
    Link<String> onButton = new Link<String>("OnButton") {
      private static final long serialVersionUID = 1L;

      @Override
      /**
       * Turn on the light in this room.
       */
      public void onClick() {
        System.out.println("Lights on");
        // do command here
      }
    };
    wmcRoom.add(onButton);

    // the off button
    Link<String> offButton = new Link<String>("OffButton") {
      private static final long serialVersionUID = 1L;

      @Override
      /**
       * Turn off the light in this room.
       */
      public void onClick() {
        System.out.println("Lights off");
        // do command here
      }
    };
    wmcRoom.add(offButton);
    add(form);
  }

  /**
   * Sets the state of the living room based on what is in repository.
   */
  // private void setLivingState() {
  // if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.LIVING).getValue()
  // == true) {
  // livingState = true;
  // }
  // else {
  // livingState = false;
  // }
  // }

  /**
   * Sets the state of the dining room based on what is in repository.
   */
  // private void setDiningState() {
  // if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.DINING).getValue()
  // == true) {
  // diningState = true;
  // }
  // else {
  // diningState = false;
  // }
  // }

  /**
   * Sets the state of the kitchen room based on what is in repository.
   */
  // public void setKitchenState() {
  // if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.KITCHEN).getValue()
  // == true) {
  // kitchenState = true;
  // }
  // else {
  // kitchenState = false;
  // }
  // }

  /**
   * Sets the state of the bathroom based on what is in repository.
   */
  // public void setBathroomState() {
  // if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.BATHROOM).getValue
  // () == true) {
  // bathroomState = true;
  // }
  // else {
  // bathroomState = false;
  // }
  // }

}
