package edu.hawaii.ihale.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.api.ApiDictionary;

/** A Class that controls the timed polling, parsing and storing of data
 * from the house simulator.  One instance of this class should be created 
 * by IHaleBackend.java.
 * @author Backend Team
 *
 */
public class Dispatcher implements Runnable{
  /** The interval to wait between polling.**/
  private long interval;
  /** The map containing all URI values for each system. **/
  private Map<String,String> map;
  /** The map containing all URI command endings for aquaponics. **/
  private static Map<String,String> aquaMap;
  /** The map containing all URI command endings for lighting. **/
  private static Map<String,String> lightMap;
  /**Maps a state name to it's command URI.*/
  private Map<String,String> commandMap;
  /**Maps a state name to it's state URI.*/
  private List<ClientResource> pollList;
  /** Initializes the dispatcher and starts it.**/
  public Dispatcher(Map<String,String> map, long interval) {
    this.interval = interval;
    this.map = map;
    init();  
    makeAquaMap();
    makeLightMap();
  }
  
  /**
   * Creates resource classes and sets up the commandMap and pollList.
   */
  public void init() { 
    String lightState = "lighting/state";
    pollList = new ArrayList<ClientResource>();
    commandMap = new HashMap<String,String>();
    //Using the URI map, create an array/list of ClientResources.
      ClientResource aquaponics = new ClientResource(map.get("aquaponics-state") 
          + "aquaponics/state"); 
      commandMap.put("aquaponics",map.get("aquaponics-control") );
      pollList.add(aquaponics);
      
      ClientResource hvac = new ClientResource(map.get("hvac-state") + "hvac/state");
      commandMap.put("hvac",map.get("hvac-control"));
      pollList.add(hvac);
      
      ClientResource electrical = new ClientResource(map.get("electrical-state") + 
          "electrical/state");  
      pollList.add(electrical);
      
      ClientResource pv = new ClientResource(map.get("pv-state") + "photovoltaics/state"); 
      pollList.add(pv);
      
      ClientResource bathroom  = new ClientResource(map.get("lighting-bathroom-state") 
          + lightState);  
      commandMap.put("bathroom",map.get("lighting-bathroom-control"));
      pollList.add(bathroom);
      
      ClientResource kitchen  = new ClientResource(map.get("lighting-kitchen-state") + 
          lightState);  
      commandMap.put("kitchen",map.get("lighting-kitchen-control"));
      pollList.add(kitchen);

      ClientResource dining  = new ClientResource(map.get("lighting-dining-state")
          + lightState);  
      commandMap.put("dining",map.get("lighting-dining-control"));
      pollList.add(dining);
      
      ClientResource living  = new ClientResource(map.get("lighting-living-state")
          + lightState); 
      commandMap.put("living",map.get("lighting-living-control"));
      pollList.add(living);
  }
  
  /** Polls data continuously from Hsim */
  @Override
  public void run() {
    while(true) {
      for(ClientResource client : pollList) {
        Representation Rep = client.get();
        client.release();
        //TODO parse rep (Waiting for Tony's parser method)
      }
      try {
        Thread.sleep(interval);
      }
      catch (InterruptedException e) {
        System.out.println("POLLING ERROR");
        e.printStackTrace();
      }
    } 
  }
  
  /** Returns the command map containing the ClientResources for
   * system commands.
   * @return the commandMap.
   */
  public Map<String,String> getCommandMap() { 
    return commandMap;
  }
  
  public static void makeAquaMap() {
    aquaMap = new HashMap<String,String> ();
    aquaMap.put(ApiDictionary.IHaleCommandType.FEED_FISH.toString(),"aquaponics/fish/feed");
    aquaMap.put(ApiDictionary.IHaleCommandType.HARVEST_FISH.toString(),"aquaponics/fish/harvest");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_NUTRIENTS.toString(),"aquaponics/fish/nutrients");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_PH.toString(),"aquaponics/ph");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_TEMPERATURE.toString(),"aquaponics/temperature");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_WATER_LEVEL.toString(),"aquaponics/water/level"); 
  }
  
  public Map<String,String> getAquaMap() {
    return aquaMap;
  }
  
  public static void makeLightMap() {
    lightMap = new HashMap<String,String>();
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_LEVEL.toString(),"lighting/level");
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_COLOR.toString(),"lighting/color");
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_ENABLED.toString(),"lighting/enabled");
  }
  public Map<String,String> getLightMap() {
    return lightMap;
  }
} 