package edu.hawaii.ihale.frontend;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import edu.hawaii.ihale.api.SystemStateEntryDB;
//import edu.hawaii.ihale.backend.DataGatheringThread;

/**
 * This top-level class is required to specify the Wicket WebApplication.
 * 
 * @author Philip Johnson
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class SolarDecathlonApplication extends WebApplication {
    
  static SystemStateEntryDB db;
  
  static {

    AquaponicsListener aquaponicsListener = new AquaponicsListener();
    HvacListener hvacListener = new HvacListener();
    LightsListener lightsListener = new LightsListener();
    PhotovoltaicListener photovoltaicListener = new PhotovoltaicListener();
    ElectricalListener electricalListener = new ElectricalListener();

    String dbClassName = "edu.hawaii.ihale.backend.SystemStateEntryDAO";

    try {
      db = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
    }
    catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    db.addSystemStateListener(aquaponicsListener);
    db.addSystemStateListener(hvacListener);
    db.addSystemStateListener(lightsListener);
    db.addSystemStateListener(photovoltaicListener);
    db.addSystemStateListener(electricalListener);
    
    
    // calling the backend naia thread to get reading from sensors
    // DataGatheringThread dataGathering = new DataGatheringThread(10000);
    // // Create Thread
    // Thread dataGatheringThread = new Thread(dataGathering);
    // // Start Thread
    // dataGatheringThread.start();
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
    String dbClassName = "edu.hawaii.ihale.backend.SystemStateEntryDAO";
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

}
