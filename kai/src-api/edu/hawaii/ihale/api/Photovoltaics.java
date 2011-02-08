package edu.hawaii.ihale.api;


/**
 * Provides a record of meter readings for the photovoltaics system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class Photovoltaics {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage/generation of the system. */
  private double currentPower;
  /** The state of the photovoltaics system. */
  private String photovoltaicsSystem;
  /** The energy being generated in kWh. */
  private double energy;
  /** The energy being generated in Ws. */
  private double energyWs;

  /**
   * Creates a photovoltaics object for storing readings.
   * @param timestamp the timestamp when the reading was taken
   * @param currentPower the current power generation of the system 
   * @param photovoltaicsSystem the state of the system
   * @param energy the energy being produced in kWh
   * @param energyWs the energy being produced in Ws
   */
  public Photovoltaics(float timestamp, double currentPower, 
          String photovoltaicsSystem, double energy, double energyWs) {
    this.timestamp = timestamp;
    this.currentPower = currentPower;
    this.photovoltaicsSystem = photovoltaicsSystem;
    this.energy = energy;
    this.energyWs = energyWs;
  }
  
  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Photovoltaics State: %s " +
                   "Energy(kWh): %s Energy(Ws): %s]", this.timestamp, this.currentPower, 
                   this.photovoltaicsSystem, this.energy, this.energyWs);
  }

  /** 
   * Sets the state of the system.
   * @param photovoltaicsSystem the current state of the system
   */
  public void setPhotovoltaicsSystem(String photovoltaicsSystem) {
    this.photovoltaicsSystem = photovoltaicsSystem;
  }

  /**
   * Gets the state of the system.
   * @return photovoltaicsSystem
   */
  public String getPhotovoltaicsSystem() {
    return photovoltaicsSystem;
  }

  /**
   * Sets the Energy reading.
   * @param energy the current energy reading
   */
  public void setEnergy(double energy) {
    this.energy = energy;
  }

  /**
   * Gets the energy reading.
   * @return energy
   */
  public double getEnergy() {
    return energy;
  }

  /**
   * Sets the energyWs reading.
   * @param energyWs the current energyWs reading
   */
  public void setEnergyWs(double energyWs) {
    this.energyWs = energyWs;
  }

  /** 
   * Gets the energyWs reading.
   * @return energyWs
   */
  public double getEnergyWs() {
    return energyWs;
  }
  
}
