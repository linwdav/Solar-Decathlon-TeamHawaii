package edu.hawaii.ihale.electrical;

public class EnergyRepository {
  private static EnergyRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String energy,power,joules;
  private EnergyRepository() {
    
  }
  
  public static synchronized EnergyRepository getInstance() {
      if (instance == null) {
          instance = new EnergyRepository();
          EnergyRepository.energy = "1443.5";
          EnergyRepository.power = "2226.2";
          EnergyRepository.joules = "2130813014";
      }
      return instance;
  }

  public void setEnergy(String energy) {
    EnergyRepository.energy = energy;
  }

  public  String getEnergy() {
    return energy;
  }

  public synchronized void setPower(String power) {
    EnergyRepository.power = power;
  }

  public String getPower() {
    return power;
  }

  public synchronized void setJoules(String watts) {
    EnergyRepository.joules = watts;
  }

  public String getJoules() {
    return joules;
  }
}
