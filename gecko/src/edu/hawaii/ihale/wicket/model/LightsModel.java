package edu.hawaii.ihale.wicket.model;

/**
 * Property Model for the Lights System.
 *  
 * @author Bret Ikehara
 * @author Shoji Bravo
 */
public class LightsModel extends SystemModel {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -9210475158220196977L;

  private Long livingRoom;
  private Long diningRoom;
  private Long kitchenRoom;
  private Long bathroom;
  
  /**
   * Default Constructor.
   */
  public LightsModel() {
      this.livingRoom = 0L;
      this.diningRoom = 0L;
      this.kitchenRoom = 0L;
      this.bathroom = 0L;
  }
  
  /**
   * Gets this Lights model time stamp.
   * 
   * @return String
   */
  @Override
  public Long getTimestamp() {
    return this.timestamp;
  }

  /**
   * Sets this Lights model time stamp.
   * 
   * @param timestamp Long
   */
  @Override
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Gets this Lights for LivingRoom value.
   * 
   * @return long
   */
  public Long getLivingRoom() {
    return livingRoom;
  }
  
  /**
   * Sets this Lights model LivingRoom value.
   * 
   * @param livingRoom Long
   */
  public void setLivingRoom(Long livingRoom) {
    this.livingRoom = livingRoom;
  }

  /**
   * Gets this Lights for DiningRoom value.
   * 
   * @return long
   */
  public Long getDiningRoom() {
    return diningRoom;
  }
  
  /**
   * Sets this Lights model DiningRoom value.
   * 
   * @param diningRoom Long
   */
  public void setDiningRoom(Long diningRoom) {
    this.diningRoom = diningRoom;
  }
  
  /**
   * Gets this Lights for Kitchen value.
   * 
   * @return long
   */
  public Long getKitchenRoom() {
    return kitchenRoom;
  }
  
  /**
   * Sets this Lights model KitchenRoom value.
   * 
   * @param kitchenRoom Long
   */
  public void setKitchenRoom(Long kitchenRoom) {
    this.kitchenRoom = kitchenRoom;
  }
  /**
   * Gets this Lights for BathRoom value.
   * 
   * @return long
   */
  public Long getBathroom() {
    return bathroom;
  }
  
  /**
   * Sets this Lights model Bathroom value.
   * 
   * @param bathRoom Long
   */
  public void setBathroom(Long bathRoom) {
    this.bathroom = bathRoom;
  }
}
