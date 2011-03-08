package edu.hawaii.ihale.aquaponics;

/**
 * Singleton object to store data for the Aquaponics system.
 * @author Team Maka
 *
 */
public class AquaponicsRepository {
  private static AquaponicsRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String pH, temp, oxygen;
  private static double goalpH, goalTemp, goalOxygen;

  /**
   * Constructor.
   */
  private AquaponicsRepository() {
    
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized AquaponicsRepository getInstance() {
      if (instance == null) {
          instance = new AquaponicsRepository();
          AquaponicsRepository.pH = "8";
          AquaponicsRepository.temp = "78";
          AquaponicsRepository.oxygen = ".5";
      }
      return instance;
  }

  /**
   * Sets the ph.
   * @param pH the new ph.
   */
  public static synchronized void setpH(String pH) {
    AquaponicsRepository.pH = pH;
  }

  /**
   * Gets the current pH.
   * @return the current ph.
   */
  public static synchronized String getpH() {
    return pH;
  }

  /**
   * Sets the temp.
   * @param temp the new temp.
   */
  public static synchronized void setTemp(String temp) {
    AquaponicsRepository.temp = temp;
  }

  /**
   * Gets the temp.
   * @return the current temp.
   */
  public static synchronized String getTemp() {
    return temp;
  }

  /**
   * Sets the oxygen level.
   * @param oxygen the new oxygen level.
   */
  public static synchronized void setOxygen(String oxygen) {
    AquaponicsRepository.oxygen = oxygen;
  }

  /**
   * Returns the current oxygen level.
   * @return the current oxygen level.
   */
  public static synchronized String getOxygen() {
    return oxygen;
  }

  /**
   * @param goalpH the goalpH to set
   */
  public static void setGoalpH(Double goalpH) {
    AquaponicsRepository.goalpH = goalpH;
  }

  /**
   * @return the goalpH
   */
  public static Double getGoalpH() {
    return goalpH;
  }

  /**
   * @param goalTemp the goalTemp to set
   */
  public static void setGoalTemp(Double goalTemp) {
    AquaponicsRepository.goalTemp = goalTemp;
  }

  /**
   * @return the goalTemp
   */
  public static Double getGoalTemp() {
    return goalTemp;
  }

  /**
   * @param goalOxygen the goalOxygen to set
   */
  public static void setGoalOxygen(Double goalOxygen) {
    AquaponicsRepository.goalOxygen = goalOxygen;
  }

  /**
   * @return the goalOxygen
   */
  public static Double getGoalOxygen() {
    return goalOxygen;
  }
}
