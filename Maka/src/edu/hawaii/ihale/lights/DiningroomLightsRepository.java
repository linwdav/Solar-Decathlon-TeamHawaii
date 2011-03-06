package edu.hawaii.ihale.lights;

public class DiningroomLightsRepository {
  private static DiningroomLightsRepository instance = null;
  private static String level;
  
  private DiningroomLightsRepository() {
    
  }
  
  public static synchronized DiningroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new DiningroomLightsRepository();
          DiningroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  public synchronized void setLevel(String level) {
    DiningroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  public synchronized String getLevel() {
    return level;
  }
}
