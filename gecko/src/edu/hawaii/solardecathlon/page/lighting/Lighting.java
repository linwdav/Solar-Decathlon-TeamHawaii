package edu.hawaii.solardecathlon.page.lighting;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The lighting page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Lighting extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   */
  public Lighting() {

    // Add image
    add(new Image("lighting-graph", new ResourceReference(BasePage.class,
        "images/lighting-graph.png")));

  }

}
