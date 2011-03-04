package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.page.AjaxUpdate;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The aquaponics page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Aquaponics extends BasePage {

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer graph = new WebMarkupContainer("graphImage");

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private AjaxUpdate phEvent;

  /**
   * Layout of page.
   */
  public Aquaponics() {

    phEvent = new AjaxUpdate();

    // Adds the aquaponics listener to the database.
    getDAO().addSystemStateListener(new SystemStateListener("aquaponics") {

      /**
       * Updates the model by SystemStateEntry.
       * 
       * @param entry SystemStateEntry
       */
      @Override
      public void entryAdded(SystemStateEntry entry) {
        System.out.println("Something just happened in Aquaponics: " + entry);

        double ph;
        String phMsg = "";
        String phClass = "column-two box";

        StatusPanel panel = (StatusPanel) get("phPanel");
        
        // Update the property model
        AquaponicsModel aquaponics = (AquaponicsModel) session.getModel("aquaponics");
        aquaponics.setTimestamp(entry.getTimestamp());
        aquaponics.setTemp(entry.getLongValue("temp"));
        aquaponics.setPh(entry.getDoubleValue("ph"));
        aquaponics.setOxygen(entry.getDoubleValue("oxygen"));

        // Update the ph panel data and styling.
        phEvent.onRequest();
        
        ph = aquaponics.getPh();
        if (ph < 6.5 || ph > 7.0) {
          phMsg = "(Alert)";
          phClass += "Red";
        }
        else {
          phClass += "Green";
        }

        panel.get("phMsg").setDefaultModelObject(phMsg);
        panel.add(new SimpleAttributeModifier("class", phClass));
      }
    });

    // Button at top of page
    Link<String> statsButton = new Link<String>("statsButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        setResponsePage(Aquaponics.class);
      }
    };

    // Add button to switch to statistics view
    add(statsButton);

    // Add Graph of Water Quality to page
    add(graph);
    displayDayGraph(graph);

    // Create the phPanel with Ajax update event.
    StatusPanel phPanel = new StatusPanel("phPanel", 1);
    phPanel.add(phEvent);
    phPanel.get("phValue").setDefaultModel(new PropertyModel<Double>(session, "aquaponics.ph"));
    add(phPanel);
  }

  /**
   * Determines which graph to display.
   * 
   * @param wmc web component to display graph in
   **/
  private void displayDayGraph(WebMarkupContainer wmc) {
    String graphURL;

    graphURL =
        "http://chart.apis.google.com/chart?chxl=0:|pH|NH3|Bacteria|Temp|O2|AT&chxt=x&"
            + "chbh=a,5,15&chs=300x200&cht=bvg&chco=FF9900,008000&chd=s:cnwhZj,eYNkph&"
            + "chdl=Actual|Recommended&chdlp=t&chts=676767,13.5";
    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));
  }
}