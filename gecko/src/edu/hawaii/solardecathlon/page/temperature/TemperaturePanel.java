package edu.hawaii.solardecathlon.page.temperature;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.listeners.HvacListener;

/**
 * Creates the current temperature condition panel.
 * 
 * @author Bret K. Ikehara
 */
public class TemperaturePanel extends Panel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 93256416491062417L;

  private transient HvacListener listener;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public TemperaturePanel(String id) {
    super(id);

    listener = SolarDecathlonApplication.getHvacListener();

    // Adds the temperature to the panel.
    List<Item<Long>> condList = new ArrayList<Item<Long>>();
    condList.add(new Item<Long>("Temp", 0, new Model<Long>() {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 7576322045264403975L;

      /**
       * Gets the temperature.
       */
      @Override
      public Long getObject() {
        return listener.getTemp();
      }
    }));

    ListView<Item<Long>> tempList = new ListView<Item<Long>>("listCond", condList) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 2037112857847650272L;

      /**
       * Populates the temperature conditions.
       */
      @Override
      protected void populateItem(ListItem<Item<Long>> item) {
        Item<Long> modelObj = item.getModelObject();

        item.add(new Label("name", modelObj.getId()));
        item.add(new Label("value", modelObj.getDefaultModel()));
      }
    };
    add(tempList);
    
    // Updates the temperature panel.
    add(new AjaxSelfUpdatingTimerBehavior(SolarDecathlonApplication.getUpdateInterval()));
  }
}
