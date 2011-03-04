package edu.hawaii.solardecathlon;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.solardecathlon.page.dashboard.Dashboard;

/**
 * This top-level class is required to specify the Wicket WebApplication.
 * 
 * @author Philip Johnson
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class SolarDecathlonApplication extends WebApplication {

  private static String dbClassName = "edu.hawaii.ihale.db.IHaleDB";
  public SystemStateEntryDB database;
  
  /**
   * Default constructor.
   */
  public SolarDecathlonApplication() {
    try {
      database = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
    }
    catch (InstantiationException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Return the home page for this application.
   * 
   * @return The home page.
   */
  @Override
  public Class<? extends Page> getHomePage() {
    return Dashboard.class;
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

  @Override
  public String getConfigurationType() {
    // Allow non-unique wicket ids
    getDebugSettings().setComponentUseCheck(false);

    // Set mode = deployment
    return Application.DEPLOYMENT;
  }
}
