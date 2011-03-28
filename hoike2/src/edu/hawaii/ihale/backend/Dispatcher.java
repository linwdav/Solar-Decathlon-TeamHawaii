package edu.hawaii.ihale.backend;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import org.restlet.ext.xml.DomRepresentation; 
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
  /**Holds the clients.*/
  private List<ClientResource> pollList; 
  /**Holds the history client.*/
  private List<ClientResource> histList;
  /** Initializes the dispatcher and starts it.**/
  public Dispatcher(Map<String,String> map, long interval) {
    this.interval = interval;
    this.map = map;
    //creates the core maps used in this class
    init(); 
    //gets the historical data from each system, parses, and stores it.
    getHist();
    //creates the aquaponics Mapping from enum strings to URL endings.
    makeAquaMap();
    //creates the lighting Mapping from enum strings to URL endings.
    makeLightMap();
  }
  
  /**
   * Creates resource classes and sets up the commandMap and pollList.
   */
  public void init() { 
    String lightState = "lighting/state";
    String since = "?since=0";
    pollList = new ArrayList<ClientResource>();
    commandMap = new HashMap<String,String>();
    
      String aquaState = map.get("aquaponics-state") + "aquaponics/state"; 
      ClientResource aquaponics = new ClientResource(aquaState); 
      pollList.add(aquaponics);
      histList.add(new ClientResource(aquaState + since));
      commandMap.put("aquaponics",map.get("aquaponics-control") );
      
      

      String hvacState = map.get("hvac-state") + "hvac/state"; 
      pollList.add(new ClientResource(hvacState));
      histList.add(new ClientResource(hvacState + since));
      commandMap.put("hvac",map.get("hvac-control"));
      
      String electricalState = map.get("electrical-state") + "electrical/state"; 
      histList.add(new ClientResource(electricalState + since));
      pollList.add(new ClientResource(electricalState));
      
      String pvState = map.get("pv-state") + "photovoltaics/state";  
      histList.add(new ClientResource(pvState + since));
      pollList.add(new ClientResource(pvState));
      
      since = "&" + since;
      String room = "?room=";

      String bathroomState = map.get("lighting-bathroom-state") + lightState; 
      histList.add(new ClientResource(bathroomState + room + "BATHROOM" + since));
      commandMap.put("bathroom",map.get("lighting-bathroom-control"));
      pollList.add(new ClientResource(bathroomState));
      
      String kitchenState = map.get("lighting-kitchen-state") + lightState; 
      histList.add(new ClientResource(kitchenState + room + "KITCHEN" + since));
      commandMap.put("kitchen",map.get("lighting-kitchen-control"));
      pollList.add(new ClientResource(kitchenState));
      
      String diningState = map.get("lighting-dining-state") + lightState; 
      histList.add(new ClientResource(diningState + room + "DINING" + since));
      commandMap.put("dining",map.get("lighting-dining-control"));
      pollList.add(new ClientResource(diningState));
      
      String livingState = map.get("lighting-living-state") + lightState; 
      histList.add(new ClientResource(livingState + room + "LIVING" + since));
      commandMap.put("living",map.get("lighting-living-control"));
      pollList.add(new ClientResource(livingState));
  }
  
  /** Polls data continuously from Hsim */
  @Override
  public void run() { 
    while(true) {
      parse(pollList, "Client Data");
      try {
        Thread.sleep(interval);
      }
      catch (InterruptedException e) {
        System.out.println("POLLING ERROR");
        e.printStackTrace();
      }
    } 
  }
  
  
  /** Gets,Parses, and Stores the history for each system.*/
  public void getHist() {
    parse(histList, "Historical Data");
  }
  
  
  /** Returns the command map containing the ClientResources for
   * system commands.
   * @return the commandMap.
   */
  public Map<String,String> getCommandMap() { 
    return commandMap;
  }
  
  /** Maps the URL endings for Aquaponics putCommand URLs to an Enum String.*/
  public static void makeAquaMap() {
    aquaMap = new HashMap<String,String> ();
    aquaMap.put(ApiDictionary.IHaleCommandType.FEED_FISH.toString(),"aquaponics/fish/feed");
    aquaMap.put(ApiDictionary.IHaleCommandType.HARVEST_FISH.toString(),"aquaponics/fish/harvest");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_NUTRIENTS.toString(),"aquaponics/fish/nutrients");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_PH.toString(),"aquaponics/ph");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_TEMPERATURE.toString(),"aquaponics/temperature");
    aquaMap.put(ApiDictionary.IHaleCommandType.SET_WATER_LEVEL.toString(),"aquaponics/water/level"); 
  }
  /** Returns the Enum to URL ending map.
   * @return Map containing Enum strings and uri endings.
   */
  public Map<String,String> getAquaMap() {
    return aquaMap;
  }
  
  /** Maps the URL endings for Lighting putCommand URLS to an Enum String.*/
  public static void makeLightMap() {
    lightMap = new HashMap<String,String>();
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_LEVEL.toString(),"lighting/level");
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_COLOR.toString(),"lighting/color");
    lightMap.put(ApiDictionary.IHaleCommandType.SET_LIGHTING_ENABLED.toString(),"lighting/enabled");
  }
  
  /** Returns the map containing a maping of enums to URL Endings.
   * @return A Map linking enum Strings and url endings
   */
  public Map<String,String> getLightMap() {
    return lightMap;
  }
  
  /** Provides a for each loop that polls the provided list of ClientResources. 
   * @param list the list of CLientResources to poll from.
   */
  public void parse(List<ClientResource> list, String listName) {
    XmlHandler handler = new XmlHandler();
    while(true) {
      for(ClientResource client : list) {
        DomRepresentation rep = (DomRepresentation) client.get();
        client.release();
        try {
          handler.xml2StateEntry(rep.getDocument());
        }
        catch (Exception e) {
          System.out.println("Error in Parsing " + listName);
        }
      }
    } 
  }
}