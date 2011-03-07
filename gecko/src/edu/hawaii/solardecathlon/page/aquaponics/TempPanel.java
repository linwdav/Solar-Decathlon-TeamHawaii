package edu.hawaii.solardecathlon.page.aquaponics;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import edu.hawaii.solardecathlon.components.StatusPanel;

/**
 * Creates the panel for the water temperature.
 * 
 * @author Bret K. Ikehara
 */
public class TempPanel extends StatusPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -7396215152454139784L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public TempPanel(String id) {
    super(id);
    
    Label title = new Label("title", "<h4 style=\"color:white;\">Current Temp</h4>");
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
    
        //TODO update with property model.
        item.add(new Label("value", "model"));
      }
    });
  }

  /**
   * Updates styles when model changes. This is called when in the AjaxUpdate.onEvent() method.
   */
  @Override
  protected void onModelChanged() {
    super.onModelChanged();
    updatePanel();
  }
  
  /**
   * Updates the panel before rendering.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    updatePanel();
  }

  /**
   * Updates the panel based upon the information in the property model.
   */
  protected void updatePanel() {
    //AquaponicsModel model =
    //    (AquaponicsModel) ((SolarDecathlonSession) getSession()).getModel("aquaponics");
    
    //TODO update with property model.
    boolean warning = false;
    String className = warning ? "boxOrange" : "boxGreen";

    // Updates the box's style.
    super.updateBackGround(className);
  }
}
