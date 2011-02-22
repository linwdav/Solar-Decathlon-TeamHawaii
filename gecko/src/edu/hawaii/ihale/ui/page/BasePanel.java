package edu.hawaii.ihale.ui.page;

import java.util.Map;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.SolarDecathlonSession;

/**
 * Extends the base page class to create the home page.
 * 
 * @author Bret I. Ikehara
 *
 */
public class BasePanel extends Panel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;
  
  protected SolarDecathlonSession session;

  /**
   * Handles the content for main portion of the Main page.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> BasePanel(String name, IModel<T> model) {
    super(name, model);
  }
  
  /**
   * Handles the content for main portion of the Main page.
   * 
   * @param name String
   */
  public BasePanel(String name) {
    super(name);
  }
  
  /**
   * Sets up the session variable.
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    
    if (session == null) {
      session = (SolarDecathlonSession) getSession();
    }
  }

  /**
   * Returns the property map associated with this user's session.
   * @return The session's property map.
   */
  public Map<String, String> getSessionProperties() {
    return ((SolarDecathlonSession)getSession()).getProperties();
  }
  
  /**
   * Returns the session property value.
   * 
   * @param key String
   * @return String
   */
  public String getSessionProperty(String key) {
    return this.getSessionProperties().get(key);
  }
}