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
  /** The threads used for polling */
  private Thread[] threads; 
  
  /** Initializes the dispatcher and starts it.
   * @param map the parsed properties file
   * @param interval the time in ms to wait between pollings.
   * @throws InterruptedException 
   */
  public Dispatcher(Map<String, String> map, long interval) throws InterruptedException {
    this.interval = interval;
    this.uriMap = map;
    init();
    main();
  }

  /**
   * Initializes polling threads
   */
  private final void init () {
    threads = new PollDevice[8];
    try {
      threads[0] = new PollDevice(IHaleSystem.AQUAPONICS, null, uriMap.get("aquaponics-state"));
      threads[1] = new PollDevice(IHaleSystem.HVAC, null, uriMap.get("hvac-state"));
      threads[2] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.LIVING, uriMap.get("lighting-living-state"));
      threads[3] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.DINING, uriMap.get("lighting-dining-state"));
      threads[4] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.KITCHEN, uriMap.get("lighting-kitchen-state"));
      threads[5] = new PollDevice(IHaleSystem.LIGHTING, 
          IHaleRoom.BATHROOM,uriMap.get("lighting-bathroom-state"));
      threads[6] = new PollDevice(IHaleSystem.ELECTRIC, null, uriMap.get("electric-state"));
      threads[7] = new PollDevice(IHaleSystem.PHOTOVOLTAIC, null, uriMap.get("photovoltaic-state"));
    }
    catch (Exception e) {
      System.err.println("An rror occured initializing polling threads!");
      e.printStackTrace();
    }
  }

  /** Polls data continuously from Hsim in set intervals.
   * @throws InterruptedException
   * */
  public void main() throws InterruptedException {
    while (true) {
      for (int i = 0; i < threads.length; i++) {
        threads[i].start();
      }
      for (int j = 0; j < threads.length; j++) {
        try {
          threads[j].join();
        }
        catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      Thread.sleep(interval);
    }
  } 
}