package edu.hawaii.ihale.frontend.page.lighting;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
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

  private String selectedRoom = ((SolarDecathlonSession) getSession()).getLightingSession()
      .getRoom();
  private boolean livingState = false;
  private boolean diningState = false;
  private boolean kitchenState = false;
  private boolean bathroomState = false;

  private String buttonOn = "green-button";
  private String buttonOff = "gray-button";

  /**
   * Layout of page.
   */
  public Lighting() {
    // More to come
    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(3);

//    setLivingState();
//    setDiningState();
//    setKitchenState();
//    setBathroomState();

    Form<String> form = new Form<String>("form");
    WebMarkupContainer wmcRoom = new WebMarkupContainer("room-wmc");
    form.add(wmcRoom);
    wmcRoom.setVisible(true);
    DropDownChoice<String> roomChoices =
        new DropDownChoice<String>("room", new PropertyModel<String>(
        ((SolarDecathlonSession) getSession()).getLightingSession(), "room"),
        ((SolarDecathlonSession) getSession()).getLightingSession().getRooms()) {
      
      private static final long serialVersionUID = 1L;
      
      /**
       * For when user chooses new room.
       */
      @Override
      protected void onSelectionChanged(String newSelection) {
        if ("Living Room".equals(newSelection)) {
          //set button to living state
          //set intensity to living state
          //set color to living state
        }
        else if ("Dining Room".equals(newSelection)) {
          //set button to dining state
          //set intensity to dining state
          //set color to dining state
        }
        else if ("Kitchen".equals(newSelection)) {
          //set button to kitchen state
          //set intensity to kitchen state
          //set color to kitchen state
        }
        else if ("Bathroom".equals(newSelection)) {
          //set button to bathroom state
          //set intensity to bathroom state
          //set color to bathroom state
        }
        //for "Choose One" option
        else {
          // make it just lighting or disable options?
        }
      }
    };
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
//  private void setLivingState() {
//    if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.LIVING).getValue() == true) {
//      livingState = true;
//    }
//    else {
//      livingState = false;
//    }
//  }

  /**
   * Sets the state of the dining room based on what is in repository.
   */
//  private void setDiningState() {
//    if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.DINING).getValue() == true) {
//      diningState = true;
//    }
//    else {
//      diningState = false;
//    }
//  }

  /**
   * Sets the state of the kitchen room based on what is in repository.
   */
//  public void setKitchenState() {
//    if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.KITCHEN).getValue() == true) {
//      kitchenState = true;
//    }
//    else {
//      kitchenState = false;
//    }
//  }

  /**
   * Sets the state of the bathroom based on what is in repository.
   */
//  public void setBathroomState() {
//    if (SolarDecathlonApplication.getRepository().getLightingEnabled(IHaleRoom.BATHROOM).getValue() == true) {
//      bathroomState = true;
//    }
//    else {
//      bathroomState = false;
//    }
//  }

}
