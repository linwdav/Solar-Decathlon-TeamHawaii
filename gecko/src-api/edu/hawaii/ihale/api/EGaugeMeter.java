package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the EGauge System class to hold all information
 * regarding the components that are metered at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class EGaugeMeter {

  private String title;
  private double energy;
  private double energyWs;
  private double power;

  /**
   * Default Constructor. Specific details can be found here: <a
   *  href="http://www.egauge.net/docs/egauge-xml-api.pdf">EGauge API</a>.
   * 
   * @param title String
   * @param energy double
   * @param energyWs double
   * @param power double
   */
  public EGaugeMeter(String title, double energy, double energyWs,
      double power) {
    this.title = title;
    this.energy = energy;
    this.energyWs = energyWs;
    this.power = power;
  }

  /**
   * Gets this EGaugeMeter source.
   * 
   * @return String
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets this EGaugeMeter cumulative energy as kilowatt per hour.
   * 
   * @return double
   */
  public double getEnergy() {
    return energy;
  }

  /**
   * Gets this EGaugeMeter energy as Watt-seconds.
   * 
   * @return double
   */
  public double getEnergyWs() {
    return energyWs;
  }

  /**
   * Gets this EGaugeMeter average power.
   * 
   * @return double
   */
  public double getPower() {
    return power;
  }

}
