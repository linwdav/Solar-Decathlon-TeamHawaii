package edu.hawaii.solardecathlon.page.aquaponics;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
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

  /**
   * Layout of page.
   */
  public Aquaponics() {

    // Button at top of page
    Link<String> statsButton = new Link<String>("statsButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        setResponsePage(new AquaponicsStats());
      }
    };

    // Add button to switch to statistics view
    add(statsButton);

    // Add Graph of Water Quality to page
    add(graph);
    displayDayGraph(graph);

    Label phLabel = new Label("ph", new Model<Double>() {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 6781493972632850218L;

      /**
       * Gets the Aquaponics's ph.
       */
      @Override
      public Double getObject() {
        return 0.0;
      }
    });
    phLabel.setOutputMarkupId(true);
    add(phLabel);
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
