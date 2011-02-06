package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface LightingDB {
  
  /**
   * Get the instance of this Lighting by Primary Key ID.
   *  
   * @param id String
   * @return Lighting
   */
  public Lighting getById(String id);

  /**
   * Get the instance of this Lighting by Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return Lighting
   */
  public Lighting getByTimeStamp(long timestamp);
  
  /**
   * Sets the status of the Lighting System.
   * 
   * @param status Integer
   */
  public void putStatus(int status);

  /**
   * Sets the profile of the Lighting System.
   * 
   * @param profile String
   */
  public void putProfile(String profile);
  
  /**
   * Sets the status of the Lighting System Room.
   * 
   * @param roomId String
   * @param status boolean
   */
  public void putRoomStatus(String roomId, boolean status);

  /**
   * Sets the color of the Lighting System Room.
   * 
   * @param roomId String
   * @param color double
   */
  public void putRoomColor(String roomId, double color);

  /**
   * Sets the lighting level of the Lighting System Room.
   * 
   * @param roomId String
   * @param level double
   */
  public void putRoomLevel(String roomId, double level);
}
