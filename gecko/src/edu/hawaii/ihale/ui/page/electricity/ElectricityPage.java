package edu.hawaii.ihale.ui.page.electricity;

import java.util.ArrayList;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.ExternalImageUrl;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * Electricity page.
 * 
 * @author Team 1
 */
public class ElectricityPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the electricity page.
   */
  public ElectricityPage() {

    ArrayList<OverAllStatus> overAllStatusList = new ArrayList<OverAllStatus>();

    overAllStatusList.add(new OverAllStatus("Production", new ResourceReference(
        OverAllStatus.class, OverAllStatus.STATUSGOOD)));
    overAllStatusList.add(new OverAllStatus("Consumption", new ResourceReference(
        OverAllStatus.class, OverAllStatus.STATUSCAUTION)));

    StringBuilder sb = new StringBuilder();
    sb.append("http://chart.apis.google.com/chart?");
    sb.append("chxr=0,0,24|1,5,100");
    sb.append('&');
    sb.append("chxs=0,676767,11.5,0,lt,676767|1,676767,10.5,0.5,lt,676767");
    sb.append('&');
    sb.append("chxt=x,y");
    sb.append('&');
    sb.append("chs=662x300");
    sb.append('&');
    sb.append("cht=lc");
    sb.append('&');
    sb.append("chco=3CBE3C,BE3C3E");
    sb.append('&');
    sb.append("chd=t:43.639,44.073,52.319,71.329,77.997,78.883,83.439,82.417,76.953,72.877,");
    sb.append("75.554,79.645,77.235,75.051,70.665,77.312,78.665,72.397,56.226,53.377,45.083,");
    sb.append("33.719,30.276,32.514|43.64,50.716,53.246,62.696,65.375,66.178,65.581,72.556,");
    sb.append("71.847,77.335,77.872,70.438,70.741,67.464,68.479,63.328,64.568,66.728,64.341,");
    sb.append("58.768,52.544,43.929,42.002,32.839");
    sb.append('&');
    sb.append("chdl=Generation|Consumption");
    sb.append('&');
    sb.append("chdlp=b");
    sb.append('&');
    sb.append("chls=1|1");
    sb.append('&');
    sb.append("chm=B,EFEFEF,0,0,0,-1");

    ArrayList<Electricity> electricityList = new ArrayList<Electricity>();
    electricityList.add(new Electricity("Aquaponics", 10, 100, 1000));

    add(CSSPackageResource.getHeaderContribution(Electricity.class, "style.css"));

    add(new OverAllStatusPanel("Status", new Model<String>("ElectricityStatus"),
        overAllStatusList));

    add(new ExternalImageUrl("ElecGraph", sb.toString(), "662", "300"));

    add(new ListView<Electricity>("ElecStatus", electricityList) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 1L;

      /**
       * Populates the Electricity details page.
       */
      @Override
      protected void populateItem(ListItem<Electricity> item) {
        Electricity elec = item.getModelObject();

        item.add(new Label("ElecApp", elec.getAppliance()));
        item.add(new Label("ElecUsage", String.valueOf(elec.getUsage())));
        item.add(new Label("ElecConsump", String.valueOf(elec.getUsage())));
        item.add(new Label("ElecLimit", String.valueOf(elec.getUsage())));
      }
    });
  }
}
