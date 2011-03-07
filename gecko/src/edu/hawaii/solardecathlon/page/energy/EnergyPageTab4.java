package edu.hawaii.solardecathlon.page.energy;

import org.apache.wicket.markup.html.panel.Panel;
import edu.hawaii.solardecathlon.components.ExternalImageUrl;

/**
 * Creates a tab for the tabbed panels.
 * 
 * @author Bret K. Ikehara
 */
public class EnergyPageTab4 extends Panel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -952062919313050604L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public EnergyPageTab4(String id) {
    super(id);

    String graphUrl =
        "http://chart.apis.google.com/chart?chxl=1:|Mon|Tues|Wed|Thurs|Fri|Sat|Sun&chxs=0,"
            + "676767,11.5,0,lt,676767|1,676767,11.5,0,lt,676767&chxtc=1,15&chxt=y,x&chs=500x350"
            + "&cht=lc&chco=DA3B15,008000&chd=s:QMNRVekpnlgfW,somedabelprmnr&chdl=Power+"
            + "Consumption+(kWh)|Power+Production+(kWh)&chdlp=t&chg=16.67,10&chls=3|2"
            + "&chts=676767,16.5";

    add(new ExternalImageUrl("graph", graphUrl, "500", "350"));
  }

}
