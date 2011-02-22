package edu.hawaii.ihale;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;

/**
 * Provide a very simple session data structure, which is just a set of string key-value pairs. The
 * session should typically store all of the "model" data for a user.
 * 
 * @author Philip Johnson
 */
public class SolarDecathlonSession extends WebSession {
  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  /** Each user has a set of properties. */
  private Map<String, String> properties = new HashMap<String, String>();

  /**
   * Create a new session for this user. Called automatically by wicket. You always need to define
   * this constructor when implementing your own application-specific Session class.
   * 
   * @param application This application.
   * @param request The request.
   */
  public SolarDecathlonSession(WebApplication application, Request request) {
    super(request);

    this.properties.put("Page", "homePage");
    this.properties.put("UserName", "Guest");
    this.properties.put("UserAuthenticated", "false");
  }

  /**
   * Return the properties instance for this specific user.
   * 
   * @return The properties.
   */
  public Map<String, String> getProperties() {
    return this.properties;
  }

  /**
   * Authenticates the user.
   * 
   * @param username String
   * @param passwd String
   * @return boolean
   */
  public boolean authenticate(String username, String passwd) {
    this.properties.put("UserAuthenticated", "true");

    return true;
  }

  /**
   * Checks if user is authenticated.
   * 
   * @return boolean
   */
  public boolean isAuthenticated() {
    String auth = properties.get("UserAuthenticated");

    if (auth != null) {
      return Boolean.valueOf(auth);
    }

    return false;
  }
}
