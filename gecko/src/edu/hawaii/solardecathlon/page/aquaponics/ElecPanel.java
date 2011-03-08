package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.markup.html.basic.Label;
import edu.hawaii.solardecathlon.components.StatusPanel;

/**
 * Creates the panel for electrical conductivity.
 * 
 * @author Bret K. Ikehara
 */
public class ElecPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1149268520431779671L;

  private Label msg;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public ElecPanel(String id) {
    super(id);

    Label title =
        new Label("title", "<h4 style=\"color:white;\">Electrical Conductivity (EC)</h4>");
    title.setEscapeModelStrings(false);
    add(title);

    // TODO update with property model
    add(new Label("value", "2.30"));

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

    // TODO update with property model.
    double ec = 2.3;

    boolean warning = (ec < 1.75 || ec > 2.25);
    String className = warning ? "boxOrange" : "boxGreen";
    String msgStr = warning ? "(WARNG)" : "";

    // Updates the box's style.
    super.updateBackGround(className);
    msg.setDefaultModelObject(msgStr);
  }
}
