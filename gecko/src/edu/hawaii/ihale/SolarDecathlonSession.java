package edu.hawaii.ihale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.ElectricalListener;
import edu.hawaii.ihale.ui.HVacListener;
import edu.hawaii.ihale.ui.LightingListener;
import edu.hawaii.ihale.ui.PhotovoltaicListener;
import edu.hawaii.ihale.ui.WaterListener;

/**
 * Provide a very simple session data structure, which is just a set of string key-value pairs. The
 * session should typically store all of the "model" data for a user.
 * 
 * @author Philip Johnson
 * @author Bret K. Ikehara
 */
public class SolarDecathlonSession extends WebSession {
  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  /** Each user has a set of properties. */
  private Map<String, String> properties = new HashMap<String, String>();
  
  private String dbClassName = "edu.hawaii.ihale.backend.rest.IHaleDAO";
  protected transient SystemStateEntryDB database;
  
  private List<SystemStateListener> listeners;

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
    
    try {
      database = (SystemStateEntryDB)Class.forName(dbClassName).newInstance();
    }
    catch (InstantiationException e1) {
      e1.printStackTrace();
    }
    catch (IllegalAccessException e1) {
      e1.printStackTrace();
    }
    catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    }
    
    listeners = new ArrayList<SystemStateListener>();
    listeners.add(new AquaponicsListener());
    listeners.add(new ElectricalListener());
    listeners.add(new HVacListener());
    listeners.add(new LightingListener());
    listeners.add(new PhotovoltaicListener());
    listeners.add(new WaterListener());
    
    for (SystemStateListener listener : listeners) {
      this.database.addSystemStateListener(listener);
    }
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
  
  /**
   * Gets this SystemStateEntryDB.
   * 
   * @return SystemStateEntryDB
   */
  public SystemStateEntryDB getIHaleDAO() {
    return this.database;
  }
  
  /**
   * Gets this System State Listeners.
   * 
   * @param systemName String
   * @return SystemStateListener
   */
  public SystemStateListener getSystemStateListener(String systemName) {
    for (SystemStateListener listener : listeners) {
      if (listener.getSystemName().equalsIgnoreCase(systemName)) {
        return listener;
      }
    }
    return null;
  }
}
