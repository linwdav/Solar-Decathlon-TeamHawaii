package edu.hawaii.ihale.api.hsim;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Aquaponics {
  MT mt = new MT();
  Map <String,String> data;
  //Array of known keys
  double PH = 7, Temp = 78.6, DO = .5;
  String[] keys = {"Temp", "PH", "DO"};
  List<String> list;
  
  public Aquaponics() {
    //initialize all lights to "off"
    data = new HashMap<String,String>();
    list = Arrays.asList(keys);
    data.put("Temp", "" + Temp);
    data.put("PH", "" + PH);
    data.put("DO", "" + DO);
  }
  
  public Map<String,String> getState() {
    data.put("Temp", "" + getTemp());
    data.put("PH", "" + getPH());
    data.put("DO", "" + getDO());
    return data;
  }
  
  public void set(String key, String val) {
    double v = sToD(val);
    if(key.equals("Temp")) {
      Temp = v;
    }
    else if (key.equals("PH")) {
      PH = v;
    }
    else if (key.equals("DO")) {
      DO = v;
    }
  }
  
  private Double sToD(String val) {
    double v = 0;
    try {
      v = Double.valueOf(val).doubleValue();
   } catch (NumberFormatException e) {
      System.out.println(e);
   }
   return v;
  }

  private double getPH() {
    double currentPH = sToD(data.get("PH"));
    return currentPH + (currentPH - PH) / 100 + mt.nextDouble(-.1,.1);
  }

  private double getDO() {
    double currentDO = sToD(data.get("DO"));
    return currentDO + (currentDO - DO) / 100 + mt.nextDouble(-.01,.01);
  }
  
  
  private double getTemp() {
    double hour = Calendar.HOUR_OF_DAY;
    double min = Calendar.MINUTE / 60;
    hour += min;
    double baseTemp = 78.5;
    double rate = 2.5 / 12.0 + mt.nextDouble(0,.05);
    double currentTemp = sToD(data.get("DO"));
    //night
    if (hour <= 6 || hour >= 18) {
      return (currentTemp + (baseTemp - (hour % 18) * rate)) / 2;
    }
    //day
    else {
      return (currentTemp + (baseTemp + (hour - 6) * rate)) /2;
    }
  }
}
