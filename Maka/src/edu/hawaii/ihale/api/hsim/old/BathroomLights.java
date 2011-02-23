package edu.hawaii.ihale.api.hsim;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
/**
 * Simulates the lighting in a room of the solar decathlon house.
 * Each class is titled after the room it represents.
 * Each room holds a value for the level of its lights (0-100),
 * 0 being off, 100 being fully on.
 * @author Team Maka
 *
 */
public class BathroomLights {
  MT mt = new MT();
  Map <String,String> data;
  //Array of known keys
  String[] keys;
  String[] localKeys = {"level"};
  List<String> list;
  
  /**
   * Constructor.
   */
  public BathroomLights() {
    keys = localKeys;
    mt = new MT(Calendar.MILLISECOND);
    //initialize all lights to "off"
    data = new HashMap<String,String>();
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
  public void set(String key, String val) {
    data.put(key,val);
  }
  
  /**
   * Refreshes the data.
   */
  //public void poll() {
  //}
}
