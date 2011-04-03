package edu.hawaii.ihale.frontend.page.aquaponics;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.Header;
import edu.hawaii.ihale.frontend.page.help.Help;

//import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * The aquaponics page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaPonics extends Header {

  private static final String boxTagName = "box";
  private static final String classTagName = "class";
  private static final String styleTagName = "style";

  // labels to store div colors
  private Label tempColorLabel;
  private Label phColorLabel;
  private Label oxygenColorLabel;
  private Label ecColorLabel;
  private Label levelColorLabel;
  private Label circulationColorLabel;
  private Label turbidityColorLabel;

  // Text field for the controls
  private TextField<String> waterTemp;
  private TextField<String> waterPh;
  private TextField<String> waterLevel;
  private TextField<String> waterNutrients;

  private static String dash = "-";

  // Initialize variable for doCommands
  private int setTemp = 0;
  private double setPh = 0;
  private int setLevel = 0;
  private double setNutrients = 0;

  // the warning and alert messages
  private static final String ALERT_MESSAGE = "<font color=\"red\">(ALERT)</font>";
  private static final String WARNG_MESSAGE = "<font color=\"#FF9900\">(WARNING)</font>";
  private static final String NORM_MESSAGE = "<font>&nbsp;</font>";

  private static final String ALERT_BACKGROUND = "background-color:red;";
  private static final String WARNG_BACKGROUND = "background-color:#FF9900;";
  private static final String NORM_BACKGROUND = "background-color:green;";

  // Range values for each system
  private static final long TEMPERATURE_RANGE_START = 82;
  private static final long TEMPERATURE_RANGE_END = 86;
  private static final long TEMPERATURE_ALERT_RANGE_START = 68;
  private static final long TEMPERATURE_ALERT_RANGE_END = 113;
  private static final double PH_RANGE_START = 6.8;
  private static final double PH_RANGE_END = 8.0;
  private static final double PH_ACCEPTED_DIFFERENCE = 1.0;
  private static final double OXYGEN_RANGE_START = 4.50;
  private static final double OXYGEN_RANGE_END = 5.50;
  private static final double OXYGEN_ACCEPTED_DIFFERENCE = 0.2;
  private static final double EC_RANGE_START = 10.0;
  private static final double EC_RANGE_END = 20.0;
  private static final double EC_ACCEPTED_DIFFERENCE = 2.0;
  private static final int LEVEL_RANGE_START = 36;
  private static final int LEVEL_RANGE_END = 48;
  private static final int LEVEL_ACCEPTED_DIFFERENCE = 2;
  private static final int CIRCULATION_RANGE_START = 60;
  private static final int CIRCULATION_RANGE_END = 100;
  private static final int CIRCULATION_ACCEPTED_DIFFERENCE = 10;
  private static final int TURBIDITY_RANGE_START = 0;
  private static final int TURBIDITY_RANGE_END = 100;
  private static final int TURBIDITY_ACCEPTED_DIFFERENCE = 5;
  //private static final int FISH_TOTAL = 20;

  // labels for recommended values
  private static final Label recommendedTempLabel = new Label("RecommendedTempLabel",
      TEMPERATURE_RANGE_START + "&deg;F - " + TEMPERATURE_RANGE_END + "&deg;F");

  private static final Label recommendedPHLabel = new Label("RecommendedPHLabel", PH_RANGE_START
      + dash + PH_RANGE_END);

  private static final Label recommendedOxygenLabel = new Label("RecommendedOxygenLabel",
      OXYGEN_RANGE_START + dash + OXYGEN_RANGE_END + " mg/l");

  private static final Label recommendedECLabel = new Label("RecommendedECLabel", EC_RANGE_START
      + dash + EC_RANGE_END + " &micro;s/cm");

  private static final Label recommendedLevelLabel = new Label("RecommendedLevelLabel",
      LEVEL_RANGE_START + dash + LEVEL_RANGE_END + " inches");

  private static final Label recommendedCirculationLabel = new Label("RecommendedCirculationLabel",
      CIRCULATION_RANGE_START + dash + CIRCULATION_RANGE_END + " gpm");

  private static final Label recommendedTurbidityLabel = new Label("RecommendedTurbidityLabel",
      TURBIDITY_RANGE_START + dash + TURBIDITY_RANGE_END + " NTUs");

  // Drop down choices for amount of feed
  private static final List<String> amount = Arrays.asList(new String[] { "0.5", "1.0", "2.0",
      "5.0", "10.0" });

  // Drop down choices for number of fish to harvest
  private static final List<String> num = Arrays.asList(new String[] { "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "10" });
  
  // Default selected value for the dropdowns
  private String selectedFeedAmnt = "0.5";
  private String selectedFishNum = "1";

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer graph = new WebMarkupContainer("graphImage");

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   * 
   * @throws Exception the exception.
   */
  public AquaPonics() throws Exception {

    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(2);

    final String onChange = "onchange";

    /*****************
     ** System Logs **
     *****************/
    // Add messages as a list view to each page

    // Get all messages applicable to this page
    List<SystemStatusMessage> msgs =
        SolarDecathlonApplication.getMessages().getMessages(IHaleSystem.AQUAPONICS);

    // Create wrapper container for pageable list view
    WebMarkupContainer systemLog = new WebMarkupContainer("AquaponicsLogContainer");
    systemLog.setOutputMarkupId(true);

    // Help button link
    Link<String> helpLink = new Link<String>("helpLink") {
      private static final long serialVersionUID = 1L;

      public void onClick() {
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(3);
        setResponsePage(Help.class);
      }
    };

    // Help Image
    helpLink
        .add(new Image("helpAqua", new ResourceReference(Header.class, "images/icons/help.png")));

    add(helpLink);

    // Create Listview
    PageableListView<SystemStatusMessage> listView =
        new PageableListView<SystemStatusMessage>("AquaponicsStatusMessages", msgs, 10) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<SystemStatusMessage> item) {

            SystemStatusMessage msg = item.getModelObject();

            // If only the empty message is in the list, then
            // display "No Messages"
            if (msg.getType() == null) {
              item.add(new Label("AquaponicsMessageType", "-"));
              item.add(new Label("AquaponicsTimestamp", "-"));
              item.add(new Label("AquaponicsMessageContent", "No Messages"));
            }
            // Populate data
            else {
              item.add(new Label("AquaponicsTimestamp", new Date(msg.getTimestamp()).toString()));
              item.add(new Label("AquaponicsMessageType", msg.getType().toString()));
              item.add(new Label("AquaponicsMessageContent", msg.getMessage()));
            }
          }
        };

    systemLog.add(listView);
    systemLog.add(new AjaxPagingNavigator("paginatorAquaponics", listView));
    systemLog.setVersioned(false);
    // Update log every 5 seconds.
    systemLog.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });
    add(systemLog);

    // End messages section
    
    /******************************************
     ** Tab button for Aquaponics Statistics **
     ******************************************/
    Link<String> statsButton = new Link<String>("statsButton") {
        private static final long serialVersionUID = 1L;

        /** Upon clicking this link, go to AquaponicsStatsPage. */
        @Override
        public void onClick() {
          try {
            setResponsePage(AquaponicsStats.class);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      };

    /********************************************************
     ** Feedback Message for all System (Warning or Alert) **
     ********************************************************/
    /** model for the TEMPERATURE feedback (WARNG or ALERT) label on page. */
    Model<String> tempStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        long value = SolarDecathlonApplication.getAquaponics().getTemp();
        String status;
        if ((value < TEMPERATURE_RANGE_START && value > TEMPERATURE_ALERT_RANGE_START)
            || (value > TEMPERATURE_RANGE_END && value < TEMPERATURE_ALERT_RANGE_END)) {
          status = WARNG_MESSAGE;
        }
        else if (value <= TEMPERATURE_ALERT_RANGE_START || value >= TEMPERATURE_ALERT_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };
    // put the temp model in temp label
    Label tempStatusLabel = new Label("tempStatus", tempStatusModel);

    // the model for the actual temp value
    Model<String> temp = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getTemp());
      }
    };

    /** model for the PH feedback (WARNG or ALERT) label on page. */
    Model<String> pHStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getPH();
        String status;
        if ((value <= (PH_RANGE_START) &&
            value >= Math.abs(PH_RANGE_START - PH_ACCEPTED_DIFFERENCE)) 
            || (value >= (PH_RANGE_END) &&
            value <= Math.abs(PH_RANGE_END + PH_ACCEPTED_DIFFERENCE)) ) {
          status = WARNG_MESSAGE;
        }
        else if (value < Math.abs(PH_RANGE_START - PH_ACCEPTED_DIFFERENCE)
            || value > Math.abs(PH_RANGE_END + PH_ACCEPTED_DIFFERENCE)) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put the ph model in ph label
    Label phStatusLabel = new Label("PhStatus", pHStatusModel);

    // model for the actual ph value
    Model<String> ph = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getPH());
      }
    };

    /** model for the OXYGEN feedback (WARNG or ALERT) label on page.*/
    Model<String> oxygenStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getOxygen();
        String status;
        if (Math.abs(value - OXYGEN_RANGE_START) < OXYGEN_ACCEPTED_DIFFERENCE
            || Math.abs(value - OXYGEN_RANGE_END) < OXYGEN_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < OXYGEN_RANGE_START || value > OXYGEN_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put oxygen model in oxygen label
    Label oxygenStatusLabel = new Label("OxygenStatus", oxygenStatusModel);

    // the model for the actual oxygen value
    Model<String> oxygen = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getOxygen());
      }
    };

    /** model for the EC feedback (WARNG or ALERT) label on page. */
    Model<String> eCStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getConductivity();
        String status;
        if ((value <= (EC_RANGE_START) &&
                value >= Math.abs(EC_RANGE_START - EC_ACCEPTED_DIFFERENCE)) 
                || (value >= (EC_RANGE_END) &&
                value <= Math.abs(EC_RANGE_END + EC_ACCEPTED_DIFFERENCE)) ) {
              status = WARNG_MESSAGE;
            }
            else if (value < Math.abs(EC_RANGE_START - EC_ACCEPTED_DIFFERENCE)
                || value > Math.abs(EC_RANGE_END + EC_ACCEPTED_DIFFERENCE)) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put the ec model in ec label
    Label ecStatusLabel = new Label("EcStatus", eCStatusModel);

    // model for the actual ec value
    Model<String> ec = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getConductivity());
      }
    };

    /* model for the WATER LEVEL feedback (WARNG or ALERT) label on page.*/
    Model<String> levelStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        int value = SolarDecathlonApplication.getAquaponics().getWaterLevel();
        String status;
        if (Math.abs(value - LEVEL_RANGE_START) < LEVEL_ACCEPTED_DIFFERENCE
            || Math.abs(value - LEVEL_RANGE_END) < LEVEL_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < LEVEL_RANGE_START || value > LEVEL_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put oxygen model in oxygen label
    Label levelStatusLabel = new Label("LevelStatus", levelStatusModel);

    // the model for the actual oxygen value
    Model<String> level = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getWaterLevel());
      }
    };

    /** model for the CIRCULATION feedback (WARNG or ALERT) label on page. */
    Model<String> circulationStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getCirculation();
        String status;

        if (Math.abs(value - CIRCULATION_RANGE_START) < CIRCULATION_ACCEPTED_DIFFERENCE
            || Math.abs(value - CIRCULATION_RANGE_END) < CIRCULATION_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < CIRCULATION_RANGE_START || value > CIRCULATION_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put circulation model in circulation label
    Label circulationStatusLabel = new Label("CirculationStatus", circulationStatusModel);

    // the model for the actual circulation value
    Model<String> circulation = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getCirculation());
      }
    };

    /** model for the TURBIDITY feedback (WARNG or ALERT) label on page. */
    Model<String> turbidityStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getTurbidity();
        String status;

        if (Math.abs(value - TURBIDITY_RANGE_START) < TURBIDITY_ACCEPTED_DIFFERENCE
            || Math.abs(value - TURBIDITY_RANGE_END) < TURBIDITY_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < TURBIDITY_RANGE_START || value > TURBIDITY_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put turbidity model in turbidity label
    Label turbidityStatusLabel = new Label("TurbidityStatus", turbidityStatusModel);

    // the model for the actual turbidity value
    Model<String> turbidity = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getTurbidity());
      }
    };

    /** model for the DEAD FISH feedback (WARNG or ALERT) label on page. */
    Model<String> fishStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        int value = SolarDecathlonApplication.getAquaponics().getDeadFish();
        String status;

        if (value > 0) {
          status = ALERT_MESSAGE;
        }
        else {
          status = NORM_MESSAGE;
        }
        return status;
      }
    };

    // put dead fish model in deadfish label
    Label fishStatusLabel = new Label("FishStatus", fishStatusModel);

    // the model for the actual oxygen value
    Model<String> deadFish = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getDeadFish());
      }
    };

    // make tags to be recognizable in html
    tempStatusLabel.setEscapeModelStrings(false);
    phStatusLabel.setEscapeModelStrings(false);
    oxygenStatusLabel.setEscapeModelStrings(false);
    ecStatusLabel.setEscapeModelStrings(false);
    levelStatusLabel.setEscapeModelStrings(false);
    circulationStatusLabel.setEscapeModelStrings(false);
    turbidityStatusLabel.setEscapeModelStrings(false);
    fishStatusLabel.setEscapeModelStrings(false);
    
    /*************************************************************
     ** Determine the Background Color (Green, Yellow, and Red) **
     *************************************************************/
    // color for current temp div
    Model<String> tempColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        long value = SolarDecathlonApplication.getAquaponics().getTemp();
        String color;
        if ((value < TEMPERATURE_RANGE_START && value > TEMPERATURE_ALERT_RANGE_START)
            || (value > TEMPERATURE_RANGE_END && value < TEMPERATURE_ALERT_RANGE_END)) {
          color = WARNG_BACKGROUND;
        }
        else if (value <= TEMPERATURE_ALERT_RANGE_START || value >= TEMPERATURE_ALERT_RANGE_END) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    tempColorLabel = new Label("tempColor", tempColorModel);

    // color for current ph div
    Model<String> phColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getPH();
        String color;
        if ((value <= (PH_RANGE_START) &&
                value >= Math.abs(PH_RANGE_START - PH_ACCEPTED_DIFFERENCE)) 
                || (value >= (PH_RANGE_END) &&
                value <= Math.abs(PH_RANGE_END + PH_ACCEPTED_DIFFERENCE))) {
          color = WARNG_BACKGROUND;
        }
        else if (value < Math.abs(PH_RANGE_START - PH_ACCEPTED_DIFFERENCE)
                || value > Math.abs(PH_RANGE_END + PH_ACCEPTED_DIFFERENCE)) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    phColorLabel = new Label("phColor", phColorModel);

    // color for current oxygen div
    Model<String> oxygenColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getOxygen();
        String color;
        if (Math.abs(value - OXYGEN_RANGE_START) < OXYGEN_ACCEPTED_DIFFERENCE
            || Math.abs(value - OXYGEN_RANGE_END) < OXYGEN_ACCEPTED_DIFFERENCE) {
          color = WARNG_BACKGROUND;
        }
        else if (value < OXYGEN_RANGE_START || value > OXYGEN_RANGE_END) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    oxygenColorLabel = new Label("OxygenColor", oxygenColorModel);

    // color for current ec div
    Model<String> ecColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getConductivity();
        String color;
        if ((value <= (EC_RANGE_START) &&
                value >= Math.abs(EC_RANGE_START - EC_ACCEPTED_DIFFERENCE)) 
                || (value >= (EC_RANGE_END) &&
                value <= Math.abs(EC_RANGE_END + EC_ACCEPTED_DIFFERENCE))) {
          color = WARNG_BACKGROUND;
        }
        else if (value < Math.abs(EC_RANGE_START - EC_ACCEPTED_DIFFERENCE)
                || value > Math.abs(EC_RANGE_END + EC_ACCEPTED_DIFFERENCE)) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    ecColorLabel = new Label("ecColor", ecColorModel);

    // color for current water level div
    Model<String> levelColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        int value = SolarDecathlonApplication.getAquaponics().getWaterLevel();
        String color;
        if (Math.abs(value - LEVEL_RANGE_START) < LEVEL_ACCEPTED_DIFFERENCE
            || Math.abs(value - LEVEL_RANGE_END) < LEVEL_ACCEPTED_DIFFERENCE) {
          color = WARNG_BACKGROUND;
        }
        else if (value < LEVEL_RANGE_START || value > LEVEL_RANGE_END) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    levelColorLabel = new Label("levelColor", levelColorModel);

    // color for current circulation div
    Model<String> circulationColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getCirculation();
        String color;
        if (Math.abs(value - CIRCULATION_RANGE_START) < CIRCULATION_ACCEPTED_DIFFERENCE
            || Math.abs(value - CIRCULATION_RANGE_END) < CIRCULATION_ACCEPTED_DIFFERENCE) {
          color = WARNG_BACKGROUND;
        }
        else if (value < CIRCULATION_RANGE_START || value > CIRCULATION_RANGE_END) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    circulationColorLabel = new Label("circulationColor", circulationColorModel);

    // color for current turbidity div
    Model<String> turbidityColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getTurbidity();
        String color;
        if (Math.abs(value - TURBIDITY_RANGE_START) < TURBIDITY_ACCEPTED_DIFFERENCE
            || Math.abs(value - TURBIDITY_RANGE_END) < TURBIDITY_ACCEPTED_DIFFERENCE) {
          color = WARNG_BACKGROUND;
        }
        else if (value < TURBIDITY_RANGE_START || value > TURBIDITY_RANGE_END) {
          color = ALERT_BACKGROUND;
        }
        else {
          color = NORM_BACKGROUND;
        }
        return color;
      }
    };
    turbidityColorLabel = new Label("turbidityColor", turbidityColorModel);

    // Add button to switch to statistics view
    add(statsButton);

    // create divs for temperature according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup tempInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("TempInnerDiv");

    WebMarkupContainerWithAssociatedMarkup tempOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("TempOuterDiv");

    tempInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    tempOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, tempColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedTempLabel.setEscapeModelStrings(false);

    tempInnerDiv.add(new Label("Temp", temp));
    tempInnerDiv.add(tempStatusLabel);
    tempOuterDiv.add(tempInnerDiv);
    tempOuterDiv.add(recommendedTempLabel);

    // Auto refresh the div
    tempOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(tempOuterDiv);

    // create divs for ph according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup phInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhInnerDiv");

    WebMarkupContainerWithAssociatedMarkup phOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhOuterDiv");

    phInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    phOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, phColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedPHLabel.setEscapeModelStrings(false);

    phInnerDiv.add(new Label("PH", ph));
    phInnerDiv.add(phStatusLabel);
    phOuterDiv.add(phInnerDiv);
    phOuterDiv.add(recommendedPHLabel);

    // Auto refresh the div
    phOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(phOuterDiv);

    // create divs for oxygen according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup oxygenInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("OxygenInnerDiv");

    WebMarkupContainerWithAssociatedMarkup oxygenOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("OxygenOuterDiv");

    oxygenInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    oxygenOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, oxygenColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedOxygenLabel.setEscapeModelStrings(false);

    oxygenInnerDiv.add(new Label("Oxygen", oxygen));
    oxygenInnerDiv.add(oxygenStatusLabel);
    oxygenOuterDiv.add(oxygenInnerDiv);
    oxygenOuterDiv.add(recommendedOxygenLabel);

    // Auto refresh the div
    oxygenOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(oxygenOuterDiv);

    // create divs for ec according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup ecInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("EcInnerDiv");

    WebMarkupContainerWithAssociatedMarkup ecOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("EcOuterDiv");

    ecInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    ecOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, ecColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedECLabel.setEscapeModelStrings(false);

    ecInnerDiv.add(new Label("EC", ec));
    ecInnerDiv.add(ecStatusLabel);
    ecOuterDiv.add(ecInnerDiv);
    ecOuterDiv.add(recommendedECLabel);

    // Auto refresh the div
    ecOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(ecOuterDiv);

    // create divs for water level according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup levelInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("LevelInnerDiv");

    WebMarkupContainerWithAssociatedMarkup levelOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("LevelOuterDiv");

    levelInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    levelOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, levelColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedLevelLabel.setEscapeModelStrings(false);

    levelInnerDiv.add(new Label("Level", level));
    levelInnerDiv.add(levelStatusLabel);
    levelOuterDiv.add(levelInnerDiv);
    levelOuterDiv.add(recommendedLevelLabel);

    // Auto refresh the div
    levelOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(levelOuterDiv);

    // create divs for circulation according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup circulationInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("CirculationInnerDiv");

    WebMarkupContainerWithAssociatedMarkup circulationOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("CirculationOuterDiv");

    circulationInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    circulationOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, circulationColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedCirculationLabel.setEscapeModelStrings(false);

    circulationInnerDiv.add(new Label("Circulation", circulation));
    circulationInnerDiv.add(circulationStatusLabel);
    circulationOuterDiv.add(circulationInnerDiv);
    circulationOuterDiv.add(recommendedCirculationLabel);

    // Auto refresh the div
    circulationOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(circulationOuterDiv);

    // create divs for turbidity according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup turbidityInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("TurbidityInnerDiv");

    WebMarkupContainerWithAssociatedMarkup turbidityOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("TurbidityOuterDiv");

    turbidityInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    turbidityOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, turbidityColorLabel.getDefaultModelObjectAsString());
      }
    });

    recommendedTurbidityLabel.setEscapeModelStrings(false);

    turbidityInnerDiv.add(new Label("Turbidity", turbidity));
    turbidityInnerDiv.add(turbidityStatusLabel);
    turbidityOuterDiv.add(turbidityInnerDiv);
    turbidityOuterDiv.add(recommendedTurbidityLabel);

    // Auto refresh the div
    turbidityOuterDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(turbidityOuterDiv);

    /*********************
     ** Slider Controls **
     *********************/
    // Create a new form for the Controls
    Form<String> tempform = new Form<String>("form");

    /** Change Temperature */
    setTemp = SolarDecathlonApplication.getAquaponics().getTemp();

    // Add the control for the water temp slider
    waterTemp = new TextField<String>("waterTemperature", new Model<String>(setTemp + ""));

    // Added for jquery control.
    waterTemp.setMarkupId(waterTemp.getId());
    waterTemp.add(new AjaxFormComponentUpdatingBehavior(onChange) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        // setTemp = Integer.valueOf(waterTemp.getValue().substring(0,
        // waterTemp.getValue().length() - 6));
        setTemp = Integer.valueOf(waterTemp.getValue());
        //System.out.println("onUpdate setTemp: " + setTemp);

        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.SET_TEMPERATURE;
        Integer newTemperature = setTemp;
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newTemperature);
      }
    });

    waterTemp.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(10)) {
      private static final long serialVersionUID = 1L;
    });

    waterTemp.setOutputMarkupId(true);
    waterTemp.setEscapeModelStrings(false);
    tempform.add(waterTemp);
    /** END **/

    /** Change Ph **/
    setPh = SolarDecathlonApplication.getAquaponics().getPH();

    // Add the control for the water temp slider
    waterPh = new TextField<String>("waterPH", new Model<String>(setPh + ""));

    // Added for jquery control.
    waterPh.setMarkupId(waterPh.getId());
    waterPh.add(new AjaxFormComponentUpdatingBehavior(onChange) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {

        setPh = Double.parseDouble(waterPh.getValue());
        //System.out.println("onUpdate setPh: " + setPh);

        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.SET_PH;
        double newPh = setPh;
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newPh);
      }
    });

    waterPh.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    waterPh.setOutputMarkupId(true);
    waterPh.setEscapeModelStrings(false);
    tempform.add(waterPh);
    /** END **/

    /** Change Water Level **/

    setLevel = SolarDecathlonApplication.getAquaponics().getWaterLevel();

    // Add the control for the water temp slider
    waterLevel = new TextField<String>("waterLEVEL", new Model<String>(setLevel + ""));

    // Added for jquery control.
    waterLevel.setMarkupId(waterLevel.getId());
    waterLevel.add(new AjaxFormComponentUpdatingBehavior(onChange) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        setLevel = Integer.valueOf(waterLevel.getValue());
        //System.out.println("onUpdate setLevel: " + setLevel);

        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.SET_WATER_LEVEL;
        Integer newWaterLevel = setLevel;
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newWaterLevel);
      }
    });

    waterTemp.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(10)) {
      private static final long serialVersionUID = 1L;
    });

    waterLevel.setOutputMarkupId(true);
    waterLevel.setEscapeModelStrings(false);
    tempform.add(waterLevel);

    /** END **/

    /** Change Water Nutrients **/
    setNutrients = SolarDecathlonApplication.getAquaponics().getNutrients();

    // Add the control for the water temp slider
    waterNutrients = new TextField<String>("waterNUTRIENTS", new Model<String>(setNutrients + ""));

    // Added for jquery control.
    waterNutrients.setMarkupId(waterNutrients.getId());
    waterNutrients.add(new AjaxFormComponentUpdatingBehavior(onChange) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {

        setNutrients = Double.parseDouble(waterNutrients.getValue());
        //System.out.println("onUpdate setNutrients: " + setNutrients);

        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.SET_NUTRIENTS;
        double newNutrients = setNutrients;
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newNutrients);
      }
    });

    waterNutrients.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(10)) {
      private static final long serialVersionUID = 1L;
    });

    waterNutrients.setOutputMarkupId(true);
    waterNutrients.setEscapeModelStrings(false);
    tempform.add(waterNutrients);
    /** END **/

    // Add form into the page
    add(tempform);
    tempform.setOutputMarkupId(true);

    /***************************************
     ** Display and Controls for the Fish **
     ***************************************/
    // create divs for dead fish according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup fishDiv =
        new WebMarkupContainerWithAssociatedMarkup("FishDiv");

    fishDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

    });

    fishDiv.add(new Label("Fish", deadFish));
    fishDiv.add(fishStatusLabel);

    // Auto refresh the div
    fishDiv.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    add(fishDiv);

    // Form for both the Feeding and Harvesting
    Form<String> feedForm = new Form<String>("FishForm");

    /** Drop down list for Feeding */
    DropDownChoice<String> feedAmount =
        new DropDownChoice<String>("FeedAmount",
            new PropertyModel<String>(this, "selectedFeedAmnt"), amount);

    feedForm.add(feedAmount);

    feedForm.add(new AjaxButton("FeedFish") {

      // support serializable
      private static final long serialVersionUID = 1L;

      /** Provide user feeback after they set a new desired temperature */
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> feedForm) {

        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.FEED_FISH;
        double newFeedAmount = Double.parseDouble(selectedFeedAmnt);
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newFeedAmount);

        //System.out.println("Feeding " + selectedFeedAmnt + " oz of feed.");
      }

    });

    /** Drop down list for Harvesting */
    DropDownChoice<String> fishNum =
        new DropDownChoice<String>("FishNum", new PropertyModel<String>(this, "selectedFishNum"),
            num);

    feedForm.add(fishNum);

    feedForm.add(new AjaxButton("HarvestFish") {

      // support serializable
      private static final long serialVersionUID = 1L;

      /** Provide user feeback after they set a new desired temperature */
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> feedForm) {

        //System.out.println("Fishnum " + selectedFishNum + " fish.");
        IHaleSystem system = IHaleSystem.AQUAPONICS;
        IHaleCommandType command = IHaleCommandType.HARVEST_FISH;
        int newFishNum = Integer.valueOf(selectedFishNum);
        SolarDecathlonApplication.getBackend().doCommand(system, null, command, newFishNum);

        //System.out.println("Harvesting " + selectedFishNum + " fish.");
      }

    });

    // Add form to page
    add(feedForm);
    feedForm.setOutputMarkupId(true);

  }

  /**
   * Returns the default value for the feed amount.
   * @return selectedFeedAmnt
   */
  public String getSelectedFeedAmnt() {
    return selectedFeedAmnt;
  }

  /**
   * Returns the default value for the fish number.
   * @return selectedFishNum
   */
  public String getSelectedFishNum() {
    return selectedFishNum;
  }

}