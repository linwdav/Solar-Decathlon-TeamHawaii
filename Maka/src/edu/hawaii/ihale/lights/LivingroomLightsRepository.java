package edu.hawaii.ihale.lights;

public class LivingroomLightsRepository {
  private static LivingroomLightsRepository instance = null;
  private static String level;
  
  private LivingroomLightsRepository() {
    
  }
  
  public static synchronized LivingroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new LivingroomLightsRepository();
          LivingroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  public synchronized void setLevel(String level) {
    LivingroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  public synchronized String getLevel() {
    return level;
  }
}
