package edu.hawaii.ihale.hvac;

/**
 * Singleton object to store data for the HVAC system.
 * @author Team Maka
 *
 */
public class HVACRepository {
  private static HVACRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String temp;
  private static double goalTemp;
  /**
   * Constructor.
   */
  private HVACRepository() {
    //empty
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized HVACRepository getInstance() {
      if (instance == null) {
          instance = new HVACRepository();
          HVACRepository.temp = "79.4";
      }
      return instance;
  }

  /**
   * Sets the temperature in the system.
   * @param temp the new goal temp.
   */
  public static void setTemp(String temp) {
    HVACRepository.temp = temp;
    //valuesMap.put("temp", AquaponicsRepository.temp);
  }

  /**
   * Returns the temp of the system.
   * @return current temp.
   */
  public static synchronized String getTemp() {
    return "" + Double.valueOf(temp) + 10;
  }

  /**
   * @param goalTemp the goalTemp to set
   */
  public static void setGoalTemp(double goalTemp) {
    HVACRepository.goalTemp = goalTemp;
  }

  /**
   * @return the goalTemp
   */
  public static double getGoalTemp() {
    return goalTemp;
  }
}
