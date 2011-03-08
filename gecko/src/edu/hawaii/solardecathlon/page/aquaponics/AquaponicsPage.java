package edu.hawaii.solardecathlon.page.aquaponics;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.SolarDecathlonApplication;
import edu.hawaii.solardecathlon.listeners.AquaponicsListener;
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

  private transient AquaponicsListener listener;

  /**
   * Default Constructor.
   */
  public AquaponicsPage() {

    listener = SolarDecathlonApplication.getAquaponicsListener();

    statusPanelReference();
    resourceReference();
    linkReference();
    graphReference();
    tempControlReference();
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
   * Adds the temperature control form to the page.
   */
  private void tempControlReference() {

    Form<String> form = new Form<String>("tempControlForm");
    add(form);

    final TextField<String> amountWater =
        new TextField<String>("amountWater", new Model<String>() {

          /**
           * Serial ID.
           */
          private static final long serialVersionUID = 8698214966288274380L;

          /**
           * Gets the formatted water temperature.
           * 
           * @return String
           */
          @Override
          public String getObject() {
            return listener.getTemp() + "°F";
          }
        });
    amountWater.add(new AjaxFormComponentUpdatingBehavior("onchange") {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 7819370842486167894L;

      /**
       * Updates the model when the value is changed on screen.
       */
      @Override
      protected void onUpdate(AjaxRequestTarget target) {

        Long temp =
            Long.valueOf(amountWater.getValue().substring(0, amountWater.getValue().length() - 2));

        // Update panel's value
        target.addComponent(amountWater);

        // Send information to the backend.
        List<String> args = new ArrayList<String>();
        args.add(temp.toString());
        dao.doCommand("aquaponics", "arduino-2", "setTemp", args);
      }
    });
    form.add(amountWater);
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