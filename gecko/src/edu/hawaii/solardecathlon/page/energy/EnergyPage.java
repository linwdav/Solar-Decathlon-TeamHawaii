package edu.hawaii.solardecathlon.page.energy;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.components.AttributeModifier;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Bret K. Ikehara
 */
public class EnergyPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 2978474992827L;

  private AjaxFallbackLink<String> selectedGraphType;
  
  private ElectricalModel elecModel;
  private PhotovoltaicsModel photoModel;

  /**
   * The page layout.
   */
  public EnergyPage() {
    
    elecModel = (ElectricalModel) session.getModel("electrical");
    photoModel = (PhotovoltaicsModel) session.getModel("photovoltaics");
    
    // Add the photovoltaics listener.
    getDAO().addSystemStateListener(new SystemStateListener("photovoltaics") {
      
      /**
       * Update model when an entry is added.
       */
      @Override
      public void entryAdded(SystemStateEntry entry) {
        photoModel.setEnergy(entry.getLongValue("energy"));
        photoModel.setPower(entry.getLongValue("power"));        
      }
    });
    
    // Add the electrical listener.
    getDAO().addSystemStateListener(new SystemStateListener("electrical") {
      
      /**
       * Update model when an entry is added.
       */
      @Override
      public void entryAdded(SystemStateEntry entry) {
        elecModel.setEnergy(entry.getLongValue("energy"));
        elecModel.setPower(entry.getLongValue("power"));        
      }
    });
    
    tabPanelResource();
    graphTypeResource();
  }

  /**
   * Inits the type of graphs that are shown in the tabbed panel.
   */
  private void graphTypeResource() {

    List<Item<Integer>> typeList = new ArrayList<Item<Integer>>();
    typeList.add(new Item<Integer>("Consumption<br/>vs. Production", 0));
    typeList.add(new Item<Integer>("Breakdown", 1));
    typeList.add(new Item<Integer>("HVAC", 2));
    typeList.add(new Item<Integer>("Aquaponics", 3));
    typeList.add(new Item<Integer>("Lighting", 4));

    add(new ListView<Item<Integer>>("graphType", typeList) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 8243513532039045086L;

      /**
       * Populates the list.
       */
      @Override
      protected void populateItem(ListItem<Item<Integer>> item) {
        final Item<Integer> modelObj = item.getModelObject();

        final AjaxFallbackLink<String> link = new AjaxFallbackLink<String>("link") {

          /**
           * Serial ID.
           */
          private static final long serialVersionUID = 809323285313262754L;

          /**
           * Updates the session value.
           */
          @Override
          public void onClick(AjaxRequestTarget target) {

            // Remove green selected color.
            selectedGraphType.add(new AttributeModifier("class", true, new Model<String>(
                CLASS_BTN_GRAY), CLASS_PAT_BTN));

            // Add new class.
            session.putProperty(BasePage.GRAPH_NUM, modelObj.getIndex());
            this.add(new AttributeModifier("class", true, new Model<String>(CLASS_BTN_GREEN),
                CLASS_PAT_BTN));

            // Update buttons, then update reference.
            target.addComponent(selectedGraphType);
            target.addComponent(this);
            selectedGraphType = this;
          }

        };
        item.add(link);

        // Parse graph type in session, else default to 0.
        Integer graphNum =
            (session.getProperty(BasePage.GRAPH_NUM) == null) ? modelObj.getIndex() : Integer
                .valueOf(session.getProperty(BasePage.GRAPH_NUM));
        boolean selectedCond = graphNum != null && graphNum == modelObj.getIndex();
        String className = (selectedCond) ? CLASS_BTN_GREEN : CLASS_BTN_GRAY;
        link.add(new SimpleAttributeModifier("class", className));

        // Save reference to remove selected class later on.
        if (selectedCond) {
          selectedGraphType = link;
        }

        Label name = new Label("name", modelObj.getId());
        name.setEscapeModelStrings(false);
        link.add(name);

      }
    });
  }

  /**
   * Inits the tabbed panel resource.
   */
  private void tabPanelResource() {
    List<ITab> timePanel = new ArrayList<ITab>();
    timePanel.add(new AbstractTab(new Model<String>("Day")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -62147237853507133L;

      /**
       * Returns the panel.
       */
      @Override
      public Panel getPanel(String arg0) {
        return new EnergyPageTab1(arg0);
      }
    });
    timePanel.add(new AbstractTab(new Model<String>("Week")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -8204517561576708816L;

      /**
       * Returns the panel.
       */
      @Override
      public Panel getPanel(String arg0) {
        return new EnergyPageTab2(arg0);
      }
    });
    timePanel.add(new AbstractTab(new Model<String>("Month")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = -2106086851756181933L;

      /**
       * Returns the panel.
       */
      @Override
      public Panel getPanel(String arg0) {
        return new EnergyPageTab3(arg0);
      }
    });
    timePanel.add(new AbstractTab(new Model<String>("Year")) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1055995056790651900L;

      /**
       * Returns the panel.
       */
      @Override
      public Panel getPanel(String arg0) {
        return new EnergyPageTab4(arg0);
      }
    });

    add(new AjaxTabbedPanel("energyGraph", timePanel));
  }
}