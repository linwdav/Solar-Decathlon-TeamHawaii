package edu.hawaii.ihale.aquaponics;

public class AquaponicsRepository {
  private static AquaponicsRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String pH, temp, oxygen;

  private AquaponicsRepository() {
    
  }
  
  public static synchronized AquaponicsRepository getInstance() {
      if (instance == null) {
          instance = new AquaponicsRepository();
          AquaponicsRepository.pH = "8";
          AquaponicsRepository.temp = "78";
          AquaponicsRepository.oxygen = ".5";
      }
      return instance;
  }

  public synchronized void setpH(String pH) {
    AquaponicsRepository.pH = pH;
  }

  public synchronized String getpH() {
    return pH;
  }

  public synchronized void setTemp(String temp) {
    AquaponicsRepository.temp = temp;
  }

  public synchronized String getTemp() {
    return temp;
  }

  public synchronized void setOxygen(String oxygen) {
    AquaponicsRepository.oxygen = oxygen;
  }

  public synchronized String getOxygen() {
    return oxygen;
  }
}
