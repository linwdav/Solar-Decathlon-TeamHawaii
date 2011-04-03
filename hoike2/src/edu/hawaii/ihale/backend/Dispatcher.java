package edu.hawaii.ihale.backend;

import java.util.Map; 
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/** A Class that controls the timed polling, parsing and storing of data
 * from the house simulator.  One instance of this class should be created 
 * by IHaleBackend.java.
 * @author Backend Team
 *
 */
public class Dispatcher {
  /** The interval to wait between polling.*/
  private long interval;
  /** The map containing all URI values for each system. */
  private Map<String, String> uriMap;
  /** The threads used for polling. */
  private PollDevice[] pollDevices; 
  
  /** Initializes the dispatcher and starts it.
   * @param map the parsed properties file
   * @param interval the time in milliseconds to wait between polling.
   * @throws InterruptedException 
   */
  public Dispatcher(Map<String, String> map, long interval) throws InterruptedException {
    this.interval = interval;
    this.uriMap = map;
    init();
    poll();
  }

  /**
   * Initializes polling threads.
   */
  private final void init () {
    pollDevices = new PollDevice[8];
    try {
      pollDevices[0] = new PollDevice(IHaleSystem.AQUAPONICS, 
          null, uriMap.get("aquaponics-state"));
      pollDevices[1] = new PollDevice(IHaleSystem.HVAC, 
          null, uriMap.get("hvac-state"));
      pollDevices[2] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.LIVING, uriMap.get("lighting-living-state"));
      pollDevices[3] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.DINING, uriMap.get("lighting-dining-state"));
      pollDevices[4] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.KITCHEN, uriMap.get("lighting-kitchen-state"));
      pollDevices[5] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.BATHROOM,uriMap.get("lighting-bathroom-state"));
      pollDevices[6] = new PollDevice(IHaleSystem.ELECTRIC, 
          null, uriMap.get("electric-state"));
      pollDevices[7] = new PollDevice(IHaleSystem.PHOTOVOLTAIC, 
          null, uriMap.get("photovoltaic-state"));
    }
    catch (Exception e) {
      System.err.println("An error occured initializing polling threads!");
      e.printStackTrace();
    }
  }

  /** Polls data continuously from the simulator in set intervals.
   * @throws InterruptedException 
   */
  public final void poll() throws InterruptedException {
    while (true) {
      for (int i = 0; i < pollDevices.length; i++) {
        pollDevices[i].start();
      }
      for (int j = 0; j < pollDevices.length; j++) {
        try {
          pollDevices[j].join();
        }
        catch (InterruptedException e) {
          System.err.println("Error occured while polling devices.");
          e.printStackTrace();
        }
      }
      Thread.sleep(interval);
    }
  } 
}