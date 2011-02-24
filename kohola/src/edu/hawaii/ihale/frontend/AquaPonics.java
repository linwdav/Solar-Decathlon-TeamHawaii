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
 */
public class AquaPonics extends Header {

  private static final String boxTagName = "box";
  private static final String classTagName = "class";
  private static final String styleTagName = "style";
  
  private static final String ALERT_MESSAGE = "<font color=\"red\">(ALERT)</font>";
  private static final String WARNG_MESSAGE =  "<font color=\"#FF9900\">(WARNG)</font>";

  /**
   * MarkupContainer for all graphs.
   */
  WebMarkupContainer graph = new WebMarkupContainer("graphImage");
 
  //static Label waterLevel = new Label("WaterLevel", "");
  static Label airTemp = new Label("AirTemp", "0");
  static Label waterTemp = new Label("WaterTemp", "0");
  static Label ph = new Label("PH", "0");
  static Label ec = new Label("EC", "0");
  
  static Label airTempStatus = new Label("AirTempStatus", "");
  static Label waterTempStatus = new Label("WaterTempStatus", "");
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
    
    airTempStatus.setEscapeModelStrings(false);
    waterTempStatus.setEscapeModelStrings(false);
    phStatus.setEscapeModelStrings(false);
    ecStatus.setEscapeModelStrings(false);

//    SystemStateEntryDB db = ((SolarDecathlonApplication) SolarDecathlonApplication.get()).getDB();
//
//    // Add listeners to the system. Listeners are the way the UI learns that new state has
//    // been received from some system in the house.
//    db.addSystemStateListener(((SolarDecathlonApplication) SolarDecathlonApplication.get())
//        .getAquaponicsListener());
//
//    new BlackMagic(db);
  
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
    // SystemStateEntry entry = db.getEntry("Aquaponics", "Arduino-23", 11);

    WebMarkupContainerWithAssociatedMarkup tempDiv =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv");

    WebMarkupContainerWithAssociatedMarkup tempDiv2 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv2");

    WebMarkupContainerWithAssociatedMarkup tempDiv3 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv3");

    WebMarkupContainerWithAssociatedMarkup tempDiv4 =
        new WebMarkupContainerWithAssociatedMarkup("TempDiv4");

