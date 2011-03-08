package edu.hawaii.ihale.photovoltaics;

/**
 * Singleton object to store data for the Photovoltaics system.
 * @author Team Maka
 *
 */

public class PhotovoltaicRepository {
  private static PhotovoltaicRepository instance = null;
  private static String energy,power,joules;
  /**
   * Constructor.
   */
  private PhotovoltaicRepository() {
    
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized PhotovoltaicRepository getInstance() {
      if (instance == null) {
          instance = new PhotovoltaicRepository();
          PhotovoltaicRepository.energy = "1443.5";
          PhotovoltaicRepository.power = "2226.2";
          PhotovoltaicRepository.joules = "2130813014";
      }
      return instance;
  }

  /**
   * Sets the energy level.
   * @param energy the energy level.
   */
  public static synchronized void setEnergy(String energy) {
    PhotovoltaicRepository.energy = energy;
  }
  
  /**
   * Returns the current energy.
   * @return the current engery.
   */
  public static synchronized String getEnergy() {
    return energy;
  }

  /**
   * Sets the power level.
   * @param power the new power level.
   */
  public static synchronized void setPower(String power) {
    PhotovoltaicRepository.power = power;
  }

  /**
   * Returs the current power level.
   * @return the current power level.
   */
  public static synchronized String getPower() {
    return power;
  }

  /**
   * Sets the joules value.
   * @param joules the new joules value.
   */
  public static synchronized void setJoules(String joules) {
    PhotovoltaicRepository.joules = joules;
  }

  /**
   * Returns the current joules value.
   * @return the current joules value.
   */
  public static synchronized String getJoules() {
    return joules;
  }
}
