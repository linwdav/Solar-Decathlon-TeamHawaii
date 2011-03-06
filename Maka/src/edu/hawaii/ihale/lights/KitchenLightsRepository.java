package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
public class KitchenLightsRepository {
  private static KitchenLightsRepository instance = null;
  private static String level;
  
  /**
   * Constructor.
   */
  private KitchenLightsRepository() {
    //empty
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized KitchenLightsRepository getInstance() {
      if (instance == null) {
          instance = new KitchenLightsRepository();
          KitchenLightsRepository.level = "79.4";
      }
      return instance;
  }

  /**
   * Sets the lighting level.
   * @param level the new lighting level.
   */
  public synchronized void setLevel(String level) {
    KitchenLightsRepository.level = level;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Returns the current lighting level.
   * @return the current ligthing level.
   */
  public synchronized String getLevel() {
    return level;
  }
}
