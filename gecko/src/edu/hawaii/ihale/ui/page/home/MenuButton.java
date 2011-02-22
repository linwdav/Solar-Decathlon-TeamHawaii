package edu.hawaii.ihale.ui.page.home;

/**
 * Creates the main buttons on the home page.
 * 
 * @author Bret K. Ikehara
 */
public class MenuButton {

  private String label;
  private String title;
  private String subLabel;
  private Class<?> link;

  /**
   * Constructor for the button object.
   * 
   * @param label String
   * @param subLabel String
   * @param link <?>
   */
  public MenuButton(String label, String subLabel, Class<?> link) {
    this(label, subLabel, link, "");
  }

  /**
   * Constructor for the button object.
   * 
   * @param label String
   * @param subLabel String
   * @param link Class<?>
   * @param title String
   */
  public MenuButton(String label, String subLabel, Class<?> link, String title) {
    this.label = label;
    this.subLabel = subLabel;
    this.link = link;
    this.title = title;
  }

  /**
   * Sets the label.
   * 
   * @param label String
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Gets the label.
   * 
   * @return String
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the title.
   * 
   * @param title String
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the title.
   * 
   * @return String
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the subLabel.
   * 
   * @param subLabel String
   */
  public void setSubLabel(String subLabel) {
    this.subLabel = subLabel;
  }

  /**
   * Gets the subLabel.
   * 
   * @return String
   */
  public String getSubLabel() {
    return subLabel;
  }

  /**
   * Sets the link.
   * 
   * @param link Class<?>
   */
  public void setLink(Class<?> link) {
    this.link = link;
  }

  /**
   * Get Link.
   * 
   * @return Class<?>
   */
  public Class<?> getLink() {
    return link;
  }

}
