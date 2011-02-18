package edu.hawaii.ihale.ui.page.status;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * Populates the over all status with text or an image.
 * 
 * @author Bret Ikehara.
 */

public class OverAllStatusPanel extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 6422929726391510078L;

  /**
   * Constructor to create the list.
   * 
   * @param name String
   * @param <T> type
   * @param model IModel<T>
   * @param los List<OverAllStatus>
   */
  public <T> OverAllStatusPanel(String name, IModel<T> model, List<OverAllStatus> los) {
    super(name, model);

    add(CSSPackageResource.getHeaderContribution(OverAllStatusPanel.class, "style.css"));

    add(new ListView<OverAllStatus>("OverAllStatus", los) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Populates the list.
       */
      @Override
      protected void populateItem(ListItem<OverAllStatus> item) {
        OverAllStatus os = item.getModelObject();
        Component label = null;
        Component status = null;

        // Handles the label.
        label =
            (os.getPage() == null) ? new Label("OverAllStatusLabel", os.getLabel())
                : new OverAllStatusPanelLink("OverAllStatusLabel", new Model<String>(
                    "OverAllStatusLabel"), os.getPage(), os.getLabel());

        // Handle the output for the label.
        status =
            (os.getStatus().getClass() == String.class) ? new Label("OverAllStatusStatus", os
                .getStatus().toString()) : new OverAllStatusPanelImage(
                "OverAllStatusStatus", new Model<String>("OverAllStatusStatus"),
                (ResourceReference) os.getStatus());

        // Run through all the attributes.
        if (os.getLabelAttributes().size() > 0) {
          for (AbstractBehavior ab : os.getLabelAttributes()) {
            label.add(ab);
          }
        }

        // If there are label, but no status attributes run label attributes on status.
        if (os.getStatusAttributes().size() > 0) {
          for (AbstractBehavior ab : os.getStatusAttributes()) {
            status.add(ab);
          }
        }
        else if (os.getStatusAttributes().size() == 0 && os.getLabelAttributes().size() > 0) {
          for (AbstractBehavior ab : os.getLabelAttributes()) {
            status.add(ab);
          }
        }

        item.add(label);
        item.add(status);
      }
    });
  }
}