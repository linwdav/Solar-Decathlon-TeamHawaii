package edu.hawaii.ihale.frontend.page.help;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.frontend.page.Header;

/**
 * The help page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Help extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Graph to display.
   */
  private int currentPageDisplay = 0;

  /**
   * Buttons.
   */
  Link<String> overviewButton;
  Link<String> energyButton;
  Link<String> aquaponicsButton;
  Link<String> lightingButton;
  Link<String> temperatureButton;
  Link<String> securityButton;
  Link<String> reportsButton;
  Link<String> settingsButton;
  Link<String> adminButton;

  /**
   * Tile portion of Help Screen.
   */
  String title = "";

  /**
   * Info portion.
   */
  String info = "";

  /**
   * Layout of page.
   */
  public Help() {

    // Buttons on left of page
    overviewButton = new Link<String>("overviewButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 0;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    energyButton = new Link<String>("energyButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 1;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    aquaponicsButton = new Link<String>("aquaponicsButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 2;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    lightingButton = new Link<String>("lightingButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 3;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    temperatureButton = new Link<String>("temperatureButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 4;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    securityButton = new Link<String>("securityButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 5;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    reportsButton = new Link<String>("reportsButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 6;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    settingsButton = new Link<String>("settingsButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 7;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    adminButton = new Link<String>("adminButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        currentPageDisplay = 8;
        try {
          setResponsePage(new Help());
        }
        catch (Exception e) {
          
          e.printStackTrace();
        }
      }
    };

    Model<String> titleModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      public String getObject() {
        updateTitle(currentPageDisplay);
        return title;
      }
    };

    Model<String> infoModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      public String getObject() {
        updateInfo(currentPageDisplay);
        return info;
      }
    };

    add(new Label("titleHelp", titleModel));
    add(new Label("infoHelp", infoModel).setEscapeModelStrings(false));

    // Add buttons to page
    add(overviewButton);
    add(energyButton);
    add(aquaponicsButton);
    add(lightingButton);
    add(temperatureButton);
    add(securityButton);
    add(reportsButton);
    add(settingsButton);
    add(adminButton);

    makeButtonActive(currentPageDisplay);

  } // End Help Constructor

  /**
   * Displays the appropriate text in the info box.
   * 
   * @param i current page indicator
   */
  private void updateInfo(int i) {
    switch (i) {

    // Buttons
    case 0:
      info =
          "<p><strong>Dashboard</strong> - The dashboard provides a quick look"
              + " at essential statistics and status of all main systems.  The page also includes "
              + "system alerts as well.</p>"
              + "<p><strong>Energy</strong> - Shows power cosumption and power"
              + " balances. Also provides the user with a breakdown of power consumption by system "
              + " as well as a portal to change settings to increase energy efficiency.</p>"
              + "<p><strong>Aquaponics</strong> - Shows the status of all essential aquaponics "
              + " related"
              + " parameters.  Offers real-time monitoring and solutions to problematic areas.</p>"
              + "<p><strong>Lighting</strong> - Allows the user to select between different "
              + " lighting modes (follow me, manual, visualizer, and strobe), control lighting, "
              + " and configure a timer to set automated lighting schedules.</p>"
              + "<p><strong>Hvac</strong> - Lets the user set the temperature for the house"
              + " and aquaponics area.  Also monitors temperature at the various areas of the "
              + "house.</p>"
              + "<p><strong>Security</strong> - Manages the alarm (disabling/enabling), locks, "
              + "and security cameras.  This module also contains a viewer to select and review "
              + "recorded sessions from and camera.</p>"
              + "<p><strong>Reports</strong> - Allows for exporting of detailed statistical "
              + "reports on each system in the house.</p>"
              + "<p><strong>Settings</strong> - Modify and manage presets for temperature and "
              + "lighting.</p>"
              + "<p><strong>Administrator</strong> - View settings and options available only to "
              + "administrators.</p>";
      break;

    case 1: // pass-through
    case 2:
    case 3:
    default:
      info =
          "Information on this page is similar to the Overview page but specific"
              + " to a particular section of the Home Management System.";
      break;

    } // End switch
  } // End updateTitle

  /**
   * Determines which title to display.
   * 
   * @param i current page indicator
   */
  private void updateTitle(int i) {
    switch (i) {

    // Buttons
    case 0:
      title = "Overview";
      break;

    case 1:
      title = "Energy";
      break;

    case 2:
      title = "Aquaponics";
      break;

    case 3:
      title = "Lighting";
      break;
    case 4:
      title = "Hvac";
      break;

    case 5:
      title = "Security";
      break;

    case 6:
      title = "Reports";
      break;

    case 7:
      title = "Settings";
      break;
    case 8:
      title = "Administrator";
      break;

    default:
      break;

    } // End switch
  } // End updateTitle

  /**
   * Determines which button to make active.
   * 
   * @param i which button to make active
   */
  private void makeButtonActive(int i) {
    String classContainer = "class";
    String buttonContainer = "green-button";

    switch (i) {

    // Buttons
    case 0:
      overviewButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 1:
      energyButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 2:
      aquaponicsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 3:
      lightingButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;
    case 4:
      temperatureButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          "green-button")));
      break;

    case 5:
      securityButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 6:
      reportsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 7:
      settingsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;
    case 8:
      adminButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;
    default:
      break;
    } // End switch
  } // End makeButtonActive
} // End Help class

