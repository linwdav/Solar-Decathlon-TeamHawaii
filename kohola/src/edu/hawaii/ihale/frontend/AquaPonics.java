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

  private static final String ALERT_MESSAGE = "<font color=\"red\">(ALERT)</font>";
  private static final String WARNG_MESSAGE = "<font color=\"#FF9900\">(WARNG)</font>";

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer graph = new WebMarkupContainer("graphImage");

  static Label temp = new Label("Temp", "0");
  static Label ph = new Label("PH", "0");
  static Label ec = new Label("EC", "0");

  static Label tempStatus = new Label("tempStatus", "");
  static Label phStatus = new Label("PhStatus", "");
  static Label ecStatus = new Label("EcStatus", "");

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   * 
   * @throws Exception the exception.
   */
  public AquaPonics() throws Exception {

    tempStatus.setEscapeModelStrings(false);
    phStatus.setEscapeModelStrings(false);
    ecStatus.setEscapeModelStrings(false);

    // SystemStateEntryDB db = ((SolarDecathlonApplication)
    // SolarDecathlonApplication.get()).getDB();
    //
    // // Add listeners to the system. Listeners are the way the UI learns that new state has
    // // been received from some system in the house.
    // db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
    // .getAquaponicsListener());
    //
    // new BlackMagic(db);

    Link<String> statsButton = new Link<String>("statsButton") {
      private static final long serialVersionUID = 1L;

      /** Upon clicking this link, go to AquaponicsStatsPage. */
      @Override
      public void onClick() {
        try {
          setResponsePage(new AquaponicsStats());
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    };

    // Add button to switch to statistics view
    add(statsButton);

    WebMarkupContainerWithAssociatedMarkup tempDiv =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv");

    WebMarkupContainerWithAssociatedMarkup tempDiv2 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv2");

    WebMarkupContainerWithAssociatedMarkup tempDiv3 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv3");

    WebMarkupContainerWithAssociatedMarkup tempDiv4 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv4");

    if (Long.parseLong((String) temp.getDefaultModelObject()) > 65
        || Long.parseLong((String) temp.getDefaultModelObject()) < 55) {

      tempDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:red");
        }
      });
    }
    else if (Long.parseLong((String) temp.getDefaultModelObject()) == 65
        || Long.parseLong((String) temp.getDefaultModelObject()) == 55) {
      tempDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:#FF9900;");
        }
      });
    }
    else {
      tempDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:green");
        }
      });
    }

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

    tempDiv4.add(temp);
    tempDiv4.add(tempStatus);

    tempDiv2.add(tempDiv4);
    tempDiv.add(tempDiv2);
    add(tempDiv);

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

    if (Double.parseDouble((String) ph.getDefaultModelObject()) < 7
        && Double.parseDouble((String) ph.getDefaultModelObject()) > 6.5) {
      phOuterDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:green");
        }
      });
    }
    else if (Double.parseDouble((String) ph.getDefaultModelObject()) == 7
        || Double.parseDouble((String) ph.getDefaultModelObject()) == 6.5) {
      phOuterDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:#FF9900;");
        }
      });
    }
    else {
      phOuterDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:red");
        }
      });
    }

    phInnerDiv.add(ph);
    phInnerDiv.add(phStatus);
    phOuterDiv.add(phInnerDiv);
    add(phOuterDiv);

    WebMarkupContainerWithAssociatedMarkup ecInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("EcInnerDiv");

    WebMarkupContainerWithAssociatedMarkup ecOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("EcOuterDiv");

    ecInnerDiv.add(new AbstractBehavior() {

      /**
       * Add attribute tags to the div.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
      }
    });

    if (Double.parseDouble((String) ec.getDefaultModelObject()) < 2.25
        && Double.parseDouble((String) ec.getDefaultModelObject()) > 1.75) {
      ecOuterDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:green");
        }
      });
    }
    else if (Double.parseDouble((String) ec.getDefaultModelObject()) == 1.75
        || Double.parseDouble((String) ec.getDefaultModelObject()) == 2.25) {
      ecOuterDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:#FF9900;");
        }
      });
    }
    else {
      ecOuterDiv.add(new AbstractBehavior() {

        /**
         * Add attribute tags to the div.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:red");
        }
      });
    }

    ecInnerDiv.add(ec);
    ecInnerDiv.add(ecStatus);
    ecOuterDiv.add(ecInnerDiv);
    add(ecOuterDiv);

    // add(ec);

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
    String tempValue = String.valueOf((String) temp.getDefaultModelObject());
    String phValue = df.format(Double.parseDouble((String) ph.getDefaultModelObject()));
    String ecValue = df.format(Double.parseDouble((String) ec.getDefaultModelObject()));
    graphURL =
        "http://chart.apis.google.com/chart" + "?chxl=0:|Temp|pH|EC" + "&chxt=x,y"
            + "&chbh=a,5,15" + "&chs=300x200" + "&cht=bvg" + "&chco=008000,FF9900"
            + "&chd=t:73,6.8,2|" + tempValue + "," + phValue + "," + ecValue
            + "&chdl=Recommended|Actual" + "&chdlp=t";
    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));

  }

  /**
   * Set the temperature label on this page.
   * 
   * @param value The temperature.
   */
  public static void setTemp(Long value) {
    temp.setDefaultModelObject(String.valueOf(value));

    if (value == 55 || value == 65) {
      tempStatus.setDefaultModelObject(WARNG_MESSAGE);
    }
    else if (value < 55 || value > 65) {
      tempStatus.setDefaultModelObject(ALERT_MESSAGE);
    }
    else {
      tempStatus.setDefaultModelObject("");
    }
  }

  /**
   * Set the ph value label on this page.
   * 
   * @param value The ph level.
   */
  public static void setPH(Double value) {
    DecimalFormat df = new DecimalFormat("#.##");
    ph.setDefaultModelObject(String.valueOf(df.format(value)));

    if (value == 6.5 || value == 7) {
      phStatus.setDefaultModelObject(WARNG_MESSAGE);
    }
    else if (value < 6.5 || value > 7) {
      phStatus.setDefaultModelObject(ALERT_MESSAGE);
    }
    else {
      phStatus.setDefaultModelObject("");
    }
  }

  /**
   * Set the electrical conductivity label on this page.
   * 
   * @param value The electrical conductivity.
   */
  public static void setEC(Double value) {
    DecimalFormat df = new DecimalFormat("#.##");
    ec.setDefaultModelObject(String.valueOf(df.format(value)));

    if ("1.75".equals(df.format(value)) || "2.25".equals(df.format(value))) {
      ecStatus.setDefaultModelObject(WARNG_MESSAGE);
    }
    else if (value < 1.75 || value > 2.25) {
      ecStatus.setDefaultModelObject(ALERT_MESSAGE);
    }
    else {
      ecStatus.setDefaultModelObject("");
    }
  }

}
