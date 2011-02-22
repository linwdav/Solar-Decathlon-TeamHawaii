package edu.hawaii.ihale.ui.ajax;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * This AJAX behavior is called when the database updates. Essentially, the
 * AjaxDatabaseUpdate.onRequest() should be called within the SystemStateListener.entryAdded()
 * method.
 * 
 * @author Bret Ikehara
 */
public class AjaxDatabaseUpdate extends AjaxEventBehavior {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 6914967669316226192L;

  /**
   * Default Constructor.
   */
  public AjaxDatabaseUpdate() {
    super("onDatabaseUpdate");
  }

  /**
   * Updates the target when the database updates.
   * 
   * @param target AjaxRequestTarget
   */
  @Override
  protected void onEvent(AjaxRequestTarget target) {
    target.addComponent(this.getComponent());
  }
}