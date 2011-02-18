package edu.hawaii.ihale.ui.page.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.AbstractBehavior;

/**
 * 
 * <p>
 * Create the OverallStatus Object. <br/>
 * <br/>
 * Create plain text:<br/>
 * new OverAllStatus("test", "test1"); <br/>
 * <br/>
 * Create image : <br/>
 * new OverAllStatus("test", new ResourceReference(OverAllStatus.class, "statusGreen.png")); <br/>
 * <br/>
 * Create link : <br/>
 * new OverAllStatus("Home", "hello", HomePage.class);<br/>
 * <br/>
 * Create image with a link: <br/>
 * new OverAllStatus("test", new ResourceReference(OverAllStatus.class, "statusGreen.png"),
 * HomePage.class); <br/>
 * <br/>
 * Append a class: <br/>
 * List<AbstractBehavior> list = new ArrayList<AbstractBehavior>(); list.add(new
 * AttributeAppender("class", new Model<String>("classNameHere1"), " ")); list.add(new
 * AttributeAppender("class", new Model<String>("classNameHere2"), " ")); new OverAllStatus("Hello",
 * "!", list);
 * </p>
 * 
 * @author Bret Ikehara
 */
public class OverAllStatus implements Serializable {

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

  private String label;
  private Object status;
  private Class<? extends Page> page;
  private List<AbstractBehavior> labelAttr;
  private List<AbstractBehavior> statusAttr;

  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param label String
   * @param status Object
   */
  public OverAllStatus(String label, Object status) {
    this(label, status, null, new ArrayList<AbstractBehavior>());
  }

  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param label String
   * @param status Object
   * @param list List<AbstractBehavior>
   */
  public OverAllStatus(String label, Object status, List<AbstractBehavior> list) {
    this(label, status, null, list);
  }

  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param label String
   * @param status Object
   * @param page Class<?>
   */
  public OverAllStatus(String label, Object status, Class<? extends Page> page) {
    this(label, status, page, new ArrayList<AbstractBehavior>());
  }

  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param label String
   * @param status Object
   * @param page Class<?>
   * @param attr AbstractBehavior
   */
  public OverAllStatus(String label, Object status, Class<? extends Page> page,
      List<AbstractBehavior> attr) {
    this.label = label;
    this.status = status;
    this.page = page;
    this.labelAttr = attr;
    this.statusAttr = new ArrayList<AbstractBehavior>();
  }

  /**
   * Constructor for the status object to display on the overall status panel.
   * 
   * @param label String
   * @param status Object
   * @param page Class<?>
   * @param labelAttr AbstractBehavior
   * @param statusAttr AbstractBehavior
   */
  public OverAllStatus(String label, Object status, Class<? extends Page> page,
      List<AbstractBehavior> labelAttr, List<AbstractBehavior> statusAttr) {
    this.label = label;
    this.status = status;
    this.page = page;
    this.labelAttr = labelAttr;
    this.statusAttr = statusAttr;
  }

  /**
   * Get the label.
   * 
   * @return String
   */
  public String getLabel() {
    return label;
  }

  /**
   * Set the label.
   * 
   * @param label String
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Get the status.
   * 
   * @return Object
   */
  public Object getStatus() {
    return status;
  }

  /**
   * Set the status.
   * 
   * @param status Object
   */
  public void setStatus(Object status) {
    this.status = status;
  }

  /**
   * Sets the response page.
   * 
   * @param page Class<?>
   */
  public void setPage(Class<? extends Page> page) {
    this.page = page;
  }

  /**
   * Gets the page.
   * 
   * @return Class<?>
   */
  public Class<? extends Page> getPage() {
    return page;
  }

  /**
   * Get the attribute.
   * 
   * @param i int
   * @return AbstractBehavior
   */
  public AbstractBehavior getLabelAttribute(int i) {
    return this.labelAttr.get(i);
  }

  /**
   * Set the attribute.
   * 
   * @param am AbstractBehavior
   */
  public void setLabelAttribute(AbstractBehavior am) {
    this.labelAttr.add(am);
  }

  /**
   * Get the attribute.
   * 
   * @param i int
   * @return AbstractBehavior
   */
  public AbstractBehavior getStatusAttribute(int i) {
    return this.statusAttr.get(i);
  }

  /**
   * Set the attribute.
   * 
   * @param am AbstractBehavior
   */
  public void setStatusAttribute(AbstractBehavior am) {
    this.statusAttr.add(am);
  }

  /**
   * Get list of label attributes.
   * 
   * @return String[]
   */
  public List<AbstractBehavior> getLabelAttributes() {
    return this.labelAttr;
  }

  /**
   * Get list of label attributes.
   * 
   * @return String[]
   */
  public List<AbstractBehavior> getStatusAttributes() {
    return this.statusAttr;
  }
}
