package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the EGauge System class to hold all information
 * regarding the component power.
 * 
 * @author Team Hawaii
 */
public class EGaugeCPower {

  private String src;
  private double i;
  private double u;
  private double cpower;

  /**
   * Default Constructor. Specific details can be found here: <a
   * href="http://www.egauge.net/docs/egauge-xml-api.pdf">EGauge API</a>.
   * 
   * @param src String
   * @param i double
   * @param u double
   * @param cpower double
   */
  public EGaugeCPower(String src, double i, double u, double cpower) {
    this.src = src;
    this.i = i;
    this.u = u;
    this.cpower = cpower;
  }

  /**
   * Gets this EGaugeCPower source.
   * 
   * @return String
   */
  public String getSrc() {
    return src;
  }

  /**
   * Gets this EGaugeCPower I value.
   * 
   * @return double
   */
  public double getI() {
    return i;
  }

  /**
   * Gets this EGaugeCPower U.
   * 
   * @return double
   */
  public double getU() {
    return u;
  }

  /**
   * Gets this EGaugeCPower component power.
   * 
   * @return double
   */
  public double getCpower() {
    return cpower;
  }
}
