package edu.hawaii.ihale.api;

/**
 * ADT to store information about the Lighting system.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class Lighting {

  /** Fields for the system. */
  private float patio;
  private float livingRoom;
  private float kitchen;
  private float bedroomA;
  private float bedroomB;
  private long lastUpdated;

  /**
   * Default constructor.
   * 
   * @param patio Brightness of LED lights in the patio.
   * @param livingRoom Brightness of LED lights in the living room.
   * @param kitchen Brightness of LED lights in the kitchen.
   * @param bedroomA Brightness of LED lights in the bedroom A.
   * @param bedroomB Brightness of LED lights in the bedroom B.
   * @param lastUpdated The timestamp for the last update.
   */
  public Lighting(float patio, float livingRoom, float kitchen, float bedroomA, float bedroomB,
      long lastUpdated) {
    this.patio = patio;
    this.livingRoom = livingRoom;
    this.kitchen = kitchen;
    this.bedroomA = bedroomA;
    this.bedroomB = bedroomB;
    this.lastUpdated = lastUpdated;
  }

  /**
   * Return the brightness of LED lights in the patio.
   * 
   * @return the patio
   */
  public float getPatio() {
    return patio;
  }

  /**
   * Return the brightness of LED lights in the living room.
   * 
   * @return the livingRoom
   */
  public float getLivingRoom() {
    return livingRoom;
  }

  /**
   * Return the brightness of LED lights in the kitchen.
   * 
   * @return the livingRoom
   */
  public float getKitchen() {
    return kitchen;
  }

  /**
   * Return the brightness of LED lights in bedroom A.
   * 
   * @return the bedroomA
   */
  public float getBedroomA() {
    return bedroomA;
  }

  /**
   * Return the brightness of LED lights in bedroom B.
   * 
   * @return the bedroomB
   */
  public float getBedroomB() {
    return bedroomB;
  }

  /**
   * Return the timestamp for the last update.
   * 
   * @return the lastUpdated
   */
  public long getLastUpdated() {
    return lastUpdated;
  }
}
