package edu.hawaii.ihale.frontend.page.help;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
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

  // for normal fonts
  private static final String P_OPEN_TAG = "<p style=\"line-height:25px;\">";
  // bigger and different color font for title of each paragraph
  private static final String P_OPEN_TAG2 =
      "<p class=\"medium-large dark-blue\" style=\"line-height:35px;\">";
  private static final String P_CLOSE_TAG = "</p>";

  // private static final String P_OPEN_TAG3 =
  // "<p class=\"large dark-blue\" style=\"line-height:35px;\">";
  private static final String LINEBREAK = "<br/>";

  /**
   * Graph to display.
   */
  // private int currentPageDisplay = 0;

  /**
   * Buttons.
   */
  Link<String> overviewButton;
  Link<String> dashboardButton;
  Link<String> energyButton;
  Link<String> aquaponicsButton;
  Link<String> lightingButton;
  Link<String> hvacButton;
  // Link<String> securityButton;
  // Link<String> reportsButton;
  // Link<String> settingsButton;
  // Link<String> adminButton;

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
    // set the tab to light up for the header
    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(5);

    // set which tab to light up on the left
    int currentTab = ((SolarDecathlonSession) getSession()).getHelpSession().getCurrentTab();

    // Buttons on left of page
    overviewButton = new Link<String>("overviewButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        // currentPageDisplay = 0;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(0);
        try {
          setResponsePage(Help.class);
        }
        catch (Exception e) {

          e.printStackTrace();
        }
      }
    };

    dashboardButton = new Link<String>("dashboardButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        // currentPageDisplay = 1;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(1);
        try {
          setResponsePage(Help.class);
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
        // currentPageDisplay = 1;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(2);
        try {
          setResponsePage(Help.class);
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
        // currentPageDisplay = 2;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(3);
        try {
          setResponsePage(Help.class);
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
        // currentPageDisplay = 3;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(4);
        try {
          setResponsePage(Help.class);
        }
        catch (Exception e) {

          e.printStackTrace();
        }
      }
    };

    hvacButton = new Link<String>("hvacButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        // currentPageDisplay = 4;
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(5);
        try {
          setResponsePage(Help.class);
        }
        catch (Exception e) {

          e.printStackTrace();
        }
      }
    };

    // securityButton = new Link<String>("securityButton") {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void onClick() {
    // currentPageDisplay = 5;
    // try {
    // setResponsePage(new Help());
    // }
    // catch (Exception e) {
    //
    // e.printStackTrace();
    // }
    // }
    // };
    //
    // reportsButton = new Link<String>("reportsButton") {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void onClick() {
    // currentPageDisplay = 6;
    // try {
    // setResponsePage(new Help());
    // }
    // catch (Exception e) {
    //
    // e.printStackTrace();
    // }
    // }
    // };
    //
    // settingsButton = new Link<String>("settingsButton") {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void onClick() {
    // currentPageDisplay = 7;
    // try {
    // setResponsePage(new Help());
    // }
    // catch (Exception e) {
    //
    // e.printStackTrace();
    // }
    // }
    // };
    //
    // adminButton = new Link<String>("adminButton") {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void onClick() {
    // currentPageDisplay = 8;
    // try {
    // setResponsePage(new Help());
    // }
    // catch (Exception e) {
    //
    // e.printStackTrace();
    // }
    // }
    // };

    Model<String> titleModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      public String getObject() {
        updateTitle(((SolarDecathlonSession) getSession()).getHelpSession().getCurrentTab());
        return title;
      }
    };

    Model<String> infoModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      public String getObject() {
        updateInfo(((SolarDecathlonSession) getSession()).getHelpSession().getCurrentTab());
        return info;
      }
    };

    add(new Label("titleHelp", titleModel));
    add(new Label("infoHelp", infoModel).setEscapeModelStrings(false));

    // Add buttons to page
    add(overviewButton);
    add(dashboardButton);
    add(energyButton);
    add(aquaponicsButton);
    add(lightingButton);
    add(hvacButton);
    // add(securityButton);
    // add(reportsButton);
    // add(settingsButton);
    // add(adminButton);

    makeButtonActive(currentTab);

  } // End Help Constructor

  /**
   * Displays the appropriate text in the info box.
   * 
   * @param i current page indicator
   */
  private void updateInfo(int i) {
    switch (i) {

    // overview
    case 0:
      info =
          P_OPEN_TAG2 + "Dashboard" + P_CLOSE_TAG + P_OPEN_TAG
              + "The dashboard provides a quick look"
              + " at essential statistics and status of all main systems.  The page also includes "
              + "system alerts as well.</p>" + LINEBREAK + P_OPEN_TAG2 + "Energy" + P_CLOSE_TAG
              + P_OPEN_TAG + "Shows current power cosumption and current power"
              + " balances as well as historical comparison of the two.</p>" + LINEBREAK
              + P_OPEN_TAG2 + "Aquaponics" + P_CLOSE_TAG + P_OPEN_TAG
              + "Shows the status of all essential aquaponics " + " related"
              + " parameters.  Offers real-time monitoring and solutions to problematic areas.</p>"
              + LINEBREAK + P_OPEN_TAG2 + "Lighting" + P_CLOSE_TAG + P_OPEN_TAG
              + "Allows the user to control the lighting in each room. </p> " + LINEBREAK
              + P_OPEN_TAG2 + "Hvac" + P_CLOSE_TAG + P_OPEN_TAG
              + "Lets the user set the temperature for the house"
              + " and aquaponics area.  Also monitors temperature at the various areas of the "
              + "house.</p>";

      break;
    // dashboard
    case 1:
      info =
          P_OPEN_TAG2 + "System Energy Statistics" + P_CLOSE_TAG + P_OPEN_TAG
              + "The graphs on the left show the comparison between the power generation"
              + " and the power consumption in kWh over a period of time "
              + "(Daily, Weekly, or Monthly)." + P_CLOSE_TAG + P_OPEN_TAG + "<strong>Day</strong>"
              + " - This graph contains the entries of power generation"
              + " and consumption over the last 24 hours." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Week</strong>" + " - This graph contains the entries of power generation"
              + " and consumption over the last 7 days." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Month</strong>" + " - This graph contains the entries of power generation"
              + " and consumption over the last 30 days." + P_CLOSE_TAG + LINEBREAK + P_OPEN_TAG2
              + "Localization" + P_CLOSE_TAG + P_OPEN_TAG
              + "Shows the current inside and outside temperatures as well as the weather"
              + " forecasts for the next few days. Note that all information regarding weather"
              + " is obtained from Google Weather API." + P_CLOSE_TAG + LINEBREAK + P_OPEN_TAG2
              + "System Log" + P_CLOSE_TAG + P_OPEN_TAG
              + "The system log provides the historical system status"
              + " messages for each house system (PV, Aquaponics, Lighting, and Hvac). A new"
              + " entry is added each time a user performs a command to the house systems."
              + P_CLOSE_TAG;
      break;
    // energy
    case 2:
      info =
          P_OPEN_TAG2 + "System Energy Statistics" + P_CLOSE_TAG + P_OPEN_TAG
              + "The graphs on the left show the comparison of the power generation "
              + "and the power consumption in kWh over a period of time"
              + " (Daily, Weekly, or Monthly). They also give the users a sense of"
              + " how much money they are saving approximately." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Day</strong> - This graph contains the entries of power generation"
              + " and consumption over the last 24 hours." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Week</strong> - This graph contains the entries of power generation"
              + " and consumption over the last 7 days." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Month</strong> - This graph contains the entries of power generation"
              + " and consumption over the last 30 days." + P_CLOSE_TAG + LINEBREAK + P_OPEN_TAG2
              + "Current Levels" + P_CLOSE_TAG + P_OPEN_TAG
              + "This section shows the current power generation and current"
              + " power consumption which helps the users become more aware of their power usage"
              + ". Whether the values are good or bad is indicated by the following colors:"
              + P_CLOSE_TAG + P_OPEN_TAG + "<font color=\"red\"><strong>Red</strong></font>:"
              + " - When the power consumption is greater than the power generation." + P_CLOSE_TAG
              + P_OPEN_TAG + "<font color=\"#FF9900\"><strong>Orange</strong></font>:"
              + " - When the power consumption is the same as the power generation." + P_CLOSE_TAG
              + P_OPEN_TAG + "<font color=\"green\"><strong>Green</strong></font>:"
              + " - When the power consumption is smaller than the power generation." + P_CLOSE_TAG
              + P_OPEN_TAG2 + LINEBREAK + "Settings" + P_CLOSE_TAG + P_OPEN_TAG
              + "When the current consumption"
              + " is abnormally high, the users can simply click on any link and direct themselves"
              + " to the pages for other house systems and turn them off to cut back the power"
              + " usage." + P_CLOSE_TAG + LINEBREAK + P_OPEN_TAG2 + "Energy Log" + P_CLOSE_TAG
              + P_OPEN_TAG
              + "The energy status messages section provides the historical system status"
              + " messages for PV and electrical systems." + P_CLOSE_TAG;
      break;
    // aquaponics
    case 3:
      info =
          P_OPEN_TAG2
              + "Aquaponics Statistics"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "This link takes the user to a page that has the graph representations of" 
              + " the conditions for the aquaponics system."             
              + P_CLOSE_TAG
              + LINEBREAK
              + P_OPEN_TAG2
              + "Status"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "The status section shows the status of all essential aquaponics related "
              + "parameters listed at the bottom of this page along with their descriptions"
              + " and recommanded range. Whether the values are good or bad is indicated by"
              + " the following colors:"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<font color=\"red\"><strong>Red</strong></font>:"
              + " - When the value is far outside the optimal range."
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<font color=\"#FF9900\"><strong>Orange</strong></font>:"
              + " - When the value is close to the borderline of the optimal range."
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<font color=\"green\"><strong>Green</strong></font>:"
              + " - When the value is within the optimal range."
              + P_CLOSE_TAG

              + LINEBREAK
              // the controls
              + P_OPEN_TAG2
              + "Controls"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "This section provides ways to interact with the aquaponics system. The users "
              + "can set the desired values for different parameters. "
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<b>Water Temp</b> : Allow the user to change the desired water "
              + "temperature (F&deg;) for the system."
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<b>pH Level</b> : Allow the user to change the pH level for the system"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<b>Water Level</b> : Allow the user to change the water "
              + "level (inches) for the system"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<b>Water nutrients</b> : Allow the user to change the "
              + "water nutrients for the system"
              + P_CLOSE_TAG
              + LINEBREAK
              // aquaponics status messages
              + P_OPEN_TAG2
              + "Aquaponics Log"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "The aquaponics log section provides a list of the historical system changes"
              + " for the aquaponics system. A new entry is added each time a user"
              + " performs a command to change the water quality in the aquaponics system."
              + P_CLOSE_TAG
              + LINEBREAK
              + LINEBREAK
              + LINEBREAK
              + LINEBREAK
              + LINEBREAK
              + LINEBREAK
              // parameters start from here
              // ph
              + P_OPEN_TAG2
              + "<u>pH</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "In chemistry, pH is a measure of the acidity or basicity of a solution."
              + "Pure water is said to be neutral, with a pH close to 7.0 at 25°C (77°F)"
              + ". Solutions with a pH less than 7 are said to be acidic and solutions with a"
              + " pH greater than 7 are said to be basic or alkaline."
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<strong>Optimal pH range</strong>: 6.8 - 8.0"
              + P_CLOSE_TAG
              + LINEBREAK
              // temperature
              + P_OPEN_TAG2
              + "<u>Temperature</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "Temperature indicates the degree of hotness or coldness of the water."
              + "The amount of oxygen which can dissolved in water depends on "
              + "temperature. The colder the water the more oxygen it can hold. It's "
              + "important to keep the temperature within the appropriate range."
              + "Unit used: Fahrenheit (F&deg;)"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<strong>Optimal temperature range</strong>: 82F&deg; - 86F&deg;"
              + P_CLOSE_TAG
              + LINEBREAK
              // oxygen
              + P_OPEN_TAG2
              + "<u>Dissolved Oxygen</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "Dissolved oxygen is the amount of oxygen that is dissolved in the water"
              + ". It's an indication of how well the water can support aquatic"
              + " plan and animal life. In most cases, higher dissolved oxygen level "
              + "means better water quality. Unit used:"
              + " mg/l"
              + P_OPEN_TAG
              + "<strong>Optimal DO range</strong>: 4.5 - 5.5 mg/l"
              + P_CLOSE_TAG
              + P_CLOSE_TAG
              + LINEBREAK
              +

              // electrical conductivity
              P_OPEN_TAG2
              + "<u>Electrical conductivity</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "Electrical conductivity estimates the amount of total dissolved"
              + " salts or ions in the water. Unit used: uS/cm"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "<strong>Optimal EC range</strong>: 10.0 - 20.0 &micro;s/cm"
              + P_CLOSE_TAG
              + LINEBREAK
              // circulation
              + P_OPEN_TAG2
              + "<u>Circulation</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "Circulation indicates the total amount of time the pump has been"
              + "on in one day divided by the total water volume. Unit used: hours/gallons."
              + P_OPEN_TAG
              + "<strong>Optimal circulation range</strong>:"
              + " 60 - 100gpm"
              + P_CLOSE_TAG
              + P_CLOSE_TAG
              + LINEBREAK
              // turbidity
              + P_OPEN_TAG2
              + "<u>Turbidity</u>"
              + P_CLOSE_TAG
              + P_OPEN_TAG
              + "Turbidity is the cloudiness of a fluid caused by individual particles."
              + "The measurement of turbidity indicates the water quality. Unit used: Formazin "
              + "Turbidity Unit (FTU)."
              + P_OPEN_TAG
              + "<strong>Optimal turbidity range</strong>: 0 - 100 NTUs"
              + P_CLOSE_TAG
              + P_CLOSE_TAG
              + LINEBREAK
              // water level
              + P_OPEN_TAG2 + "<u>Water Level</u>" + P_CLOSE_TAG + P_OPEN_TAG
              + "The measurement of water level indicates the amount of water in the"
              + " aquaponics system. Unit used: gallons" + P_OPEN_TAG
              + "<strong>Optimal water level range</strong>: 36 - 48 inches"
              + P_CLOSE_TAG
              + P_CLOSE_TAG
              + LINEBREAK
              // nutrient
              + P_OPEN_TAG2 + "<u>Nutrient</u>" + P_CLOSE_TAG + P_OPEN_TAG
              + "The measurement of nutrients indicated the amount of nutrients in" + " the water."
              + P_CLOSE_TAG + P_OPEN_TAG + "<strong>Optimal nutrient range</strong>: TBA by David"
              + P_CLOSE_TAG;

      break;

    // lighting
    case 4:
      info =
          P_OPEN_TAG2 + "General Settings" + P_CLOSE_TAG + P_OPEN_TAG
              + "This section allows the user to control the lighting (e.g. light switch"
              + ", light brightness, light color) in each room." + P_CLOSE_TAG + P_OPEN_TAG2
              + "Lighting Log" + P_CLOSE_TAG + P_OPEN_TAG
              + "The lighting log section provides a list of the historical system changes"
              + " for the lighting system. A new entry is added each time a user"
              + " changes the lighting in the house." + P_CLOSE_TAG;
      break;

    case 5:
      info =
          P_OPEN_TAG2 + "Temperature Settings" + P_CLOSE_TAG + P_OPEN_TAG
              + "This section allows the user to control the air conditioning system."
              + P_CLOSE_TAG + LINEBREAK + P_OPEN_TAG2 + "Inside vs Outside Temperature"
              + P_CLOSE_TAG + P_OPEN_TAG + "The graphs show the comparison of the inside "
              + "and the outside temperatures over a period of time"
              + " (Daily, Weekly, or Monthly). " + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Day</strong> - This graph contains the entries of inside and"
              + " outside temperatures over the last 24 hours." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Week</strong> - This graph contains the entries of inside and"
              + " outside temperatures over the last 7 days." + P_CLOSE_TAG + P_OPEN_TAG
              + "<strong>Month</strong> - This graph contains the entries of inside and"
              + " outside temperatures over the last 30 days." + P_CLOSE_TAG + P_OPEN_TAG2
              + "Hvac Log" + P_CLOSE_TAG + P_OPEN_TAG
              + "The Hvac log section provides a list of the historical system changes"
              + " for the Hvac system. A new entry is added each time a user"
              + " changes the desired temperature for the house." + P_CLOSE_TAG;
      break;

    default:
      // info =
      // "Information on this page is similar to the Overview page but specific"
      // + " to a particular section of the Home Management System.";
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
      title = "Dashboard";
      break;

    case 2:
      title = "Energy";
      break;

    case 3:
      title = "Aquaponics";
      break;

    case 4:
      title = "Lighting";
      break;

    case 5:
      title = "Hvac";
      break;

    // case 5:
    // title = "Security";
    // break;
    //
    // case 6:
    // title = "Reports";
    // break;
    //
    // case 7:
    // title = "Settings";
    // break;
    // case 8:
    // title = "Administrator";
    // break;

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
      dashboardButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 2:
      energyButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 3:
      aquaponicsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;

    case 4:
      lightingButton.add(new AttributeModifier(classContainer, true, new Model<String>(
          buttonContainer)));
      break;
    case 5:
      hvacButton
          .add(new AttributeModifier(classContainer, true, new Model<String>("green-button")));
      break;

    // case 5:
    // securityButton.add(new AttributeModifier(classContainer, true, new Model<String>(
    // buttonContainer)));
    // break;
    //
    // case 6:
    // reportsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
    // buttonContainer)));
    // break;
    //
    // case 7:
    // settingsButton.add(new AttributeModifier(classContainer, true, new Model<String>(
    // buttonContainer)));
    // break;
    // case 8:
    // adminButton.add(new AttributeModifier(classContainer, true, new Model<String>(
    // buttonContainer)));
    // break;
    default:
      break;
    } // End switch
  } // End makeButtonActive
} // End Help class

