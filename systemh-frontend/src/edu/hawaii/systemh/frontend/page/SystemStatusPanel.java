package edu.hawaii.systemh.frontend.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import edu.hawaii.systemh.frontend.components.panel.SystemPanel;
import edu.hawaii.systemh.frontend.page.SystemStatusPanel.SystemStatus;
import edu.hawaii.systemh.frontend.page.energy.Energy;

/**
 * Creates the system status panel.
 * 
 * @author Bret K. Ikehara
 */
public class SystemStatusPanel extends SystemPanel {

  /**
   * System Status values.
   */
  public enum SystemStatus {
    /** System OK. */
    OK,
    
    /** System getting close to bad. */
    CAUTION,
    
    /** Something wrong with the system. */
    WARNING
  };

  private static final String ORB_OK = "images/icons/ball_green.png";
  private static final String ORB_CAUTION = "images/icons/ball_yellow.png";
  private static final String ORB_WARNING = "images/icons/ball_red.png";

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 990554229501390289L;

  /**
   * Default constructor.
   * 
   * @param id String
   * @param model IModel<String>
   */
  public SystemStatusPanel(String id, IModel<String> model) {
    super(id, model);

    add(CSSPackageResource.getHeaderContribution(SystemStatusPanel.class,
        "SystemStatusPanel.css", "screen"));

    List<SystemStatusObj> list = new ArrayList<SystemStatusObj>();
    list.add(new SystemStatusObj("Electrical", Energy.class, SystemStatus.OK));

    addToPanel(new ListView<SystemStatusObj>("systems", list) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -5119857624396832441L;

      /**
       * Populates the system status panel.
       * 
       * @param item ListItem<SystemStatusObj>
       */
      @Override
      protected void populateItem(ListItem<SystemStatusObj> item) {
        final SystemStatusObj obj = item.getModelObject();

        Link<SystemStatusObj> link = new Link<SystemStatusObj>("link") {

          /**
           * Serial ID.
           */
          private static final long serialVersionUID = 920138749897234234L;

          /**
           * Goes to this page.
           */
          @Override
          public void onClick() {
            setResponsePage(obj.getLinkClass());
          }
        };
        item.add(link);
        link.add(new Label("linkLabel", obj.getLinkLabel()));

        Image img = new Image("image");
        if (SystemStatus.OK == obj.getStatus()) {
          img.setImageResourceReference(new ResourceReference(Header.class, ORB_OK));
        }
        else if (SystemStatus.CAUTION == obj.getStatus()) {
          img.setImageResourceReference(new ResourceReference(Header.class, ORB_CAUTION));
        }
        else if (SystemStatus.WARNING == obj.getStatus()) {
          img.setImageResourceReference(new ResourceReference(Header.class, ORB_WARNING));
        }
        img.add(new SimpleAttributeModifier("alt", obj.getStatus().toString()));
        item.add(img);
      }
    });
  }
}

/**
 * Creates the System Status panel objects.
 * 
 * @author Bret K. Ikehara
 */
class SystemStatusObj implements Serializable {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 2499335143682204436L;

  private String labelLabel;
  private Class<? extends Page> linkClass;
  private SystemStatus status;

  /**
   * Default Construtor.
   * 
   * @param linkLabel String
   * @param linkClass Class
   * @param status SystemStatus
   */
  public SystemStatusObj(String linkLabel, Class<? extends Page> linkClass, SystemStatus status) {
    this.labelLabel = linkLabel;
    this.linkClass = linkClass;
    this.status = status;
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
   * Get this status.
   * 
   * @return SystemStatus
   */
  public SystemStatus getStatus() {
    return this.status;
  }
}