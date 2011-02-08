package edu.hawaii.ihale.api;

/**
 * This class extends the GenericSystem to create the Aquaponics System class to hold all
 * information regarding the Aquaponics at a certain time stamp.
 * 
 * @author Team Hawaii
 */
public class Photovoltaics extends GenericSystem {

  private double direction;
  private double angle;

  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   * @param direction double
   * @param angle double
   */
  public Photovoltaics(String title, String desc, long timestamp, int status, double direction,
      double angle) {
    super(title, desc, timestamp, status);
    this.direction = direction;
    this.angle = angle;
  }

  /**
   * Get this System's photovoltaic panel heading direction.
   * 
   * @return double
   */
  public double getDirection() {
    return direction;
  }

  /**
   * Get this System's photovoltaic panel heading angle.
   * 
   * @return double
   */
  public double getAngle() {
    return angle;
  }
}
