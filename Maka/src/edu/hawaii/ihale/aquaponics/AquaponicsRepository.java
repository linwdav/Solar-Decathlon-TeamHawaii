package edu.hawaii.ihale.aquaponics;

/**
 * Singleton object to store data for the Aquaponics system.
 * @author Team Maka
 *
 */
@edu.umd.cs.findbugs.annotations.SuppressWarnings(value =
  "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
  justification = "Singleton data storage class.")
public class AquaponicsRepository {
  private static AquaponicsRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String pH, temp, oxygen;

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
  public synchronized void setpH(String pH) {
    AquaponicsRepository.pH = pH;
  }

  /**
   * Gets the current pH.
   * @return the current ph.
   */
  public synchronized String getpH() {
    return pH;
  }

  /**
   * Sets the temp.
   * @param temp the new temp.
   */
  public synchronized void setTemp(String temp) {
    AquaponicsRepository.temp = temp;
  }

  /**
   * Gets the temp.
   * @return the current temp.
   */
  public synchronized String getTemp() {
    return temp;
  }

  /**
   * Sets the oxygen level.
   * @param oxygen the new oxygen level.
   */
  public synchronized void setOxygen(String oxygen) {
    AquaponicsRepository.oxygen = oxygen;
  }

  /**
   * Returns the current oxygen level.
   * @return the current oxygen level.
   */
  public synchronized String getOxygen() {
    return oxygen;
  }
}
