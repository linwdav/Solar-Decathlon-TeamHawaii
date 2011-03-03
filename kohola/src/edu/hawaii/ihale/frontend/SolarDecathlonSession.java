package edu.hawaii.ihale.frontend;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;

/**
 * Session that holds each user's state.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 * 
 */
public class SolarDecathlonSession extends WebSession {

  // Support serialization
  private static final long serialVersionUID = 1L;

  private Map<String, Integer> properties = new HashMap<String, Integer>();

  /**
   * Create default values for the application.
   * 
   * @param application This application.
   * @param request This request.
   */
  public SolarDecathlonSession(WebApplication application, Request request) {
    super(request);
    this.properties.put("AquaponicsStatsGraph", 0);
    this.properties.put("EnergyGraph", 0);
    this.properties.put("ActivePage", 0);       
  }

  /**
   * Return the properties instance for this specific user.
   * 
   * @return The graph to display represented by an Integer.
   */
  public Map<String, Integer> getProperties() {
    return this.properties;
  }
}
