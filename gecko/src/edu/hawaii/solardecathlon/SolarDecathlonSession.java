package edu.hawaii.solardecathlon;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import edu.hawaii.solardecathlon.components.BaseModel;
import edu.hawaii.solardecathlon.page.aquaponics.AquaponicsModel;
import edu.hawaii.solardecathlon.page.energy.EnergyModel;
import edu.hawaii.solardecathlon.page.lighting.LightingModel;
import edu.hawaii.solardecathlon.page.lighting.RoomModel;
import edu.hawaii.solardecathlon.page.temperature.TemperatureModel;

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

  protected AquaponicsModel aquaponics;
  protected LightingModel lighting;
  protected TemperatureModel temperature;
  protected EnergyModel energy;

  /**
   * Create a new session for this user. Called automatically by wicket. You always need to define
   * this constructor when implementing your own application-specific Session class.
   * 
   * @param webApp This application.
   * @param request The request.
   */
  public SolarDecathlonSession(WebApplication webApp, Request request) {
    super(request);

    this.properties.put("TabNum", "0");
    this.properties.put("PageNum", "0");
    this.properties.put("GraphNum", "0");

    // Initiate all the models.
    aquaponics = new AquaponicsModel();

    lighting = new LightingModel();
    lighting.putRoom(new RoomModel("Living Room"));
    lighting.putRoom(new RoomModel("Dining Room"));
    lighting.putRoom(new RoomModel("Bathroom"));
    lighting.putRoom(new RoomModel("Kitchen"));

    temperature = new TemperatureModel();
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
   * Sets a property in this session.
   * 
   * @param prop String
   * @param value String
   */
  public void putProperty(String prop, String value) {
    this.properties.put(prop, value);
  }

  /**
   * Sets a property in this session.
   * 
   * @param prop String
   * @param value String
   */
  public void putProperty(String prop, Integer value) {
    this.properties.put(prop, value.toString());
  }
  
  /**
   * Gets a property in this session.
   * 
   * @param prop String
   * @return String
   */
  public String getProperty(String prop) {

    return this.properties.get(prop);
  }

  /**
   * Gets this system models.
   * 
   * @param systemName String
   * @return SystemModel
   */
  public BaseModel getModel(String systemName) {
    return ("aquaponics".equalsIgnoreCase(systemName)) ? aquaponics : ("lighting"
        .equalsIgnoreCase(systemName)) ? lighting
        : ("temperature".equalsIgnoreCase(systemName)) ? temperature : ("energy"
            .equalsIgnoreCase(systemName)) ? energy : null;
  }
}
