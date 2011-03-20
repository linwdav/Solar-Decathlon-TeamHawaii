package edu.hawaii.ihale.backend;

import java.util.Map;
import org.restlet.resource.ClientResource;

/** A Class that controls the timed polling, parsing and storing of data
 * from the house simulator.  One instance of this class should be created 
 * by IHaleBackend.java.
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
    //Using the URI map, create an array/list of ClientResources.
      //create lights
      //create aquaponics 
      ClientResource aquaponics = new ClientResource(map.get("aquaponics-state"));
      //create HVAC
    
    //Start a timed main loop that calls getData() on each resource class in the array/list.
      //either parse the data in this loop (if it can be done in a nice, dynamic way), or call
      //another method to handle each respective system's xml parsing and storing.
  }
}
