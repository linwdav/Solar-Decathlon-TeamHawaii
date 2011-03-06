package edu.hawaii.ihale.lights;

public class KitchenLightsRepository {
  private static KitchenLightsRepository instance = null;
  private static String level;
  
  private KitchenLightsRepository() {
    
  }
  
  public static synchronized KitchenLightsRepository getInstance() {
      if (instance == null) {
          instance = new KitchenLightsRepository();
          KitchenLightsRepository.level = "79.4";
      }
      return instance;
  }

  public synchronized void setLevel(String level) {
    KitchenLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  public synchronized String getLevel() {
    return level;
  }
}
