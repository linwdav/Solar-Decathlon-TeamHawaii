package edu.hawaii.ihale;

import java.util.HashMap;
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

  private transient AquaponicsListener listAquaponics;
  private transient ElectricalListener listElectrical;
  private transient HVacListener listHvac;
  private transient LightingListener listLighting;
  private transient PhotovoltaicListener listPhotovoltaic;

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
      database = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
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

    listAquaponics = new AquaponicsListener();
    this.database.addSystemStateListener(listAquaponics);

    listElectrical = new ElectricalListener();
    this.database.addSystemStateListener(listElectrical);

    listHvac = new HVacListener();
    this.database.addSystemStateListener(listHvac);

    listLighting = new LightingListener();
    this.database.addSystemStateListener(listLighting);

    listPhotovoltaic = new PhotovoltaicListener();
    this.database.addSystemStateListener(listPhotovoltaic);
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
    return ("aquaponics".equalsIgnoreCase(systemName)) ? listAquaponics : ("electrical"
        .equalsIgnoreCase(systemName)) ? listElectrical
        : ("hvac".equalsIgnoreCase(systemName)) ? listHvac : ("lighting"
            .equalsIgnoreCase(systemName)) ? listLighting : ("photovoltaic"
            .equalsIgnoreCase(systemName)) ? listPhotovoltaic : null;
  }
}
