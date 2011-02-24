package edu.hawaii.ihale.api.lights;

import java.util.Calendar;
import java.util.Arrays;
import edu.hawaii.ihale.api.hsim.Arduino;
import edu.hawaii.ihale.api.hsim.MT;
/**
 * Simulates the lighting in a room of the solar decathlon house.
 * Each class is titled after the room it represents.
 * Each room holds a value for the level of its lights (0-100),
 * 0 being off, 100 being fully on.
 * @author Team Maka
 *
 */
public class KitchenLightsResource extends Arduino{
  MT mt = new MT();
  //Array of known keys
  String[] localKeys = {"kilevel"};
  
  /**
   * Constructor.
   */
  public KitchenLightsResource() {
    super("lighting","arduino-7");
    keys = localKeys;
    mt = new MT(Calendar.MILLISECOND);
    //initialize all lights to "off"
    list = Arrays.asList(keys);
    for (String s : list) { 
      int val = (int) mt.nextDouble(0, 100);
      data.put(s , "" + val);
    }
  }
  /**
   * Adds a value to the map.
   * @param key Item's key.
   * @param val Item's value.
   */
  @Override
  public void set(String key, String val) {
    data.put(list.get(0), val);
  }
  @Override
  public void poll() {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Refreshes the data.
   */
  //public void poll() {
  //}
}
