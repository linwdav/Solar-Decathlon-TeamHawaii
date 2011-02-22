package edu.hawaii.ihale;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import edu.hawaii.ihale.ui.page.service.LogInPage;

/**
 * This top-level class is required to specify the Wicket WebApplication.
 * 
 * @author Philip Johnson
 * @author Bret K. Ikehara
 */
public class SolarDecathlonApplication extends WebApplication {
  
  /**
   * Return the home page for this application. 
   * @return The home page. 
   */
  @Override
  public Class<? extends Page> getHomePage() {
    return LogInPage.class;
  }
  
  /**
   * Return a session object to hold the models for each user of this application.
   * 
   * @param request The http request.
   * @param response The http response. 
   * @return The session instance for this user. 
   */
  @Override
  public Session newSession(Request request, Response response) {
    SolarDecathlonSession session = new SolarDecathlonSession(this, request);
    
    session.getProperties().put("ConfigType", this.getConfigurationType());
    
    return session;
  }
  
  /**
   * Gets the configuration type.
   * 
   * @return String
   */
  @Override
  public String getConfigurationType() {
    return Application.DEVELOPMENT;
  }
}
