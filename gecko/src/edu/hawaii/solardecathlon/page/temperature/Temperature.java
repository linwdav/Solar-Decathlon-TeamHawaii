package edu.hawaii.solardecathlon.page.temperature;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The temperature page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Temperature extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   */
  public Temperature() {

    add(new Image("tempY", new ResourceReference(BasePage.class, "images/tempY.png")));
    add(new Image("tempM", new ResourceReference(BasePage.class, "images/tempM.png")));
    add(new Image("tempW", new ResourceReference(BasePage.class, "images/tempW.png")));
    add(new Image("tempD", new ResourceReference(BasePage.class, "images/tempD.png")));

  }

}
