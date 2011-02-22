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


  private Long LivingRoom;
  private Long DiningRoom;
  private Long KitchenRoom;
  private Long Bathroom;
  
  /**
   * Default Constructor.
   */
  public LightsModel() {
      this.LivingRoom = (long) 0;
      this.DiningRoom = (long) 0;
      this.KitchenRoom = (long) 0;
      this.Bathroom = (long) 0;

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
    return LivingRoom;
  }
  
  /**
   * Sets this Lights model LivingRoom value.
   * 
   * @param livingRoom Long
   */
  public void setLivingRoom(Long livingRoom) {
    this.LivingRoom = livingRoom;
  }

  /**
   * Gets this Lights for DiningRoom value.
   * 
   * @return long
   */
  public Long getDiningRoom() {
    return DiningRoom;
  }
  
  /**
   * Sets this Lights model DiningRoom value.
   * 
   * @param diningRoom Long
   */
  public void setDiningRoom(Long diningRoom) {
    this.DiningRoom = diningRoom;
  }
  
  /**
   * Gets this Lights for Kitchen value.
   * 
   * @return long
   */
  public Long getKitchenRoom() {
    return KitchenRoom;
  }
  
  /**
   * Sets this Lights model KitchenRoom value.
   * 
   * @param kitchenRoom Long
   */
  public void setKitchenRoom(Long kitchenRoom) {
    this.KitchenRoom = kitchenRoom;
  }
  /**
   * Gets this Lights for BathRoom value.
   * 
   * @return long
   */
  public Long getBathroom() {
    return Bathroom;
  }
  
  /**
   * Sets this Lights model Bathroom value.
   * 
   * @param bathRoom Long
   */
  public void setBathroom(Long bathRoom) {
    this.Bathroom = bathRoom;
  }
}
