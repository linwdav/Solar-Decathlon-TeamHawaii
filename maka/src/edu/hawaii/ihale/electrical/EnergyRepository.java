package edu.hawaii.ihale.electrical;

/**
 * Singleton object to store data for the Electrical system.
 * @author Team Maka
 *
 */
public class EnergyRepository {
  private static EnergyRepository instance = null;
  private static String energy,power,joules;
  /**
   * Constructor.
   */
  private EnergyRepository() {
    //empty
  }
  
  /**
   * Returns the singleton instance.
   * @return the singleton instance.
   */
  public static synchronized EnergyRepository getInstance() {
      if (instance == null) {
        instance = new EnergyRepository();
        EnergyRepository.energy = "1443.5";
        EnergyRepository.power = "2226.2";
        EnergyRepository.joules = "2130813014";
      }
      return instance;
  }

  /**
   * Sets the energy level.
   * @param energy the new energy level.
   */
  public static synchronized void setEnergy(String energy) {
    EnergyRepository.energy = energy;
  }

  /**
   * Returns the current energy level.
   * @return the current energy level.
   */
  public static synchronized String getEnergy() {
    return energy;
  }

  /**
   * Sets the power value.
   * @param power the new power value.
   */
  public static synchronized void setPower(String power) {
    EnergyRepository.power = power;
  }

  /**
   * Returns the current power value.
   * @return the current power value.
   */
  public static synchronized String getPower() {
    return power;
  }

  /**
   * Sets the joules value.
   * @param watts the new joules value.
   */
  public static synchronized void setJoules(String watts) {
    EnergyRepository.joules = watts;
  }

  /**
   * Returns the current joules value.
   * @return the current joules value.
   */
  public static synchronized String getJoules() {
    return joules;
  }
}
