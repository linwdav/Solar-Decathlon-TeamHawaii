package edu.hawaii.ihale.frontend.page.security;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import edu.hawaii.ihale.frontend.page.Header;

/**
 * The security recordings page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class SecurityRec extends Header {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   */
  public SecurityRec() {
    add(new Image("control", new ResourceReference(Header.class, "images/control.png")));
  }

}
