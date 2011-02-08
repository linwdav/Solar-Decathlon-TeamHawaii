package edu.hawaii.ihale.api;

import java.util.List;

/**
 * This class extends the GenericSystem to create the EGauge System class to hold all information
 * regarding the electicity meter at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class EGauge extends GenericSystem {

  List<EGaugeCPower> cpower;
  List<EGaugeMeter> meter;
  private double freq;
  private List<Double> volt;
  private List<Double> curr;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param cpower List<EGaugeCPower>
   * @param meter List<EGaugeMeter>
   * @param frequency double
   * @param voltage List<Double>
   * @param current List<Double>
   */
  public EGauge(String title, String desc, long timestamp, List<EGaugeCPower> cpower,
      List<EGaugeMeter> meter, double frequency, List<Double> voltage, List<Double> current) {
    super(title, desc, timestamp, 1);
    this.meter = meter;
    this.cpower = cpower;
    this.freq = frequency;
    this.volt = voltage;
    this.curr = current;
  }

  /**
   * Gets this EGauge components power.
   * 
   * @return List<EGaugeCPower>
   */
  public List<EGaugeCPower> getCpower() {
    return cpower;
  }

  /**
   * Gets this EGauge components that are metered.
   * 
   * @return List<EGaugeComponent>
   */
  public List<EGaugeMeter> getMeter() {
    return meter;
  }

  /**
   * Gets this EGauge frequency.
   * 
   * @return double
   */
  public double getFrequency() {
    return freq;
  }

  /**
   * Gets this EGuage voltage.
   * 
   * @return List<Double>
   */
  public List<Double> getVoltage() {
    return volt;
  }

  /**
   * Gets this EGauge Current.
   * 
   * @return List<Double>
   */
  public List<Double> getCurrent() {
    return curr;
  }
}
