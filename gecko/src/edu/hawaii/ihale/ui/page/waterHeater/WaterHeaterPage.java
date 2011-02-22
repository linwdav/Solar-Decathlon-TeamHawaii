package edu.hawaii.ihale.ui.page.waterHeater;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.wicket.url.ExternalImageUrl;
/**
 * Water Heater Page.
 * 
 * @author Shoji Bravo
 * @author Bret K. Ikehara
 */
public class WaterHeaterPage extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a Water Heater Page.
   */
  public WaterHeaterPage() {
    
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

    add(CSSPackageResource.getHeaderContribution(WaterHeaterPage.class, "style.css"));

    add(new ExternalImageUrl("WaterHeaterGraph", sb.toString(), "662", "300"));
    
    add(new Label("Status"));
  }
}
