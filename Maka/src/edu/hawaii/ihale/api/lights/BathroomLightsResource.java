package edu.hawaii.ihale.api.lights;
 
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import edu.hawaii.ihale.api.hsim.Arduino; 
/**
 * Simulates the lighting in a room of the solar decathlon house.
 * Each class is titled after the room it represents.
 * Each room holds a value for the level of its lights (0-100),
 * 0 being off, 100 being fully on.
 * @author Team Maka
 *
 */
public class BathroomLightsResource extends Arduino { 
  String[] localKeys = {"level"};
  //Maps need to be non-final...
  @SuppressWarnings("PMD.AssignmentToNonFinalStatic")
  static Map<String, String> bathroomLightsData = new ConcurrentHashMap<String, String>();

  
  /**
   * Constructor.
   */
  public BathroomLightsResource() {
    super("lighting","arduino-8");
    room = "bathroom";
    if (bathroomLightsData.get(localKeys[0]) == null) {
      bathroomLightsData.put(localKeys[0], String.valueOf((int) mt.nextDouble(0, 100)));
      data2.put(room, bathroomLightsData);
    }
    keys = localKeys; 
    list = Arrays.asList(keys);
  }
  
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    bathroomLightsData.put(list.get(0),val);
  }

  @Override
  public void poll() {
    // TODO Auto-generated method stub
  }

}
