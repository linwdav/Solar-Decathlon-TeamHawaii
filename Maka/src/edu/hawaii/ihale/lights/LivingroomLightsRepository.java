package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
public class LivingroomLightsRepository {
  private static LivingroomLightsRepository instance = null;
  private static String level;
  
  /**
   * Constructor.
   */
  private LivingroomLightsRepository() {
    
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized LivingroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new LivingroomLightsRepository();
          LivingroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  /**
   * Sets the lighting level.
   * @param level the lighting level.
   */
  public static synchronized void setLevel(String level) {
    LivingroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Returns the current lighting level.
   * @return the current lighting level.
   */
  public static synchronized String getLevel() {
    return level;
  }
}
