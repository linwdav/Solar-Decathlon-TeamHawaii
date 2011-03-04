package edu.hawaii.ihale.frontend;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import edu.hawaii.ihale.api.SystemStateEntryDB;
//import edu.hawaii.ihale.backend.DataGatheringThread;
//import edu.hawaii.ihale.backend.rest.IHaleServer;

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
  
  //private static final String NAIA_DB_CLASS_NAME = "edu.hawaii.ihale.backend.SystemStateEntryDAO";
  // private static final String HOIKE_DB_CLASS_NAME = "edu.hawaii.ihale.backend.rest.IHaleDAO";
  private static final String BLACKMAGIC_DB_CLASS_NAME = "edu.hawaii.ihale.db.IHaleDB";

  private static AquaponicsListener aquaponicsListener;
  private static HvacListener hvacListener;
  private static LightsListener lightsListener;
  private static PhotovoltaicListener photovoltaicListener;
  private static ElectricalListener electricalListener;
  static SystemStateEntryDB db;

  static {

    aquaponicsListener = new AquaponicsListener();
    hvacListener = new HvacListener();
    lightsListener = new LightsListener();
    photovoltaicListener = new PhotovoltaicListener();
    electricalListener = new ElectricalListener();

    /**
     * Choose 1, 2, or 3 below to test with different systems.
     */
    // 1. for integration with Naia backend
    //String dbClassName = NAIA_DB_CLASS_NAME;

    // 2. for integration with Hoike backend
    // String dbClassName = HOIKE_DB_CLASS_NAME;

    // 3. for testing our frontend solely
    String dbClassName = BLACKMAGIC_DB_CLASS_NAME;

    /**
     * Or read from the parameter from the command line
     */
    // String dbClassName;
    // if ("naia".equalsIgnoreCase(Jetty.getBackendName())) {
    // dbClassName = NAIA_DB_CLASS_NAME;
    // }
    // else if ("hoike.equalsIgnoreCase.(Jetty.getBackendName())) {
    // dbClassName = HOIKE_DB_CLASS_NAME;
    // }
    // else {
    // dbClassName = BLACKMAGIC_DB_CLASS_NAME;
    // }

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
    db.addSystemStateListener(aquaponicsListener);
    db.addSystemStateListener(hvacListener);
    db.addSystemStateListener(lightsListener);
    db.addSystemStateListener(photovoltaicListener);
    db.addSystemStateListener(electricalListener);

    /***************************************************************
     * Choose 1, 2, or 3 below to test with different systems.
     ***************************************************************/
    // 1. calling the backend naia thread to get readings from sensors
    // DataGatheringThread dataGathering = new DataGatheringThread(10000);
    // // Create Thread
    // Thread dataGatheringThread = new Thread(dataGathering);
    // // Start Thread
    // dataGatheringThread.start();

    // 2. calling the backend Hoike thread to get readings from sensors
    // IHaleServer iHaleServer = new IHaleServer(10000, Jetty.getSimulationName());
    // // Create Thread
    // Thread iHaleServerThread = new Thread(iHaleServer);
    // // Start Thread
    // iHaleServerThread.start();

    // 3. call BlackMagic
    // Please modify the code in Header.java

    /*********************************************************************
     * To be uncommented after backend teams finish their implementation.
     **********************************************************************/
    // if ("naia".equalsIgnoreCase(Jetty.getBackendName())) {
    // DataGatheringThread dataGathering =
    // new DataGatheringThread(10000, Jetty.getSimulationName());
    // // Create Thread
    // Thread dataGatheringThread = new Thread(dataGathering);
    // // Start Thread
    // dataGatheringThread.start();
    // }
    //
    // else if ("hoike".equalsIgnoreCase(Jetty.getBackendName())) {
    // IHaleServer iHaleServer = new IHaleServer(10000, Jetty.getSimulationName());
    // // // Create Thread
    // Thread iHaleServerThread = new Thread(iHaleServer);
    // // // Start Thread
    // iHaleServerThread.start();
    // }

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
   * 
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
   * 
   * @param request The http request.
   * @param response The http response.
   * @return The session instance for this user.
   */
  @Override
  public Session newSession(Request request, Response response) {
    return new SolarDecathlonSession(this, request);
  }

  /**
   * Returns the aquaponics listener.
   * 
   * @return The aquaponics listener
   */
  public static AquaponicsListener getAquaponics() {
    return aquaponicsListener;
  }

  /**
   * Returns the hvac listener.
   * 
   * @return The hvac listener
   */
  public static HvacListener getHvac() {
    return hvacListener;
  }

  /**
   * Returns the lights listener.
   * 
   * @return The lights listener
   */
  public static LightsListener getLights() {
    return lightsListener;
  }

  /**
   * Returns the photovoltaic listener.
   * 
   * @return The photovoltaic listener
   */
  public static PhotovoltaicListener getPhotovoltaic() {
    return photovoltaicListener;
  }

  /**
   * Returns the electrical listener.
   * 
   * @return The electrical listener
   */
  public static ElectricalListener getElectrical() {
    return electricalListener;
  }

}
