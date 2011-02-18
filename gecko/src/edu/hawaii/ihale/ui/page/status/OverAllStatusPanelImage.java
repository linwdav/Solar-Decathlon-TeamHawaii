package edu.hawaii.ihale.ui.page.status;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Creates the image panel to display images inside the overall status.
 * 
 * @author Bret Ikehara
 * 
 */
public class OverAllStatusPanelImage extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -147992438179444977L;

  /**
   * Constructor for image panel. Adds image to the panel.
   * 
   * @param name String
   * @param <T> type
   * @param model IModel<Image>
   * @param rr ResourceReference
   */
  public <T> OverAllStatusPanelImage(String name, IModel<T> model, ResourceReference rr) {
    super(name, model);

    add(new Image("OverAllStatusImage", rr));
  }
}
