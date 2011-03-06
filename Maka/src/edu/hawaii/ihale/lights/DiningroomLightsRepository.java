package edu.hawaii.ihale.lights;

/**
 * Singleton object to store data for the Lighting system.
 * @author Team Maka
 *
 */
public class DiningroomLightsRepository {
  private static DiningroomLightsRepository instance = null;
  private static String level;
  
  /**
   * Constructor.
   */
  private DiningroomLightsRepository() {
    
  }
  
  /**
   * Returns the singleton instance.
   * @return the singletong instance.
   */
  public static synchronized DiningroomLightsRepository getInstance() {
      if (instance == null) {
          instance = new DiningroomLightsRepository();
          DiningroomLightsRepository.level = "79.4";
      }
      return instance;
  }

  /**
   * Sets the lighting level.
   * @param level the new lighting level.
   */
  public synchronized void setLevel(String level) {
    DiningroomLightsRepository.level = level;
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
