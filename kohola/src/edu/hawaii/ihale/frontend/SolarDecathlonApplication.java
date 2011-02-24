package edu.hawaii.ihale.frontend;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import edu.hawaii.ihale.api.SystemStateEntryDB;

/**
 * This top-level class is required to specify the Wicket WebApplication.
 * 
 * @author Philip Johnson
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 */
public class SolarDecathlonApplication extends WebApplication {
    
  AquaponicsListener aquaponicsListener = new AquaponicsListener();
  HvacListener hvacListener = new HvacListener();
  LightsListener lightsListener = new LightsListener();
  PhotovoltaicListener photovoltaicListener = new PhotovoltaicListener();
  ConsumptionListener consumptionListener = new ConsumptionListener();

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
   * Run in deployment mode.
   * @return Application.DEPLOYMENT
   */
  @Override
  public String getConfigurationType() {
    
    // Allow non-unique wicket ids
    getDebugSettings().setComponentUseCheck(false);

    // Set mode = deployment
    return Application.DEPLOYMENT;

  }
  
 /**
  * Return a session object to hold the models for each user of this application.
  * @param request The http request.
  * @param response The http response.
  * @return The session instance for this user.
  */
  @Override
  public Session newSession(Request request, Response response) {
    return new SolarDecathlonSession(this, request);
  }

  /**
   * Returns an instance of the database.
   * @return db The database.
   */
  public SystemStateEntryDB getDB() {
    // Create an instance of the database.
    // We could read this string from a properties file on startup, for example.
    String dbClassName = "edu.hawaii.ihale.db.IHaleDB";
    SystemStateEntryDB db = null;
    try {
      db = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
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
        
    return db;
  }
  
  /**
   * Returns the aquaponics listener.
   * @return aquaponicsListener
   */
  public AquaponicsListener getAquaponicsListener() {
    return aquaponicsListener;
  }
  
  /**
   * Returns the hvac listener.
   * @return hvacListener.
   */
  public HvacListener getHvacListener() {
    return hvacListener;
  }
  
  /**
   * Returns the lights listener.
   * @return lightsLisnter.
   */
  public LightsListener getLightsListener() {
    return lightsListener;
  }
  
  /**
   * Returns the photovoltaic listener.
   * @return photovoltaicListener.
   */
  public PhotovoltaicListener getPhotovoltaicListener() {
    return photovoltaicListener;
  }
  
  /**
   * Returns the consumption listener.
   * @return consumptionListener.
   */
  public ConsumptionListener getConsumptionListener() {
    return consumptionListener;
  }

}
