package edu.hawaii.ihale.backend;

import java.util.Map;

/** A class that creates and manages the resource classes.  This
 * Class controls the interval between the polling of data.  One
 * instance of this class should be created and started by 
 * IHaleBackend.java.
 * @author Backend
 *
 */
public class Dispatcher {
  /** The interval to wait between polling.**/
  private long interval;
  /** The map containing URI values for each system. **/
  private Map<String,String> map;
  
  /** Initializes the dispatcher and starts it.**/
  public Dispatcher(Map<String,String> map, long interval) {
    this.interval = interval;
    this.map = map;
    start();
  }
  
  /**
   * Creates resource classes and continuously preforms gets on them.
   */
  public void start() {
    //Using the URI map, create the resource classes. (AquaponicsResource,HVACResource)
      //create lights
      //create aquaponics
      //create HVAC
    
    //Start a timed main loop that calls getData() on each resource class.
  }
}
