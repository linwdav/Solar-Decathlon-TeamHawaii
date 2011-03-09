package edu.hawaii.ihale.frontend;

import java.text.DecimalFormat;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

//import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * The aquaponics page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaPonics extends Header {

  private static final String boxTagName = "box";
  private static final String classTagName = "class";
  private static final String styleTagName = "style";

  // labels to store div colors
  private Label tempColorLabel;
  private Label phColorLabel;
  private Label oxygenColorLabel;
  
  // the warning and alert messages
  private static final String ALERT_MESSAGE = "<font color=\"red\">(ALERT)</font>";
  private static final String WARNG_MESSAGE = "<font color=\"#FF9900\">(WARNG)</font>";

  // recommended values for aquaponics
  private static final long TEMPERATURE_RANGE_START = 55;
  private static final long TEMPERATURE_RANGE_END = 65;
  private static final double PH_RANGE_START = 6.5;
  private static final double PH_RANGE_END = 7.5;
  private static final double PH_ACCEPTED_DIFFERENCE = 0.2;
  private static final double OXYGEN_RANGE_START = 4.50;
  private static final double OXYGEN_RANGE_END = 5.50;
  private static final double OXYGEN_ACCEPTED_DIFFERENCE = 0.2;
  
  // labels for recommended values
  private static final Label recommendedTempLabel = new Label("RecommendedTempLabel",
      TEMPERATURE_RANGE_START + "&deg;F - " + TEMPERATURE_RANGE_END + "&deg;F");

  private static final Label recommendedPHLabel = new Label("RecommendedPHLabel", PH_RANGE_START
      + " - " + PH_RANGE_END);

  private static final Label recommendedOxygenLabel = new Label("RecommendedOxygenLabel",
      OXYGEN_RANGE_START + " - " + OXYGEN_RANGE_END);

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer graph = new WebMarkupContainer("graphImage");

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   * 
   * @throws Exception the exception.
   */
  public AquaPonics() throws Exception {

    // model for the temperature feedback (WARNG or ALERT) label on page.
    Model<String> tempStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        long value = SolarDecathlonApplication.getAquaponics().getTemp();
        String status;
        if (value == TEMPERATURE_RANGE_START || value == TEMPERATURE_RANGE_END) {
          status = WARNG_MESSAGE;
        }
        else if (value < TEMPERATURE_RANGE_START || value > TEMPERATURE_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = "";
        }
        return status;
      }
    };
    // put the temp model in temp label
    Label tempStatusLabel = new Label("tempStatus", tempStatusModel);

    // the model for the actual temp value
    Model<String> temp = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getTemp());
      }
    };

    // model for the pH feedback (WARNG or ALERT) label on page.
    Model<String> pHStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getPH();
        String status;
        if (Math.abs(value - PH_RANGE_START) < PH_ACCEPTED_DIFFERENCE
            || Math.abs(value - PH_RANGE_END) < PH_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < PH_RANGE_START || value > PH_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = "";
        }
        return status;
      }
    };

    // put the ph model in ph label
    Label phStatusLabel = new Label("PhStatus", pHStatusModel);

    // model for the actual ph value
    Model<String> ph = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getPH());
      }
    };

    // model for the oxygen feedback (WARNG or ALERT) label on page.
    Model<String> oxygenStatusModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        // set the text and color according to the value
        double value = SolarDecathlonApplication.getAquaponics().getOxygen();
        String status;
        if (Math.abs(value - OXYGEN_RANGE_START) < OXYGEN_ACCEPTED_DIFFERENCE
            || Math.abs(value - OXYGEN_RANGE_END) < OXYGEN_ACCEPTED_DIFFERENCE) {
          status = WARNG_MESSAGE;
        }
        else if (value < OXYGEN_RANGE_START || value > OXYGEN_RANGE_END) {
          status = ALERT_MESSAGE;
        }
        else {
          status = "";
        }
        return status;
      }
    };

    // put oxygen model in oxygen label
    Label oxygenStatusLabel = new Label("OxygenStatus", oxygenStatusModel);

    // the model for the actual oxygen value
    Model<String> oxygen = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return String.valueOf(SolarDecathlonApplication.getAquaponics().getOxygen());
      }
    };
    
    // make tags to be recognizable in html
    tempStatusLabel.setEscapeModelStrings(false);
    phStatusLabel.setEscapeModelStrings(false);
    oxygenStatusLabel.setEscapeModelStrings(false);

    Link<String> statsButton = new Link<String>("statsButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        try {
          setResponsePage(new AquaponicsStats());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    // color for current temp div
    Model<String> tempColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        long value = SolarDecathlonApplication.getAquaponics().getTemp();
        String color;
        if (value == TEMPERATURE_RANGE_END || value == TEMPERATURE_RANGE_START) {
          color = "background-color:#FF9900;";
        }
        else if (value < TEMPERATURE_RANGE_START || value > TEMPERATURE_RANGE_END) {
          color = "background-color:red;";
        }
        else {
          color = "background-color:green;";
        }
        return color;
      }
    };
    tempColorLabel = new Label("tempColor", tempColorModel);

    // color for current ph div
    Model<String> phColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getPH();
        String color;       
        if (Math.abs(value - PH_RANGE_START) < PH_ACCEPTED_DIFFERENCE
            || Math.abs(value - PH_RANGE_END) < PH_ACCEPTED_DIFFERENCE) {
          color = "background-color:#FF9900;";
        }
        else if (value < PH_RANGE_START || value > PH_RANGE_END) {
          color = "background-color:red;";
        }
        else {
          color = "background-color:green;";
        }
        return color;
      }
    };
    phColorLabel = new Label("phColor", phColorModel);

    // color for current oxygen div
    Model<String> oxygenColorModel = new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        double value = SolarDecathlonApplication.getAquaponics().getOxygen();
        String color;
        if (Math.abs(value - OXYGEN_RANGE_START) < OXYGEN_ACCEPTED_DIFFERENCE
            || Math.abs(value - OXYGEN_RANGE_END) < OXYGEN_ACCEPTED_DIFFERENCE) {
          color = "background-color:#FF9900;";
        }       
        else if (value < OXYGEN_RANGE_START || value > OXYGEN_RANGE_END) {
          color = "background-color:red;";
        }
        else {
          color = "background-color:green;";
        }
        return color;
      }
    };
    oxygenColorLabel = new Label("OxygenColor", oxygenColorModel);

    // Add button to switch to statistics view
    add(statsButton);

    // create divs for temperature according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup tempDiv =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv");

    WebMarkupContainerWithAssociatedMarkup tempDiv2 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv2");

    WebMarkupContainerWithAssociatedMarkup tempDiv3 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv3");

    WebMarkupContainerWithAssociatedMarkup tempDiv4 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv4");

    tempDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, tempColorLabel.getDefaultModelObjectAsString());
      }
    });
 
    tempDiv2.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, "padding-bottom:0;");
      }
    });

    tempDiv3.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "column-three");
      }
    });

    tempDiv4.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "column-three-two");
      }
    });

    tempDiv2.add(tempDiv3);

    tempDiv4.add(new Label("Temp", temp));
    tempDiv4.add(tempStatusLabel);

    tempDiv2.add(tempDiv4);
    tempDiv.add(tempDiv2);
    add(tempDiv);

    // add recommended temp range label to page
    recommendedTempLabel.setEscapeModelStrings(false);
    add(recommendedTempLabel);

 // create divs for ph according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup phInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhInnerDiv");

    WebMarkupContainerWithAssociatedMarkup phOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhOuterDiv");

    phInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    phOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, phColorLabel.getDefaultModelObjectAsString());
      }
    });

    phInnerDiv.add(new Label("PH", ph));
    phInnerDiv.add(phStatusLabel);
    phOuterDiv.add(phInnerDiv);
    add(phOuterDiv);

    // add recommended pH range label to page
    recommendedPHLabel.setEscapeModelStrings(false);
    add(recommendedPHLabel);

    // create divs for oxygen according to the hierarchy in html
    WebMarkupContainerWithAssociatedMarkup oxygenInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("OxygenInnerDiv");

    WebMarkupContainerWithAssociatedMarkup oxygenOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("OxygenOuterDiv");

    oxygenInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    oxygenOuterDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, oxygenColorLabel.getDefaultModelObjectAsString());
      }
    });

    oxygenInnerDiv.add(new Label("Oxygen", oxygen));
    oxygenInnerDiv.add(oxygenStatusLabel);
    oxygenOuterDiv.add(oxygenInnerDiv);
    add(oxygenOuterDiv);

    // add recommended oxygen range label to page
    recommendedOxygenLabel.setEscapeModelStrings(false);
    add(recommendedOxygenLabel);

    // Add Graph of Water Quality to page
    add(graph);
    displayDayGraph(graph);

  }

  /**
   * Determines which graph to display.
   * 
   * @param wmc web component to display graph in
   **/
  private void displayDayGraph(WebMarkupContainer wmc) {
    String graphURL;
    DecimalFormat df = new DecimalFormat("#");
    String tempValue = String.valueOf(SolarDecathlonApplication.getAquaponics().getTemp());
    String phValue = df.format(SolarDecathlonApplication.getAquaponics().getPH());
    String oxygenValue = df.format(SolarDecathlonApplication.getAquaponics().getOxygen());
    graphURL =
        "http://chart.apis.google.com/chart" + "?chxl=0:|Temp|pH|Oxygen" + "&chxt=x,y"
            + "&chbh=a,5,15" + "&chs=300x200" + "&cht=bvg" + "&chco=008000,FF9900"
            + "&chd=t:73,6.8,5|" + tempValue + "," + phValue + "," + oxygenValue
            + "&chdl=Recommended|Actual" + "&chdlp=t";
    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));

  }
}