package edu.hawaii.ihale.ui.page.status;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Creates the image panel to display images inside the overall status.
 * 
 * @author Bret Ikehara
 * 
 */
public class OverAllStatusPanelLink extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -147992438179444977L;

  /**
   * Constructor for image panel. Adds image to the panel.
   * 
   * @param name String
   * @param <T> type
   * @param model IModel<T>
   * @param page Class<? extends org.apache.wicket.Page>
   * @param label String
   */
  public <T> OverAllStatusPanelLink(String name, IModel<T> model, final Class<? extends Page> page,
      String label) {
    super(name, model);
    
    Link<String> link = new Link<String>("OverAllStatusLabelLink") {
      private static final long serialVersionUID = 1L;

      /**
       * Upon clicking this link, go to ListPage.
       */
      @Override
      public void onClick() {
        setResponsePage(page);
      }
    };
    link.add(new Label("OverAllStatusLabelLinkLabel", label));
    
    add(link);

  }
}
