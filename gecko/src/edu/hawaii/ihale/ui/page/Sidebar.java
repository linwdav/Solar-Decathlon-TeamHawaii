package edu.hawaii.ihale.ui.page;

import java.io.Serializable;
import org.apache.wicket.Component;

/**
 * Creates the over all status panel objects.
 * 
 * @author Bret K. Ikehara
 */
public class Sidebar implements Serializable {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 3502062022789147958L;

  /**
   * No action required on user's part.
   */
  public static final String STATUSGOOD = "statusGreen.png";

  /**
   * There are a few warnings available.
   */
  public static final String STATUSCAUTION = "statusYellow.png";

  /**
   * Immediate action is required on user's part.
   */
  public static final String STATUSWARNING = "statusRed.png";

  private Component left;
  private Component right;
  
  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param leftComponent Component
   * @param rightComponent Component
   */
  public Sidebar(Component leftComponent, Component rightComponent) {
    this.left = leftComponent;
    this.right = rightComponent;
  }

  /**
   * Gets this status panel's left side Component.
   * 
   * @return Component
   */
  public Component getLeftComponent() {
    return left;
  }

  /**
   * Sets this status panel's left side Component.
   * 
   * @param left Component
   */
  public void setLeftComponent(Component left) {
    this.left = left;
  }

  /**
   * Gets this status panel's right side Component.
   * 
   * @return Component
   */
  public Component getRightComponent() {
    return right;
  }

  /**
   * Sets this status panel's right side Component.
   * 
   * @param right Component
   */
  public void setRightComponent(Component right) {
    this.right = right;
  }
}
