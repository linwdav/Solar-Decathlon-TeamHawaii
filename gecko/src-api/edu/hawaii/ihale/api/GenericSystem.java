package edu.hawaii.ihale.api;

/**
 * Generic class for implementing the database objects.
 * 
 * @author Team Hawaii
 */
public class GenericSystem extends GenericSystemADT {
  
  /**
   * String for Alive status.
   */
  public static final String ALIVE = "alive";
  
  /**
   * Integer for Alive status.
   */
  public static final int ALIVE_INT = 1;
  
  /**
   * String for down status.
   */
  public static final String DOWN = "down";
  
  /**
   * Integer for down status.
   */
  public static final int DOWN_INT = 0;
  
  /**
   * String for maintenance status.
   */
  public static final String MAINTENANCE = "maintenance";
  
  /**
   * Integer for maintenance status.
   */
  public static final int MAINTENANCE_INT = 2;
  
  protected int status;
  protected long timestamp;
  protected String desc;
  protected String title;
  
  /**
   * Default Constructor.
   * 
   * @param title String
   * @param desc String
   * @param timestamp long
   * @param status int
   */
  public GenericSystem(String title, String desc, long timestamp, int status) {
    this.title = title;
    this.desc = desc;
    this.timestamp = timestamp;
    this.status = status;
  }

  /**
   * Gets this System status.
   * 
   * @return int
   */
  @Override
  public int getStatus() {
    return this.status;
  }

  /**
   * Gets this System time stamp.
   * 
   * @return long
   */
  @Override
  public long getTimeStamp() {
    return this.timestamp;
  }

  /**
   * Gets this System title.
   * 
   * @return String
   */
  @Override
  public String getTitle() {
    return this.title;
  }

  /**
   * Gets this System description.
   * 
   * @return String
   */
  @Override
  public String getDescription() {
    return this.desc;
  }

  /**
   * Gets this System data as a String value.
   * 
   * @return String
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.title);
    sb.append(" Description: ").append(this.desc);
    sb.append(" Timestamp: ").append(this.timestamp);
    sb.append(" Status: ").append(this.status);
    return sb.toString();
  }
}
