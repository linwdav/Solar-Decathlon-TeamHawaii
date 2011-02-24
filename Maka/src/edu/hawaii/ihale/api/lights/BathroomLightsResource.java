package edu.hawaii.ihale.api.lights;
 
import java.util.Arrays;
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
  String[] localKeys = {"balevel"};

  
  /**
   * Constructor.
   */
  public BathroomLightsResource() {
    super("lighting","arduino-8");
    keys = localKeys; 
    //initialize all lights to "off"
    list = Arrays.asList(keys);
    if (data.get(localKeys[0]) == null) {
      for (String s : list) { 
        int val = (int) mt.nextDouble(0, 100);
        data.put(s , "" + val);
      }
    }
  }
  
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    data.put(list.get(0),val);
  }

  /**
   * Does nothing, because this never changes!
   */
  @Override
  public void poll() {
  }
  
  /**
   * Refreshes the data.
   */
  //public void poll() {
  //}
}
