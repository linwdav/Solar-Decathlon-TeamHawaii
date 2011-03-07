package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.solardecathlon.SolarDecathlonSession;
import edu.hawaii.solardecathlon.components.StatusPanel;

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

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public PhPanel(String id) {
    super(id);
    Label title = new Label("title", "<h4 style=\"color:white;\">Current pH</h4>");
    title.setEscapeModelStrings(false);
    add(title);

    add(new Label("value", new PropertyModel<Double>(getSession(), "aquaponics.ph")));

    msg = new Label("msg", "");
    add(msg);

    updatePanel();
  }

  /**
   * Updates styles when model changes.
   */
  @Override
  protected void onModelChanged() {
    super.onModelChanged();
    updatePanel();
  }

  /**
   * Updates the panel based upon the information in the property model.
   */
  private void updatePanel() {
    AquaponicsModel model =
        (AquaponicsModel) ((SolarDecathlonSession) getSession()).getModel("aquaponics");
    double ph = model.getPh();

    String className = (ph < 6.0 || ph > 8.0) ? "boxRed" : "boxGreen";
    String msgStr = (ph < 6.0 || ph > 8.0) ? "(Alert)" : "";

    // Updates the box's style.
    super.updateBackGround(className);
    msg.setDefaultModelObject(msgStr);
  }
}
