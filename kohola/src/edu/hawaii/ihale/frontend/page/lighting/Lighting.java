package edu.hawaii.ihale.frontend.page.lighting;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
//import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
//import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.Header;

/**
 * The lighting page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Lighting extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private static final String greenTag = "<font color=\"green\">";
  private static final String fontTag = "</font>";
  private static final String successTag = "Success:<br />Desired room color is now ";
  private static final String CLASS = "class";
  private static final String LIVING_ROOM = "Living Room";
  private static final String DINING_ROOM = "Dining Room";
  private static final String KITCHEN = "Kitchen";
  private static final String BATHROOM = "Bathroom";
  
  private boolean livingState;
  private boolean diningState;
  private boolean kitchenState;
  private boolean bathroomState;

  private String buttonOn = "green-button";
  private String buttonOff = "gray-button";

  private HiddenField<String> colorChange;
  private Label feedback;

  private String setColor = "#FFFFFF";
  private String desiredColor = "#FFFFFF";

  private DropDownChoice<String> roomChoices;
  private static final List<String> rooms = Arrays.asList(new String[] { LIVING_ROOM,
      DINING_ROOM, KITCHEN, BATHROOM });
  private String currentRoom = ((SolarDecathlonSession) getSession()).getLightingSession()
      .getRoom();

  private Link<String> onButton;
  private Link<String> offButton;
  
  /**
   * Layout of page.
   */
  public Lighting() {

    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(3);

    Repository repository = new Repository();
    livingState = repository.getLightingEnabled(IHaleRoom.LIVING).getValue();
    diningState = repository.getLightingEnabled(IHaleRoom.DINING).getValue();
    kitchenState = repository.getLightingEnabled(IHaleRoom.KITCHEN).getValue();
    bathroomState = repository.getLightingEnabled(IHaleRoom.BATHROOM).getValue();
    
    // setLivingState();
    // setDiningState();
    // setKitchenState();
    // setBathroomState();

    // the on button
    onButton = new Link<String>("OnButton") {
      private static final long serialVersionUID = 1L;

      @Override
      /**
       * Turn on the light in this room.
       */
      public void onClick() {
        handleRoomState(currentRoom, true);
      }
    };
    // set markup id to true for ajax update
    onButton.setOutputMarkupId(true);

    // the off button
    offButton = new Link<String>("OffButton") {
      private static final long serialVersionUID = 1L;

      @Override
      /**
       * Turn off the light in this room.
       */
      public void onClick() {
        handleRoomState(currentRoom, false);
      }
    };
    // set markup id to true for ajax update
    offButton.setOutputMarkupId(true);

    // set the buttons according to user's current dropdownchoice
    if (LIVING_ROOM.equals(currentRoom)) {
      setButtons(livingState);
    }
    if (DINING_ROOM.equals(currentRoom)) {
      setButtons(diningState);
    }
    if (KITCHEN.equals(currentRoom)) {
      setButtons(kitchenState);
    }
    if (BATHROOM.equals(currentRoom)) {
      setButtons(bathroomState);
    }

    // create feedback and color selection
    feedback = new Label("feedback", "");
    feedback.setEscapeModelStrings(false);
    feedback.setOutputMarkupId(true);
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
          if (LIVING_ROOM.equals(currentRoom)) {
            room = IHaleRoom.LIVING;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for living room lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
            System.out.println(feedback.getDefaultModelObject());
          }
          else if (DINING_ROOM.equals(currentRoom)) {
            room = IHaleRoom.DINING;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for dining room lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
          }
          else if (KITCHEN.equals(currentRoom)) {
            room = IHaleRoom.KITCHEN;
            SolarDecathlonApplication.getBackend().doCommand(system, room, command, desiredColor);
            System.out.println("do command sent for kitchen lights color with color: "
                + desiredColor);
            feedback.setDefaultModelObject(greenTag + successTag + desiredColor + fontTag);
          }
          else if (BATHROOM.equals(currentRoom)) {
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
          target.addComponent(feedback);
        }
      }
    });

    add(colorChange);
    add(feedback);

    // Form<String> form = new Form<String>("form");
    // WebMarkupContainer wmcRoom = new WebMarkupContainer("room-wmc");
    // form.add(wmcRoom);
    // wmcRoom.setVisible(true);
    // roomChoices =
    // new DropDownChoice<String>("room", new PropertyModel<String>(
    // ((SolarDecathlonSession) getSession()).getLightingSession(), "room"),
    // ((SolarDecathlonSession) getSession()).getLightingSession().getRooms());
    roomChoices =
        new DropDownChoice<String>("room", new PropertyModel<String>(this, "currentRoom"), rooms);

    roomChoices.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      private static final long serialVersionUID = 1L;

      /**
       * For when user chooses new room.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        Repository repository = new Repository();
        String newSelection = roomChoices.getDefaultModelObjectAsString();
        currentRoom = newSelection;
        System.out.println("new room selection: " + newSelection);
        if (LIVING_ROOM.equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom(LIVING_ROOM);
          // set button to living state          
          livingState = repository.getLightingEnabled(IHaleRoom.LIVING).getValue();
          setButtons(livingState);
          // set intensity to living state
          // set color to living state
        }
        else if (DINING_ROOM.equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom(DINING_ROOM);
          // set button to dining state
          diningState = repository.getLightingEnabled(IHaleRoom.DINING).getValue();
          setButtons(diningState);
          // set intensity to dining state
          // set color to dining state
        }
        else if (KITCHEN.equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom(KITCHEN);
          // set button to kitchen state
          kitchenState = repository.getLightingEnabled(IHaleRoom.KITCHEN).getValue();
          setButtons(kitchenState);
          // set intensity to kitchen state
          // set color to kitchen state
        }
        else if (BATHROOM.equals(newSelection)) {
          ((SolarDecathlonSession) getSession()).getLightingSession().setRoom(BATHROOM);
          // set button to bathroom state
          bathroomState = repository.getLightingEnabled(IHaleRoom.BATHROOM).getValue();
          setButtons(bathroomState);
          // set intensity to bathroom state
          // set color to bathroom state
        }

        // reset feedback
        feedback.setDefaultModelObject("");
        // add components in the page we want to update to the target.
        target.addComponent(feedback);
        target.addComponent(onButton);
        target.addComponent(offButton);
      }
    });
    add(roomChoices.setRequired(true));
    add(onButton);
    add(offButton);
    // add(form);
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

  /**
   * Set the light switch according to the state of the selected room.
   * 
   * @param enabled Whether the lights are on.
   */
  private void setButtons(final boolean enabled) {
    onButton.add(new AbstractBehavior() {

      // support serialization
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        if (enabled) {
          tag.put(CLASS, buttonOn);
        }
        else {
          tag.put(CLASS, buttonOff);
        }
      }
    });

    offButton.add(new AbstractBehavior() {

      // support serialization
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        if (enabled) {
          tag.put(CLASS, buttonOff);
        }
        else {
          tag.put(CLASS, buttonOn);
        }
      }
    });

  }

  /**
   * Set the light switch and send a command to the house system.
   * 
   * @param roomName The room name.
   * @param enabled Whether the light is enabled.
   */
  private void handleRoomState(String roomName, boolean enabled) {

    IHaleBackend backend = new IHaleBackend();

    if (LIVING_ROOM.equals(roomName)) {
      livingState = enabled;
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
          IHaleCommandType.SET_LIGHTING_ENABLED, enabled);
    }
    if (DINING_ROOM.equals(roomName)) {
      diningState = enabled;
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
          IHaleCommandType.SET_LIGHTING_ENABLED, enabled);
    }
    if (KITCHEN.equals(roomName)) {
      kitchenState = enabled;
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
          IHaleCommandType.SET_LIGHTING_ENABLED, enabled);
    }
    if (BATHROOM.equals(roomName)) {
      bathroomState = enabled;
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
          IHaleCommandType.SET_LIGHTING_ENABLED, true);
    }

    if (enabled) {
      System.out.println("Command { ON } sent to " + roomName);
    }
    else {
      System.out.println("Command {OFF} sent to " + roomName);
    }
    feedback.setDefaultModelObject("");
    setButtons(enabled);
  }

}
