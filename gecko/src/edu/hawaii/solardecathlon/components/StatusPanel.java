package edu.hawaii.solardecathlon.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * Creates the Status Panel for the Aquaponics system so that a user can easily identify problems.
 * 
 * @author Bret K. Ikehara
 */
public abstract class StatusPanel extends Panel {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 9197552736141510649L;

  protected static final Pattern CLASS_NAME = Pattern.compile("box(Red|Green|Orange)");
  
  /**
   * The id for the insides of the status panel.
   */
  public static final String STATUSID = "panel";

  /**
   * The title for the insides of the status panel.
   */
  public static final String TITILEID = "title";
  
  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public StatusPanel(String id) {
    super(id);
  }
  
  /**
   * Updates the background with the new class of boxRed, boxGreen, or boxOrange.
   * 
   * @param className String
   */
  public void updateBackGround(String className) {
  this.add(new AttributeModifier("class", true, new Model<String>(className)) {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4254166375725522357L;

    @Override
    protected String newValue(String currentValue, String replacementValue) {
      Matcher m = CLASS_NAME.matcher(currentValue);

      return (m.find()) ? m.replaceAll(replacementValue) : currentValue + " "
          + replacementValue;
    }
  });
  }
}
