package edu.hawaii.solardecathlon.page.temperature;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.solardecathlon.SolarDecathlonSession;

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

  private TemperatureModel propModel;
  
  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public TemperaturePanel(String id) {
    super(id);
    
    propModel = (TemperatureModel) ((SolarDecathlonSession) getSession()).getModel("temperature");

    List<Item<String>> condList = new ArrayList<Item<String>>();
    condList.add(new Item<String>("Temp", 0, new Model<String>("temperature.temp")));

    ListView<Item<String>> tempList = new ListView<Item<String>>("listCond", condList) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 2037112857847650272L;

      /**
       * Populates the temperature conditions.
       */
      @Override
      protected void populateItem(ListItem<Item<String>> item) {
        Item<String> modelObj = item.getModelObject();

        item.add(new Label("name", modelObj.getId()));
        item.add(new Label("value", new PropertyModel<Long>(propModel, modelObj
            .getModelObject())));
      }
    };

    add(tempList);
  }
}
