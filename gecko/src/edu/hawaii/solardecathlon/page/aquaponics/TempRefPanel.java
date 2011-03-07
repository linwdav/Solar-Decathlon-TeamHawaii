package edu.hawaii.solardecathlon.page.aquaponics;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import edu.hawaii.solardecathlon.components.StatusPanel;

/**
 * Creates the panel for temperture reference.
 * 
 * @author Bret K. Ikehara
 */
public class TempRefPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -6076914788246070907L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public TempRefPanel(String id) {
    super(id);

    Label title = new Label("title", "<h5>Recommeded pH</h5>");
    title.setEscapeModelStrings(false);
    add(title);
    
    List<String> list = new ArrayList<String>();
    list.add("Air:");
    list.add("H<sub>2</sub>0:");
    
    add(new ListView<String>("list", list) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -7442484019487581953L;

      /**
       * Populates the list.
       */
      @Override
      protected void populateItem(ListItem<String> item) {
        String s = item.getModelObject();
        
        item.add(new Label("name", s).setEscapeModelStrings(false));
    
        //TODO update with model.
        item.add(new Label("value", "model"));
      }
    });
    
  }
}
