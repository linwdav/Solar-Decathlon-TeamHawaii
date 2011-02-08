package edu.hawaii.ihale.api;


/**
 * Provides a record of meter readings for the Lighting system.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public class Lighting {
  /** The timestamp when the reading was taken also unique ID. */
  private float timestamp;
  /** The current power usage of the system. */
  private double currentPower;
  /** State of the lights, on or off. */
  private String lightingSystem;
  /** Mode of the lights. */
  private String lightingMode;
  /** Bedroom's lighting brightness. */
  private double bedroom;
  /** Natural Space's lighting brightness. */
  private double naturalSpace;
  /** Bathroom's lighting brightness. */
  private double bathroom;
  /** Kitchen's lighting brightness. */
  private double kitchen;
  /** Living Room's lighting brightness. */
  private double livingroom;
  /** Porch lighting brightness. */
  private double porch;
  
  /**
   * Creates a lighting object given the required parameters.
   * @param timestamp the timestamp when readings were taken
   * @param currentPower the current power usage of the system
   * @param lightingSystem the state of the lighting system
   * @param lightingMode the mode of the lighting system
   * @param bedroom the brightness in the bedroom
   * @param naturalSpace the brightness in the natural space
   * @param bathroom the brightness in the bathroom
   * @param kitchen the brightness in the kitchen
   * @param livingroom the brightness in the livingroom
   * @param porch the brightness on the porch
   */
  public Lighting (float timestamp, double currentPower, String lightingSystem, 
          String lightingMode, double bedroom, double naturalSpace, double bathroom,
          double kitchen, double livingroom, double porch) {
    this.timestamp = timestamp;
    this.currentPower = currentPower;
    this.lightingSystem = lightingSystem;
    this.lightingMode = lightingMode;
    this.bedroom = bedroom;
    this.naturalSpace = naturalSpace;
    this.bathroom = bathroom;
    this.kitchen = kitchen;
    this.livingroom = livingroom;
    this.porch = porch;
  }

  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[Timestamp: %s Current Power: %s Lighting State: %s Lighting Mode: %s" +
                   " Bedroom: %s Natural Space: %s Bathroom: %s Kitchen: %s Living Room: %s " +
                   "Porch: %s]", this.timestamp, this.currentPower, 
                    this.lightingSystem, this.lightingMode, this.bedroom, this.naturalSpace, 
                    this.bathroom, this.kitchen, this.livingroom, this.porch);
  }

/**
 * Sets the timestamp for the lighting object.
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
   * Sets the lightingSystem state.
   * @param lightingSystem the current state of the lighting system
   */
  public void setLightingSystem(String lightingSystem) {
    this.lightingSystem = lightingSystem;
  }

  /**
   * Gets the lightingSystem state.
   * @return lightingSystem
   */
  public String getLightingSystem() {
    return lightingSystem;
  }

  /**
   * Sets the lightingMode.
   * @param lightingMode the current lightingMode
   */
  public void setLightingMode(String lightingMode) {
    this.lightingMode = lightingMode;
  }

  /**
   * Gets the current lightingMode.
   * @return lightingMode
   */
  public String getLightingMode() {
    return lightingMode;
  }

  /**
   * Sets the current bedroom lighting brightness level.
   * @param bedroom stores the current lighting brightness level
   */
  public void setBedroom(double bedroom) {
    this.bedroom = bedroom;
  }

  /**
   * Gets the current bedroom lighting brightness level. 
   * @return bedroom
   */
  public double getBedroom() {
    return bedroom;
  }

  /**
   * Sets the current naturalSpace lighting brightness level.
   * @param naturalSpace stores the current lighting brightness level
   */
  public void setNaturalspace(double naturalSpace) {
    this.naturalSpace = naturalSpace;
  }

  /**
   * Gets the current naturalSpace lighting brightness level. 
   * @return naturalSpace
   */
  public double getNaturalSpace() {
    return naturalSpace;
  }

  /**
   * Sets the current bathroom lighting brightness level.
   * @param bathroom stores the current lighting brightness level
   */
  public void setBathroom(double bathroom) {
    this.bathroom = bathroom;
  }

  /**
   * Gets the current bathroom lighting brightness level. 
   * @return bathroom
   */
  public double getBathroom() {
    return bathroom;
  }

  /**
   * Sets the current kitchen lighting brightness level.
   * @param kitchen stores the current lighting brightness level
   */
  public void setKitchen(double kitchen) {
    this.kitchen = kitchen;
  }

  /**
   * Gets the current kitchen lighting brightness level. 
   * @return kitchen
   */
  public double getKitchen() {
    return kitchen;
  }

  /**
   * Sets the current livingroom lighting brightness level.
   * @param livingroom stores the current lighting brightness level
   */
  public void setLivingroom(double livingroom) {
    this.livingroom = livingroom;
  }

  /**
   * Gets the current livingroom lighting brightness level. 
   * @return livingroom
   */
  public double getLivingroom() {
    return livingroom;
  }

  /**
   * Sets the current porch lighting brightness level.
   * @param porch stores the current lighting brightness level
   */
  public void setPorch(double porch) {
    this.porch = porch;
  }

  /**
   * Gets the current porch lighting brightness level. 
   * @return porch
   */
  public double getPorch() {
    return porch;
  }
  
}
