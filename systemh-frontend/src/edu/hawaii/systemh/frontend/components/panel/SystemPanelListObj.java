package edu.hawaii.systemh.frontend.components.panel;

import java.io.Serializable;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.model.Model;

/**
 * Creates the System Status panel objects.
 * 
 * @author Bret K. Ikehara
 */
public class SystemPanelListObj implements Serializable {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 2499335143682204436L;

  private String labelLabel;
  private Class<? extends Page> linkClass;
  private Model<ResourceReference> model;

  /**
   * Default Construtor.
   * 
   * @param linkLabel String
   * @param linkClass Class
   * @param model Model<ResourceReference>
   */
  public SystemPanelListObj(String linkLabel, Class<? extends Page> linkClass,
      Model<ResourceReference> model) {
    this.labelLabel = linkLabel;
    this.linkClass = linkClass;
    this.model = model;
  }

  /**
   * Gets this link label.
   * 
   * @return String
   */
  public String getLinkLabel() {
    return this.labelLabel;
  }

  /**
   * Gets this link label.
   * 
   * @return String
   */
  public Class<? extends Page> getLinkClass() {
    return this.linkClass;
  }

  /**
   * Get this model.
   * 
   * @return Model<SystemStatus>
   */
  public Model<ResourceReference> getModel() {
    return this.model;
  }
}