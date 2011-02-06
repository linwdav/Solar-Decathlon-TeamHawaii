package edu.hawaii.ihale.api;

/**
 * Provides information about photovoltaics system of the house.
 * 
 * @author Team Kohola
 */
public class Photovoltaics {

  private String timestamp;
  private long energyGeneration;
  private long energyConsumption;

  /**
   * Create a photovoltaics instance given its fields.
   * 
   * @param timestamp The timestamp reading sent by the eGauge sensor.
   * @param energyGeneration The energy generation reading sent by the eGauge sensor in KWh.
   * @param energyConsumption The energy consumption reading sent by the eGauge sensor in KWh.
   */
  public Photovoltaics(String timestamp, long energyGeneration, long energyConsumption) {
    this.timestamp = timestamp;
    this.energyGeneration = energyGeneration;
    this.energyConsumption = energyConsumption;
  }

  /**
   * Returns the timestamp associated with this instance.
   * 
   * @return The timestamp.
   */
  public String getTimestamp() {
    return this.timestamp;
  }

  /**
   * Returns the energy generation associated with this instance.
   * 
   * @return The energy generation in KWh.
   */
  public long getEnergyGeneration() {
    return this.energyGeneration;
  }

  /**
   * Returns the energy consumption associated with this instance.
   * 
   * @return The energy consumption in KWh.
   */
  public long getEnergyConsumption() {
    return this.energyConsumption;
  }

}
