package edu.hawaii.solardecathlon;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.solardecathlon.listeners.AquaponicsListener;
import edu.hawaii.solardecathlon.listeners.ElectricalListener;
import edu.hawaii.solardecathlon.listeners.HvacListener;
import edu.hawaii.solardecathlon.listeners.LightingListener;
import edu.hawaii.solardecathlon.listeners.PhotovoltaicsListener;
import edu.hawaii.solardecathlon.page.dashboard.Dashboard;
import edu.hawaii.solardecathlon.page.lighting.RoomModel;

//TODO remove non-unique wicket ids.

/**
 * This top-level class is required to specify the Wicket WebApplication.
 * 
 * @author Philip Johnson
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class SolarDecathlonApplication extends WebApplication {

  private static String dbClassName = "edu.hawaii.ihale.backend.SystemStateEntryDAO";
  private static SystemStateEntryDB DAO;

  private static AquaponicsListener aquaponicsListener;
  private static ElectricalListener electricalListener;
  private static HvacListener hvacListener;
  private static LightingListener lightingListener;
  private static PhotovoltaicsListener photovoltaicsListener;
  private static Duration updateInterval;

  static {
    // Setup reference to database.
    try {
      DAO = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
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

    aquaponicsListener = new AquaponicsListener("aquaponics");
    DAO.addSystemStateListener(aquaponicsListener);

    electricalListener = new ElectricalListener("electrical");
    DAO.addSystemStateListener(electricalListener);

    hvacListener = new HvacListener("hvac");
    DAO.addSystemStateListener(hvacListener);

    lightingListener = new LightingListener("lighting");
    DAO.addSystemStateListener(lightingListener);
    lightingListener.putRoom(new RoomModel("Living Room", "arduino-5"));
    lightingListener.putRoom(new RoomModel("Dining Room", "arduino-6"));
    lightingListener.putRoom(new RoomModel("Kitchen", "arduino-7"));
    lightingListener.putRoom(new RoomModel("Bathroom", "arduino-8"));

    photovoltaicsListener = new PhotovoltaicsListener("photovoltaics");
    DAO.addSystemStateListener(photovoltaicsListener);
    
    updateInterval = Duration.seconds(2);
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

  /**
   * Gets the database access object.
   * 
   * @return SystemStateEntryDB
   */
  public static SystemStateEntryDB getDAO() {
    return DAO;
  }

  /**
   * Gets the aquaponics listener.
   * 
   * @return AquaponicsListener
   */
  public static AquaponicsListener getAquaponicsListener() {
    return aquaponicsListener;
  }

  /**
   * Gets the electical listener.
   * 
   * @return ElectricalListener
   */
  public static ElectricalListener getElectricalListener() {
    return electricalListener;
  }

  /**
   * Gets the HVAC listener.
   * 
   * @return HvacListener
   */
  public static HvacListener getHvacListener() {
    return hvacListener;
  }

  /**
   * Gets the lighting listener.
   * 
   * @return LightingListener
   */
  public static LightingListener getLightingListener() {
    return lightingListener;
  }

  /**
   * Gets the photovoltaics listener.
   * 
   * @return PhotovoltaicsListener
   */
  public static PhotovoltaicsListener getPhotovoltaicsListener() {
    return photovoltaicsListener;
  }
  
  /**
   * Gets this update interval.
   * 
   * @return Duration
   */
  public static Duration getUpdateInterval() {
    return updateInterval;
  }
}
