package edu.hawaii.ihale.frontend;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
//import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.repository.IHaleRepository;
import edu.hawaii.ihale.api.repository.impl.Repository;
//import edu.hawaii.ihale.backend.DataGatheringThread;
import edu.hawaii.ihale.backend.IHaleBackend;
//import edu.hawaii.ihale.backend.rest.IHaleServer;
import edu.hawaii.ihale.frontend.page.aquaponics.AquaPonics;
import edu.hawaii.ihale.frontend.page.aquaponics.AquaponicsListener;
import edu.hawaii.ihale.frontend.page.aquaponics.AquaponicsStats;
import edu.hawaii.ihale.frontend.page.dashboard.Dashboard;
import edu.hawaii.ihale.frontend.page.energy.ElectricalListener;
import edu.hawaii.ihale.frontend.page.energy.Energy;
import edu.hawaii.ihale.frontend.page.energy.PhotovoltaicListener;
import edu.hawaii.ihale.frontend.page.help.Help;
import edu.hawaii.ihale.frontend.page.hvac.Hvac;
import edu.hawaii.ihale.frontend.page.hvac.HvacListener;
import edu.hawaii.ihale.frontend.page.lighting.Lighting;
import edu.hawaii.ihale.frontend.page.lighting.LightsListener;
import edu.hawaii.ihale.frontend.page.messages.Messages;
import edu.hawaii.ihale.frontend.page.messages.MessagesListener;

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
    
  private static AquaponicsListener aquaponicsListener;
  private static HvacListener hvacListener;
  private static LightsListener lightsListener;
  private static PhotovoltaicListener photovoltaicListener;
  private static ElectricalListener electricalListener;
  
  // Supports status messages and updates to messages
  private static MessagesListener messagesListener;
  private static Messages messages;
  
  //private static SystemStateEntryDB db;
 // private static IHaleRepository repository;
  private static Repository repository;
  private static IHaleBackend backend;
 
  
  static {
    
    backend = new IHaleBackend();

    aquaponicsListener = new AquaponicsListener();
    hvacListener = new HvacListener();
    lightsListener = new LightsListener();
    photovoltaicListener = new PhotovoltaicListener();
    electricalListener = new ElectricalListener();
    
    // For message log
    messagesListener = new MessagesListener();
    messages = new Messages();
    
    /**
     * Choose 1, 2, or 3 below to test with different systems.
     */
    // 1. for integration with Naia backend
    //String dbClassName = "edu.hawaii.ihale.backend.SystemStateEntryDAO";

    // 2. for integration with Hoike backend
    //String dbClassName = "edu.hawaii.ihale.backend.rest.IHaleDAO";

    // 3. for testing our frontend solely
    //String dbClassName = "edu.hawaii.ihale.db.IHaleDB";
    
//    try {
//      db = (SystemStateEntryDB) Class.forName(dbClassName).newInstance();
//    }
//    catch (InstantiationException e) {
//      e.printStackTrace();
//    }
//    catch (IllegalAccessException e) {
//      e.printStackTrace();
//    }
//    catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    }
    repository = new Repository();    
    repository.addSystemStateListener(aquaponicsListener);
    repository.addSystemStateListener(hvacListener);
    repository.addSystemStateListener(lightsListener);
    repository.addSystemStateListener(photovoltaicListener);
    repository.addSystemStateListener(electricalListener);
    repository.addSystemStatusMessageListener(messagesListener);

    /***************************************************************
     * Choose 1, 2, or 3 below to test with different systems.
     ***************************************************************/
    RepositoryRefresher refresh = new RepositoryRefresher(backend,repository);
    refresh.start(5000);
    /*
    Object x = new Integer(2);
    if (x instanceof Integer) {
      
    }
    */
    // 1. calling the backend naia thread to get readings from sensors
    // DataGatheringThread dataGathering = new DataGatheringThread(10000);
    // // // Create Thread
    // Thread dataGatheringThread = new Thread(dataGathering);
    // // // Start Thread
    // dataGatheringThread.start();

//     //2. calling the backend Hoike thread to get readings from sensors
//     IHaleServer iHaleServer = new IHaleServer(10000);
//     // Create Thread
//     Thread iHaleServerThread = new Thread(iHaleServer);
//     // Start Thread
//     iHaleServerThread.start();

    // 3. call BlackMagic
    // Please modify the code in Header.java
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
   * Do default setup and initialization when this web application is started up. 
   */
  @Override
  public void init() {
    mountBookmarkablePage("dashboard", Dashboard.class);
    mountBookmarkablePage("energy", Energy.class);
    mountBookmarkablePage("aquaponics", AquaPonics.class);
    mountBookmarkablePage("lighting", Lighting.class);
    mountBookmarkablePage("hvac", Hvac.class);
    mountBookmarkablePage("aquaponicsStats", AquaponicsStats.class);
    mountBookmarkablePage("help", Help.class);
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
  
  /**
   * Returns the repository.
   * @return The repository.
   */
  public static IHaleRepository getRepository() {
    return repository;
  }
  
  /**
   * Returns the backend.
   * @return The backend.
   */
  public static IHaleBackend getBackend() {
    return backend;
  }
  
  /**
   * Returns the Messages Listener.
   * @return The messages listener.
   */
  public static MessagesListener getMessagesListener() {
    return messagesListener;
  }
  
  /**
   * Returns the Messages object that stores all system status messages.
   * @return The messages object.
   */
  public static Messages getMessages() {
    return messages;
  }

}
