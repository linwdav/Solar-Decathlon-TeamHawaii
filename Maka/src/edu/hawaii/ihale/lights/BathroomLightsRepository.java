package edu.hawaii.ihale.lights;

public class BathroomLightsRepository {
  private static BathroomLightsRepository instance = null;
  private static String level;
  
  private BathroomLightsRepository() {
    
  }
  
  public static synchronized BathroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new BathroomLightsRepository();
          BathroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  public synchronized void setLevel(String level) {
    BathroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  public synchronized String getLevel() {
    return level;
  }
}
