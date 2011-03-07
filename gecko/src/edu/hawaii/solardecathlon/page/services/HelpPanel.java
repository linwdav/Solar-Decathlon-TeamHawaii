package edu.hawaii.solardecathlon.page.services;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * An abstract class to allow the help panel content to be displayed by extending this class.
 * 
 * @author Bret K. Ikehara
 */
public abstract class HelpPanel extends Panel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 5338125210996189481L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public HelpPanel(String id) {
    super(id);
  }
}
