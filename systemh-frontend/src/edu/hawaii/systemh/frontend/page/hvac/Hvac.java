package edu.hawaii.systemh.frontend.page.hvac;

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
import org.apache.wicket.markup.html.basic.Label;
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
import edu.hawaii.systemh.frontend.SolarDecathlonApplication;
import edu.hawaii.systemh.frontend.SolarDecathlonSession;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;
import edu.hawaii.systemh.frontend.googlecharts.ChartGenerator;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.TimeInterval;
import edu.hawaii.systemh.frontend.page.Header;
import edu.hawaii.systemh.frontend.page.help.Help;

/**
 * The temperature(Hvac) page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Hvac extends Header {

  private boolean DEBUG = false;

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  // desired room temperature range
  private static final long LOW_ROOM_TEMP_BOTTOM = 18L;
  private static final long LOW_ROOM_TEMP_TOP = 18L;
  private static final long HIGH_ROOM_TEMP_BOTTOM = 30L;
  private static final long HIGH_ROOM_TEMP_TOP = 32L;

  // for validating user's input for setTemp
  // don't want them perform duplicate doCommand with the same temperature.
  private int desiredTemp = SolarDecathlonApplication.getHvac().getTempCommand();
  private int setTemp = SolarDecathlonApplication.getHvac().getTemp();

  // feedback to user after they setTemp, failed or successful
  private Label feedback;
  // textfield for setTemp
  private TextField<String> airTemp;

  // values (attributes) for the on off hvac button
  private String buttonLabel = "Activate HVAC";
  private String buttonClass = "green-button right";
  private String buttonColor = "background-color:green";

  // the on off message to the right of the button.
  private Label hvacState = new Label("hvacState", "<font color=\"red\">OFF</font>");
  // to keep track of the state of hvac button
  private boolean hvacOn = false;

  /**
   * The temperature(Hvac) page.
   * 
   * @throws Exception the Exception
   */
  public Hvac() throws Exception {

    ((SolarDecathlonSession) getSession()).getHeaderSession().setActiveTab(4);

    // Messages
    // Add messages as a list view to each page

    // Get all messages applicable to this page
    List<SystemStatusMessage> msgs =
        SolarDecathlonApplication.getMessages().getMessages(IHaleSystem.HVAC);

    // Create wrapper container for pageable list view
    WebMarkupContainer systemLog = new WebMarkupContainer("HVACSystemLogContainer");
    systemLog.setOutputMarkupId(true);

    // Help button link
    Link<String> helpLink = new Link<String>("helpLink") {
      private static final long serialVersionUID = 1L;

      public void onClick() {
        ((SolarDecathlonSession) getSession()).getHelpSession().setCurrentTab(5);
        setResponsePage(Help.class);
      }
    };

    // Help Image
    helpLink
        .add(new Image("helpHvac", new ResourceReference(Header.class, "images/icons/help.png")));

    add(helpLink);

    // Create Listview
    PageableListView<SystemStatusMessage> listView =
        new PageableListView<SystemStatusMessage>("HVACStatusMessages", msgs, 10) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<SystemStatusMessage> item) {

            SystemStatusMessage msg = item.getModelObject();

            // If only the empty message is in the list, then
            // display "No Messages"
            if (msg.getType() == null) {
              item.add(new Label("HVACMessageType", "-"));
              item.add(new Label("HVACTimestamp", "-"));
              item.add(new Label("HVACMessageContent", "No Messages"));
            }
            // Populate data
            else {
              item.add(new Label("HVACTimestamp", new Date(msg.getTimestamp()).toString()));
              item.add(new Label("HVACMessageType", msg.getType().toString()));
              item.add(new Label("HVACMessageContent", msg.getMessage()));
            }
          }
        };

    systemLog.add(listView);
    systemLog.add(new AjaxPagingNavigator("paginatorHVAC", listView));
    // Update log every 5 seconds.
    systemLog.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });
    systemLog.setVersioned(false);
    add(systemLog);

    // End messages section

    // model for inside temperature label
    Model<String> insideTempModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      /**
       * Override the getObject for dynamic programming and change the text color according to the
       * temperature value.
       */
      @Override
      public String getObject() {
        long value = SolarDecathlonApplication.getHvac().getTemp();
        String original = value + "&deg;C";
        String closeTag = "</font>";
        if ((value <= LOW_ROOM_TEMP_TOP && value >= LOW_ROOM_TEMP_BOTTOM)
            || (value <= HIGH_ROOM_TEMP_TOP && value >= HIGH_ROOM_TEMP_BOTTOM)) {
          original = "<font color=\"#FF9900\">" + original + closeTag;
        }
        else if (value < LOW_ROOM_TEMP_BOTTOM || value > HIGH_ROOM_TEMP_TOP) {
          original = "<font color=\"red\">" + original + closeTag;
        }
        else {
          original = "<font color=\"green\">" + original + closeTag;
        }
        return original;
      }
    };
    Label insideTemperature = new Label("InsideTemperature", insideTempModel);
    insideTemperature.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)) {
      private static final long serialVersionUID = 1L;
    });

    // model for outside temperature label
    Model<String> outsideModel = new Model<String>() {
      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(currentWeather.getTempC() + "&deg;C");
      }
    };
    Label outsideTemperature = new Label("OutsideTemperature", outsideModel);

    // clear feedback each time the page is refreshed.
    feedback = new Label("Feedback", "&nbsp;");

    // the on off button for hvac
    Link<String> onOffButton = new Link<String>("button") {
      private static final long serialVersionUID = 1L;

      @Override
      /**
       * Turn the Hvac on or off, change button color and label.
       */
      public void onClick() {

        // change hvac state and button attributes.
        hvacOn = !hvacOn;
        if (hvacOn) {
          setButtonLabel("Deactivate HVAC");
          setButtonClass("red-button right");
          setButtonColor("background-color:red");
          hvacState.setDefaultModelObject("<font color=\"green\">ON</font>");

        }
        else {
          setButtonLabel("Activate HVAC");
          setButtonClass("green-button right");
          setButtonColor("background-color:green");
          hvacState.setDefaultModelObject("<font color=\"red\">OFF</font>");
        }
      }

    };
    // add the button value. e.g. Activate HVAC / Deactivate HVAC
    onOffButton.add(new Label("buttonLabel", new PropertyModel<String>(this, "buttonLabel"))
        .setEscapeModelStrings(false));

    // add some coponent tags to the button.
    onOffButton.add(new AbstractBehavior() {

      // support serialization
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put("class", buttonClass);
        tag.put("style", buttonColor);
      }
    });

    // add button to the page.
    add(onOffButton);

    hvacState.setEscapeModelStrings(false);
    // add hvac state label to the page.
    add(hvacState);

    // set label for inside temp
    // insideTemperature = new Label("InsideTemperature", insideTempLabel);
    insideTemperature.setEscapeModelStrings(false);
    // set label for outside temp
    outsideTemperature.setEscapeModelStrings(false);
    // add labels to the page
    add(insideTemperature);
    add(outsideTemperature);

    Form<String> form = new Form<String>("form");

    // Add the control for the air temp slider
    airTemp = new TextField<String>("airTemperature", new Model<String>(String.valueOf(setTemp)));
    airTemp.setEscapeModelStrings(false);

    // Added for jquery control.
    airTemp.setMarkupId(airTemp.getId());
    airTemp.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        setTemp = Integer.valueOf(airTemp.getValue());
        if (DEBUG) {
          System.out.println("onUpdate setTemp: " + setTemp);
        }
      }
    });

    // airTemp.setOutputMarkupId(true);
    form.add(airTemp);
    form.add(new AjaxButton("SubmitTemp") {

      // support serializable
      private static final long serialVersionUID = 1L;

      /** Provide user feeback after they set a new desired temperature */
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        if (setTemp == desiredTemp) {
          feedback.setDefaultModelObject("<font color=\"#FF9900\">Same: " + "(" + desiredTemp
              + "&deg;C)</font>");
          // target.addComponent(textField);
          target.addComponent(feedback);
          return;
        }
        else {
          desiredTemp = setTemp;

          IHaleSystem system = IHaleSystem.HVAC;
          IHaleCommandType command = IHaleCommandType.SET_TEMPERATURE;
          Integer newTemperature = setTemp;
          SolarDecathlonApplication.getBackend().doCommand(system, null, command, newTemperature);

          feedback.setDefaultModelObject("<font color=\"green\">" + "Success: (" + desiredTemp
              + "&deg;C)</font>");
        }
        // target.addComponent(textField);
        target.addComponent(feedback);
      }
    });

    form.setOutputMarkupId(true);
    feedback.setEscapeModelStrings(false);
    feedback.setOutputMarkupId(true);
    form.add(feedback);
    add(form);

    // MarkupContainers for graphs.
    WebMarkupContainer dayGraph = new WebMarkupContainer("dayGraphImage");
    WebMarkupContainer weekGraph = new WebMarkupContainer("weekGraphImage");
    WebMarkupContainer monthGraph = new WebMarkupContainer("monthGraphImage");

    // Setup charts
    ChartGenerator cgDay = new ChartGenerator(TimeInterval.DAY,
        ChartDataType.HVAC_TEMP, dayGraph, 500, 250 );
    ChartGenerator cgWeek = new ChartGenerator(TimeInterval.WEEK,
        ChartDataType.HVAC_TEMP, weekGraph, 500, 250 );
    ChartGenerator cgMonth = new ChartGenerator(TimeInterval.MONTH,
        ChartDataType.HVAC_TEMP, monthGraph, 500, 250 );
    
    // Generate charts
    cgDay.addChart();
    cgWeek.addChart();
    cgMonth.addChart();
    
    // Add Charts to view
    add(dayGraph);
    add(weekGraph);
    add(monthGraph);
  } // End constructor

  /**
   * Set the button label.
   * 
   * @param label The label.
   */
  public void setButtonLabel(String label) {
    this.buttonLabel = label;
  }

  /**
   * Get the button label. For PropertyModel to access.
   * 
   * @return The label.
   */
  public String getButtonLabel() {
    return this.buttonLabel;
  }

  /**
   * Set the button class attribute.
   * 
   * @param newClass The new class attribute.
   */
  public void setButtonClass(String newClass) {
    this.buttonClass = newClass;
  }

  /**
   * Set the button background color.
   * 
   * @param newColor The new color.
   */
  public void setButtonColor(String newColor) {
    this.buttonColor = newColor;
  }

}