package edu.hawaii.ihale.ui.page;

import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * Populates the over all status with text or an image.
 * 
 * @author Bret K. Ikehara
 */

public class SidebarPanel extends BasePanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 6422929726391510078L;
  
  /**
   * Left-side Component Wicket ID.
   */
  public static final String LEFT = "OverAllStatusLeft";

  /**
   * Right-side Component Wicket ID.
   */
  public static final String RIGHT = "OverAllStatusRight";

  /**
   * Constructor to show empty sidebar.
   * 
   * @param name String
   */
  public SidebarPanel(String name) {
    super(name);
  }
  
  /**
   * Constructor to create the list.
   * 
   * @param name String
   * @param los List<OverAllStatus>
   */
  public SidebarPanel(String name, List<Sidebar> los) {
    super(name);

    add(new ListView<Sidebar>("OverAllStatus", los) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Populates the list.
       */
      @Override
      protected void populateItem(ListItem<Sidebar> item) {
        Sidebar os = item.getModelObject();
        
        item.add(os.getLeftComponent());
        item.add(os.getRightComponent());
      }
    });
  }
}