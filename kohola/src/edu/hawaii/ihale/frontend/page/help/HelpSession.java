package edu.hawaii.ihale.frontend.page.help;

import java.io.Serializable;

/**
 * The lighting sessions page.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class HelpSession implements Serializable {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  private int currentTab;
  
  /**
   * empty for now.
   */
  public HelpSession() {
    //empty
  }
  
  /**
   * Returns the current tab associated with the users session.
   * @return The current tab.
   */
  public int getCurrentTab() {
    return currentTab;
  }
  
  /**
   * Sets the current tab for the user's session.
   * @param currentTab - the tab the user selected.
   */
  public void setCurrentTab(int currentTab) {
    this.currentTab = currentTab;
  }
  
}
