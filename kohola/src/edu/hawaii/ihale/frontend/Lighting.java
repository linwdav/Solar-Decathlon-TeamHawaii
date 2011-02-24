package edu.hawaii.ihale.frontend;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;

/**
 * The lighting page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class Lighting extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   */
  public Lighting() {

    // Add image
    add(new Image("lighting-graph", new ResourceReference(Header.class,
        "images/lighting-graph.png")));

  }

}
