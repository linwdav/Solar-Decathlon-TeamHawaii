package edu.hawaii.ihale.ui.page.waterHeater;

import java.util.ArrayList;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.ExternalImageUrl;
import edu.hawaii.ihale.ui.page.electricity.Electricity;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * Water Heater Page.
 * 
 * @author Team 1
 */
public class WaterHeaterPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a Water Heater Page.
   */
  public WaterHeaterPage() {

    ArrayList<OverAllStatus> overAllStatusList = new ArrayList<OverAllStatus>();

    overAllStatusList.add(new OverAllStatus("Status", new ResourceReference(OverAllStatus.class,
        OverAllStatus.STATUSCAUTION)));

    overAllStatusList.add(new OverAllStatus("Current Level", "50 g"));
    
    overAllStatusList.add(new OverAllStatus("Average Usage", "10 g/hr"));

    overAllStatusList.add(new OverAllStatus("Temperature", "150 f"));
    
    StringBuilder sb = new StringBuilder();
    sb.append("http://chart.apis.google.com/chart?");
    sb.append("chxt=y,x").append('&');
    sb.append("chbh=a").append('&');
    sb.append("chs=662x300").append('&');
    sb.append("cht=bvs").append('&');
    sb.append("chco=76A4FB,FFCC33").append('&');
    sb.append("chd=t:100,100,100,80,80,80,70,50,0,0,0,0,0,0,0,0,0,0,0,0,0");
    sb.append(",0,0,0|0,0,0,20,0,0,10,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").append('&');
    sb.append("chma=|15").append('&');
    sb.append("chdlp=b").append('&');
    sb.append("chdl=Water+Level+(gallon)|Usage");

    add(new OverAllStatusPanel("Status", new Model<String>("Status"), overAllStatusList));

    add(CSSPackageResource.getHeaderContribution(Electricity.class, "style.css"));

    add(new ExternalImageUrl("WaterHeaterGraph", sb.toString(), "662", "300"));
  }
}
