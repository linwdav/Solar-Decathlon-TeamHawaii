package edu.hawaii.solardecathlon.page.temperature;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.components.AjaxUpdate;
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

  private TemperatureModel propModel;
  private TemperaturePanel tempPanel;
  private AjaxUpdate tempEvent;
  private TextField<String> airTemp;

  /**
   * Layout of page.
   */
  public TemperaturePage() {
    propModel = (TemperatureModel) session.getModel("temperature");

    add(new Image("tempY", new ResourceReference(BasePage.class, "images/tempY.png")));
    add(new Image("tempM", new ResourceReference(BasePage.class, "images/tempM.png")));
    add(new Image("tempW", new ResourceReference(BasePage.class, "images/tempW.png")));
    add(new Image("tempD", new ResourceReference(BasePage.class, "images/tempD.png")));

    tempEvent = new AjaxUpdate();

    getDAO().addSystemStateListener(new SystemStateListener("hvac") {

      /**
       * Updates the model by SystemStateEntry.
       * 
       * @param entry SystemStateEntry
       */
      @Override
      public void entryAdded(SystemStateEntry entry) {
        System.out.println("Something just happened in HVAC: " + entry);

        // Update the property model
        propModel.setTimestamp(entry.getTimestamp());
        propModel.setTemp(entry.getLongValue("temp"));

        // Update the ph panel data and styling.
        tempEvent.onRequest();
      }
    });

    Form<String> form = new Form<String>("temperatureControl");
    add(form);

    airTemp =
        new TextField<String>("airTemperature", new Model<String>(propModel.getTemp().toString()
            + "°F"));
    // Added for jquery control. Important for AjaxFormComponentUpdatingBehavior.onUpdate() method
    // to work!
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
        propModel.setTemp(temp);

        target.addComponent(tempPanel);
      }
    });
    form.add(airTemp);

    tempPanel = new TemperaturePanel("tempConditions");
    tempPanel.add(tempEvent);
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