    if (Long.parseLong((String) airTemp.getDefaultModelObject()) > 77
        || Long.parseLong((String) airTemp.getDefaultModelObject()) < 70
        || Long.parseLong((String) waterTemp.getDefaultModelObject()) > 65
        || Long.parseLong((String) waterTemp.getDefaultModelObject()) < 55) {

      tempDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:red");
        }
      });
    }
    else if (Long.parseLong((String) airTemp.getDefaultModelObject()) == 77
        || Long.parseLong((String) airTemp.getDefaultModelObject()) == 70
        || Long.parseLong((String) waterTemp.getDefaultModelObject()) == 65
        || Long.parseLong((String) waterTemp.getDefaultModelObject()) == 55) {
      tempDiv.add(new AbstractBehavior() {

        /**
         * testing.
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
         * testing.
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
       * testing.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, boxTagName);
        tag.put(styleTagName, "padding-bottom:0;");
      }
    });

    tempDiv3.add(new AbstractBehavior() {

      /**
       * testing.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "column-three");
      }
    });

    tempDiv4.add(new AbstractBehavior() {

      /**
       * testing.
       */
      private static final long serialVersionUID = 1L;

      public void onComponentTag(Component component, ComponentTag tag) {
        tag.put(classTagName, "column-three-two");
      }
    });

    tempDiv2.add(tempDiv3);

    tempDiv4.add(airTemp);
    tempDiv4.add(airTempStatus);
    tempDiv4.add(waterTemp);
    tempDiv4.add(waterTempStatus);

    tempDiv2.add(tempDiv4);
    tempDiv.add(tempDiv2);
    add(tempDiv);

    // if (Double.parseDouble((String) ph.getDefaultModelObject()) > 7
    // || Double.parseDouble((String) ph.getDefaultModelObject()) < 6.5) {
    //
    // phDiv.add(new AbstractBehavior() {
    //
    // /**
    // * testing.
    // */
    // private static final long serialVersionUID = 1L;
    //
    // public void onComponentTag(Component component, ComponentTag tag) {
    // tag.put("class", "box");
    // tag.put("style", "background-color:red");
    // }
    // });
    // }
    // else if (Double.parseDouble((String) ph.getDefaultModelObject()) == 7
    // || Double.parseDouble((String) ph.getDefaultModelObject()) == 6.5) {
    // phDiv.add(new AbstractBehavior() {
    //
    // /**
    // * testing.
    // */
    // private static final long serialVersionUID = 1L;
    //
    // public void onComponentTag(Component component, ComponentTag tag) {
    // tag.put("class", "box");
    // tag.put("style", "background-color:#FF9900;");
    // }
    // });
    // }
    // else {
    // phDiv.add(new AbstractBehavior() {
    //
    // /**
    // * testing.
    // */
    // private static final long serialVersionUID = 1L;
    //
    // public void onComponentTag(Component component, ComponentTag tag) {
    // tag.put("class", "box");
    // tag.put("style", "background-color:green");
    // }
    // });
    // }
    //
    // phDiv2.add(new AbstractBehavior() {
    //
    // /**
    // * testing.
    // */
    // private static final long serialVersionUID = 1L;
    //
    // public void onComponentTag(Component component, ComponentTag tag) {
    // tag.put("class", "box");
    // }
    // });
    //
    // phDiv2.add(ph);
    // phDiv.add(phDiv2);

    WebMarkupContainerWithAssociatedMarkup phInnerDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhInnerDiv");

    WebMarkupContainerWithAssociatedMarkup phOuterDiv =
        new WebMarkupContainerWithAssociatedMarkup("PhOuterDiv");

    phInnerDiv.add(new AbstractBehavior() {

      /**
       * testing.
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
      //String original = (String)ph.getDefaultModelObject();
      //String warningMsg = "<font color=\"#FF9900\">(WARNG)</font>";
      //ph.setDefaultModelObject(original + warningMsg);
      phOuterDiv.add(new AbstractBehavior() {

        /**
         * testing.
         */
        private static final long serialVersionUID = 1L;

        public void onComponentTag(Component component, ComponentTag tag) {
          tag.put(classTagName, boxTagName);
          tag.put(styleTagName, "background-color:#FF9900;");
        }
      });
    }
    else {
      //String original = (String) ph.getDefaultModelObject();
      //String alertMsg = original + "<font color=\"red\">(ALERT)</font>";      
      //ph.setDefaultModelObject(String.valueOf(alertMsg));
      
      phOuterDiv.add(new AbstractBehavior() {

        /**
         * testing.
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
    
    //////////////////////////////////////////////////////

    
    WebMarkupContainerWithAssociatedMarkup ecInnerDiv =
      new WebMarkupContainerWithAssociatedMarkup("EcInnerDiv");

  WebMarkupContainerWithAssociatedMarkup ecOuterDiv =
      new WebMarkupContainerWithAssociatedMarkup("EcOuterDiv");

  ecInnerDiv.add(new AbstractBehavior() {

    /**
     * testing.
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
       * testing.
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
       * testing.
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
       * testing.
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
    
    
    
    //add(ec);

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
    String airValue = String.valueOf((String) airTemp.getDefaultModelObject());
    String waterValue = String.valueOf((String) waterTemp.getDefaultModelObject());
    String phValue = df.format(Double.parseDouble((String) ph.getDefaultModelObject()));
    String ecValue = df.format(Double.parseDouble((String) ec.getDefaultModelObject()));
    graphURL =
      "http://chart.apis.google.com/chart" +
        "?chxl=0:|Air+Temp|Water+Temp|pH|EC" +
        "&chxt=x,y" +
        "&chbh=a,5,15" +
        "&chs=300x200" +
        "&cht=bvg" +
        "&chco=008000,FF9900" +
        "&chd=t:73,60,6.8,2|" + airValue + "," + waterValue + "," + phValue + "," + ecValue +
        "&chdl=Recommended|Actual" +
        "&chdlp=t";
    wmc.add(new AttributeModifier("src", true, new Model<String>(graphURL)));

  }

  /**
   * Set the air temperature label on this page.
   * 
   * @param value The air temperature.
   */
  public static void setAirTemp(Long value) {
    airTemp.setDefaultModelObject(String.valueOf(value));
    
    if (value == 70 || value == 77) {
      airTempStatus.setDefaultModelObject(WARNG_MESSAGE);
    }
    else if (value < 70 || value > 77) {
      airTempStatus.setDefaultModelObject(ALERT_MESSAGE);
    }
    else {
      airTempStatus.setDefaultModelObject("");
    }
  }

  /**
   * Set the water temperature label on this page.
   * 
   * @param value The water temperature.
   */
  public static void setWaterTemp(Long value) {
    waterTemp.setDefaultModelObject(String.valueOf(value));
    
    if (value == 55 || value == 65) {
      waterTempStatus.setDefaultModelObject(WARNG_MESSAGE);
    }
    else if (value < 55 || value > 65) {
      waterTempStatus.setDefaultModelObject(ALERT_MESSAGE);
    }
    else {
      waterTempStatus.setDefaultModelObject("");
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
