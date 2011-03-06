package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
public class BathroomLightsRepository {
  private static BathroomLightsRepository instance = null;
  private static String level;
  
  /** 
   * Constructor.
   */
  private BathroomLightsRepository() {
    //empty
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized BathroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new BathroomLightsRepository();
          BathroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  /**
   * Sets the lighting level.
   * @param level the new level.
   */
  public synchronized void setLevel(String level) {
    BathroomLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Gets the lighting level.
   * @return the current level.
   */
  public synchronized String getLevel() {
    return level;
  }
}
