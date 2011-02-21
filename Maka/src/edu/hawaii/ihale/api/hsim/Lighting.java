package edu.hawaii.ihale.api.hsim;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Lighting {
  MT mt = new MT();
  Map <String,String> data;
  //Array of known keys
  String[] keys = {"Dining room", "Kitchen", "Living room", "Bathroom"};
  List<String> list;
  
  public Lighting() {
    //initialize all lights to "off"
    data = new HashMap<String,String>();
    list = Arrays.asList(keys);
    for (String s : list) { 
      int val = (int) mt.nextDouble(0, 100);
      data.put(s , "" + val);
    }
  }
  
  public void setLevel(String key, String val) {
    data.put(key,val);
  }
  
  public Map<String,String> getState() {
    return data;
  }
  
}
