package edu.hawaii.ihale.ui.page.service;

import edu.hawaii.ihale.ui.page.BasePage;


/**
 * Invalidates the user's session.
 * 
 * @author Bret K. Ikehara
 */
public class LogOutPage extends BasePage {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Will Invalidate user's session, then redirect to login page.
   */
  public LogOutPage() {
    this.getSession().invalidate();
    getRequestCycle().setRedirect(true);
    setResponsePage(LogInPage.class);
  }
}
