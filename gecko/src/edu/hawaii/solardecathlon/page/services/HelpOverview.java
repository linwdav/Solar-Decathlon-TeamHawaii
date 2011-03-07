package edu.hawaii.solardecathlon.page.services;

import org.apache.wicket.markup.html.basic.Label;

/**
 * The help overview panel.
 * 
 * @author Bret K. Ikehara
 */
public class HelpOverview extends HelpPanel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -696751701332684442L;

  /**
   * Default Constructor.
   * 
   * @param id String
   */
  public HelpOverview(String id) {
    super(id);
    
    add(new Label("title", "Overview"));
  }
}
