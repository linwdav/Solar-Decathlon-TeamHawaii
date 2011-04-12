package edu.hawaii.ihale.backend;

import java.util.Map;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * This class controls the timed polling, parsing, and storing of data from the IHale system
 * devices. One instance of this class should be created by IHaleBackend.java.
 * 
 * @author Tony Gaskell
 * @author Gregory Burgess
 */
public class Dispatcher extends Thread {

  /** The interval to wait between polling. */
  private long interval;
  /** The map containing all URI values for each system. */
  private Map<String, String> uriMap;
  /** The PollingDevices. */
  private PollDevice[] pollDevices;

  /**
   * Initializes the dispatcher and starts it.
   * 
   * @param map the parsed properties file
   * @param interval the time in milliseconds to wait between polling.
   */
  public Dispatcher(Map<String, String> map, long interval) {
    this.interval = interval;
    this.uriMap = map;
    init();
  }

  /**
   * Initializes the polling threads.
   */
  private final void init() {
    pollDevices = new PollDevice[8];

    try {
      pollDevices[0] = new PollDevice(IHaleSystem.AQUAPONICS, uriMap.get("aquaponics-state"));
      pollDevices[1] = new PollDevice(IHaleSystem.HVAC, uriMap.get("hvac-state"));
      pollDevices[2] = new PollDevice(IHaleSystem.LIGHTING, uriMap.get("lighting-living-state"));
      pollDevices[3] = new PollDevice(IHaleSystem.LIGHTING, uriMap.get("lighting-dining-state"));
      pollDevices[4] = new PollDevice(IHaleSystem.LIGHTING, uriMap.get("lighting-kitchen-state"));
      pollDevices[5] = new PollDevice(IHaleSystem.LIGHTING, uriMap.get("lighting-bathroom-state"));
      pollDevices[6] = new PollDevice(IHaleSystem.ELECTRIC, uriMap.get("electric-state"));
      pollDevices[7] = new PollDevice(IHaleSystem.PHOTOVOLTAIC, uriMap.get("photovoltaic-state"));
    }
    catch (Exception e) {
      System.err.println("An error occured initializing polling threads!");
      e.printStackTrace();
    }
  }

  /**
   * Polls data continuously from the simulator in set intervals.
   */
  @Override
  public final void run() {
    while (true) {

      // Poll each IHale system device.
      for (int i = 0; i < pollDevices.length; i++) {
        pollDevices[i].poll();
      }

      /*
       * for (int j = 0; j < pollDevices.length; j++) { try { pollDevices[j].join(); } catch
       * (InterruptedException e) { System.err.println("Error occured while polling devices.");
       * e.printStackTrace(); } }
       */
      try {
        Thread.sleep(interval);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}