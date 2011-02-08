package edu.hawaii.ihale.api;


/**
 * Provides a record of meter readings for the security system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class Security {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage of the system. */
  private double currentPower;
  /** The state of the security system. */
  private String securityState;
  /** The state of the cameras. */
  private String cameras;
  /** The state of the motion detection. */
  private String motionDetection;
  /** The state of the alarm. */
  private String alarm;
  
  /**
   * Creates a new security object to store readings.
   * @param timestamp the timestamp when the reading was taken
   * @param currentPower the current power usage of the system
   * @param securityState the state of the security system
   * @param cameras the state of the cameras
   * @param motionDetection the state of the motion detection 
   * @param alarm the state of the alarm system
   */
  public Security(float timestamp, double currentPower, String securityState,
           String cameras, String motionDetection, String alarm) {
      this.timestamp = timestamp;
      this.currentPower = currentPower; 
      this.securityState = securityState;
      this.cameras = cameras;
      this.motionDetection = motionDetection;
      this.alarm = alarm;
  }
  
  
  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Security State: %s " +
                   "Cameras: %s Motion Detection: %s Alarm: %s]", this.timestamp, 
                   this.currentPower, this.securityState, this.cameras, this.motionDetection,
                   this.alarm);
  }

/**
 * Sets the timestamp for the aquaponics object.
 * @param timestamp the time when reading was taken
 */
  public void setTimestamp(float timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Returns the timestamp of when readings were taken.
   * @return timestamp
   */
  public float getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the currentPower usage.
   * @param currentPower the current power usage reading
   */
  public void setCurrentPower(double currentPower) {
    this.currentPower = currentPower;
  }

  /**
   * Gets the currentPower usage.
   * @return currentPower
   */
  public double getCurrentPower() {
    return currentPower;
  }

  /**
   * Sets the state of the system.
   * @param securityState the state of the system
   */
  public void setSecurityState(String securityState) {
    this.securityState = securityState;
  }

  /**
   * Gets the state of the system.
   * @return securityState
   */
  public String getSecurityState() {
    return securityState;
  }

  /**
   * Sets the state of the cameras.
   * @param cameras the state of the cameras
   */
  public void setCameras(String cameras) {
    this.cameras = cameras;
  }

  /**
   * Gets the state of the cameras.
   * @return cameras
   */
  public String getCameras() {
    return cameras;
  }

  /**
   * Sets the state of the motion detection system.
   * @param motionDetection the state of the motion detection system
   */
  public void setMotionDetection(String motionDetection) {
    this.motionDetection = motionDetection;
  }

  /**
   * Gets the state of the motion detection system.
   * @return motionDetection
   */
  public String getMotionDetection() {
    return motionDetection;
  }
  
  /**
   * Sets the state of the alarm system.
   * @param alarm the state of the alarm system.
   */
  public void setAlarm(String alarm) {
    this.alarm = alarm;
  }

  /**
   * Gets the state of the alarm system.
   * @return alarm
   */
  public String getAlarm() {
    return alarm;
  }

}
