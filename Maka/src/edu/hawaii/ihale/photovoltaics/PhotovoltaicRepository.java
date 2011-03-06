package edu.hawaii.ihale.photovoltaics;

public class PhotovoltaicRepository {
  private static PhotovoltaicRepository instance = null;
  //private double goalPH = 7, goalTemp = 78, goalOxygen = .5;
  private static String energy,power,joules;
  private PhotovoltaicRepository() {
    
  }
  
  public static synchronized PhotovoltaicRepository getInstance() {
      if (instance == null) {
          instance = new PhotovoltaicRepository();
          PhotovoltaicRepository.energy = "1443.5";
          PhotovoltaicRepository.power = "2226.2";
          PhotovoltaicRepository.joules = "2130813014";
      }
      return instance;
  }

  public void setEnergy(String energy) {
    PhotovoltaicRepository.energy = energy;
  }

  public  String getEnergy() {
    return energy;
  }

  public synchronized void setPower(String power) {
    PhotovoltaicRepository.power = power;
  }

  public String getPower() {
    return power;
  }

  public synchronized void setJoules(String joules) {
    PhotovoltaicRepository.joules = joules;
  }

  public String getJoules() {
    return joules;
  }
}
