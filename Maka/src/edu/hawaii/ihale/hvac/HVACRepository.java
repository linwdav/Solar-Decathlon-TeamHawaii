package edu.hawaii.ihale.hvac;

public class HVACRepository {
  private static HVACRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String temp;
  private HVACRepository() {
    
  }
  
  public static synchronized HVACRepository getInstance() {
      if (instance == null) {
          instance = new HVACRepository();
          HVACRepository.temp = "79.4";
      }
      return instance;
  }

  public synchronized void setTemp(String temp) {
    HVACRepository.temp = temp;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  public synchronized String getTemp() {
    return temp;
  }
}
