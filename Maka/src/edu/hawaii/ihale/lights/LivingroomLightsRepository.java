package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
@edu.umd.cs.findbugs.annotations.SuppressWarnings(value =
  "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
  justification = "Singleton data storage class.")
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
  public synchronized void setLevel(String level) {
    LivingroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Returns the current lighting level.
   * @return the current lighting level.
   */
  public synchronized String getLevel() {
    return level;
  }
}
