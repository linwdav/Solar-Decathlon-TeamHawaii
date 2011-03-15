package edu.hawaii.ihale.frontend.page.lighting;

import edu.hawaii.ihale.frontend.SolarDecathlonSession;
import edu.hawaii.ihale.frontend.page.Header;

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
    // More to come
    ((SolarDecathlonSession)getSession()).getHeaderSession().setActiveTab(3);

  }

}
