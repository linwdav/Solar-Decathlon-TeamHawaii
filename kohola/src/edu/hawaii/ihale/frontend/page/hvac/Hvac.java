package edu.hawaii.ihale.frontend.page.hvac;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
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
import com.codecommit.wicket.AbstractChartData;
import com.codecommit.wicket.Chart;
import com.codecommit.wicket.ChartAxis;
import com.codecommit.wicket.ChartAxisType;
import com.codecommit.wicket.ChartDataEncoding;
import com.codecommit.wicket.ChartProvider;
import com.codecommit.wicket.ChartType;
import com.codecommit.wicket.IChartData;
import com.codecommit.wicket.LineStyle;
import com.codecommit.wicket.MarkerType;
import com.codecommit.wicket.ShapeMarker;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;
import edu.hawaii.ihale.frontend.SolarDecathlonApplication;
import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.Header;

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

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  // desired room temperature range
  private static final long TEMPERATURE_RANGE_START = 60L;
  private static final long TEMPERATURE_RANGE_END = 80L;

  // for validating user's input for setTemp
  // don't want them perform duplicate doCommand with the same temperature.
  private int desiredTemp = SolarDecathlonApplication.getHvac().getTemp();
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
        String original = value + "&deg;F";
        String closeTag = "</font>";
        if (value > TEMPERATURE_RANGE_START && value < TEMPERATURE_RANGE_END) {
          original = "<font color=\"green\">" + original + closeTag;
        }
        else if (value == TEMPERATURE_RANGE_START || value == TEMPERATURE_RANGE_END) {
          original = "<font color=\"#FF9900\">" + original + closeTag;
        }
        else {
          original = "<font color=\"red\">" + original + closeTag;
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
        return String.valueOf(currentWeather.getTempF() + "&deg;F");
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
        System.out.println("onUpdate setTemp: " + setTemp);
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
              + "&deg;F)</font>");
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
              + "&deg;F)</font>");
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

    // temporary images yet to be replaced.
    add(new Image("tempY", new ResourceReference(Header.class, "images/tempY.png")));
    add(new Image("tempM", new ResourceReference(Header.class, "images/tempM.png")));
    add(new Image("tempW", new ResourceReference(Header.class, "images/tempW.png")));
    // add(new Image("tempD", new ResourceReference(Header.class, "images/tempD.png")));
    addDayGraph();

  }

  /**
   * Add day graph to the page.
   */
  private void addDayGraph() {
    IChartData data = new AbstractChartData(ChartDataEncoding.TEXT, 100) {

      private static final long serialVersionUID = 1L;

      public double[][] getData() {

        Calendar current = Calendar.getInstance();
        long lastTwentyFour = 24 * 60 * 60 * 1000L;
        long time = (new Date()).getTime();
        long mHourBegin =
            current.get(Calendar.MINUTE) * 60000 + current.get(Calendar.SECOND) * 1000
                + current.get(Calendar.MILLISECOND);
        long twoHours = 2 * 60 * 60 * 1000L;
        List<TimestampIntegerPair> temperatureList =
            SolarDecathlonApplication.getRepository()
                .getHvacTemperatureSince(time - lastTwentyFour);

        double[] data = new double[13];
        int pointsFound = 0;
        long value = 0;        
        long totalValue = 0;

        for (int i = 12; i >= 0; i--) {
          for (int j = temperatureList.size() - 1; j >= 0; j--) {

            if ((temperatureList.get(j).getTimestamp() < 
                ((time - mHourBegin) - (twoHours * (i - 1))))
                && (temperatureList.get(j).getTimestamp() > 
                ((time - mHourBegin) - (twoHours * i)))) {
              // data[j]= temperatureList.get(j).getValue();
              value = temperatureList.get(j).getValue();
              totalValue += value;
              pointsFound++;
            }
          }
          if (pointsFound > 0) {
            data[12 - i] = totalValue / pointsFound;
          }
          else {
            data[12 - i] = 0;
          }
        }

        //
        // double[] test = new double[300];
        // int rand = 0;
        // for(int i = 0; i < test.length; i++) {
        // rand = (int) (Math.random() * 100);
        // test[i] = rand;
        // }
        //
        // return test;
        return new double[][] { {}, data };
      }
    };

    ChartProvider provider = new ChartProvider(new Dimension(650, 275), ChartType.LINE_XY, data);
    provider.setColors(new Color[] { Color.BLUE });
    provider.setTitle("Inside Temperature (°F / Hour)");
    ChartAxis axisX = new ChartAxis(ChartAxisType.BOTTOM);
    axisX.setLabels(generateXAxis(0));
    
    provider.addAxis(axisX);
    ChartAxis axisY = new ChartAxis(ChartAxisType.LEFT);
    axisY.setLabels(new String[] { "45", "50", "55", "60", "65", "70", "75", "80", "85", "90",
            "95" });
    provider.addAxis(axisY);

    provider.setLineStyles(new LineStyle[] { new LineStyle(2, 4, 1) });

    provider.addShapeMarker(new ShapeMarker(MarkerType.DIAMOND, Color.RED, 0, -1, 5));
    

    add(new Chart("tempD", provider));
  }

  /**
   * Generate and return the X axis.
   * 
   * @param type 0: day graph 1: week graph 2: month graph
   * @return A string array.
   */
  private String[] generateXAxis(int type) {
    String[] axisLabels = new String[13];
    Calendar calendar = Calendar.getInstance();

    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    if (type == 0) {
      for (int i = 0; i <= 11; i++) {
        if (((currentHour + i) * 2) % 12 == 0) {
          axisLabels[i] = "12";
        }
        else {
          axisLabels[i] = String.valueOf((currentHour + i * 2) % 12);
        }
      }
      if (currentHour == 12) {
        axisLabels[12] = "12";
      }
      else {
        axisLabels[12] = String.valueOf(currentHour % 12);
      }
    }
//    if (type == 1) {
//
//    }
//    else {
//
//    }
    return axisLabels;
  }

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
   * Get the button class attribute. For PropertyModel to access.
   * 
   * @return The button class attribute.
   */
  public String getButtonClass() {
    return this.buttonClass;
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
   * Get the button background color. For PropertyModel to access.
   * 
   * @return The button background color.
   */
  public String getButtonColor() {
    return this.buttonColor;
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