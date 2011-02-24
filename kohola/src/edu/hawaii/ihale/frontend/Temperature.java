package edu.hawaii.ihale.frontend;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
//import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
//import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * The temperature(Hvac) page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Temperature extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  // temperature labels
  static Label insideTemperature = new Label("InsideTemperature", "0");
  static Label outsideTemperature = new Label("OutsideTemperature", "0");
  static Label waterTemperature = new Label("WaterTemperature", "0");

  // values (attributes) for the on off hvac button
  private String buttonLabel = "Activate HVAC";
  private String buttonClass = "green-button right";
  private String buttonColor = "background-color:green";

  // the on off message to the right of the button.
  private Label hvacState = new Label("hvacState", "<font color=\"red\">OFF</font>");

  // to keep track of the state of hvac.
  private boolean hvacOn = false;

  /**
   * The temperature(Hvac) page.
   * @throws Exception the Exception
   */
  public Temperature() throws Exception {

    // SystemStateEntryDB db = ((SolarDecathlonApplication)
    // SolarDecathlonApplication.get()).getDB();

    // This is totally bogus!!
    // Right now there's no water temp in the dictionary so we just use a random number
    Long rand = (long) (Math.random() * 100);
    setWaterTemp(rand);

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

    // copy over the temperature values from the header to this page since they should be the same.
    String insideTempStr = (String) insideTemperatureHeader.getDefaultModelObject();
    insideTemperature.setDefaultModelObject(insideTempStr);
    String outsideTempStr = (String) outsideTemperatureHeader.getDefaultModelObject();
    outsideTemperature.setDefaultModelObject(outsideTempStr);

    // set the labels for temperatures
    long insideTemp = Long.parseLong(insideTempStr);
    insideTemperature.setDefaultModelObject(insideTemp + "&deg;F");
    insideTemperature.setEscapeModelStrings(false);
    long outsideTemp = Long.parseLong(outsideTempStr);
    outsideTemperature.setDefaultModelObject(outsideTemp + "&deg;F");
    outsideTemperature.setEscapeModelStrings(false);
    waterTemperature.setEscapeModelStrings(false);

    // change the label color to green, yellow, or red according to the temperature value.
    determineInsideTempTextColor(insideTemp);

    add(insideTemperature);
    add(outsideTemperature);
    // add(waterTemperature);

    Form<String> form = new Form<String>("form");
    final TextField<String> textField = new TextField<String>("SetTemp");
    form.add(textField);
    form.add(new Link<String>("SubmitTemp") {

      // support serializable
      private static final long serialVersionUID = 1L;

      /** Display the page again, now with the updated values of field1 and field2. */
      @Override
      public void onClick() {
        // setResponsePage(FormPage.class);
        // do a put command
        List<String> list = new ArrayList<String>();
        list.add((String) textField.getDefaultModelObject());
        // db.doCommand("HVAC", "Arduino-4", "SetTemp", list);

      }
    });
    add(form);

    // temporary images to be replaced.
    add(new Image("tempY", new ResourceReference(Header.class, "images/tempY.png")));
    add(new Image("tempM", new ResourceReference(Header.class, "images/tempM.png")));
    add(new Image("tempW", new ResourceReference(Header.class, "images/tempW.png")));
    add(new Image("tempD", new ResourceReference(Header.class, "images/tempD.png")));

  }

  /**
   * Set the corresponding color for the inside temperature label according to the value.
   * 
   * @param value The room temperature.
   */
  private void determineInsideTempTextColor(Long value) {
    String original = (String) insideTemperature.getDefaultModelObject();
    String closeTag = "</font>";
    if (value > 70 && value < 77) {
      original = "<font color=\"green\">" + original + closeTag;
    }
    else if (value == 70 || value == 77) {
      original = "<font color=\"#FF9900\">" + original + closeTag;
    }
    else {
      original = "<font color=\"red\">" + original + closeTag;
    }
    insideTemperature.setDefaultModelObject(original);
  }

  /**
   * Set the water temperature label and to its appropriate color.
   * 
   * @param value The water temperature value.
   */
  public static void setWaterTemp(Long value) {

    String original = value + "&deg;F";

    String closeTag = "</font>";
    if (value > 59 && value < 68) {
      original = "<font color=\"green\">" + original + closeTag;
    }
    else if (value == 59 || value == 68) {
      original = "<font color=\"#FF9900\">" + original + closeTag;
    }
    else {
      original = "<font color=\"red\">" + original + closeTag;
    }
    waterTemperature.setDefaultModelObject(original);
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
