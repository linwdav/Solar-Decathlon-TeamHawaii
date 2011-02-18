package edu.hawaii.ihale.ui.page.electricity;

import java.io.Serializable;

/**
 * Holds the details for the constructor.
 * 
 * @author Bret Ikehara
 */
public class Electricity implements Serializable {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  private String appliance;
  private double usage;
  private double level;
  private double limit;

  /**
   * Holds the details for an electric appliance.
   * 
   * @param appliance String
   * @param usage double
   * @param level double
   * @param limit double
   */
  public Electricity(String appliance, double usage, double level, double limit) {
    this.appliance = appliance;
    this.usage = usage;
    this.level = level;
    this.limit = limit;
  }

  /**
   * Gets the appliance.
   * 
   * @return String
   */
  public String getAppliance() {
    return appliance;
  }

  /**
   * Sets the appliance.
   * 
   * @param appliance String
   */
  public void setAppliance(String appliance) {
    this.appliance = appliance;
  }

  /**
   * Gets the electricity usage.
   * 
   * @return double
   */
  public double getUsage() {
    return usage;
  }

  /**
   * Sets the electricity usage.
   * 
   * @param usage double
   */
  public void setUsage(double usage) {
    this.usage = usage;
  }

  /**
   * Gets the electricity consumption level.
   * 
   * @return level double
   */
  public double getLevel() {
    return level;
  }

  /**
   * Sets the electricity consumption level.
   * 
   * @param level double
   */
  public void setLevel(double level) {
    this.level = level;
  }

  /**
   * Gets the electricity consumption limit.
   * 
   * @return double
   */
  public double getLimit() {
    return limit;
  }

  /**
   * Sets the electricity consumption limit.
   * 
   * @param limit double
   */
  public void setLimit(double limit) {
    this.limit = limit;
  }
}
