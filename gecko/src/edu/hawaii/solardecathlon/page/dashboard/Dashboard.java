package edu.hawaii.solardecathlon.page.dashboard;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The energy page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Dashboard extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The page layout.
   */
  public Dashboard() {
    // Temporary Images
    add(new Image("weather", new ResourceReference(BasePage.class, "images/weather.jpg")));
    add(new Image("graph", new ResourceReference(BasePage.class, "images/graph.jpg")));
    add(new Image("consprodD", new ResourceReference(BasePage.class, "images/consprodD.png")));
    add(new Image("consprodW", new ResourceReference(BasePage.class, "images/consprodW.png")));
    add(new Image("consprodM", new ResourceReference(BasePage.class, "images/consprodM.png")));
    add(new Image("consprodY", new ResourceReference(BasePage.class, "images/consprodY.png")));

    add(new Image("lighting-graph", new ResourceReference(BasePage.class,
        "images/lighting-graph.png")));

  }

}
