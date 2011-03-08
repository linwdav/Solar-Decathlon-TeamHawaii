package edu.hawaii.solardecathlon.page.temperature;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.listeners.HvacListener;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The temperature page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Bret K. Ikehara
 */
public class TemperaturePage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private transient HvacListener listener;
  private TemperaturePanel tempPanel;
  private TextField<String> airTemp;

  /**
   * Layout of page.
   */
  public TemperaturePage() {
    listener = SolarDecathlonApplication.getHvacListener();

    add(new Image("tempY", new ResourceReference(BasePage.class, "images/tempY.png")));
    add(new Image("tempM", new ResourceReference(BasePage.class, "images/tempM.png")));
    add(new Image("tempW", new ResourceReference(BasePage.class, "images/tempW.png")));
    add(new Image("tempD", new ResourceReference(BasePage.class, "images/tempD.png")));

    Form<String> form = new Form<String>("temperatureControl");
    add(form);

    // Add the control for the air temp slider
    airTemp = new TextField<String>("airTemperature", new Model<String>() {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 2999582492534916694L;

      /**
       * Gets the temperature formatted.
       */
      @Override
      public String getObject() {
        return listener.getTemp() + "°F";
      }
    });
    // Added for jquery control.
    airTemp.setMarkupId(airTemp.getId());
    airTemp.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 7819370842486167894L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {

        Long temp =
            Long.valueOf(airTemp.getValue().substring(0, airTemp.getValue().length() - 2));

        // Update panel's value
        target.addComponent(tempPanel);

        // Send information to the backend.
        List<String> args = new ArrayList<String>();
        args.add(temp.toString());
        dao.doCommand("hvac", "arduino-4", "setTemp", args);
      }
    });
    form.add(airTemp);

    tempPanel = new TemperaturePanel("tempConditions");
    tempPanel.setMarkupId("tempConditions");
    add(tempPanel);

    resourceRef();
  }

  /**
   * References all the resources on the page.
   */
  private void resourceRef() {
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/temperature.css", CSS_SCREEN));
  }
}
