package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
public class BathroomLightsRepository {
  private static BathroomLightsRepository instance = null;
  private static String newLevel;
  
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
          newLevel = "79.4";
      }
      return instance;
  }

  /**
   * Sets the lighting level.
   * @param level the new level.
   */
  public static synchronized void setLevel(String level) {
    newLevel = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Gets the lighting level.
   * @return the current level.
   */
  public static synchronized String getLevel() {
    return newLevel;
  }
}
