package edu.hawaii.solardecathlon.page.security;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The security recordings page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class SecurityRec extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Layout of page.
   */
  public SecurityRec() {
    add(new Image("control", new ResourceReference(BasePage.class, "images/control.png")));
  }

}
