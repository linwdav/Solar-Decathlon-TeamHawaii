package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.components.StatusPanel;
import edu.hawaii.solardecathlon.listeners.AquaponicsListener;

/**
 * Creates the panel for pH.
 * 
 * @author Bret K. Ikehara
 */
public class PhPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 535900568957796624L;

  private Label msg;

  private transient AquaponicsListener listener;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public PhPanel(String id) {
    super(id);

    listener = SolarDecathlonApplication.getAquaponicsListener();

    Label title = new Label("title", "<h4 style=\"color:white;\">Current pH</h4>");
    title.setEscapeModelStrings(false);
    add(title);

    add(new Label("value", new Model<Double>() {
      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 6604204917573981631L;

      /**
       * Gets the ph.
       */
      @Override
      public Double getObject() {
        return listener.getPh();
      }
    }));

    msg = new Label("msg", "");
    add(msg);

    // Updates the ph panel.
    add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 7748435291994928648L;

      @Override
      protected void onPostProcessTarget(AjaxRequestTarget target) {
        super.onPostProcessTarget(target);
        updatePanel();
      }
    });

    updatePanel();
  }

  /**
   * Updates the panel based upon the information in the property model.
   */
  private void updatePanel() {
    double ph = listener.getPh();

    String className = (ph < 6.0 || ph > 8.0) ? "boxRed" : "boxGreen";
    String msgStr = (ph < 6.0 || ph > 8.0) ? "(Alert)" : "";

    // Updates the box's style.
    super.updateBackGround(className);
    msg.setDefaultModelObject(msgStr);
  }
}
