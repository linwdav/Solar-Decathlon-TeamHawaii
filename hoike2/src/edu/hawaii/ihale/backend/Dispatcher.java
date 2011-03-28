package edu.hawaii.ihale.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/** A Class that controls the timed polling, parsing and storing of data
 * from the house simulator.  One instance of this class should be created 
 * by IHaleBackend.java.
 * @author Backend Team
 *
 */
public class Dispatcher {
  /** The interval to wait between polling.**/
  private long interval;
  /** The map containing all URI values for each system. **/
  private Map<String,String> map;
  /**Maps a state name to it's command URI.*/
  private Map<String,ClientResource> commandMap;
  /**Maps a state name to it's state URI.*/
  private List<ClientResource> pollList;
  /** Initializes the dispatcher and starts it.**/
  public Dispatcher(Map<String,String> map, long interval) {
    this.interval = interval;
    this.map = map;
    init();
    try {
      start();
    }
    catch (InterruptedException e) {
      System.out.println("Failed to start");
      e.printStackTrace();
    }
  }
  
  /**
   * Creates resource classes and sets up the commandMap and pollList.
   */
  public void init() {
    pollList = new ArrayList<ClientResource>();
    commandMap = new HashMap<String,ClientResource>();
    //Using the URI map, create an array/list of ClientResources.
      ClientResource aquaponics = new ClientResource(map.get("aquaponics-state"));
      ClientResource aquaponicsControl = new ClientResource(map.get("aquaponics-control"));
      commandMap.put("aquaponics",aquaponicsControl);
      pollList.add(aquaponics);
      
      ClientResource hvac = new ClientResource(map.get("hvac-state"));
      ClientResource hvacControl = new ClientResource(map.get("hvac-control"));
      commandMap.put("hvac",hvacControl);
      pollList.add(hvac);
      
      ClientResource electrical = new ClientResource(map.get("electrical-state"));  
      pollList.add(electrical);
      
      ClientResource pv = new ClientResource(map.get("pv-state")); 
      pollList.add(pv);
      
      ClientResource bathroom  = new ClientResource(map.get("lighting-bathroom-state")); 
      ClientResource bathroomControl = new ClientResource(map.get("lighting-bathroom-control"));
      commandMap.put("bathroom",bathroomControl);
      pollList.add(bathroom);
      
      ClientResource kitchen  = new ClientResource(map.get("lighting-kitchen-state")); 
      ClientResource kitchenControl = new ClientResource(map.get("lighting-kitchen-control"));
      commandMap.put("kitchen",kitchenControl);
      pollList.add(kitchen);

      ClientResource dining  = new ClientResource(map.get("lighting-dining-state")); 
      ClientResource diningControl = new ClientResource(map.get("lighting-dining-control"));
      commandMap.put("dining",diningControl);
      pollList.add(dining);
      
      ClientResource living  = new ClientResource(map.get("lighting-living-state"));
      ClientResource livingControl = new ClientResource(map.get("lighting-living-control"));
      commandMap.put("living",livingControl);
      pollList.add(living);
  }
  
  /** Polls data continuously from Hsim 
   * @throws InterruptedException */
  public void start() throws InterruptedException {
    while(true) {
      for(ClientResource client : pollList) {
        Representation Rep = client.get();
        //TODO parse rep (Waiting for Tony's parser method)
      }
      Thread.sleep(interval);
    } 
  }
  
  /** Returns the command map containing the ClientResources for
   * system commands.
   * @return the commandMap.
   */
  public Map<String,ClientResource> getCommandMap() {
    return commandMap;
  }
}
