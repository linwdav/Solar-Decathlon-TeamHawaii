package edu.hawaii.ihale.ui.page;

import java.io.Serializable;
import java.util.Map;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.SolarDecathlonSession;
import edu.hawaii.ihale.ui.page.service.LogInPage;
import edu.hawaii.ihale.ui.page.style.IEStylesheetHeaderContributor;

/**
 * A Base page that serves as the superclass for all pages in this application. All user-related
 * information must be set in the onBeforeRender method.
 * 
 * @author Bret Ikehara
 */
public abstract class BasePage extends WebPage implements Serializable {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  protected SolarDecathlonSession session;

  /**
   * The base page layout used by all pages. This includes a title and the links that appear in the
   * tabbed menu.
   */
  public BasePage() {
    // Add CSS definitions for use in all pages
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.ui.page.BasePage.class,
        "style/blueprint/screen.css", "screen"));
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.ui.page.BasePage.class,
        "style/blueprint/print.css", "print"));
    add(new IEStylesheetHeaderContributor(new ResourceReference(
        edu.hawaii.ihale.ui.page.BasePage.class, "style/blueprint/ie.css")));
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.ui.page.BasePage.class,
        "style/basepage.css"));
    add(CSSPackageResource.getHeaderContribution(edu.hawaii.ihale.ui.page.BasePage.class,
        "style/general.css"));

    add(new Label("title", "Solar Decathlon"));

    add(new Image("basePageImage", new ResourceReference(
        edu.hawaii.ihale.ui.page.BasePage.class, "logo.png")));
  }

  /**
   * <p>
   * Add new components before rendering.<br/>
   * This is used for the user name because when the constructor is created, we still don't have
   * access to getSessionProperty method. This will allow us to access session properties before
   * rendering the web page.
   * </p>
   */
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    if (session.isAuthenticated()) {
      add(new BasePageLogOutPanel("BaseLogOutPanel", new Model<String>("BaseLogOutPanel")));
      add(new BasePageMenuBarPanel("BasePageMenuBar", new Model<String>("BasePageMenuBar"),
          this.getClass()));
    }
    else {
      add(new Label("BaseLogOutPanel"));
      add(new Label("BasePageMenuBar"));
    }
  }

  /**
   * Returns the property map associated with this user's session.
   * 
   * @return The session's property map.
   */
  public Map<String, String> getSessionProperties() {
    return ((SolarDecathlonSession) getSession()).getProperties();
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

  /**
   * Handles user authentication.
   * 
   * @return boolean
   */
  @Override
  public boolean isVisible() {

    if (session == null) {
      session = (SolarDecathlonSession) getSession();
    }
    
    if (this.getClass() != LogInPage.class &&
        !session.isAuthenticated()) {
      setResponsePage(LogInPage.class);
      return false;
    }

    return true;
  }
}
