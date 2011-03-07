package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.components.AjaxUpdate;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The aquaponics page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Bret K. Ikehara
 */
public class AquaponicsPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 3L;

  private AjaxUpdate phEvent;

  private AquaponicsModel propModel;

  /**
   * Default Constructor.
   */
  public AquaponicsPage() {
    propModel = (AquaponicsModel) session.getModel("aquaponics");
    
    databaseReference();
    statusPanelReference();
    resourceReference();
    linkReference();
    graphReference();
  }
  
  /**
   * Reference to the database and its listeners.
   */
  private void databaseReference() {
    //Creates the update event.
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
        // Update the property model
        propModel.setTimestamp(entry.getTimestamp());
        propModel.setTemp(entry.getLongValue("temp"));
        propModel.setPh(entry.getDoubleValue("ph"));
        propModel.setOxygen(entry.getDoubleValue("oxygen"));

        // Update the ph panel data and styling.
        phEvent.onRequest();
      }
    });    
  }
  
  /**
   * References the status panels.
   */
  private void statusPanelReference() {
    // Create the temp with Ajax update event
    // TODO attach ajax event.
    TempPanel tempPanel = new TempPanel("tempPanel");
    add(tempPanel);
    
    TempRefPanel tempRefPanel = new TempRefPanel("tempRefPanel");
    add(tempRefPanel);

    // Create the phPanel with Ajax update event    
    PhPanel phStatus = new PhPanel("phPanel");
    phStatus.add(phEvent);
    add(phStatus);

    PhRefPanel phRefPanel = new PhRefPanel("phRefPanel");    
    add(phRefPanel);

    // Create the elec panel with Ajax update event.
    // TODO attach ajax event.
    ElecPanel elecPanel = new ElecPanel("elecPanel");
    add(elecPanel);

    ElecRefPanel elecRefPanel = new ElecRefPanel("elecRefPanel");
    add(elecRefPanel);
    
  }
  
  /**
   * References all the javascript, css, and images.
   */
  private void resourceReference() {

    // Add CSS references.
    add(CSSPackageResource.getHeaderContribution(SolarDecathlonApplication.class,
        "page/style/aquaponics.css", CSS_SCREEN));
  }
  
  /**
   * References all the links.
   */
  private void linkReference() {
    // Button at top of page
    Link<String> statsButton = new Link<String>("statsButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        setResponsePage(AquaponicsPage.class);
      }
    };
    add(statsButton);
    
  }

  /**
   * Determines which graph to display.
   **/
  private void graphReference() {
    
    /**
     * MarkupContainer for all graphs.
     */
    WebMarkupContainer graph = new WebMarkupContainer("graphImage");
    
    String graphURL =
        "http://chart.apis.google.com/chart?chxl=0:|pH|NH3|Bacteria|Temp|O2|AT&chxt=x&"
            + "chbh=a,5,15&chs=300x200&cht=bvg&chco=FF9900,008000&chd=s:cnwhZj,eYNkph&"
            + "chdl=Actual|Recommended&chdlp=t&chts=676767,13.5";
    graph.add(new AttributeModifier("src", true, new Model<String>(graphURL)));
    
    add(graph);
  }
}